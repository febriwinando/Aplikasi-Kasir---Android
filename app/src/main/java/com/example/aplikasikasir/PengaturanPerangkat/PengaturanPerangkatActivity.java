package com.example.aplikasikasir.PengaturanPerangkat;

import static android.content.ContentValues.TAG;

import static com.example.aplikasikasir.PengaturanPerangkat.UtilBluetooth.CONNECTING_STATUS;
import static com.example.aplikasikasir.PengaturanPerangkat.UtilBluetooth.MESSAGE_READ;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikasir.Database.DatabaseKasir;
import com.example.aplikasikasir.FirebaseModel.ModelMejaFirebase;
import com.example.aplikasikasir.FirebaseModel.ModelProdukFirebase;
import com.example.aplikasikasir.FirebaseModel.ModelTAXFirebase;
import com.example.aplikasikasir.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class PengaturanPerangkatActivity extends AppCompatActivity {
    private ArrayAdapter<String> mBTArrayAdapter;
    UUID sppUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static Handler mHandler;
    public static BluetoothDevice bluetoothDevice;
    private static final int REQUEST_ENABLE_BT = 1;
    public  static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Switch switchOnOffBluetooth, switchCashDrawer, switchBarcode, switchTipeStore;
    public static ConnectedThread mConnectedThread;
    DaftarBluetoothAdapter listBluetoothAdapter;
    UtilBluetooth utilBluetooth = new UtilBluetooth();
    SintaksPOST sintaksPOST = new SintaksPOST();
    TableRow cariPerangkat, ujiPrinter, trUnduhDaftarproduk;
    ProgressBar progressBarSearchBluetooth;
    private static final ArrayList<ModelListBluetooth> listBluetooth = new ArrayList<>();
    static ArrayList<String> listNameBluetooth = new ArrayList<String>();
    static ArrayList<String> listAddressBluetooth = new ArrayList<String>();
    RecyclerView rvListBluetooth;
    Set<BluetoothDevice> devices;
    public static BluetoothSocket bluetoothSocket = null;
    public static OutputStream outputStreamer;
    TextInputEditText tietJumlahMeja, tietTax;
    TextView pengaturan_bluetooth_status;
    int statusCashDrawer = 0;
    private InputStream mInputStream;
    DatabaseKasir databaseKasir;
    private Set<BluetoothDevice> mPairedDevices;
    private byte[] mDataBuffer = new byte[1024];
    InputConnectThread inputConnectThread;
    ImageView ivKembaliDariPengaturanPerangkat, ivSuksesUnduh;
    ProgressBar pbUnduhProduk;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pengaturan_perangkat);

        databaseKasir = new DatabaseKasir(this);
        listNameBluetooth.clear();
        listAddressBluetooth.clear();
        listBluetooth.clear();

        switchOnOffBluetooth = findViewById(R.id.switchOnOffBluetooth);
        switchCashDrawer = findViewById(R.id.switchCashDrawer);
        switchBarcode = findViewById(R.id.switchBarcode);
        switchTipeStore = findViewById(R.id.switchTipeStore);
        tietJumlahMeja = findViewById(R.id.tietJumlahMeja);
        tietTax = findViewById(R.id.tietTax);
        pbUnduhProduk = findViewById(R.id.pbUnduhProduk);
        trUnduhDaftarproduk = findViewById(R.id.trUnduhDaftarproduk);
        ivSuksesUnduh = findViewById(R.id.ivSuksesUnduh);

        cariPerangkat = findViewById(R.id.cariPerangkat);
        progressBarSearchBluetooth = findViewById(R.id.progressBarSearchBluetooth);
        rvListBluetooth = findViewById(R.id.rvListBluetooth);
        ujiPrinter = findViewById(R.id.ujiPrinter);
        pengaturan_bluetooth_status = findViewById(R.id.pengaturan_bluetooth_status);
        ivKembaliDariPengaturanPerangkat = findViewById(R.id.ivKembaliDariPengaturanPerangkat);
        if (ContextCompat.checkSelfPermission(PengaturanPerangkatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(PengaturanPerangkatActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
            }
        }

        trUnduhDaftarproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbUnduhProduk.setVisibility(View.VISIBLE);
                dataProduk();
            }
        });


        mBTArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        devices = bluetoothAdapter.getBondedDevices();
        cariPerangkat.setOnClickListener(v -> {
                    progressBarSearchBluetooth.setVisibility(View.VISIBLE);
                    listPairedDevices();
                }
        );


        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
