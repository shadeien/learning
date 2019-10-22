package com.shadeien.reactor.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

@ChannelHandler.Sharable
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        int length = buf.readableBytes();
        byte[] data = new byte[length];
        buf.getBytes(buf.readerIndex(), data, 0, length);
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(data));
    }
}
