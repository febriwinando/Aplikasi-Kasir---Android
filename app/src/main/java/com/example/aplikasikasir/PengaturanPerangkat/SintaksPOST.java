package com.example.aplikasikasir.PengaturanPerangkat;

import static com.example.aplikasikasir.FormatWaktu.SIMPLE_FORMAT_TANGGAL_INVOICE;
import static com.example.aplikasikasir.FormatWaktu.formatRupiah;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.aplikasikasir.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SintaksPOST {
    ArrayList<ModelProdukFinalTransaksi> modelProdukFinalTransaksis = new ArrayList<>();

    public void droping(Activity context,
                        ArrayList<ModelProdukFinalTransaksi> modelProdukFinalTransaksis,
                        BluetoothSocket mBTSocket, OutputStream outputStream,
                        String pengunjung, String invoice, String tanggal, String costumer,
                        String tax, String order, String uangditerima, String uangkembali, String totalTansaksi, String status) {
        mBTSocket.getRemoteDevice();
        mBTSocket.isConnected();
        //print command
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream = mBTSocket.getOutputStream();

            byte[] printformat = new byte[]{0x1B,0x21,0x03};
            outputStream.write(printformat);

            printCustom("KRIPSANG LUMER MAK EL",1,1, outputStream);
            printCustom("Jl. Danau Laut Tawar No.7, Kec.Padang Hulu",0,1, outputStream);
            printCustom("Hot Line: 0857-6218-3348",0,1, outputStream);
//            printText(leftRightAlign("Outlet: ", nama_outlet.toUpperCase()), outputStream);
            printText(leftRightAlign("Tanggal", SIMPLE_FORMAT_TANGGAL_INVOICE.format(new Date())), outputStream);
            printText(leftRightAlign("Invoice", invoice), outputStream);
            printText(leftRightAlign("Pelanggan" , costumer), outputStream);

//            Float total = 0.00F;
            for (ModelProdukFinalTransaksi modelDroping: modelProdukFinalTransaksis){
                float jumlahDroping = Float.parseFloat(modelDroping.getHarga_produk()) * Float.parseFloat(String.valueOf(modelDroping.getQty()));
                printText(leftRightAlign(modelDroping.getQty()+"   "+modelDroping.getNama_produk().toUpperCase(), formatRupiah.format(jumlahDroping)), outputStream);
//                total += jumlahDroping;
//                printNewLine(outputStream);
            }
            printCustom(new String(new char[42]).replace("\0", "."),0,1, outputStream);
            printText(leftRightAlign("Jumlah Pesanan" , order), outputStream);
            printText(leftRightAlign("PPN" , "(10%) "+tax), outputStream);
            printText(leftRightAlign("Total Belanja" , totalTansaksi), outputStream);
            if (status.equals("1")){

                printText(leftRightAlign("Uang Diterima" , uangditerima), outputStream);
                printText(leftRightAlign("Uang Kembali" , uangkembali), outputStream);
                printNewLine(outputStream);

            }else{
                printNewLine(outputStream);
                printNewLine(outputStream);
                printCustom("\n\n\n",0,1, outputStream);

            }

            if (status.equals("1")){
                printCustom("LUNAS",1,1, outputStream);
                printNewLine(outputStream);
                printCustom("Thank you for coming & we look",0,1, outputStream);
                printCustom("forward to serve you again\n\n\n",0,1, outputStream);
            }else if (status.equals("2")){
                printCustom("HUTANG",1,1, outputStream);
                printNewLine(outputStream);
                printCustom("Thank you for coming & we look",0,1, outputStream);
                printCustom("forward to serve you again\n\n\n",0,1, outputStream);
            }

//            printPhoto(context, R.drawable.imgtest, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    //    private static OutputStream outputStream;
    public void bill(Activity context, BluetoothSocket mBTSocket, OutputStream outputStream) {
        mBTSocket.getRemoteDevice();
        mBTSocket.isConnected();
        //print command
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream = mBTSocket.getOutputStream();

            byte[] printformat = new byte[]{0x1B,0x21,0x03};
            outputStream.write(printformat);

            printCustom("Febri Winando Purba",2,1, outputStream);
            printCustom("PT. KEGAGALAN Ltd.",0,1, outputStream);
//                printPhoto(R.drawable.ic_icon_pos);
            printCustom("Hot Line: 0821 6655 1658",0,1, outputStream);
            printCustom("Vat Reg : 0000000000, Mushak : 11",0,1, outputStream);
            String[] dateTime = getDateTime();
            printText(leftRightAlign(dateTime[0], dateTime[1]), outputStream);
            printText(leftRightAlign("Qty: Name" , "Price "), outputStream);
            printCustom(new String(new char[32]).replace("\0", "."),0,1, outputStream);
            printText(leftRightAlign("Total" , "5,000,000/="), outputStream);
            printNewLine(outputStream);
            printCustom("Thank you for coming & we look",0,1, outputStream);
            printCustom("forward to serve you again",3,1, outputStream);
            printNewLine(outputStream);
            printNewLine(outputStream);
            printUnicode(outputStream);
//            printPhoto(context, R.drawable.logo, outputStream);
            printNewLine(outputStream);
            printNewLine(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //print photo
    public void printPhoto(Activity context, int img, OutputStream outputStream) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                    img);
            if(bmp != null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command, outputStream);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

//    public void printPhoto(Activity context, int img, OutputStream outputStream) {
//        try {
//            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), img);
//            if (bmp != null) {
//                byte[] command = Utils.decodeBitmap(bmp);
//                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
//                outputStream.write(command);
//                outputStream.flush();  // Pastikan untuk memanggil flush setelah menulis ke outputStream
//            } else {
//                Log.e("Print Photo error", "the file isn't exists");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("PrintTools", "Error printing photo");
//        }
//    }

    //print unicode
    public void printUnicode(OutputStream outputStream){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void printCustom(String msg, int size, int align, OutputStream outputStream) {

        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }

            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print text
    public void printText(String msg, OutputStream outputStream) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg, OutputStream outputStream) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //print new line
    public void printNewLine(OutputStream outputStream) {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String leftRightAlign(String str1, String str2) {
        String ans = str1+""+str2;
        if(ans.length() <= 42){
            int n = (42 - (str1.length() + str2.length()));
            ans = str1 +""+ new String(new char[n]).replace("\0", " ")+""+ str2;
        }
        return ans;
    }

//    public String leftRightAlign(String str1, String str2) {
//        String ans = str1+""+ str2;
//        if(ans.length() <= 42){
//            int n = (42 - (str1.length() + str2.length()));
//            ans = ans.length()+" "+str1 +" "+ String.valueOf(n)+ str2;
//        }
//
//        return ans;
//    }
    public String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

    public void openCashDrawer(OutputStream outputStream) {
        try {
            byte[] bytes = intArrayToByteArray(new int[]{27, 112, 0, 50, 250});
            outputStream.write(bytes);
//            return true;
        } catch (IOException e) {

//            return false;
        }
    }

    private byte[] intArrayToByteArray(int[] Iarr) {
        byte[] bytes = new byte[Iarr.length];
        for (int i = 0; i < Iarr.length; i++) {
            bytes[i] = (byte) (Iarr[i] & 0xFF);
        }
        return bytes;
    }



}
