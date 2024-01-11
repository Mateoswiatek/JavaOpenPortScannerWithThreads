package com.swiatek.mateusz;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class PortScannerTest {

    @Test
    void scannOneComputers() {
        new PortScanner().scannComputers("127.0.0.1", 20);
    }

    @Test
    void testScannOneComputerPorts() {
        new PortScanner().scannComputers("127.0.0.1", new ArrayList<>(List.of(1, 2, 3, 3, 4, 5, 20, 30, 40, 50, 60, 62, 63, 6, 5, 5, 5,5 ,4)));
    }

    @Test
    void scannComputersPorts() {
    }

    @Test
    void testScannComputers1() {
    }

    @Test
    void testScannComputers2() {
    }

    @Test
    void testScannComputers3() {
    }

    @Test
    void setComputers() {
    }

    @Test
    void setPorts() {
    }

    @Test
    void setCntthreads() {
    }

    @Test
    void setTimeout() {
    }

    @Test
    void setWriteAll() {
    }

    @Test
    void testSetWriteAll() {
    }
}