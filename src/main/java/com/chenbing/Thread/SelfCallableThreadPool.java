package com.chenbing.Thread;

import com.chenbing.WebServer.HttpServer;

import java.net.Socket;
import java.util.concurrent.*;

public class SelfCallableThreadPool {
    //创建等待队列
    static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(200);
    //创建线程池，池中保存的线程数为3，允许的最大线程数为5
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(300,3000,5000, TimeUnit.MILLISECONDS,bqueue);

    public static void startThread(final Socket socket){
        Callable syncCallable = new Callable<String>() {
            public String call() {
                HttpServer.doing(socket);
                return "ok";
            }
        };
        Future<String> task = pool.submit(syncCallable);
        try {
            System.out.println("---result---"+task.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String startThread(final String string){
        Callable syncCallable = new Callable<String>() {
            public String call() {
                return HttpServer.doing(string);
            }
        };
        Future<String> task = pool.submit(syncCallable);
        try {
            System.out.println("---result---"+task.get());
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}


