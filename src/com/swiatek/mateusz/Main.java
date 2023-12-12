package com.swiatek.mateusz;

import java.io.BufferedWriter;
import java.io.FileWriter;

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
        int DELAY = 100;
        int OFFSET = 200; // how many threads are running at the same time

        if(args.length > 0){
            IP = args[0];
        }
        // nie trzeba zamykać, try-with-resources
        // max 65 535
        try(
            BufferedWriter writerOpen = new BufferedWriter(new FileWriter(fileNameOpen));
            BufferedWriter writerClose = new BufferedWriter(new FileWriter(fileNameClose))
        ){
            for(int i = 0; i <3000; i+=OFFSET){
                for(int j=0; j < OFFSET; j++){
                    //System.out.println(i+j);
                    new CheckThread(IP, i+j, writerOpen, writerClose, timeout).start();
                }
                System.out.println("Added for checked: " + i);
                Thread.sleep(DELAY);  // A break between subsequent packs of threads.
            }

            long startTime = System.currentTimeMillis();
            System.out.println("Saving...");
            // If the checking threads have these objects, it will wait here -> ensuring that all data is written correctly.
            synchronized (writerOpen) {
                System.out.println("Saved Open port");
            }
            synchronized (writerClose){
                System.out.println("Saved Close port.");
            }
            //Thread.sleep(2000); //  time for saving to file. An old, naive, heuristic way of ensuring that all data is recorded

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Time: " + duration + " milisekund");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}