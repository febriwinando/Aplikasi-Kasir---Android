package com.example.aplikasikasir.TambahProduk;

import static com.example.aplikasikasir.utilcamera.AmbilFoto.REQUEST_CODE_ASK_PERMISSIONS;
import static com.example.aplikasikasir.utilcamera.AmbilFoto.getDriveFilePath;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasikasir.Database.DatabaseKasir;
import com.example.aplikasikasir.FirebaseModel.ModelProdukFirebase;
import com.example.aplikasikasir.R;
import com.example.aplikasikasir.utilcamera.AmbilFoto;
import com.example.aplikasikasir.viewnotif.ViewNotif;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class TambahProdukActivity extends AppCompatActivity {
    ImageView gambarProduk, ibSaveDataProduk;
    private static final String KEY_ALLOW_MANUAL_INPUT = "allow_manual_input";
    private boolean allowManualInput;
    TextView notifScanner, tvTambahGambar;
    String[] satuan =  {"Porsi", "Gelas", "Renteng","Kotak","Pcs","Batang","Rol", "Unit", "Lisensi", "Rim"};
    String[] kategori =  {"Makanan", "Minuman", "Cemilan", "Bir", "Peralatan", "Deterjen", "Jajanan", "Alat Tulis"};
    AutoCompleteTextView satuanProduk;
    AutoCompleteTextView kategoriProduk;
    ArrayAdapter<String> adapterSatuan;
    ArrayAdapter<String> adapterKategori;
    TextInputEditText kodeBarcode, namaProduk, hargaProduk;
    TextInputLayout tilBarcodeTambahProduk;
    LinearLayout addGambar;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] imageInByte;
    Bitmap rotationBitmapSurat;
    DatabaseKasir databaseKasir;
    ProgressDialog progressDialog;
    String itemKategori_produk, itemSatuan_produk;
    Animation animation;
    ImageView kembaliDariTambahProduk;
    ViewNotif viewNotif = new ViewNotif();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference;
    String id_produk;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_tambah_produk);

        storageReference = FirebaseStorage.getInstance().getReference("gambar_pruduk");
        databaseKasir = new DatabaseKasir(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.scale_animator);

        tvTambahGambar = findViewById(R.id.tvTambahGambar);
        notifScanner = findViewById(R.id.notifScanner);
        kodeBarcode = findViewById(R.id.kodeBarcode);
        namaProduk = findViewById(R.id.namaProduk);
        hargaProduk = findViewById(R.id.hargaProduk);
        kategoriProduk = findViewById(R.id.kategoriProduk);
        satuanProduk = findViewById(R.id.satuanProduk);
        addGambar = findViewById(R.id.addGambar);
        gambarProduk = findViewById(R.id.gambarProduk);
        ibSaveDataProduk = findViewById(R.id.ibSaveDataProduk);
        kembaliDariTambahProduk = findViewById(R.id.kembaliDariTambahProduk);
        tilBarcodeTambahProduk = findViewById(R.id.tilBarcodeTambahProduk);


        kategoriProduk.setEnabled(false);
        satuanProduk.setEnabled(false);

        addGambar.setOnClickListener(v -> viewLampiran());

        adapterSatuan = new ArrayAdapter<>(TambahProdukActivity.this, R.layout.list_item, satuan);
        satuanProduk.setAdapter(adapterSatuan);

        satuanProduk.setOnItemClickListener((parent, view12, position, id) -> {
            itemSatuan_produk = parent.getItemAtPosition(position).toString();
        });

        adapterKategori = new ArrayAdapter<>(TambahProdukActivity.this, R.layout.list_item, kategori);
        kategoriProduk.setAdapter(adapterKategori);

        kategoriProduk.setOnItemClickListener((parent, view1, position, id) -> {
            itemKategori_produk = parent.getItemAtPosition(position).toString();
        });

