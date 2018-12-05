package com.chenbing.Thread;

import com.chenbing.HttpServer;

import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
    public static void startThread(final Socket socket){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        Runnable syncRunnable = new Runnable() {
            public void run() {
                HttpServer.doing(socket);
                System.out.println(Thread.currentThread().getName());
            }
        };
        executorService.scheduleAtFixedRate(syncRunnable, 500, 300, TimeUnit.MILLISECONDS);
    }
}
