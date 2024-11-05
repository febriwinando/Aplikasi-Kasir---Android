package com.example.aplikasikasir.PengaturanPerangkat;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class InputConnectThread extends Thread{

    private final InputStream mmInputStream;

    public InputConnectThread(InputStream inputStream) {
        mmInputStream = inputStream;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int numBytes;

        while (true) {
            try {
                // Baca dari input stream
                numBytes = mmInputStream.read(buffer);

                // Konversi byte menjadi string
                String message = new String(buffer, 0, numBytes);

                // Lakukan sesuatu dengan pesan yang diterima
                Log.d("BluetoothThread", "Pesan diterima: " + message);

            } catch (IOException e) {
                Log.e("BluetoothThread", "Koneksi input stream terputus", e);
                break;
            }
        }
    }
}
