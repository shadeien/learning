package com.shadeien.reactor.bio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {

    public static void main(String[] args) {
        Socket socket = null;
        PrintWriter printWriter = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            String s = "hello world";

            printWriter.println(s);
            printWriter.println(s);
            printWriter.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != printWriter) {
                printWriter.close();
                printWriter = null;
            }
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