//        Check Status Barcode
        Cursor checkBarcode = databaseKasir.getDataBarcode();
        if (checkBarcode.getCount() > 0){
            while (checkBarcode.moveToNext()){
                if (checkBarcode.getString(1).equals("1")){
                    kodeBarcode.requestFocus();
                }else{
                    namaProduk.requestFocus();
                }
            }
        }else{
            namaProduk.requestFocus();
        }

        ibSaveDataProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kodeBarcodeString = "";
                Cursor checkBarcode = databaseKasir.getDataBarcode();
                if (checkBarcode.getCount() > 0){
                    while (checkBarcode.moveToNext()){
                        if (checkBarcode.getString(1).equals("1")){
                            tilBarcodeTambahProduk.setVisibility(View.VISIBLE);
                            if (kodeBarcode.getText().toString().trim().isEmpty()){
                                kodeBarcode.requestFocus();

                                viewNotif.viewGagal(TambahProdukActivity.this, "Isi Kolom Barcode Terlebih Dahulu.");
                                return;
                            }else{
                                Cursor getDataProduk = databaseKasir.getDataProdukByBarcode(kodeBarcode.getText().toString().trim());
                                if (getDataProduk.getCount()>0){
                                    viewNotif.viewGagal(TambahProdukActivity.this, "Produk Sudah Ditambahkan.");

                                    return;
                                }
                            }
                        }
                    }

                    kodeBarcodeString = kodeBarcode.getText().toString().trim();
                }

                if(namaProduk.getText().toString().isEmpty()){
                    namaProduk.setError("Pastikan Nama Produk Sudah Terisi");
                }else if(hargaProduk.getText().toString().isEmpty()){
                    hargaProduk.setError("Pastikan Nama Produk Sudah Terisi");
                }else if(kategoriProduk.getText().toString().isEmpty()){
                    kategoriProduk.setError("Pastikan Nama Produk Sudah Terisi");
                }else if(hargaProduk.getText().toString().isEmpty()){
                    hargaProduk.setError("Pastikan Nama Produk Sudah Terisi");
                }


//                if(namaProduk.getText().toString().trim().isEmpty() || hargaProduk.getText().toString().trim().isEmpty() || itemKategori_produk == null|| itemSatuan_produk == null || imageInByte == null){
//
//
//                    viewNotif.viewGagal(TambahProdukActivity.this, "Pastikan semua kolom telah terisi.");
//                    return;
//                }

                if(uriGambarProduk != null){
                    id_produk = namaProduk.getText().toString().toLowerCase().replaceAll("\\s", "");
                    String finalKodeBarcodeString = kodeBarcodeString;
                    databaseReference.child("Produk").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean status_id_produk = false;

                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                ModelProdukFirebase modelProdukFirebase = dataSnapshot.getValue(ModelProdukFirebase.class);
                                if (modelProdukFirebase.getId_produk().toString().equals(id_produk)){
                                    status_id_produk = true;
                                }

                            }

                            if (status_id_produk){
                                Toast.makeText(TambahProdukActivity.this, "Produk Sudah Tersedia, Periksa Kembali", Toast.LENGTH_SHORT).show();
                            }else{
                                simpanFirebase(finalKodeBarcodeString);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        kodeBarcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    // mengembalikan fokus ke EditText
                    namaProduk.requestFocus();
                    namaProduk.clearComposingText();
                    return true;
                } else {
                    return false;
                }
            }
        });

