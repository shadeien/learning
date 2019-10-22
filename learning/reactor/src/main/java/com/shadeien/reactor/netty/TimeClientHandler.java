package com.shadeien.reactor.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class TimeClientHandler extends SimpleChannelInboundHandler<String>  {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        String data = "HELLO SERVER" + Constant.separator;
//        byte[] bytes = data.getBytes();
//        ByteBuf byteBuf = null;
//        for (int i = 0; i < 100; i++) {
//            byteBuf = Unpooled.buffer(bytes.length);
//            byteBuf.writeBytes(bytes);
//            ctx.writeAndFlush(byteBuf);
//        }
        List<UserInfo> infos = getInfos();
        ctx.writeAndFlush(infos);
    }

    private List<UserInfo> getInfos() {
        int length = 10;
        List<UserInfo> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName("a"+i);
            userInfo.setAge(i);
            list.add(userInfo);
        }

        return list;
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(bytes);
//        String body = new String(bytes, "UTF-8");
//        log.info("{}", body);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String body) throws Exception {
        log.info("{}, {}", body, ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
}
