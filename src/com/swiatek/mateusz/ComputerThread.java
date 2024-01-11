package com.swiatek.mateusz;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ComputerThread extends Thread {
    String ip;
    List<Integer> ports;
    int timeout;
    BlockingQueue<String> results;
    public ComputerThread(String ip, List<Integer> ports, BlockingQueue<String> results, int timeout){
        this.ip = ip;
        this.ports = ports;
        this.timeout = timeout;
        this.results = results;
    }

    @Override
    public void run() {
        boolean flaga = false;
        for(int port : ports){

            try(Socket socket = new Socket()){
                socket.connect(new InetSocketAddress(ip, port), timeout);
                flaga = true;
            } catch (Exception e){
                flaga = false;
            }
            //System.out.println("port: " + port);
            try {
                results.put(String.valueOf(port));
                //results.put(ip+port+" "+flaga);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
