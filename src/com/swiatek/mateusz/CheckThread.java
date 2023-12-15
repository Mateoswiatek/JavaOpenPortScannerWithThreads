package com.swiatek.mateusz;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class CheckThread extends Thread {
    String IP;
    int port;
    BufferedWriter writerOpen;
    BufferedWriter writerClose;
    int timeout;
    List<Thread> threadList;
    public CheckThread(String IP, int port, BufferedWriter writerOpen, BufferedWriter writerClose, int timeout, List<Thread> list){
        this.IP = IP;
        this.port = port;
        this.writerOpen = writerOpen;
        this.writerClose = writerClose;
        this.timeout = timeout;
        this.threadList = list;
    }

    @Override
    public void run() {
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(IP, port), timeout);
            synchronized(writerOpen) {
                writerOpen.write(IP + ":" + port + "\n");
                writerOpen.flush();
                synchronized (threadList) {
                    threadList.remove(this);
                }
            }
        } catch (Exception e){
            synchronized (threadList) {
                threadList.remove(this);
            }
            try {
                synchronized(writerClose) {
                    writerClose.write("Closed: " + IP + ":" + port + "\n");
                }
            } catch (IOException ex) {
                System.err.println("Port: " + port + "Exception file: " + ex);
            }
        }
    }
}
