package com.shadeien.reactor.nio;

public class NioTimeServer {
    public static void main(String[] args) {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(8888);
        new Thread(timeServer).start();
    }
}
