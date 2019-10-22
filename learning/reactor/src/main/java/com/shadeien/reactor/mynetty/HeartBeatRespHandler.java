package com.shadeien.reactor.mynetty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class HeartBeatRespHandler extends SimpleChannelInboundHandler<NettyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        Header header = msg.getHeader();
        if (header != null && header.getType() == MyConstants.MessageType.HEARBEAT_REQ.getValue()) {
            log.info("server receive client hear beat");
            NettyMessage heartBeat = buildHeartBeat();
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MyConstants.MessageType.HEARBEAT_RESP.getValue());
        header.setLength(18);
        nettyMessage.setHeader(header);

        return nettyMessage;
    }
}
