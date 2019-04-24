package com.see.rpc.nio.handler;

import com.see.rpc.nio.base.Message;
import com.see.rpc.nio.base.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 发送请求，接收响应
 */
public class RequestHandler extends SimpleChannelInboundHandler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) {
        if (StringUtils.isNotBlank(message.getId()) ){
            if(MessageFactory.contains(message.getId())) {
                logger.info("返回响应 ." + message.getId());
                MessageFactory.set(message);
            } else {
                logger.warn("Unknown Message !!");
            }
        } else {
            ctx.fireChannelRead(message);
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isWritable()) {
            //通道恢复可用，根据服务器拒绝策略 进行失败消息处理
            logger.info("channel resume write...............");
        } else {
            logger.info("channel is not writable.......");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (cause instanceof IOException) {
            logger.error("远程({})IO异常。{}", ctx.channel().remoteAddress(), cause.getMessage());
        } else {
            logger.error("还有什么异常会在这里出现({})?", ctx.channel().remoteAddress());
            super.exceptionCaught(ctx,cause);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error("channel失效({})执行重连操作，唤醒所有等待中的请求...",ctx.channel().remoteAddress());
        MessageFactory.destroy();
        super.channelInactive(ctx);
    }
}
