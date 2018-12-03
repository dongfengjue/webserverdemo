package com.chenbing.Thread;

import com.chenbing.HttpServer;

import java.net.Socket;

public class NormalThread extends Thread{

    private Socket socket;
    public NormalThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        HttpServer.doing(socket);
    }
}
