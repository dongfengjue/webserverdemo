package com.chenbing.WebServer;

public class Start {
    public static void main(String[] args) {
        new NioWebServer().startServer(8081);
//        new WebServer().startServer(8081);
    }
}
