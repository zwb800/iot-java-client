package com.mobilejohnny.iotclient;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.mobilejohnny.SerialComm;
import gnu.io.CommPort;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;


public class Main {

    private static final String APP_KEY = "55c8c6339477ebf5246956dc";

    public static void main(String[] args) {
        System.out.println("Starting...");
        boolean result = SerialComm.connect("/dev/ttyUSB0");

        if(true)
        {
            try {
                final Socket socket = IO.socket("https://sock.yunba.io");

                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
//                        System.out.println("SocketIO connected");
                    }
                }).on("socketconnectack", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            JSONObject jsonObject = (JSONObject) args[0];

                            System.out.println(jsonObject.getString("msg"));

                            socket.emit("connect", "{'appkey':'"+APP_KEY+"'}");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).on("connack", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            JSONObject jsonObject = (JSONObject) args[0];

                            System.out.println(jsonObject.getBoolean("success"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                socket.connect();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

//            System.out.println( "Serial port connected");

//            new Thread(new ServerRunnable()).start();
        }

    }
}
