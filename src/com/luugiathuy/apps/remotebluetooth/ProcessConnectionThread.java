package com.luugiathuy.apps.remotebluetooth;

import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnection;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ProcessConnectionThread implements Runnable{

    private StreamConnection mConnection;
    UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
    String connectionString = "btspp://localhost:" + uuid +";name=RemoteBluetooth";
    String s = "";
    String speed;
    String RPM;
    Vehicle vehicleObject = Vehicle.getInstance();

    // Constant that indicate command from devices
    private static final int EXIT_CMD = -1;
    private static final int KEY_RIGHT = 1;
    private static final int KEY_LEFT = 2;

    public ProcessConnectionThread(StreamConnection connection)
    {
        mConnection = connection;
    }

    @Override
    public void run() {
        try {

            // prepare to receive data
            InputStream inputStream = mConnection.openInputStream();
            OutputStream outStream = mConnection.openOutputStream();
            byte[] msgBuffer = new byte[0];
            System.out.println("-----------------------------");
            System.out.println(vehicleObject.readSpeed);
            System.out.println("-----------------------------");
            String prefixSpeed = "410D";
            String prefixRPM = "410C";
            System.out.println(prefixSpeed.concat(Integer.toHexString(Integer.parseInt(vehicleObject.readSpeed))));
            System.out.println("waiting for input");

            while (true) {
                int char1;// = inputStream.read();
                int i=0;
                byte[] buffer = new byte[1024];
                while((char1 = inputStream.read())!= '\r'){
                    buffer[i]=(byte)char1;
                    i++;
                }
                String tmp = new String(buffer, 0, i);
                String request = tmp.trim();

                /*
                System.out.println("command  " + char1);
                System.out.println(Character.toString ((char) char1));
                s = s.concat(Character.toString((char) char1));
*/

                //processCommand(char1);
                System.out.println(request);
                String protocol = "OK\n>";
                String displayProtocol = "AUTOMATIC\n>";
                String RPM = "410C14E9\n>";

                //String speed = "410D21\n>";
                if (request.equals("ATSP0")) {
                    msgBuffer = protocol.getBytes();
                }
                if (request.equals("ATDP")) {
                    msgBuffer = displayProtocol.getBytes();
                }
                if (request.equals("ATE0")) {
                    msgBuffer = RPM.getBytes();
                }
                if (request.equals("ATS0")) {
                    msgBuffer = RPM.getBytes();
                }
                if (request.equals("010C")) {
                    RPM = prefixRPM.concat(Integer.toHexString(Integer.parseInt(vehicleObject.readRPM)*4).concat("\n>"));
                    msgBuffer = RPM.getBytes();
                }
                if (request.equals("010D")) {
                    speed = prefixSpeed.concat(Integer.toHexString(Integer.parseInt(vehicleObject.readSpeed)).concat("\n>"));
                    System.out.println(speed);
                    msgBuffer = speed.getBytes();
                }
                if (outStream != null) {
                    try {
                        System.out.println("writing to output buffer");
                        System.out.println(msgBuffer);
                        outStream.write(msgBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ttttttttttttttttttttttttttttt");
        System.out.println(s);
    }


    private void send(String response) {

    }

    /**
     * Process the command from client
     * @param command the command code
     */
    private void processCommand(int command) {
        try {
            Robot robot = new Robot();
            switch (command) {
                case KEY_RIGHT:
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    System.out.println("Right");
                    // release the key after it is pressed. Otherwise the event just keeps getting trigged
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    break;
                case KEY_LEFT:
                    robot.keyPress(KeyEvent.VK_LEFT);
                    System.out.println("Left");
                    // release the key after it is pressed. Otherwise the event just keeps getting trigged
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
