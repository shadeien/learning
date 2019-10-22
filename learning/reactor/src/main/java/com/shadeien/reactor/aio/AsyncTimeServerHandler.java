package com.shadeien.reactor.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class AsyncTimeServerHandler implements Runnable {

    private int port;
    private CountDownLatch countDownLatch;
    private AsynchronousServerSocketChannel socketChannel;

    public AsynchronousServerSocketChannel getSocketChannel() {
        return socketChannel;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            socketChannel = AsynchronousServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccept();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        socketChannel.accept(this, new AcceptCompletionHandler());
    }
}
