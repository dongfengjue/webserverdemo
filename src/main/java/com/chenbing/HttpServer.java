package com.chenbing;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class HttpServer{
    /**
     * web资源根路径
     */
    public static final String ROOT = "D:\\github\\HttpServer\\src\\web";

    public static void doing(Socket socket){
        try {
            InputStream input = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            String filePath = read(input);
            response(filePath,out);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String doing(String input){

            String filePath = read(input);
            return response(filePath);

    }

    private static String response(String filePath) {
            StringBuffer sb = new StringBuffer();
            String line = "hello world";

            StringBuffer result = new StringBuffer();
            result.append("HTTP/1.1 200 ok \r\n");
            result.append("Content-Type:text/html;charset=utf-8 \r\n");
            result.append("Content-Length:" + line.length() + "\r\n");
            result.append("\r\n" +line);

            result.append("\r\n" + sb.toString());
            System.out.println("----"+result);
            return result.toString();

    }

    /**
     * @description: 读取资源文件，响应给浏览器。
     * @param:@param filePath
     *                   资源文件路径
     * @return:void
     * @version:v1.0
     * @author:w
     * @date:2018年6月6日 上午11:42:37
     *
     */

    private static void response(String filePath,OutputStream out) {
        File file = new File(ROOT + filePath);
        if (file.exists()) {
            // 1、资源存在，读取资源
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
                StringBuffer result = new StringBuffer();
                result.append("HTTP/1.1 200 ok \r\n");
                result.append("Content-Type:text/html;charset=utf-8 \r\n");
                result.append("Content-Length:" + file.length() + "\r\n");

                result.append("\r\n" + sb.toString());
                System.out.println("----"+result);
                out.write(result.toString().getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // 2、资源不存在，提示 file not found
            StringBuffer error = new StringBuffer();
            error.append("HTTP/1.1 400 file not found \r\n");
            error.append("Content-Type:text/html \r\n");
            error.append("Content-Length:20 \r\n").append("\r\n");
            error.append("<h1 >File Not Found..</h1>");
            try {
                out.write(error.toString().getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static String read(String readLine) {
            // 读取请求头， 如：GET /index.html HTTP/1.1
            String[] split = readLine.split(" ");
            if (split.length != 3) {
                return null;
            }
            System.out.println(readLine);
            return split[1];
    }

    /**
     *
     * @description:解析资源文件路径
     * @example: GET /index.html HTTP/1.1
     * @param:@return
     * @return:String
     * @version:v1.0
     * @author:w
     * @date:2018年6月6日 上午11:39:42
     *
     */
    private static String read(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            // 读取请求头， 如：GET /index.html HTTP/1.1
            String readLine = reader.readLine();
            String[] split = readLine.split(" ");
            if (split.length != 3) {
                return null;
            }
            System.out.println(readLine);
            return split[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
