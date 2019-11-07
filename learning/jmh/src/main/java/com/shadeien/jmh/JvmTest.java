package com.shadeien.jmh;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JvmTest {
    static int _1MB = 1 * 1024 * 1024;
    public static void main(String[] args) throws InterruptedException {
        log.info("start");
        byte[] a = new byte[8*_1MB];
        byte[] b = new byte[8*_1MB];
        Thread.sleep(10000);
    }
}