//        kodeBarcode.requestFocus();

        kembaliDariTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





    private void simpanFirebase(String kodeBarcodeString){
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                +"."+getFileExtention(uriGambarProduk));
        uploadTask = fileReference.putFile(uriGambarProduk);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }

                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    String mUri  = downloadUri.toString();
                    DatabaseReference produkRef = databaseReference.child("Produk");
                    DatabaseReference newProdukRef = produkRef.push();
                    String key = newProdukRef.getKey();

                    newProdukRef.setValue(new ModelProdukFirebase(id_produk, "", namaProduk.getText().toString().trim(),
                                    hargaProduk.getText().toString().trim(),  itemKategori_produk, itemSatuan_produk, mUri))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    databaseReference.onDisconnect();
                                    simpanData(id_produk,
                                            kodeBarcodeString,
                                            namaProduk.getText().toString(),
                                            hargaProduk.getText().toString().trim(),
                                            itemKategori_produk.toString().trim(),
                                            itemSatuan_produk.toString().trim(),
                                            mUri,
                                            key);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TambahProdukActivity.this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(TambahProdukActivity.this, "Gagal", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TambahProdukActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void simpanData(String idProduk, String finalKodeBarcodeString, String namaProduk1, String hargaProduk1,
                            String itemKategori_produk1, String itemSatuan_produk1, String mUri, String key) {

        Cursor dataProdukById = databaseKasir.getDataProduksByIdNew(idProduk);
        if (dataProdukById.moveToFirst()){
            viewNotif.viewError(TambahProdukActivity.this);
        }else{
            boolean instertProduk = databaseKasir.insertDataProduk(idProduk, finalKodeBarcodeString, namaProduk1,
                    hargaProduk1,itemKategori_produk1,itemSatuan_produk1, mUri, key);
            if (instertProduk){

                kodeBarcode.setText("");
                namaProduk.setText("");
                hargaProduk.setText("");
                kategoriProduk.setText("");
                satuanProduk.setText("");
                gambarProduk.setVisibility(View.GONE);
                namaProduk.requestFocus();
                viewNotif.viewSukses(TambahProdukActivity.this);

            }else {
                viewNotif.viewGagal(TambahProdukActivity.this, "Gagal menambah produk, coba lagi.");
            }
        }
        dataProdukById.close();

    }

    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    String barcode = "";
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
//        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        if(e.getAction()==KeyEvent.ACTION_DOWN){

            Log.i("Barcode","dispatchKeyEvent: "+e.toString());
            char pressedKey = (char) e.getUnicodeChar();
            barcode += pressedKey;
        }

        if (e.getAction()==KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            kodeBarcode.setText(barcode);
            namaProduk.setText("");
            hargaProduk.setText("");
            barcode = "";
        }


