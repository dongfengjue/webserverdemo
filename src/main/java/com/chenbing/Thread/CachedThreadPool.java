package com.chenbing.Thread;

import com.chenbing.WebServer.HttpServer;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    static  ExecutorService executorService = Executors.newCachedThreadPool();
    public static void startThread(final Socket socket){
        Runnable syncRunnable = new Runnable() {
            public void run() {
                HttpServer.doing(socket);
                System.out.println(Thread.currentThread().getName());
            }
        };
        executorService.execute(syncRunnable);
    }

}
