package com.luugiathuy.apps.remotebluetooth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Bhashi on 2/7/2017.
 */
public class Vehicle implements Runnable {
    String readSpeed;
    String readRPM;
    private static Vehicle Vehicleinstance = new Vehicle();
    private Vehicle(){}
    public static Vehicle getInstance(){
        return Vehicleinstance;
    }
    public void run(){
        readCSV();
    }

    public void start () {
        Thread readThread = new Thread(this, "thread 1");
        readThread.start ();
    }

    public void readCSV(){
        String csvFile = "/E:/Bashinee/Educational/Level 04/Semester 01/Project/data/Book1.csv";
        String cvsSplitBy = ",";
        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                readSpeed = data[0];
                readRPM = data[2];
                System.out.print(  readSpeed + " , " );
                System.out.print(  readRPM + " , " );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
