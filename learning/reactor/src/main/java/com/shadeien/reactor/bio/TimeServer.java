package com.shadeien.reactor.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class TimeServer {

    public static void main(String[] args) {
        int port = 8888;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                Socket accept = serverSocket.accept();
                pool.execute(new TimeServerHandler(accept));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }
    }
}
