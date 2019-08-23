package com.shadeien.reactor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class NIOClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream out = socket.getOutputStream();
        String s = "hello world";


        Thread.sleep(1000);
        out.write(s.getBytes());
        out.write(s.getBytes());
        out.write(s.getBytes());
        out.close();
    }
}
