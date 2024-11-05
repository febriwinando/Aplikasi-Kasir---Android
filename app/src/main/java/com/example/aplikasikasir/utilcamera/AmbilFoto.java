package com.example.aplikasikasir.utilcamera;


import static com.example.aplikasikasir.utilcamera.CompressFile.getFileExt;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AmbilFoto {

    Context context;

    public AmbilFoto(Context context) {
        this.context = context;
    }

    final public static int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public static Matrix exifInterface(String currentPhotoPath){

        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(currentPhotoPath);
        }catch (IOException e){
            e.printStackTrace();
        }

        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_UNDEFINED:
                matrix.setRotate(0);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;
            default:
        }

        return matrix;
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(fileUri.getPath());
        Log.i("here is error",file.getAbsolutePath());
        // create RequestBody instance from file

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    public Bitmap fileBitmap(File file){
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;


        if (getFileExt(file.getName()).equals("png") || getFileExt(file.getName()).equals("PNG")) {
            if (Build.VERSION.SDK_INT > 21){
                o.inSampleSize = 3;
            }else{
                o.inSampleSize = 3;
            }
        } else {
            if (Build.VERSION.SDK_INT > 27){
                o.inSampleSize = 2;
            }else{
                o.inSampleSize = 2;
            }
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BitmapFactory.decodeStream(inputStream, null, o);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The new size we want to scale to
        final int REQUIRED_SIZE = 110;

        // Find the correct scale value. It should be the power of 2.
        int scale = 1;
        if (Build.VERSION.SDK_INT > 27){
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
        }else{
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 3;
            }
        }


        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);

        return selectedBitmap;
    }
    public String getPDFPath(Uri uri, Context context, String idE, String time){
        String absolutePath = "";
        try{
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] pdfInBytes = new byte[inputStream.available()];
            inputStream.read(pdfInBytes);
            int offset = 0;
            int numRead = 0;
            while (offset < pdfInBytes.length && (numRead = inputStream.read(pdfInBytes, offset, pdfInBytes.length - offset)) >= 0) {
                offset += numRead;
            }

            String mPath = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {

                mPath= context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"absensi-"+idE+"-"+time + ".pdf";
            }
            else
            {
                mPath= Environment.getExternalStorageDirectory().toString() + "absensi-"+idE+"-"+time + ".pdf";
            }

            File pdfFile = new File(mPath);
            OutputStream op = new FileOutputStream(pdfFile);
            op.write(pdfInBytes);
            absolutePath = pdfFile.getPath();

        }catch (Exception ae){
            ae.printStackTrace();
        }
        return absolutePath;
    }

    public static String getDriveFilePath(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();

        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
