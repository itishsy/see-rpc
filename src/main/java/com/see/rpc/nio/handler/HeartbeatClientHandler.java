package com.see.rpc.nio.handler;

import com.see.rpc.nio.Constants;
import com.see.rpc.nio.client.ReconnectExecutor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 客户端心跳处理器
 * 忽略写大数据超时触发情况，即IdleStateHandler.observeOutput=true的场景
 */
public class HeartbeatClientHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatClientHandler.class);

    private static final long readerIdleTimeNanos = TimeUnit.SECONDS.toNanos(20);
    private static final long writerIdleTimeNanos = TimeUnit.SECONDS.toNanos(10);

    private ScheduledFuture<?> readerIdleTimeout;
    private long lastReadTime;
    private int readTimeoutCount;

    private ScheduledFuture<?> writerIdleTimeout;
    private long lastWriteTime;

    private byte state; // 0 - none, 1 - initialized, 2 - destroyed
    private boolean reading;

    // Not create a new ChannelFutureListener per write operation to reduce GC pressure.
    private final ChannelFutureListener writeListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            lastWriteTime = System.nanoTime();
        }
    };

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        initialize(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        reading = true;
        readTimeoutCount = 0;
        ByteBuf byteBuf;
        if (msg instanceof ByteBuf && (byteBuf = (ByteBuf) msg).readableBytes() == 1 && byteBuf.getByte(0) == "o".getBytes()[0]) {
            logger.debug("收到服务器心跳响应");
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (reading) {
            lastReadTime = System.nanoTime();
            reading = false;
        }
        ctx.fireChannelReadComplete();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise).addListener(writeListener);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("channel inactive.......{}....{}....",ctx.channel().localAddress(),ctx.channel().remoteAddress());
        destroy();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        ReconnectExecutor.getInstance().reconnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
        super.channelInactive(ctx);
    }

    private void initialize(ChannelHandlerContext ctx) {
        // Avoid the case where destroy() is called before scheduling timeouts.
        // See: https://github.com/netty/netty/issues/143
        switch (state) {
            case 1:
            case 2:
                return;
        }

        state = 1;

        lastReadTime = lastWriteTime = System.nanoTime();
        if (readerIdleTimeNanos > 0) {
            readerIdleTimeout = ctx.executor().schedule(new ReaderIdleTimeoutTask(ctx), readerIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
        if (writerIdleTimeNanos > 0) {
            writerIdleTimeout = ctx.executor().schedule(new WriterIdleTimeoutTask(ctx), writerIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
    }

    private void destroy() {
        state = 2;
        if (readerIdleTimeout != null) {
            readerIdleTimeout.cancel(false);
            readerIdleTimeout = null;
        }
        if (writerIdleTimeout != null) {
            writerIdleTimeout.cancel(false);
            writerIdleTimeout = null;
        }
    }

    private abstract static class AbstractIdleTask implements Runnable {

        private final ChannelHandlerContext ctx;

        AbstractIdleTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (!ctx.channel().isOpen()) {
                return;
            }

            run(ctx);
        }

        protected abstract void run(ChannelHandlerContext ctx);
    }

    private final class ReaderIdleTimeoutTask extends AbstractIdleTask {

        ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
            super(ctx);
        }

        @Override
        protected void run(ChannelHandlerContext ctx) {
            long nextDelay = readerIdleTimeNanos;
            if (!reading) {
                nextDelay -= System.nanoTime() - lastReadTime;
            }

            if (nextDelay <= 0) {
                // Reader is idle - set a new timeout and notify the callback.
                readerIdleTimeout = ctx.executor().schedule(this, readerIdleTimeNanos, TimeUnit.NANOSECONDS);

                if((++readTimeoutCount) > Constants.START_RECONNECT_AFTER_LOST_HAERTBEAT_SIZE) {
                    logger.warn("连续{}次没收到服务器心跳响应，关闭当前channel,触发重连", Constants.START_RECONNECT_AFTER_LOST_HAERTBEAT_SIZE);
                    ctx.channel().close();
                }
            } else {
                // Read occurred before the timeout - set a new timeout with shorter delay.
                readerIdleTimeout = ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }

    private final class WriterIdleTimeoutTask extends AbstractIdleTask {

        WriterIdleTimeoutTask(ChannelHandlerContext ctx) {
            super(ctx);
        }

        @Override
        protected void run(ChannelHandlerContext ctx) {

            long lastWriteTime = HeartbeatClientHandler.this.lastWriteTime;
            long nextDelay = writerIdleTimeNanos - (System.nanoTime() - lastWriteTime);
            if (nextDelay <= 0) {
                // Writer is idle - set a new timeout and notify the callback.
                writerIdleTimeout = ctx.executor().schedule(this, writerIdleTimeNanos, TimeUnit.NANOSECONDS);

                logger.debug("发送检测心跳到服务器:{}", ctx.channel().remoteAddress());
                ctx.writeAndFlush(Unpooled.wrappedBuffer("i".getBytes()));
            } else {
                // Write occurred before the timeout - set a new timeout with shorter delay.
                writerIdleTimeout = ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }
}
