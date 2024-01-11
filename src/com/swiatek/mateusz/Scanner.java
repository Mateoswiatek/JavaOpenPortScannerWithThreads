package com.swiatek.mateusz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * kazdy watek dostaje koknkretny adres ip oraz zakres portow ktore ma sprawdzic.
 * Wyniki wrzuca do kolejki. watek Scanner odczytuje te wartosci. Dodajac do buforw dla kazdego kompa
 */
public class Scanner {
    ExecutorService executor;
    BlockingQueue<String> results = new ArrayBlockingQueue<>(100);
    int cntthreads;
    List<Integer> ports;
    List<String> computers;
    public Scanner(List<String> computers, List<Integer> ports, int cntthreads){
        this.computers = computers;
        this.ports = ports;
        this.cntthreads = cntthreads;
        this.executor = Executors.newFixedThreadPool(cntthreads);
    }

    public void scannComputers(){
        scannComputers(computers, ports, 1);
    }
    public void scannComputers(List<String> newcomputers){
        scannComputers(newcomputers, ports, 1);
    }
    public void scannComputersPorts(List<Integer> newports){
        scannComputers(computers, newports, 1);
    }
    public void scannComputers(List<String> newcomputers, List<Integer> ports, int kubelki){
        int startindex = 0;
        int diff = ports.size() / kubelki;
        for(String computer : newcomputers){
            for(int i=0;i<kubelki;i++) {
                executor.execute(new ComputerThread(computer, ports.subList(startindex, (startindex += diff)), results, 200));
            }
        }
        executor.shutdown();


        /*
        try(
            BufferedWriter writerOpen = new BufferedWriter(new FileWriter("Open_" + IP + ".txt"));
            BufferedWriter writerClose = new BufferedWriter(new FileWriter("Close_" + IP + ".txt"))
            { }
         */
        //TODO przetwarzanie odpowiedzi, przeez wskazany buffor (element klasy)
        // zrobic ze jesli w kolejce jest powyzej 100 elementow to w tedy zapisuje te 100 do pliku.
        // aby caly czas nie spamowac
        // Tworzymy dwa pliki, otware i zamkniete, jesli otwary to do otwaretgo, jesli zamkniety to do zamnietetgo
        // Druga wersja - Tworzymy tyle plikow ile jest komputerow, zapisujemy najpierw otwarte a potem zamkniete porty.
        try {
            for(int i =0; i<ports.size()* newcomputers.size(); i++){
                System.out.println(results.take());
                //results.take();

            }
        } catch (InterruptedException e){
            System.out.println(e);
        }

    }
    public void setComputers(List<String> newcomputers){
        computers = newcomputers;
    }
    public void setPorts(List<Integer> newports){
        ports = newports;
    }
    //TODO czy to jest bezpieczne ? takie modyfikowanie, ew wymuszenie, że zmiana może być tylko w tedy
    // kiedy zaden watek nie wykonuje zadnego zadania.
    public void setCntthreads(int cnt){
        cntthreads = cnt;
        executor = Executors.newFixedThreadPool(cntthreads);
    }



}
