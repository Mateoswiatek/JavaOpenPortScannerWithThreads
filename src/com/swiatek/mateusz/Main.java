package com.swiatek.mateusz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
* Opern port:
* sudo nc -l 20
*/
public class Main {
    public static void main(String[] args) {
        //System.out.println("dlugosc to:" + args.length);
        String IP = "127.0.0.1";
        String fileNameOpen = "open1.txt";
        String fileNameClose = "close.txt";
        int timeout = 100;
        int DELAY = 100;
        int OFFSET = 1000; // how many threads are running at the same time
        List<Thread> threadList = new ArrayList<>();
        // nie trzeba zamykać, try-with-resources
        // max 65 535

        try(
            BufferedWriter writerOpen = new BufferedWriter(new FileWriter(fileNameOpen));
            BufferedWriter writerClose = new BufferedWriter(new FileWriter(fileNameClose))
        ){
            for(int i = 0; i <65535; i+=OFFSET){
                for(int j=0; j < OFFSET; j++){
                    CheckThread thread = new CheckThread(IP, i+j, writerOpen, writerClose, timeout, threadList);
                    threadList.add(thread);
                    thread.start();
                }
                System.out.println("Added for checked: " + i);
                //Thread.sleep(DELAY);  // A break between subsequent packs of threads.
            }

            // czy lepsze jest usuwanie nieżyjących, czy nic nie robienie w tej pętli, czekając aż warunek przestanie być spełniony?
            while (threadList.stream().anyMatch(Thread::isAlive)) {
                threadList = threadList.stream().filter(Thread::isAlive).collect(Collectors.toList());
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}