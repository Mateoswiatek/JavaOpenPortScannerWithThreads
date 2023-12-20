package com.swiatek.mateusz;

import java.io.IOException;
import java.io.PipedInputStream;

public class CheckComputerThread extends Thread {
    PipedInputStream inputStream;
    int minPort;
    int maxPort;
    public CheckComputerThread(PipedInputStream inputStream, int minPort, int maxPort){
        this.inputStream = inputStream;
        this.minPort = minPort;
        this.maxPort = maxPort;
    }

    @Override
    public void run() {
        for(;;){
            try {
                inputStream.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
