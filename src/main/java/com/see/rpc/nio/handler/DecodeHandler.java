package com.see.rpc.nio.handler;

import com.see.rpc.common.serializer.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class DecodeHandler extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public DecodeHandler(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            if (in.readableBytes() < 4) {
                return;
            }
            in.markReaderIndex();
            int dataLength = in.readInt();
            if (dataLength < 0) {
                ctx.close();
            }
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            byte[] data = new byte[dataLength];
            in.readBytes(data);

            Object obj = SerializationUtil.deserialize(data);
            if (genericClass.isInstance(obj)) {
                out.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.add(genericClass.newInstance());
        }
    }
}
