package com.luugiathuy.apps.remotebluetooth;

public class RemoteBluetoothServer {

    public static void main(String[] args) {
        //readCSV readThread = new readCSV();
       // readThread.start();
        Vehicle vehicleObject = Vehicle.getInstance();
        vehicleObject.start();
		Thread waitThread = new Thread(new WaitThread());
        waitThread.start();

       // SampleSPPClient SampleSPPClientobj  = new SampleSPPClient();

	}

    }

