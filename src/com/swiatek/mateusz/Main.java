package com.swiatek.mateusz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
* Opern port:
* sudo nc -l 20
*/
public class Main {
    public static void main(String[] args) {
        //System.out.println("dlugosc to:" + args.length);
        String IP = "127.0.0.1";
        String fileNameOpen = "open.txt";
        String fileNameClose = "close.txt";
        int timeout = 500;
        int DELAY = 1;
        int OFFSET = 200; // how many threads are running at the same time
        List<Thread> threadList = new ArrayList<>();

        if(args.length > 0){
            IP = args[0];
        }
        // nie trzeba zamykać, try-with-resources
        // max 65 535

        // Testowy
        new Thread(() -> {
            int newSize = -1;
            while(true) {
                if(newSize != threadList.size()) {
                    newSize = threadList.size();
                    System.out.println(newSize);
                }
            }
        }).start();

        try(
            BufferedWriter writerOpen = new BufferedWriter(new FileWriter(fileNameOpen));
            BufferedWriter writerClose = new BufferedWriter(new FileWriter(fileNameClose))
        ){
            for(int i = 0; i <3000; i+=OFFSET){
                for(int j=0; j < OFFSET; j++){
                    CheckThread thread = new CheckThread(IP, i+j, writerOpen, writerClose, timeout, threadList);
                    synchronized (threadList) {
                        threadList.add(thread);
                    }
                    thread.start();
                }
                System.out.println("Added for checked: " + i);
                Thread.sleep(DELAY);  // A break between subsequent packs of threads.
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        while(!threadList.isEmpty()){
            System.out.println(threadList.size());
        }
        System.out.println("End!");
    }
}