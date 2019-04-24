package com.see.rpc.nio.handler;

import com.see.rpc.common.serializer.SerializationUtil;
import com.see.rpc.nio.base.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.see.rpc.cache.ClientCache;
import com.see.rpc.nio.base.ServerInfo;

import java.net.InetSocketAddress;

/**
 * 发送请求，接收响应
 */
public class HealthClientHandler extends SimpleChannelInboundHandler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(HealthClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        try {
            ServerInfo serverInfo = (ServerInfo) SerializationUtil.deserialize(msg.getData());
            logger.info(">>>>>>>>远程服务状态变更为：" + serverInfo);
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            String host = address.getAddress().getHostAddress();
            int port = address.getPort();
            serverInfo.setAddress(host + ":" + port);
            ClientCache.setServerInfo(serverInfo);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.fireChannelRead(msg);
        }
    }

}
