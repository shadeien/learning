package com.shadeien.reactor.mynetty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Map;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in = (ByteBuf) super.decode(ctx, in);
        if (null == in) {
            return null;
        }
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setSessionID(in.readLong());
        header.setType(in.readByte());
        header.setPriority(in.readByte());
        int attachSize = in.readInt();
        if (attachSize > 0) {
            byte[] attachBytes = new byte[attachSize];
            in.readBytes(attachBytes);
            Map attach = JSON.parseObject(attachBytes, Map.class);
            header.setAttachment(attach);
        }
        nettyMessage.setHeader(header);

        int bodySize = in.readInt();
        if (bodySize > 0) {
            byte[] bodyBytes = new byte[bodySize];
            in.readBytes(bodyBytes);
            Object body = JSON.parseObject(bodyBytes, Object.class);
            nettyMessage.setBody(body);
        }

        return nettyMessage;
    }
}
