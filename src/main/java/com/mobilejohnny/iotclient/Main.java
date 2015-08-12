package com.mobilejohnny.iotclient;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class Main {

    private static final String APP_KEY = "55c8c6339477ebf5246956dc";

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Starting...");
        boolean result = SerialComm.connect("/dev/ttyUSB0");

        if(true)
        {
                final SocketIO socketIO = new SocketIO();
                socketIO.connect("http://sock.yunba.io:3000", new IOCallback() {
                    @Override
                    public void onDisconnect() {

                    }

                    @Override
                    public void onConnect() {

                    }

                    @Override
                    public void onMessage(String s, IOAcknowledge ioAcknowledge) {
                        System.out.println(s);
                    }

                    @Override
                    public void onMessage(JSONObject jsonObject, IOAcknowledge ioAcknowledge) {
                        try {
                            System.out.println(jsonObject.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void on(String event, IOAcknowledge ioAcknowledge, Object... args) {
                        try {
                            if(event.equals("socketconnectack"))
                            {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("appkey",APP_KEY);
                                jsonObject.put("customid","1");

                                socketIO.emit("connect",jsonObject);
                            }
                            else if(event.equals("connack"))
                            {
                                JSONObject jsonObject = (JSONObject)args[0];
                                String sessionid = jsonObject.getString("sessionid");
                                System.out.println(sessionid);
                                JSONObject jsonAlias = new JSONObject();
                                jsonAlias.put("alias","1");
                                socketIO.emit("set_alias",jsonAlias);
                            }
                            else if(event.equals("set_alias_ack"))
                            {
                                JSONObject jsonObject = (JSONObject)args[0];
                                boolean success = jsonObject.getBoolean("success");
                                System.out.println("set alias "+(success?"success":"failed"));
                            }
                            else if(event.equals("message"))
                            {
                                JSONObject jsonObject = (JSONObject)args[0];
                                String msg = jsonObject.getString("msg");
                                System.out.println(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(SocketIOException e) {

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

//            System.out.println( "Serial port connected");

//            new Thread(new ServerRunnable()).start();

        }

    }
}
