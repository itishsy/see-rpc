package com.see.rpc.nio.handler;

import com.google.common.util.concurrent.FutureCallback;
import com.see.rpc.common.exceptions.ServiceException;
import com.see.rpc.common.serializer.SerializationUtil;
import com.see.rpc.nio.base.Context;
import com.see.rpc.nio.server.TaskExecutor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.see.rpc.cache.ServerCache;
import com.see.rpc.nio.base.Message;
import com.see.rpc.nio.base.Request;
import com.see.rpc.nio.base.Response;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class ResponseHandler extends SimpleChannelInboundHandler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    public static final long NANO_TIME = 1000000L;

    @Override
    @SuppressWarnings("unchecked")
    public void channelRead0(final ChannelHandlerContext ctx, Message message) {
        String id = message.getId();
        if (null == id) {
            logger.warn("接收数据不完整或系列化异常，被忽略的请求。。。" + ctx.channel().remoteAddress());
            return;
        }

        Callable<Message> task = newTask(message);
        FutureCallback<Message> callback = getCallback(ctx.channel(), id);
        logger.info("接收业务请求：" + id);
        TaskExecutor.getInstance().execute(task, callback);
    }

    private Callable<Message> newTask(final Message message) {
        return () -> {
            Message retData = new Message(message.getId());
            Response response = new Response();
            try {
                Request request = (Request) SerializationUtil.deserialize(message.getData());
                logger.debug("开始执行任务(" + message.getId() + ")：" + request);

                if (StringUtils.isNotBlank(message.getSessionId())) {
                    Context.set(message.getSessionId());
                }

                String service = request.getService();
                Object bean = ServerCache.getBean(service);
                if (null == bean) {
                    throw new RuntimeException("service bean no found!");
                }
                String method = request.getMethod();
                Object[] args = request.getArgs();

                //服务执行开始时间
                long startTime = System.nanoTime();
                Object result = MethodUtils.invokeMethod(bean, method, args);

                //服务执行耗时
                long useTime = (System.nanoTime() - startTime) / NANO_TIME;
                if (useTime > request.getTimeout()) {
                    logger.info("执行超时的无效任务，返回空");
                    return null;
                }

                response.setResult(result);
                logger.debug("任务执行完成(" + message.getId() + ")：" + request);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Throwable target = e.getTargetException();
                if(target.getClass().getName().contains("BusinessException")){
                    response.setThrowable(target);
                } else {
                    ServiceException serviceException = new ServiceException(target.getMessage());
                    serviceException.setStackTrace(target.getStackTrace());
                    response.setThrowable(serviceException);
                }
            } catch (Throwable e) {
                e.printStackTrace();
                response.setThrowable(e);
            }
            retData.setData(SerializationUtil.serialize(response));
            return retData;
        };
    }

    private FutureCallback getCallback(Channel channel, String id) {
        return new FutureCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                if (null == message) {
                    return;
                }
                if (channel.isActive()) {
                    channel.writeAndFlush(message);
//                .addListener((ChannelFutureListener) future -> {
//                    if (!future.isSuccess()) {
//                    }
//                });
                } else {
                    logger.warn("服务任务执行完成，返回响应的channel已失效。" + channel);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
                Message message = new Message(id);
                message.setData(SerializationUtil.serialize(new Response(e)));
                channel.writeAndFlush(message);
            }
        };
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isWritable()) {
            logger.info("server channel isWritable true ...............");
        } else {
            logger.info("server channel isWritable false...............");
        }

    }

}
