package com.see.rpc.nio.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HeartbeatServerHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatServerHandler.class);

    private static final long readerIdleTimeNanos = TimeUnit.SECONDS.toNanos(120);

    private ScheduledFuture<?> readerIdleTimeout;
    private long lastReadTime;

    private byte state; // 0 - none, 1 - initialized, 2 - destroyed
    private boolean reading;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        reading = true;
        ByteBuf byteBuf;
        if (msg instanceof ByteBuf && (byteBuf = (ByteBuf)msg).readableBytes() == 1 && byteBuf.getByte(0) == "i".getBytes()[0]){
            logger.debug("接收到检测心跳，回写心跳响应:{}", ctx.channel().remoteAddress());
            ctx.writeAndFlush(Unpooled.wrappedBuffer("o".getBytes()));
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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        destroy();
        logger.warn("rpc server channel inactive : {}<==>{}" , ctx.channel().localAddress(), ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        initialize(ctx);
        logger.info("rpc server channel active :  {}<==>{}" , ctx.channel().localAddress(), ctx.channel().remoteAddress());
        super.channelActive(ctx);
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

        lastReadTime = System.nanoTime();
        if (readerIdleTimeNanos > 0) {
            readerIdleTimeout = ctx.executor().schedule(new ReaderIdleTimeoutTask(ctx), readerIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
    }


    private void destroy() {
        state = 2;
        if (readerIdleTimeout != null) {
            readerIdleTimeout.cancel(false);
            readerIdleTimeout = null;
        }
    }

    private final class ReaderIdleTimeoutTask implements Runnable {

        private final ChannelHandlerContext ctx;

        ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (!ctx.channel().isOpen()) {
                return;
            }

            long nextDelay = readerIdleTimeNanos;
            if (!reading) {
                nextDelay -= System.nanoTime() - lastReadTime;
            }

            if (nextDelay <= 0) {
                logger.error("服务器超时未接收到客户端({})请求，关闭channel" ,ctx.channel().remoteAddress());
                ctx.channel().close();
            } else {
                // Read occurred before the timeout - set a new timeout with shorter delay.
                readerIdleTimeout = ctx.executor().schedule(this, nextDelay, TimeUnit.NANOSECONDS);
            }
        }
    }
}
