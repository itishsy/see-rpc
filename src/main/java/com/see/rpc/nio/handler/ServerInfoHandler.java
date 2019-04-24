package com.see.rpc.nio.handler;

import com.see.rpc.common.serializer.SerializationUtil;
import com.see.rpc.config.PropertyConfiguration;
import com.see.rpc.nio.Constants;
import com.see.rpc.nio.base.Message;
import com.see.rpc.nio.base.ServerInfo;
import com.see.rpc.nio.server.TaskExecutor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ChannelHandler.Sharable
public class ServerInfoHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ServerInfoHandler.class);

    public ServerInfoHandler() {
        Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory(Constants.SERVER_HEALTH_STATUS_THREAD_NAME))
                .scheduleWithFixedDelay(new PressureMonitorTask(),
                        0,
                        10,
                        TimeUnit.SECONDS);
    }


    private static class Singleton {
        private static final ServerInfoHandler instance = new ServerInfoHandler();
    }

    public static ServerInfoHandler getInstance(){return Singleton.instance;}

    private static ServerInfo lastState = ServerInfo.NORMAL;

    private static Set<Channel> channels = new ConcurrentSet<>();
    private static Vector<Long> completedVector = new Vector<>();
    public static final int maxVectorSize = 60;

    private static final int core = PropertyConfiguration.STRESSED_SIZE;
    private static final int max = core * 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }


    private final class PressureMonitorTask implements Runnable{

        @Override
        public void run() {
            try {
                ThreadPoolExecutor pool = (ThreadPoolExecutor) TaskExecutor.getInstance().getPool();
                ServerInfo state = getCurrentState(pool);
                if(!state.equals(lastState)){
                    for (Channel channel : channels) {
                        Message message = new Message();
                        message.setData(SerializationUtil.serialize(state));
                        channel.writeAndFlush(message);
                    }
                    lastState = state;
                    completedVector.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 队列任务数（QTC）
         * 临界值默认（CORE=1000）
         * 最大临界值默认（MAX=CORE*3）
         * NORMAL：当前为NORMAL状态下，QTC < CORE ；非NORMAL状态下 QTC < CORE/2
         * STRESSED：QTC连续3次超过CORE
         * BLOCKED：QTC连续3次超过MAX、且任务完成数未增加；或者QTC>0、且连续maxVectorSize(默认60)次(10分钟)完成任务数未增加
         * @param pool 任务线程池
         * @return
         */
        private ServerInfo getCurrentState(ThreadPoolExecutor pool) {
            long qtc = pool.getQueue().stream().filter(t -> t instanceof Future).count();
//            logger.info(">>>>>>>>>>>>>>>>>>>。qtc：" + qtc);

            // NORMAL
            if ((lastState == ServerInfo.NORMAL && qtc <= core)
                    || (lastState != ServerInfo.NORMAL && qtc <= core / 2)) {
                logger.debug("当前服务任务状态：正常。QTC：" + qtc);
                completedVector.clear();
                return ServerInfo.NORMAL;
            }

            if (qtc > core) {
                Long completed = pool.getCompletedTaskCount();
//                logger.info(">>>>>>>>>>>>>>>>>>>。completed：" + completed);
                if (completedVector.size() < 3) {
                    completedVector.add(completed);
                    return lastState;
                }

                // BLOCKED
                if (qtc > max
                        && completedVector.get(completedVector.size() - 1).equals(completed)
                        && completedVector.get(completedVector.size() - 2).equals(completed)) {
                    logger.error("当前服务任务状态：阻塞。（执行中的任务慢，等待任务数量过多）QTC：" + qtc);
                    return ServerInfo.BLOCKED;
                }

                // STRESSED
                logger.warn("当前服务任务状态：压力。QTC：" + qtc);
                return ServerInfo.STRESSED;
            }

            if(completedVector.size() < maxVectorSize){
                completedVector.add(pool.getCompletedTaskCount());
                return lastState;
            }

            //另类的 阻塞
            if (qtc > 0 && completedVector.stream().collect(Collectors.groupingBy(Long::longValue)).keySet().size() ==1) {
                logger.error("当前服务任务状态：阻塞。（执行中的任务超时，无法处理新请求）QTC：" + qtc);
                return ServerInfo.BLOCKED;
            }

            //限制vector长度
            completedVector.remove(0);
            return lastState;
        }
    }
}
