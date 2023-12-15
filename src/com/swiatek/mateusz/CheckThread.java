package com.swiatek.mateusz;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class CheckThread extends Thread {
    String ip;
    int port;
    int timeout;
    List<Thread> threadList;

    //these are all references, these are not new objects
    BufferedWriter writerOpen;
    BufferedWriter writerClose;
    BufferedWriter buffer;

    public CheckThread(String ip, int port, BufferedWriter writerOpen, BufferedWriter writerClose, int timeout, List<Thread> list){
        this.ip = ip;
        this.port = port;
        this.writerOpen = writerOpen;
        this.writerClose = writerClose;
        this.timeout = timeout;
        this.threadList = list;
    }

    @Override
    public void run() {
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(ip, port), timeout);
            buffer = writerOpen;
        } catch (Exception e){
            buffer = writerClose;
        }
        // saving
        synchronized(buffer) {
            try {
                buffer.write(ip + ":" + port + "\n");
                buffer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