//                    mReadBuffer.setText(readMessage);
                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1){
                        pengaturan_bluetooth_status.setText((CharSequence) msg.obj);
                    }
                    else{
                        pengaturan_bluetooth_status.setText(getString(R.string.BTconnFail));
                    }
                }
            }
        };

        switchOnOffBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchOnOffBluetooth.isChecked()){
                    if (!bluetoothAdapter.isEnabled()){
                        nyalakanBluetooh();
                    }else{
                        Toast.makeText(PengaturanPerangkatActivity.this, "Bluetooth Telah Aktif", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    switchOnOffBluetooth.setChecked(true);
                }
            }
        });

        switchBarcode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Cursor statusBarcode = databaseKasir.getDataBarcode();
                if (switchBarcode.isChecked()){
                    if (statusBarcode.getCount() > 0){
                        while (statusBarcode.moveToNext()){
                            if (statusBarcode.getInt(1) == 0){
                                databaseKasir.updateDataBarcode(String.valueOf(statusBarcode.getInt(0)), "1");
                            }
                        }
                    }else{
                        databaseKasir.insertDataBarcode("1");
//                        Toast.makeText(PengaturanPerangkatActivity.this, "B1", Toast.LENGTH_SHORT).show();
                    }

                    switchBarcode.setChecked(true);
                }else{
                    if (statusBarcode.getCount() > 0){
                        while (statusBarcode.moveToNext()){
                            if (statusBarcode.getInt(1) == 1){
                                databaseKasir.updateDataBarcode(String.valueOf(statusBarcode.getInt(0)), "0");
                            }
                        }
                    }else{
//                        Toast.makeText(PengaturanPerangkatActivity.this, "B2", Toast.LENGTH_SHORT).show();

                        databaseKasir.insertDataBarcode("0");
                    }

                    switchBarcode.setChecked(false);
                }

            }
        });

        switchCashDrawer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Cursor statusCD = databaseKasir.getDataCd();
                int dataCD = statusCD.getCount();
                if (switchCashDrawer.isChecked()){
                    if (dataCD > 0){
                        while (statusCD.moveToNext()){
                            if (statusCD.getInt(1)== 0){
                                databaseKasir.updateDataCD(String.valueOf(statusCD.getInt(0)), "1");
                            }
                        }
                    }else{
                        databaseKasir.insertDataCD("1");
                    }
                    statusCashDrawer = 1;
                    switchCashDrawer.setChecked(true);
                }else {

                    if (dataCD > 0){
                        while (statusCD.moveToNext()){
                            if (statusCD.getInt(1) == 1){
                                databaseKasir.updateDataCD(String.valueOf(statusCD.getInt(0)), "0");
                            }
                        }
                    }else{
                        databaseKasir.insertDataCD("0");
                    }

                    statusCashDrawer = 0;
                    switchCashDrawer.setChecked(false);

                }
            }
        });

        switchTipeStore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Cursor statusModelToko = databaseKasir.getDataModelToko();
                int dataMT = statusModelToko.getCount();
                if (switchTipeStore.isChecked()){
                    if (dataMT > 0){
                        while (statusModelToko.moveToNext()){
                            if (statusModelToko.getInt(1) == 0){
                                databaseKasir.updateDataModelToko(String.valueOf(statusModelToko.getInt(0)), "1");
                            }
                        }
                    }else{
                        databaseKasir.insertDataModelToko("1");
                    }
                    switchTipeStore.setChecked(true);
                }else {
                    if (dataMT > 0){
                        while (statusModelToko.moveToNext()){
                            if (statusModelToko.getInt(1) == 1){
                                databaseKasir.updateDataModelToko(String.valueOf(statusModelToko.getInt(0)), "0");
                            }
                        }
                    }else{
                        databaseKasir.insertDataModelToko("0");
                    }

                    switchTipeStore.setChecked(false);

                }
            }
        });

        tietJumlahMeja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                databaseReference.child("Jumlah_Meja").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                                DatabaseReference databaseReferenceUpdate = databaseReference.child("Jumlah_Meja").child(dataSnapshot.getKey());
                                databaseReferenceUpdate.setValue(new ModelMejaFirebase(String.valueOf(s))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Cursor tblJumlahMeja = databaseKasir.getJlahMeja();
                                        if (tblJumlahMeja.getCount() > 0){
                                            while (tblJumlahMeja.moveToNext()){
                                                databaseKasir.updateDataJlhMeja(dataSnapshot.getKey(), String.valueOf(s));
                                            }
                                        }else{
                                            databaseKasir.insertDataJlhMeja(dataSnapshot.getKey(), String.valueOf(s));
                                        }
                                    }
                                });
                            }
                        }else{

                            DatabaseReference databaseReferenceMeja = databaseReference.child("Jumlah_Meja");
                            DatabaseReference databaseReferenceNew = databaseReferenceMeja.push();
                            String key = databaseReferenceNew.getKey();

                            databaseReferenceNew.setValue(new ModelMejaFirebase(String.valueOf(s))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Cursor tblJumlahMeja = databaseKasir.getJlahMeja();
                                    if (tblJumlahMeja.getCount() > 0){
                                        while (tblJumlahMeja.moveToNext()){
                                            databaseKasir.updateDataJlhMeja(key, String.valueOf(s));
                                        }
                                    }else{
                                        databaseKasir.insertDataJlhMeja(key, String.valueOf(s));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PengaturanPerangkatActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        DatabaseReference databaseReferenceMeja = databaseReference.child("Jumlah_Meja");
                        DatabaseReference databaseReferenceNew = databaseReferenceMeja.push();
                        String key = databaseReferenceNew.getKey();

                        databaseReferenceNew.setValue(new ModelMejaFirebase(String.valueOf(s))).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PengaturanPerangkatActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        if (bluetoothAdapter == null) {
//            // Perangkat tidak mendukung Bluetooth
//        }

//        mPairedDevices = bluetoothAdapter.getBondedDevices();

        ivKembaliDariPengaturanPerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ujiPrinter.setOnClickListener(v -> {
            if (utilBluetooth.isConnected(bluetoothSocket)){
                try {
                    outputStreamer = bluetoothSocket.getOutputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sintaksPOST.bill(PengaturanPerangkatActivity.this, bluetoothSocket, outputStreamer);
                openCashDrawer();
                Toast.makeText(this, ""+bluetoothSocket, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Belum Terhubung", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, ""+bluetoothSocket, Toast.LENGTH_SHORT).show();

        });


        tietTax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                databaseReference.child("TAX").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                                DatabaseReference databaseReferenceUpdate = databaseReference.child("TAX").child(dataSnapshot.getKey());
                                databaseReferenceUpdate.setValue(new ModelTAXFirebase(String.valueOf(s))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Cursor taxRestauran = databaseKasir.getDataTax();
                                        if (!s.toString().isEmpty()){
                                            if (taxRestauran.getCount() > 0){
                                                while (taxRestauran.moveToNext()){
                                                    databaseKasir.updateDataTax(databaseReferenceUpdate.getKey(), Integer.parseInt(String.valueOf(s)));
                                                }
                                            }else{
                                                databaseKasir.insertTaxRestauran(databaseReferenceUpdate.getKey(), Integer.parseInt(String.valueOf(s)));
                                            }
                                        }else{
                                            databaseKasir.deleteDataTax();
                                        }
                                    }
                                });
                            }
                        }else{

                            DatabaseReference databaseReferenceMeja = databaseReference.child("TAX");
                            DatabaseReference databaseReferenceNew = databaseReferenceMeja.push();
                            String key = databaseReferenceNew.getKey();

                            databaseReferenceNew.setValue(new ModelTAXFirebase(String.valueOf(s))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Cursor taxRestauran = databaseKasir.getDataTax();
                                    if (!s.toString().isEmpty()){
                                        if (taxRestauran.getCount() > 0){
                                            while (taxRestauran.moveToNext()){
                                                databaseKasir.updateDataTax(String.valueOf(taxRestauran.getInt(0)), Integer.parseInt(String.valueOf(s)));
                                            }
                                        }else{
                                            databaseKasir.insertTaxRestauran(key, Integer.parseInt(String.valueOf(s)));
                                        }
                                    }else{
                                        databaseKasir.deleteDataTax();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PengaturanPerangkatActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PengaturanPerangkatActivity.this, "Coba Lagi", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void dataProduk(){
        databaseReference.child("Produk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        ModelProdukFirebase modelProdukFirebase = dataSnapshot.getValue(ModelProdukFirebase.class);
                        simpanData(modelProdukFirebase.getId_produk(),
                                modelProdukFirebase.getBarcode_produk(),
                                modelProdukFirebase.getNama_produk(),
                                modelProdukFirebase.getHarga_produk(),
                                modelProdukFirebase.getKategori_produk(),
                                modelProdukFirebase.getSatuan_produk(),
                                modelProdukFirebase.getGambar_produk(),
                                dataSnapshot.getKey());

                        Log.d("TampilData", modelProdukFirebase.getNama_produk()+" == "+modelProdukFirebase.getHarga_produk());

                    }

                    handlerProgressDialog();
                }else{
                    pbUnduhProduk.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pbUnduhProduk.setVisibility(View.GONE);
            }
        });
    }

    public void handlerProgressDialog(){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            pbUnduhProduk.setVisibility(View.GONE);
            ivSuksesUnduh.setVisibility(View.VISIBLE);
        }, 3000);

    }

    private void simpanData(String idProduk, String finalKodeBarcodeString, String namaProduk1, String hargaProduk1,
                            String itemKategori_produk1, String itemSatuan_produk1, String mUri, String key) {

        Cursor dataProdukById = databaseKasir.getDataProduksByIdNew(idProduk);
        if (dataProdukById.getCount()>0){
            databaseKasir.updateDaftarProduk(idProduk,
                    finalKodeBarcodeString,
                    namaProduk1,
                    hargaProduk1,
                    itemKategori_produk1,
                    itemSatuan_produk1,
                    mUri,
                    key);
        }else{
            databaseKasir.insertDataProduk(idProduk,
                    finalKodeBarcodeString,
                    namaProduk1,
                    hargaProduk1,
                    itemKategori_produk1,
                    itemSatuan_produk1,
                    mUri,
                    key);
        }
        dataProdukById.close();

    }
    private void listPairedDevices(){
        listNameBluetooth.clear();
        listAddressBluetooth.clear();
        if (ContextCompat.checkSelfPermission(PengaturanPerangkatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(PengaturanPerangkatActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
            }
        }

        mBTArrayAdapter.clear();
        Set<BluetoothDevice> mPairedDevices = bluetoothAdapter.getBondedDevices();
        if(bluetoothAdapter.isEnabled()) {
            // put it's one to the adapter

            if (mPairedDevices.size() > 0 ){
//                tvInfoDaftarBluetoothPerangkat.setVisibility(View.VISIBLE);
            }
            for (BluetoothDevice device : mPairedDevices){

                listNameBluetooth.add(device.getName());
                listAddressBluetooth.add(device.getAddress());
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }

        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
        }

        listBluetooth.clear();
        listBluetooth.addAll(getListBluetooth());

        showRecyclerList();

        inputConnectThread = new InputConnectThread(mInputStream);
    }
    String barcode = "";
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if(e.getAction()==KeyEvent.ACTION_DOWN){
            Log.i(TAG,"dispatchKeyEvent: "+e.toString());
            char pressedKey = (char) e.getUnicodeChar();
            barcode += pressedKey;
        }

        if (e.getAction()==KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            pengaturan_bluetooth_status.setText(barcode);
            barcode="";
        }

        return super.dispatchKeyEvent(e);
    }

    public void openCashDrawer() {
        Cursor statusCD = databaseKasir.getDataCd();
        int dataCD = statusCD.getCount();
        if (dataCD > 0){
            while (statusCD.moveToNext()){
                if (statusCD.getInt(1) != 0){
                    try {
                        byte[] bytes = intArrayToByteArray(new int[]{27, 112, 0, 50, 250});
                        outputStreamer.write(bytes);
                        //            return true;
                    } catch (IOException e) {
                        Log.e(TAG, "Open drawer error", e);
                        //            return false;
                    }
                }
            }
        }
    }
    //
    private byte[] intArrayToByteArray(int[] Iarr) {
        byte[] bytes = new byte[Iarr.length];
        for (int i = 0; i < Iarr.length; i++) {
            bytes[i] = (byte) (Iarr[i] & 0xFF);
        }
        return bytes;
    }

    private void showRecyclerList(){

        rvListBluetooth.setLayoutManager(new LinearLayoutManager(this));
        listBluetoothAdapter = new DaftarBluetoothAdapter(listBluetooth);
        rvListBluetooth.setAdapter(listBluetoothAdapter);
        progressBarSearchBluetooth.setIndeterminate(false);
        progressBarSearchBluetooth.setVisibility(View.GONE);

        listBluetoothAdapter.setOnItemClickCallback(new DaftarBluetoothAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelListBluetooth data) {

                if (!bluetoothAdapter.isEnabled()){
                    nyalakanBluetooh();
                }

                if (!utilBluetooth.isConnected(bluetoothSocket)){
                    utilBluetooth.finishConnected(bluetoothSocket, outputStreamer);
                }

                menghubungkanPerangkatBLuetooth(data.getAddress_bluetooth(), data.getNama_bluetooth(), PengaturanPerangkatActivity.this);

            }
        });
    }

    public static final boolean menghubungkanPerangkatBLuetooth(String addresBLuetoothPerangkat, String namaPerangkat, Activity context){
        final boolean[] fail = {false};

        new Thread()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                bluetoothDevice = bluetoothAdapter.getRemoteDevice(addresBLuetoothPerangkat);

                try {
                    bluetoothSocket = createBluetoothSocket(bluetoothDevice, context);
                } catch (IOException e) {
                    fail[0] = true;
//                    Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        {
                            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                        }
                    }
                    bluetoothSocket.connect();

                    if (bluetoothSocket.isConnected()){
//                        Toast.makeText(PengaturanPerangkatActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
//                        bluetooth_status.setText("Berhasil");
                    }
