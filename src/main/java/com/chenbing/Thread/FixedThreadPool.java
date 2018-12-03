package com.chenbing.Thread;

import com.chenbing.HttpServer;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void startThread(final Socket socket){
        Runnable syncRunnable = new Runnable() {
            public void run() {
                HttpServer.doing(socket);
            }
        };
        executorService.execute(syncRunnable);
    }
}
