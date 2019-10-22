package com.shadeien.reactor.aio;

public class AioTimeClient {
    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", 8888)).start();
    }
}
