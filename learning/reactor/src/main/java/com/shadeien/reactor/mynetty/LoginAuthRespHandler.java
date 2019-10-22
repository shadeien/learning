package com.shadeien.reactor.mynetty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ChannelHandler.Sharable
public class LoginAuthRespHandler extends SimpleChannelInboundHandler<NettyMessage> {
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        log.info("{}", msg);
        Header header = msg.getHeader();
        if (header != null && header.getType() == MyConstants.MessageType.LOGIN_REQ.getValue()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                loginResp = buildResponse((byte) 0);
                nodeCheck.put(nodeIndex, true);
            }
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MyConstants.MessageType.LOGIN_RESP.getValue());
        header.setLength(19);
        nettyMessage.setHeader(header);
        nettyMessage.setBody(result);

        return nettyMessage;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.fireExceptionCaught(cause);
    }
}
