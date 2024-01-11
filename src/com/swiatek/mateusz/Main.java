package com.swiatek.mateusz;

/*
* Opern port:
* sudo nc -l 20
*
* nie trzeba zamykać, try-with-resources
* max 65 535
*/

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> ports = new ArrayList<>();
        List<String> computers = new ArrayList<>();
        computers.add("127.0.0.1");
        for(int i = 0; i<10;i++){
            ports.add(i);
        }
        Scanner scanner = new Scanner(computers, ports, 16);
        scanner.scannComputers();
    }
}