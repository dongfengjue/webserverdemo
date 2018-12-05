package com.chenbing;

import com.chenbing.Thread.CachedThreadPool;
import com.chenbing.Thread.FixedThreadPool;
import com.chenbing.Thread.ScheduledThreadPool;
import com.chenbing.Thread.SelfThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public void startServer(int port){
        try {

            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                System.out.println("------socket------");
                Socket socket = serverSocket.accept();
//                new NormalThread(socket).start();
//                FixedThreadPool.startThread(socket);
//                  CachedThreadPool.startThread(socket);
//                ScheduledThreadPool.startThread(socket);
                SelfThreadPool.startThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
