package com.mobilejohnny;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zwb on 15-8-4.
 */
public class ServerRunnable implements Runnable {

    @Override
    public void run() {
        try {
            while(!Thread.interrupted())
            {
                Thread.sleep(1000);
                OutputStream outputStream = SerialComm.getOutputStream();
                byte[] data = new byte[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
                outputStream.write(data,0,data.length);
                System.out.println("Send");
            }

        } catch (IOException e) {
            System.out.println("Error:Can't read or write to port");
        } catch (InterruptedException e) {
            System.out.println("Error:Thread interrupted");
        }
    }
}
