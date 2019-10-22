package com.shadeien.reactor.mynetty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@ChannelHandler.Sharable
public class HeartBeatReqHandler extends SimpleChannelInboundHandler<NettyMessage> {
    private volatile ScheduledFuture<?> scheduledFuture;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        Header header = msg.getHeader();
        if (header != null && header.getType() == MyConstants.MessageType.LOGIN_RESP.getValue()) {
            scheduledFuture = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 30, TimeUnit.SECONDS);
        } else if (header != null && header.getType() == MyConstants.MessageType.HEARBEAT_RESP.getValue()) {
            log.info("client receive server heart beat");
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        if (null != scheduledFuture) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}