package com.shadeien.reactor.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Slf4j
public class TimeServerHandler implements Runnable {
    private Socket socket;
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            while (true) {
                String str = bufferedReader.readLine();
                if (null != str && str.length() > 0) {
                    log.info("{}", str);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                socket = null;
            }
            log.info("down");
        }

    }
}
