package com.mobilejohnny;
import gnu.io.CommPort;

import java.io.IOException;
import java.io.OutputStream;


public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");
        boolean result = SerialComm.connect("/dev/ttyUSB0");

        if(result)
        {
            System.out.println( "Port connected" );
            new Thread(new ServerRunnable()).start();
        }

    }
}
