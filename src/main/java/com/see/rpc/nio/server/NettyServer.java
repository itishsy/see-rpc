package com.see.rpc.nio.server;

import com.see.rpc.nio.Server;
import com.see.rpc.nio.base.Message;
import com.see.rpc.nio.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * Created by Administrator on 2018/12/9.
 */
public class NettyServer implements Server {

    private static final String SERVER_WORKER_THREAD_NAME = "rpc-netty-worker-pool-thread";
    private static final int SERVER_WORKER_THREAD_NUMBER = Runtime.getRuntime().availableProcessors();

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    public NettyServer(){
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(SERVER_WORKER_THREAD_NUMBER, new DefaultThreadFactory(SERVER_WORKER_THREAD_NAME));
    }

    @Override
    public void start(String host,int port) {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    //.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024, 32 * 1024))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline()
                                    .addLast(new HeartbeatServerHandler()) // 心跳检测
                                    .addLast(new DecodeHandler(Message.class)) // 反序列化
                                    .addLast(new EncodeHandler(Message.class)) // 序列化
                                    .addLast(new ResponseHandler())// 处理请求;
                                    .addLast(ServerInfoHandler.getInstance());// 压力检测;
                        }
                    });
            bootstrap.bind(host, port).syncUninterruptibly(); //同步
        } catch (Exception e) {
            shutdown();
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
