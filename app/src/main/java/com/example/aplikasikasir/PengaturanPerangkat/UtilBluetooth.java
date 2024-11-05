package com.example.aplikasikasir.PengaturanPerangkat;

import android.bluetooth.BluetoothSocket;

import java.io.OutputStream;
import java.util.UUID;

public class UtilBluetooth {
    public static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    public final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    public final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status
    public boolean isConnected(BluetoothSocket mBTSocket) {
        return mBTSocket != null && mBTSocket.isConnected();
    }

    public void finishConnected(BluetoothSocket mBTSocket, OutputStream outputStream) {
        if (mBTSocket != null) {
            try {
                Thread.sleep(2000);
                outputStream.close();
                mBTSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mBTSocket = null;
        }
    }



}
