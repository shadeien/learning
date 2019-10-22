package com.shadeien.reactor.mynetty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class LoginAuthReqHandler extends SimpleChannelInboundHandler<NettyMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    private NettyMessage buildLoginReq() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MyConstants.MessageType.LOGIN_REQ.getValue());
        header.setLength(18);
        nettyMessage.setHeader(header);

        return nettyMessage;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        log.info("{}", msg);
        Header header = msg.getHeader();
        if (header != null && header.getType() == MyConstants.MessageType.LOGIN_RESP.getValue()) {
//            byte loginResult = (byte) msg.getBody();
            int loginResult = (int) msg.getBody();
            if (loginResult != 0) {
                ctx.close();
            } else {
                log.info("login success!");
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
