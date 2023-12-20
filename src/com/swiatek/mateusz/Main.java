package com.swiatek.mateusz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
* Opern port:
* sudo nc -l 20
*
* nie trzeba zamykać, try-with-resources
* max 65 535
*/

public class Main {
    public static void main(String[] args) throws IOException {
        //String IP = "127.0.0.1";
        int minPort = 1;
        int maxPort = 200; // 65535
        int timeout = 100;
        int offset = 1000;
        List<Thread> threadComputerList; // when splitting into classes, you will need to check the state of the computers' threads.
        PipedOutputStream outputStreamComp = new PipedOutputStream();
        PipedInputStream inputStreamComp = new PipedInputStream(outputStreamComp);

        int n = 10;
        while(n-->0){
            CheckComputerThread checkComputerThread = new CheckComputerThread(inputStreamComp, minPort, maxPort);
            // chyba jednak PipedStream się nie nada do tego. doczytać, może jest prostrzy sposób, ew flagi i oczekwania.
            // wrzuamy wszystkie kombinacja do streamu, a np 20 wątków sobie wychwytuje i dopisuje do odpowiednich plików????

        }

        if(args[0].equals("-")){
            int ilosc_komp = 0;
            String fileName = args[1];
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String ip;
                while ((ip = br.readLine()) != null) {
                    outputStreamComp.write(Integer.parseInt(ip));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Sprawdzamy komputerow: " + ilosc_komp);
        } else {
            for (String ip : args) {
                outputStreamComp.write(Integer.parseInt(ip));
            }
            System.out.println("Sprawdzamy komputerow: " + args.length);
        }

    }

    private static void checkComputers(String IP, int minPort, int maxPort, int timeout, int offset) {
        int DELAY = 100;
        int OFFSET = Math.min(maxPort - minPort, offset);
        List<Thread> threadList = new ArrayList<>();

        try(
            BufferedWriter writerOpen = new BufferedWriter(new FileWriter("Open_" + IP + ".txt"));
            BufferedWriter writerClose = new BufferedWriter(new FileWriter("Close_" + IP + ".txt"))
        ){
            for(int i = minPort; i <maxPort; i+=OFFSET){
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