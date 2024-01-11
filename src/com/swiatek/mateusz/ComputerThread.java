package com.swiatek.mateusz;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ComputerThread extends Thread {
    String ip;
    List<Integer> ports;
    int timeout;
    BlockingQueue<String> results;
    boolean writeAll;
    public ComputerThread(String ip, List<Integer> ports, BlockingQueue<String> results, int timeout, boolean writeAll){
        this.ip = ip;
        this.ports = ports;
        this.timeout = timeout;
        this.results = results;
        this.writeAll = writeAll;
    }

    @Override
    public void run() {
        if(writeAll){
            int flaga;
            for(int port : ports){
                try(Socket socket = new Socket()){
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    flaga = 1;
                } catch (Exception e){
                    flaga = 0;
                }

                try {
                    results.put(ip+":"+port+" "+flaga);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            for(int port : ports){

                try(Socket socket = new Socket()){
                    socket.connect(new InetSocketAddress(ip, port), timeout);
//                    results.put(ip+":"+port); // problem, bo potem nie wiadomo czy faktycznie
                    results.put(ip+":"+port+" 1");

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e){
                    // znaczy, ze jest zamkniety
                }
            }
        }
    }
}