//                    bluetoothSocket.getMaxTransmitPacketSize();
                    bluetoothSocket.getMaxTransmitPacketSize();
                } catch (IOException e) {
                    try {
                        fail[0] = true;
                        bluetoothSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
//                        Toast.makeText(context, getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                    }
                }

                if(!fail[0]) {
                    mConnectedThread = new ConnectedThread(bluetoothSocket, mHandler);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, namaPerangkat)
                            .sendToTarget();
                }
            }
        }.start();

        return !fail[0];
    }

    private static BluetoothSocket createBluetoothSocket(BluetoothDevice device, Activity context) throws IOException {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
            }
        }
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, UtilBluetooth.BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(UtilBluetooth.BT_MODULE_UUID);
    }

    static ArrayList<ModelListBluetooth> getListBluetooth() {
        ArrayList<ModelListBluetooth> modelListBluetooths = new ArrayList<>();
        modelListBluetooths.clear();
        for (int position = 0; position < listNameBluetooth.size(); position++) {
            ModelListBluetooth modelListBluetooth = new ModelListBluetooth();
            modelListBluetooth.setNama_bluetooth(listNameBluetooth.get(position));
            modelListBluetooth.setAddress_bluetooth(listAddressBluetooth.get(position));
            modelListBluetooths.add(modelListBluetooth);
        }
        return modelListBluetooths;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                if (bluetoothAdapter.isEnabled()) {
                    switchOnOffBluetooth.setChecked(true);
                }else{
                    switchOnOffBluetooth.setChecked(false);
                }
                // Izin akses Bluetooth diberikan, lanjutkan dengan operasi Bluetooth yang diinginkan
            } else {

                nyalakanBluetooh();
            }
        }
    }

    public void nyalakanBluetooh(){
        if (ContextCompat.checkSelfPermission(PengaturanPerangkatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(PengaturanPerangkatActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_ENABLE_BT);
            }
        }

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    public void terhubung(){
        if (ContextCompat.checkSelfPermission(PengaturanPerangkatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(PengaturanPerangkatActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
            }
        }
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            try {


                // Dapatkan soket Bluetooth
                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(sppUuid);
                socket.connect();
                Log.d("Bluetoothts", "Perangkat terhubung: " + device.getName() + " (" + device.getAddress() + ")");
//                socket.close();

            } catch (IOException e) {
                // Perangkat tidak terhubung atau tidak siap menerima input stream
                Log.e("Bluetoothts", "Perangkat tidak terhubung: " + device.getName() + " (" + device.getAddress() + ")");
            }
        }
    }

    protected void onResume() {
        super.onResume();

        Cursor checkCD = databaseKasir.getDataCd();

        if (checkCD.getCount() == 0){
            switchCashDrawer.setChecked(false);
//            return;
        }

        while (checkCD.moveToNext()){
            if (checkCD.getString(1).equals("1")){
                switchCashDrawer.setChecked(true);
                statusCashDrawer = 1;
            }else{
                switchCashDrawer.setChecked(false);
                statusCashDrawer = 0;
            }
        }


        Cursor checkBarcode = databaseKasir.getDataBarcode();

        if (checkBarcode.getCount() == 0){
            switchBarcode.setChecked(false);
//            return;
        }

        while (checkBarcode.moveToNext()){
            if (checkBarcode.getString(1).equals("1")){
                switchBarcode.setChecked(true);
            }else{
                switchBarcode.setChecked(false);
            }
        }

        Cursor checkMeja = databaseKasir.getJlahMeja();
        if (checkMeja.getCount() > 0){
            while (checkMeja.moveToNext()){
                tietJumlahMeja.setText(String.valueOf(checkMeja.getString(1)));
            }
        }else{
            tietJumlahMeja.clearFocus();
        }


        Cursor checkTax = databaseKasir.getDataTax();
        if (checkTax.getCount() > 0){
            while (checkTax.moveToNext()){
                tietTax.setText(String.valueOf(checkTax.getInt(1)));
            }
        }else{
            tietTax.clearFocus();
        }

        if (ContextCompat.checkSelfPermission(PengaturanPerangkatActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(PengaturanPerangkatActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
            }


        }else{
            if (!bluetoothAdapter.isEnabled()){
                switchOnOffBluetooth.setChecked(false);

                nyalakanBluetooh();
            }else{
                switchOnOffBluetooth.setChecked(true);
            }
        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}