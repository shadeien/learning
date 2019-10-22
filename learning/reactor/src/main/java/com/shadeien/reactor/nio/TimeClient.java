package com.shadeien.reactor.nio;

public class TimeClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandler("127.0.0.1", 8888)).start();
    }
}
