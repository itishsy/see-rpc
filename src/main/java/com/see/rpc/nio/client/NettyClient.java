package com.see.rpc.nio.client;

import com.see.rpc.common.exceptions.RpcException;
import com.see.rpc.common.serializer.SerializationUtil;
import com.see.rpc.nio.base.MessageFactory;
import com.see.rpc.nio.Client;
import com.see.rpc.nio.base.*;
import com.see.rpc.nio.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Administrator on 2018/12/8.
 */
public class NettyClient implements Client {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;

    private volatile Channel channel = null;

    private Bootstrap bootstrap = null;

    private EventLoopGroup eventLoopGroup = null;

    public NettyClient(String address){
        String[] array = address.split(":");
        this.host = array[0];
        this.port = Integer.parseInt(array[1]);
        init();
        connect();
    }

    private void init(){
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_REUSEADDR, true)
                        //.option(ChannelOption.WRITE_BUFFER_WATER_MARK,new WriteBufferWaterMark(24,100)) //DEFAULT: low 32 * 1024 high 64 * 1024
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new HeartbeatClientHandler())          // 心跳检测
                                .addLast(new EncodeHandler(Message.class))   // 反序列化
                                .addLast(new DecodeHandler(Message.class)) // 序列化
                                .addLast(new RequestHandler())  // 处理请求响应
                                .addLast(new HealthClientHandler()); // 接收服务状态;
                    }
                });
    }

    @Override
    public boolean connect() {
        if(connected()) return true;
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            this.channel = future.channel();
            return future.isSuccess();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResponseFuture send(Request request) {
        if(!connected()){
            throw new RpcException("channel不可用");
        }

        try {
            String id = UUID.randomUUID().toString().replace("-", "");
            Message message = new Message(id);
            message.setSessionId(Context.get());
            message.setData(SerializationUtil.serialize(request));

            logger.info("发送一个请求(" + id + ")：" + channel.remoteAddress() +  request);
            channel.writeAndFlush(message);
            /*.addListener(future -> {
                if (!future.isSuccess()) {
                    logger.error("channel writeAndFlush failed");
                    syncObject.release();
                } else {
                    logger.info("channel writeAndFlush success");
                }
            });*/
            return MessageFactory.newFuture(id,request.getTimeout());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpcException(e.getMessage());
        }
    }

    @Override
    public String remoteAddress() {
        return host + ":" + port;
    }

    @Override
    public boolean connected() {
        return (null != channel) && channel.isActive();
    }

    @Override
    public void shutdown() {
        channel.close();
        eventLoopGroup.shutdownGracefully();
    }
}
