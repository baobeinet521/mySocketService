package com.socket.service.util;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendThread extends Thread{
    public static String TAG="SendThread";
    public Socket socket;
    public PrintWriter printWriter = null;
    public SendThread(Socket socket) {
        this.socket = socket;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String reqMessage = "HelloWorld！ from clientsocket this is test half packages!";
        for (int i = 0; i < 10; i++) {
//            sendPacket(reqMessage);
            sendPacketHeaderInt(reqMessage);
        }
//        sendPacket(reqMessage);
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 包体长度用 String 类型固定4位传送
     * @param message
     */
    public void sendPacket(String message) {
        byte[] contentBytes = message.getBytes();// 包体内容
        int contentlength = contentBytes.length;// 包体长度

        Log.d(TAG, "sendPacket: sendManyData:   数据长度是： " + contentlength);
        String head = String.valueOf(contentlength);// 头部内容
        int sizeLen = head.length();
        String legthStrFinal = "";
        if (sizeLen < 4) {
            String strTemp = "";
            for (int i = 0; i < 4 - sizeLen; i++) {
                strTemp += "0";
            }
            legthStrFinal = strTemp + head;
            Log.d(TAG, "sendManyData:   拼接后 数据长度字符串 是： " + legthStrFinal);
        }
        message = legthStrFinal + message;
        Log.d(TAG, "sendManyData:   拼接后 reqMessage 是： " + message);

        byte[] headbytes = legthStrFinal.getBytes();// 头部内容字节数组
        byte[] bytes = new byte[headbytes.length + contentlength];// 包=包头+包体
        int i = 0;
        for (i = 0; i < headbytes.length; i++) {// 包头
            bytes[i] = headbytes[i];
        }
        for (int j = i, k = 0; k < contentlength; k++, j++) {// 包体
            bytes[j] = contentBytes[k];
        }

        try {
            OutputStream writer = socket.getOutputStream();
            writer.write(bytes);
//				writer.write(message.getBytes());
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 包头信息用int表示传送
     * @param message
     */
    public void sendPacketHeaderInt(String message) {
        byte[] contentBytes = message.getBytes();// 包体内容
        int contentlength = contentBytes.length;// 包体长度

        Log.d(TAG, "sendPacket: sendManyData:   数据长度是： " + contentlength);

        byte[] headbytes  = DataDealWithUtil.IntToByte(contentlength);// 头部内容字节数组
        try {
            OutputStream writer = socket.getOutputStream();
            writer.write(headbytes);
            writer.write(contentBytes);
//				writer.write(message.getBytes());
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
