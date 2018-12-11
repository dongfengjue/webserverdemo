package com.chenbing.Redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    static Socket client = new Socket();
    static InputStream is;
    static OutputStream os;
    static {
        try {
            client.connect(new InetSocketAddress("127.0.0.1", 6379));
            is = client.getInputStream();
            os = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        writeAndFlush("set baby hello \n");

        printResult();
        // result : +OK

        writeAndFlush("get baby \n");

        Thread.sleep(100);// 等 redis 返回数据

        printResult();
        // result : $5
        // result : hello

        writeAndFlush("del baby \n");

        Thread.sleep(100);// 等 redis 返回数据

        printResult();
        // result  :1

        writeAndFlush("xxx \n");

        Thread.sleep(100);// 等 redis 返回数据

        printResult();
        // -ERR unknown command `xxx`, with args beginning with:

        writeAndFlush("mget hello baby \r\n");

        Thread.sleep(100);
        printResult();

        closeStream();
    }

    static void writeAndFlush(String content) {

        try {
            os.write(content.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void closeStream() {
        try {
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printResult() {
        try {

            int a = is.available();
            byte[] b = new byte[a];
            is.read(b);

            String result = new String(b);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
