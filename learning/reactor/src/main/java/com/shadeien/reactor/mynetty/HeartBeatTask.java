package com.shadeien.reactor.mynetty;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatTask implements Runnable {
    private final ChannelHandlerContext channelHandlerContext;

    public HeartBeatTask(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void run() {
        NettyMessage heartBeat = buildHeartBeat();
        log.info("client heart beat");
        channelHandlerContext.writeAndFlush(heartBeat);
    }

    private NettyMessage buildHeartBeat() {
        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setType(MyConstants.MessageType.HEARBEAT_REQ.getValue());
        header.setLength(18);
        nettyMessage.setHeader(header);

        return nettyMessage;
    }
}
