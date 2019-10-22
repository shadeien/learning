package com.shadeien.reactor.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOServer {



    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            Selector selector_work = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            ServerSocket socket = serverSocketChannel.socket();
            socket.bind(new InetSocketAddress("localhost", 8888));

            ExecutorService executorService = Executors.newFixedThreadPool(20);
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector_work, SelectionKey.OP_READ);


                        executorService.submit(() -> {
                            while (true) {
                                selector_work.select();
                                Iterator<SelectionKey> keys = selector_work.selectedKeys().iterator();
                                while (keys.hasNext()) {
                                    SelectionKey readkey = keys.next();
                                    System.out.println(readkey);
                                    if (readkey.isAcceptable()) {
                                        System.out.println("isAcceptable");
                                    }
                                    if (readkey.isReadable()) {
                                        System.out.println("inner:"+readDataFromSocketChannel((SocketChannel) readkey.channel()));
                                        accept.close();

                                    }

                                    keys.remove();
                                }
                            }
                        });

                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        System.out.println(readDataFromSocketChannel(channel));
                        channel.close();
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readDataFromSocketChannel(SocketChannel channel) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            byteBuffer.clear();
            try {
                int n = channel.read(byteBuffer);
                if (n == -1) {
                    break;
                }
                byteBuffer.flip();
                int limit = byteBuffer.limit();
                char[] dst = new char[limit];
                for (int i = 0; i < limit; i++) {
                    dst[i] = (char) byteBuffer.get(i);
                }
                stringBuffer.append(dst);
                byteBuffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();
    }
}
