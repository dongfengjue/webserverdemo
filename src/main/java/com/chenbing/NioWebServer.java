package com.chenbing;

import com.chenbing.Thread.SelfCallableThreadPool;
import com.chenbing.Thread.SelfExtendsThreadPool;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioWebServer {
    private static final int BUF_SIZE=1024;
    private static final int TIMEOUT = 3000;

    public void startServer(int port){
        selector(port);
    }
    public static void handleAccept(SelectionKey key) throws IOException{
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }
    /*public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer)key.attachment();
        long bytesRead = sc.read(buf);
        while(bytesRead>0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = sc.read(buf);
        }
        if(bytesRead == -1){
            sc.close();
        }
    }*/

    //客户端信道已经准备好了从信道中读取数据到缓冲区
    public static void handleRead(SelectionKey key) throws IOException{
        SocketChannel clntChan = (SocketChannel) key.channel();
        //获取该信道所关联的附件，这里为缓冲区
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = clntChan.read(buf);
        //如果read（）方法返回-1，说明客户端关闭了连接，那么客户端已经接收到了与自己发送字节数相等的数据，可以安全地关闭
        if (bytesRead == -1){
//            clntChan.close();
        }else if(bytesRead > 0){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
//            buf.clear();
//            bytesRead = clntChan.read(buf);

            //如果缓冲区总读入了数据，则将该信道感兴趣的操作设置为为可读可写
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    //客户端信道已经准备好了将数据从缓冲区写入信道
    public static void handleWrite(SelectionKey key) throws IOException {
        //获取与该信道关联的缓冲区，里面有之前读取到的数据
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //重置缓冲区，准备将数据写入信道
        buf.flip();
        SocketChannel clntChan = (SocketChannel) key.channel();

        StringBuffer inString = new StringBuffer();
        while(buf.hasRemaining()){
            inString.append(buf.get());
        }

        ByteBuffer resultBuf = ByteBuffer.allocate(1024);
//        String result = HttpServer.doing(inString.toString());
        String result = SelfCallableThreadPool.startThread(inString.toString());
        System.out.println("-----result-----"+result);

        //将数据写入到信道中
        clntChan.write(ByteBuffer.wrap(result.getBytes()));
        if (!buf.hasRemaining()){
            //如果缓冲区中的数据已经全部写入了信道，则将该信道感兴趣的操作设置为可读
            key.interestOps(SelectionKey.OP_READ);
        }
        //为读入更多的数据腾出空间
        buf.compact();
    }

    /*public static void handleWrite(SelectionKey key) throws IOException{
        ByteBuffer buf = (ByteBuffer)key.attachment();
        buf.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        while(buf.hasRemaining()){
            sc.write(buf);
        }
        buf.compact();
    }*/
    public static void selector(int port) {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try{
            selector = Selector.open();
            ssc= ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(port));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while(true){
                if(selector.select(TIMEOUT) == 0){
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while(iter.hasNext()){
                    SelectionKey key = iter.next();
                    if(key.isAcceptable()){
                        handleAccept(key);
                    }
                    if(key.isReadable()){
                        handleRead(key);
                    }
                    if(key.isWritable() && key.isValid()){
                        handleWrite(key);
                    }
                    if(key.isConnectable()){
                        System.out.println("isConnectable = true");
                    }
                    iter.remove();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(selector!=null){
                    selector.close();
                }
                if(ssc!=null){
                    ssc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
