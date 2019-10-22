package com.shadeien.reactor.mynetty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("the encoder msg is null");
        }

        out.writeInt(msg.getHeader().getCrcCode());
        out.writeInt(msg.getHeader().getLength());
        out.writeLong(msg.getHeader().getSessionID());
        out.writeByte(msg.getHeader().getType());
        out.writeByte(msg.getHeader().getPriority());

        int size = msg.getHeader().getAttachment().size();
        if (size > 0) {
            byte[] bytes = JSON.toJSONBytes(msg.getHeader().getAttachment());
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        } else {
            out.writeInt(0);
        }
        if (msg.getBody() != null) {
            byte[] bytes = JSON.toJSONBytes(msg.getBody());
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        } else {
            out.writeInt(0);
        }
//        out.setInt(4, out.readableBytes());
    }
}
