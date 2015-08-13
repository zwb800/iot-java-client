package com.mobilejohnny.iotclient;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

/*×

 */
public class Main {



    public static void main(String[] args) {
        System.out.println("Starting...");
        boolean result = SerialComm.connect("/dev/ttyUSB0");

        if(result)
        {
            final OutputStream outputStream = SerialComm.getOutputStream();

            Push.connect(new PushCallback() {
                @Override
                public void onMessage(String msg) {
                    try {
                        outputStream.write((msg).getBytes());
                        System.out.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(120000);
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
