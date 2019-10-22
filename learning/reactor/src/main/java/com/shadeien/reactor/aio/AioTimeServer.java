package com.shadeien.reactor.aio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AioTimeServer {
    public static void main(String[] args) {
        new Thread(new AsyncTimeServerHandler(8888)).start();
    }
}