//        Cursor checkBarcode = databaseKasir.getDataBarcode();
//        if (checkBarcode.getCount() > 0){
//            while (checkBarcode.moveToNext()){
//                if (checkBarcode.getString(1).equals("1")){
//                    kodeBarcode.setText("");
//                }else{
//                    namaProduk.setText("");
//                    namaProduk.requestFocus();
//                }
//            }
//        }else{
//            namaProduk.setText("");
//
//            namaProduk.requestFocus();
//        }

        return super.dispatchKeyEvent(e);

    }


    File imageFile;

    private String currentPhotoPath;
    private void ambilFoto(String jenis_sumber){
        progressDialog.dismiss();
        if (jenis_sumber.equals("galeri")){
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Pilih Gambar"), 33);
        }else{
            String filename = "photo";
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
            try {
                imageFile = File.createTempFile(filename, ".png", storageDirectory);
                currentPhotoPath = null;
                currentPhotoPath = imageFile.getAbsolutePath();
                Uri imageUri = FileProvider.getUriForFile(TambahProdukActivity.this, "go.kreasi.kasirapp.fileprovider", imageFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 100);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        Cursor checkBarcode = databaseKasir.getDataBarcode();
        if (checkBarcode.getCount() > 0){
            while (checkBarcode.moveToNext()){
                if (checkBarcode.getString(1).equals("0")){
                    tilBarcodeTambahProduk.setVisibility(View.GONE);
                }else{
                    tilBarcodeTambahProduk.setVisibility(View.VISIBLE);
                }
            }
        }else{
            tilBarcodeTambahProduk.setVisibility(View.GONE);
        }
    }

    AmbilFoto ambilFoto = new AmbilFoto(TambahProdukActivity.this);
    Uri uriGambarProduk;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED){
            if (requestCode == 33) {

                gambarProduk.setVisibility(View.VISIBLE);
                requestPermission();
                uriGambarProduk = data.getData();
                String FilePath2  = getDriveFilePath(uriGambarProduk, TambahProdukActivity.this);

                File file1 = new File(FilePath2);
                Bitmap bitmap = ambilFoto.fileBitmap(file1);
                rotationBitmapSurat = Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight(), AmbilFoto.exifInterface(FilePath2), true);
                gambarProduk.setImageBitmap(rotationBitmapSurat);

                byteArrayOutputStream = new ByteArrayOutputStream();
                rotationBitmapSurat.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
                imageInByte = byteArrayOutputStream.toByteArray();

                gambarProduk.startAnimation(animation);
                tvTambahGambar.setText("Gambar Produk");

            } else if (requestCode == 100) {

                gambarProduk.setVisibility(View.VISIBLE);
                File file = new File(currentPhotoPath);
                Bitmap bitmap = ambilFoto.fileBitmap(file);
                rotationBitmapSurat = Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight(), AmbilFoto.exifInterface(currentPhotoPath), true);

                byteArrayOutputStream = new ByteArrayOutputStream();
                rotationBitmapSurat.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
                imageInByte = byteArrayOutputStream.toByteArray();
                gambarProduk.setImageBitmap(rotationBitmapSurat);

                gambarProduk.startAnimation(animation);
                tvTambahGambar.setText("");
            }
        }

    }

    public void viewLampiran(){
        Dialog dialogLampiran = new Dialog(TambahProdukActivity.this, R.style.DialogStyle);
        dialogLampiran.setContentView(R.layout.view_add_gambar);
        LinearLayout llFileManager = dialogLampiran.findViewById(R.id.llFileManager);
        LinearLayout llKamera = dialogLampiran.findViewById(R.id.llKamera);
        ImageView ivTutupViewLampiran = dialogLampiran.findViewById(R.id.ivTutupViewLampiran);

        llFileManager.setOnClickListener(v -> {
            progressDialog = new ProgressDialog(TambahProdukActivity.this, R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Sedang memproses...");
            progressDialog.show();
            ambilFoto("galeri");
            dialogLampiran.dismiss();
        });


        llKamera.setOnClickListener(v -> {
            progressDialog = new ProgressDialog(TambahProdukActivity.this, R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Sedang memproses...");
            progressDialog.show();
            ambilFoto("kamera");
            dialogLampiran.dismiss();
        });

        ivTutupViewLampiran.setOnClickListener(v -> dialogLampiran.dismiss());

        dialogLampiran.show();
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(TambahProdukActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(TambahProdukActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_ALLOW_MANUAL_INPUT, allowManualInput);
        allowManualInput = savedInstanceState.getBoolean(KEY_ALLOW_MANUAL_INPUT);

        super.onSaveInstanceState(savedInstanceState);
    }



    private String getSuccessfulMessage(Barcode barcode) {
        String barcodeValue =
                String.format(
                        Locale.US,
                        "Display Value: %s\nRaw Value: %s\nFormat: %s\nValue Type: %s",
                        barcode.getDisplayValue(),
                        barcode.getRawValue(),
                        barcode.getFormat(),
                        barcode.getValueType());
        return getString(R.string.barcode_result, barcodeValue);
    }

    @SuppressLint("SwitchIntDef")
    private String getErrorMessage(Exception e) {
        if (e instanceof MlKitException) {
            switch (((MlKitException) e).getErrorCode()) {
                case MlKitException.CODE_SCANNER_CAMERA_PERMISSION_NOT_GRANTED:
                    return getString(R.string.error_camera_permission_not_granted);
                case MlKitException.CODE_SCANNER_APP_NAME_UNAVAILABLE:
                    return getString(R.string.error_app_name_unavailable);
                default:
                    return getString(R.string.error_default_message, e);
            }
        } else {
            return e.getMessage();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Tombol kembali di ActionBar ditekan
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}