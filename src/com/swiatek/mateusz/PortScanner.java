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
public class PortScanner {
    ExecutorService executor;
    BlockingQueue<String> results = new ArrayBlockingQueue<>(100);
    int cntthreads = 16;
    List<Integer> ports = new ArrayList<>();
    List<String> computers = new ArrayList<>();
    int kubelki = 5;

    int timeout = 200;
    boolean writeAll = true;
    public PortScanner(List<String> computers, List<Integer> ports, int cntthreads){
        this.computers = computers;
        this.ports = ports;
        this.cntthreads = cntthreads;
        this.executor = Executors.newFixedThreadPool(cntthreads);
    }
    public PortScanner(){
        cntthreads = 16;
        executor = Executors.newFixedThreadPool(cntthreads);
    }

    public void scannComputers(){
        scannComputers(computers, ports, kubelki);
    }
    public void scannComputers(List<String> newcomputers){
        scannComputers(newcomputers, ports, kubelki);
    }
    public void scannComputersPorts(List<Integer> newports){
        scannComputers(computers, newports, kubelki);
    }
    public void scannComputers(String computer, List<Integer> ports){

        scannComputersPorts(ports);
    }
    public void scannComputers(String computer, int port){
        scannComputers(new ArrayList<>(List.of(computer)), new ArrayList<>(List.of(port)), kubelki);
    }

    public void scannComputers(List<String> newcomputers, List<Integer> ports, int kubelki){
        System.out.println(newcomputers);
        System.out.println(ports);
        if(kubelki > ports.size()) kubelki = 1;
        int startindex = 0;
        int diff = ports.size() / kubelki;
        for(String computer : newcomputers){
            for(int i=0;i<kubelki;i++) {
                executor.execute(new ComputerThread(computer, ports.subList(startindex, (startindex += diff)), results, timeout, writeAll));
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

        //TODO Jesli zwracamy tylko otwarte watki, to trzeba byc przygotowanym, ze kolejka bedzie pusta. ale chyba lepiej i tak zwracac cos
        /*
        try {
            for(int i =0; i<ports.size()* newcomputers.size(); i++){

                System.out.println(results.take());
            }
        } catch (InterruptedException e){
            System.out.println(e);
        } */

        // przetwarzanie w Scanerze
        try {
            for(int i =0; i<ports.size()* newcomputers.size(); i++){
                String str = results.take();
//                System.out.println(str);
                if(str.charAt(str.length()-1) == '1'){
                    System.out.println(str);
                }

            }
        } catch (InterruptedException e){
            System.out.println(e);
        }


    }
    public PortScanner setComputers(List<String> newcomputers){
        computers = newcomputers;
        return this;
    }
    public PortScanner setPorts(List<Integer> newports){
        ports = newports;
        return this;
    }
    //TODO czy to jest bezpieczne ? takie modyfikowanie, ew wymuszenie, że zmiana może być tylko w tedy
    // kiedy zaden watek nie wykonuje zadnego zadania.
    public PortScanner setCntthreads(int cnt){
        cntthreads = cnt;
        executor = Executors.newFixedThreadPool(cntthreads);
        return this;
    }
    public PortScanner setTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }
    public PortScanner setWriteAll(boolean writeAll){
        this.writeAll = writeAll;
        return this;
    }
    public PortScanner setWriteAll(int writeAll){
        this.writeAll = (writeAll != 0);
        return this;
    }

}
