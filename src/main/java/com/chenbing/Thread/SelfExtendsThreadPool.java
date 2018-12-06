package com.chenbing.Thread;

import com.chenbing.HttpServer;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SelfExtendsThreadPool {
    //创建等待队列
    static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(200);
    //创建线程池，池中保存的线程数为3，允许的最大线程数为5
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(300,3000,5000, TimeUnit.MILLISECONDS,bqueue);

    public static void startThread(final Socket socket){
        Thread syncRunnable = new Thread() {
            public void run() {
                HttpServer.doing(socket);
            }
        };
        pool.execute(syncRunnable);
    }

}


