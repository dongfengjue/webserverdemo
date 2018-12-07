package com.chenbing.Thread;

import com.chenbing.HttpServer;
import com.chenbing.NioWebServer;

import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SelfThreadPool{
    //创建等待队列
    static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(200);
    //创建线程池，池中保存的线程数为3，允许的最大线程数为5
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(300,3000,5000, TimeUnit.MILLISECONDS,bqueue);

    public static void startThread(final Socket socket){
        Runnable syncRunnable = new Runnable() {
            public void run() {
                HttpServer.doing(socket);
            }
        };
        pool.execute(syncRunnable);
    }
    public static void startThread(final int port){
        Runnable syncRunnable = new Runnable() {
            public void run() {
                NioWebServer.selector(port);
            }
        };
        pool.execute(syncRunnable);
    }

    public static void startThread(final ServerSocketChannel serverSocketChannel){
        Runnable syncRunnable = new Runnable() {
            public void run() {
                NioWebServer.selector(serverSocketChannel);
            }
        };
        pool.execute(syncRunnable);
    }
}


