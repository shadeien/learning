package com.shadeien.reactor.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class TimeServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object body) throws Exception {
        log.info("TimeServerHandler:{}", body);
//        String data = "HELLO CLIENT" + Constant.separator;
//        byte[] bytes = data.getBytes();
//        ByteBuf writeBuf = Unpooled.buffer(bytes.length);
//        writeBuf.writeBytes(bytes);
        channelHandlerContext.writeAndFlush("abc");
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(bytes);
//        String body = new String(bytes, "UTF-8");
//        log.info("{}", body);
//
//        ByteBuf writeBuf = Unpooled.copiedBuffer("HELLOCLIENT".getBytes());
//        channelHandlerContext.writeAndFlush(writeBuf);
//    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
