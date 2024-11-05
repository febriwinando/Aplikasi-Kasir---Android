package com.example.aplikasikasir.Database;


import static com.example.aplikasikasir.Database.ModelBluetooth.BarcodeStatus.BARCODE_TBL;
import static com.example.aplikasikasir.Database.ModelBluetooth.BarcodeStatus.BCD_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.BarcodeStatus.BCD_STATUS;
import static com.example.aplikasikasir.Database.ModelBluetooth.CashDrawer.DATA_CD;
import static com.example.aplikasikasir.Database.ModelBluetooth.CashDrawer.ID_CD;
import static com.example.aplikasikasir.Database.ModelBluetooth.CashDrawer.STATUS_CD;
import static com.example.aplikasikasir.Database.ModelBluetooth.JumlahMeja.MEJA;
import static com.example.aplikasikasir.Database.ModelBluetooth.JumlahMeja.M_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.JumlahMeja.M_JUMLAH;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelBarangMasuk.BARANG_MASUK;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelBarangMasuk.BM_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelBarangMasuk.BM_IDPRODUK;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelBarangMasuk.BM_INVOICE;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelBarangMasuk.BM_QTY;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelBarangMasuk.BM_TGL_MASUK;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelOrderanMeja.OM_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelOrderanMeja.OM_INVOICE;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelOrderanMeja.OM_MEJA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelOrderanMeja.OM_STATUS;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelOrderanMeja.OM_TBL;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPerangkatPrinter.ALAMAT_PRINTER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPerangkatPrinter.DATA_PERANGKAT_PRINTER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPerangkatPrinter.ID_PRINTER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPerangkatPrinter.NAMA_PRINTER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPerangkatPrinter.STATUS_KONEKSI;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_COSTUMER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_DAFTAR;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_HARGA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_INVOICE;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_JAM;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_JUMLAH;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_MEJA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_ORDER_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_STATUS;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_TANGGAL;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesanan.PM_TBL;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_COSTUMER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_DAFTAR;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_HARGA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_INVOICE;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_JAM;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_JUMLAH;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_MEJA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_ORDER_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_STATUS;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_TANGGAL;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelPesananTambah.TAMBAH_PM_TBL;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.PRODUK;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_BARCODE;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_GAMBAR;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_HARGA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_KATEGORI;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_KEY_FIREBASE;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_NAMA_PRDUK;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_QTY;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelProduk.P_SATUAN;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.ALAMAT;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.DATA_TOKO;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.KECAMATAN;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.KELURAHAN;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.KODE_POS;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.KONTAK;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.KOTA;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.NAMA_TOKO;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.OWNER;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelToko.PROVINSI;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelTokoStatus.MODEL_TOKO_TBL;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelTokoStatus.MT_ID;
import static com.example.aplikasikasir.Database.ModelBluetooth.ModelTokoStatus.MT_STATUS;
import static com.example.aplikasikasir.Database.ModelBluetooth.Tax.TAX;
import static com.example.aplikasikasir.Database.ModelBluetooth.Tax.TAX_BESARAN;
import static com.example.aplikasikasir.Database.ModelBluetooth.Tax.TAX_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseKasir extends SQLiteOpenHelper {
    public static final String NAMA_DATABASE = "kasir.db";

    public DatabaseKasir(Context context) {
        super(context, NAMA_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            sqLiteDatabase.execSQL("create table "+DATA_TOKO+" ("+ ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAMA_TOKO+" TEXT, " +OWNER+ " TEXT,"+
                    KONTAK+" TEXT, "+ALAMAT+" TEXT, "+KELURAHAN+" TEXT, "+KECAMATAN+" TEXT, "+KOTA+" TEXT, "+PROVINSI+" TEXT, "+KODE_POS+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+DATA_CD+" ("+ID_CD+" INTEGER PRIMARY KEY AUTOINCREMENT, "+STATUS_CD+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+DATA_PERANGKAT_PRINTER+" (ID_PRINTER INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PRINTER TEXT, ALAMAT_PRINTER TEXT, " +
                    "STATUS_KONEKSI TEXT) ");

            sqLiteDatabase.execSQL("create table "+PRODUK+" ("+P_ID+" TEXT, "+P_BARCODE+" TEXT, "+P_NAMA_PRDUK+" TEXT, "+
                    P_HARGA+" TEXT, " +P_KATEGORI+" TEXT, "+P_QTY+" TEXT, "+P_SATUAN+" TEXT, "+P_GAMBAR+" TEXT, "+P_KEY_FIREBASE+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+BARANG_MASUK+" ("+BM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+BM_INVOICE+" TEXT, "+BM_IDPRODUK+" TEXT, "+BM_QTY+" TEXT, "+
                    BM_TGL_MASUK+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+MEJA+" ("+M_ID+" TEXT, "+M_JUMLAH+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+BARCODE_TBL+" ("+BCD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+BCD_STATUS+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+MODEL_TOKO_TBL+" ("+MT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MT_STATUS+" TEXT) ");

            sqLiteDatabase.execSQL("create table "+PM_TBL+" ("+PM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PM_INVOICE+" TEXT, "+PM_ORDER_ID+" TEXT, " +
                    PM_MEJA+" TEXT, "+PM_DAFTAR +" TEXT, "+PM_JUMLAH+" TEXT, "+PM_HARGA+" TEXT, "+PM_STATUS+" TEXT, "+PM_TANGGAL+" TEXT, "+PM_JAM+" TEXT, "+PM_COSTUMER+" TEXT ) ");

            sqLiteDatabase.execSQL("create table "+TAMBAH_PM_TBL+" ("+TAMBAH_PM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TAMBAH_PM_INVOICE+" TEXT, "+TAMBAH_PM_ORDER_ID+" TEXT, " +
                    TAMBAH_PM_MEJA+" TEXT, "+TAMBAH_PM_DAFTAR +" TEXT, "+TAMBAH_PM_JUMLAH+" TEXT, "+TAMBAH_PM_HARGA+" TEXT, "+TAMBAH_PM_STATUS+" TEXT, "+TAMBAH_PM_TANGGAL+" TEXT, "+TAMBAH_PM_JAM+" TEXT, "+TAMBAH_PM_COSTUMER+" TEXT ) ");

            sqLiteDatabase.execSQL("create table "+OM_TBL+" ("+OM_ID+" TEXT, "+OM_INVOICE+" TEXT, "+OM_MEJA+" TEXT, " +
                    OM_STATUS+" TEXT ) ");


            sqLiteDatabase.execSQL("create table "+TAX+" ("+TAX_ID+" TEXT, "+TAX_BESARAN+" TEXT)");


        }catch (Exception e){
            sqLiteDatabase.beginTransaction();
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATA_TOKO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATA_PERANGKAT_PRINTER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATA_CD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRODUK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BARANG_MASUK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MEJA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BARCODE_TBL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MODEL_TOKO_TBL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PM_TBL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OM_TBL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TAX);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TAMBAH_PM_TBL);

        onCreate(sqLiteDatabase);
    }

    public boolean insertDataProduk(String id, String barcode, String nama_produk, String harga, String kategori, String satuan, String url_gambar, String id_firebase){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(P_ID, id);
        contentValues.put(P_BARCODE, barcode);
        contentValues.put(P_NAMA_PRDUK, nama_produk);
        contentValues.put(P_HARGA, harga);
        contentValues.put(P_KATEGORI, kategori);
        contentValues.put(P_QTY, "0");
        contentValues.put(P_SATUAN, satuan);
        contentValues.put(P_GAMBAR, url_gambar);
        contentValues.put(P_KEY_FIREBASE, id_firebase);

        long result = db.insert(PRODUK, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    public Cursor getDataProduks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+PRODUK+" order by "+P_KATEGORI+" asc", null);
        return res;
    }



    public Cursor getDataProduksByIdNew(String idProduk){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM data_produk_tbl WHERE "+P_ID+" = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{idProduk});
        return cursor;
    }



    public Cursor getDataProduksByIdNotSame(String idProduk){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM data_produk_tbl WHERE "+P_ID+" = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{idProduk});
        return cursor;
    }


    public Cursor getDataProduksById(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM "+PRODUK+" WHERE "+P_ID+" = ?";

// Lakukan parameter binding
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{id});
        return cursor;
    }


    public boolean updateDaftarProduk(String id, String barcode, String nama_produk, String harga, String kategori, String satuan, String url_gambar, String id_firebase){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(P_ID, id);
        contentValues.put(P_BARCODE, barcode);
        contentValues.put(P_NAMA_PRDUK, nama_produk);
        contentValues.put(P_HARGA, harga);
        contentValues.put(P_KATEGORI, kategori);
        contentValues.put(P_SATUAN, satuan);
        contentValues.put(P_GAMBAR, url_gambar);
        contentValues.put(P_KEY_FIREBASE, id_firebase);

        dataCD.update(PRODUK, contentValues, P_ID+" = ?  ", new String[]{id});
        return true;
    }


    public Cursor getDataProdukByBarcode(String barcode){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+PRODUK+" WHERE "+P_BARCODE+" = "+barcode, null);
        return res;
    }


    public boolean updateTerimaBarang(String id, String qty){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(P_QTY, qty);

        dataCD.update(PRODUK, contentValues, P_ID+" = ?  ", new String[]{id});
        return true;
    }

    public boolean updateSetelahSimpan(){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(P_QTY, "0");

        dataCD.update(PRODUK, contentValues, null, null);
        return true;
    }


    public boolean insertDataStok( String invoice, String id_produk, String qty_produk, String tgl_masuk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BM_INVOICE, invoice);
        contentValues.put(BM_IDPRODUK, id_produk);
        contentValues.put(BM_QTY, qty_produk);
        contentValues.put(BM_TGL_MASUK, tgl_masuk);

        long result = db.insert(BARANG_MASUK, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    public boolean insertDataCD( String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(STATUS_CD, status);

        long result = db.insert(DATA_CD, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getDataCd(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+DATA_CD, null);
        return res;
    }

    public boolean updateDataCD(String id, String status){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS_CD, status);

        dataCD.update(DATA_CD, contentValues, ID_CD+" = ?  ", new String[]{id});
        return true;
    }

    public boolean insertDataPrinter( String nama, String alamat, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAMA_PRINTER, nama);
        contentValues.put(ALAMAT_PRINTER, alamat);
        contentValues.put(STATUS_KONEKSI, status);

        long result = db.insert(DATA_PERANGKAT_PRINTER, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getDataPtinter(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+DATA_PERANGKAT_PRINTER, null);
        return res;
    }

    public boolean insertDataToko( String nama_toko, String owner, String kontak, String alamat, String kelurahan, String kecamatan, String kota, String provinsi, String kode_pos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAMA_TOKO, nama_toko);
        contentValues.put(OWNER, owner);
        contentValues.put(KONTAK, kontak);
        contentValues.put(ALAMAT, alamat);
        contentValues.put(KELURAHAN, kelurahan);
        contentValues.put(KECAMATAN, kecamatan);
        contentValues.put(KOTA, kota);
        contentValues.put(PROVINSI, provinsi);
        contentValues.put(KODE_POS, kode_pos);

        long result = db.insert(DATA_TOKO, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public boolean updateDataToko(int id, String nama_toko, String owner, String kontak, String alamat){
        SQLiteDatabase updateDataToko = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMA_TOKO, nama_toko);
        contentValues.put(OWNER, owner);
        contentValues.put(KONTAK, kontak);
        contentValues.put(ALAMAT, alamat);

        updateDataToko.update(DATA_TOKO, contentValues, ID+" = ? "+id, null);
        return true;
    }

    public Cursor getDataToko(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+DATA_TOKO, null);
        return res;
    }

    public boolean updateDataPrinter(int id, String nama, String alamat, String status){
        SQLiteDatabase updateDataToko = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMA_PRINTER, nama);
        contentValues.put(ALAMAT_PRINTER, alamat);
        contentValues.put(STATUS_KONEKSI, status);

        updateDataToko.update(DATA_TOKO, contentValues, ID_PRINTER+" = ? "+id, null);
        return true;
    }

    public Integer deleteDataToko(int id){
        SQLiteDatabase deleteDataToko = this.getWritableDatabase();

        return deleteDataToko.delete( DATA_PERANGKAT_PRINTER, "ID = ?"+id, null );
    }

    public Integer deleteDataPrinter(){
        SQLiteDatabase deleteDataToko = this.getWritableDatabase();

        return deleteDataToko.delete( DATA_PERANGKAT_PRINTER, null, null );
    }

    public boolean insertDataJlhMeja(String key, String jumlah){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(M_ID, key);
        contentValues.put(M_JUMLAH, jumlah);

        long result = db.insert(MEJA, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getJlahMeja(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+MEJA, null);
        return res;
    }

    public boolean updateDataJlhMeja(String id, String status){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(M_JUMLAH, status);

        dataCD.update(MEJA, contentValues, M_ID+" = ?  ", new String[]{id});
        return true;
    }

    public Integer deleteJlhMeja(){
        SQLiteDatabase deleteDataToko = this.getWritableDatabase();

        return deleteDataToko.delete( MEJA, null, null );
    }


    public boolean insertDataBarcode( String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BCD_STATUS, status);

        long result = db.insert(BARCODE_TBL, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getDataBarcode(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+BARCODE_TBL, null);
        return res;
    }

    public boolean updateDataBarcode(String id, String status){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BCD_STATUS, status);

        dataCD.update(BARCODE_TBL, contentValues, BCD_ID+" = ?  ", new String[]{id});
        return true;
    }

    public boolean insertDataModelToko(String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MT_STATUS, status);

        long result = db.insert(MODEL_TOKO_TBL, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getDataModelToko(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+MODEL_TOKO_TBL, null);
        return res;
    }

    public boolean updateDataModelToko(String id, String status){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MT_STATUS, status);

        dataCD.update(MODEL_TOKO_TBL, contentValues, MT_ID+" = ?  ", new String[]{id});
        return true;
    }

    public boolean insertDataPesananMeja(String id_order, String invoice, String meja, String daftar_pesanan, String jumlah, String harga, String tanggal, String jam, String status, String costumer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PM_ORDER_ID, id_order);
        contentValues.put(PM_INVOICE, invoice);
        contentValues.put(PM_COSTUMER, costumer);
        contentValues.put(PM_MEJA, meja);
        contentValues.put(PM_DAFTAR, daftar_pesanan);
        contentValues.put(PM_JUMLAH, jumlah);
        contentValues.put(PM_HARGA, harga);
        contentValues.put(PM_TANGGAL, tanggal);
        contentValues.put(PM_JAM, jam);
        contentValues.put(PM_STATUS, status);

        long result = db.insert(PM_TBL, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public boolean insertDataPesananMejaTambah(String id_order, String invoice, String meja, String daftar_pesanan, String jumlah, String harga, String tanggal, String jam, String status, String costumer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TAMBAH_PM_ORDER_ID, id_order);
        contentValues.put(TAMBAH_PM_INVOICE, invoice);
        contentValues.put(TAMBAH_PM_COSTUMER, costumer);
        contentValues.put(TAMBAH_PM_MEJA, meja);
        contentValues.put(TAMBAH_PM_DAFTAR, daftar_pesanan);
        contentValues.put(TAMBAH_PM_JUMLAH, jumlah);
        contentValues.put(TAMBAH_PM_HARGA, harga);
        contentValues.put(TAMBAH_PM_TANGGAL, tanggal);
        contentValues.put(TAMBAH_PM_JAM, jam);
        contentValues.put(TAMBAH_PM_STATUS, status);

        long result = db.insert(TAMBAH_PM_TBL, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getDataTambahPesananMeja(String id_order, String meja, String id_produk){
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+PM_TBL+" where "+PM_ORDER_ID+" = "+id_order+" and "+PM_MEJA+" = "+meja+" and "+PM_DAFTAR+" = "+id_produk, null);
        String sqlQuery = "select * from "+PM_TBL+" where "+PM_ORDER_ID+" = ? and "+PM_MEJA+" = ? and "+PM_DAFTAR+" = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{id_order, meja, id_produk});
        return cursor;
    }

    public Cursor getDataTambahPesananMejaTambah(String id_order, String meja, String id_produk){
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+PM_TBL+" where "+PM_ORDER_ID+" = "+id_order+" and "+PM_MEJA+" = "+meja+" and "+PM_DAFTAR+" = "+id_produk, null);
        String sqlQuery = "select * from "+TAMBAH_PM_TBL+" where "+TAMBAH_PM_ORDER_ID+" = ? and "+TAMBAH_PM_MEJA+" = ? and "+TAMBAH_PM_DAFTAR+" = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{id_order, meja, id_produk});
        return cursor;
    }

    public Integer deleteDataPesanan(){
        SQLiteDatabase deleteDataToko = this.getWritableDatabase();
        return deleteDataToko.delete( PM_TBL, null, null );
    }

    public Integer deletePesananMeja(String invoice){
        SQLiteDatabase deletePesanan = this.getWritableDatabase();

        return deletePesanan.delete( PM_TBL, PM_INVOICE+" = ? ", new String[]{invoice});
    }
    public Cursor getDaftarPesananMeja(String id_order, String meja, String status){
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuery = "select * from "+PM_TBL+" where "+PM_ORDER_ID+" = ? and "+PM_MEJA+" = ? and "+PM_STATUS+" = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{id_order, meja, status});
//        Cursor res = db.rawQuery("select * from "+PM_TBL+" where "+PM_ORDER_ID+" = "+id_order+" and "+PM_MEJA+" = "+meja+" and "+PM_STATUS+" = "+status, null);
        return cursor;
    }

    public Cursor getDaftarPesananMejaTambah(String id_order, String meja, String status){
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuery = "select * from "+TAMBAH_PM_TBL+" where "+TAMBAH_PM_ORDER_ID+" = ? and "+TAMBAH_PM_MEJA+" = ? and "+TAMBAH_PM_STATUS+" = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{id_order, meja, status});
//        Cursor res = db.rawQuery("select * from "+PM_TBL+" where "+PM_ORDER_ID+" = "+id_order+" and "+PM_MEJA+" = "+meja+" and "+PM_STATUS+" = "+status, null);
        return cursor;
    }

    public boolean updateTambahPesananMeja(int id_pesanan, String jumlah){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PM_JUMLAH, jumlah);

        dataCD.update(PM_TBL, contentValues, PM_ID+" = "+id_pesanan, null);
        return true;
    }

    public boolean updateTambahPesananMejaTambah(int id_pesanan, String jumlah){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAMBAH_PM_JUMLAH, jumlah);

        dataCD.update(TAMBAH_PM_TBL, contentValues, TAMBAH_PM_ID+" = "+id_pesanan, null);
        return true;
    }
    public boolean updateStatusPesanan(String invoice, String status){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PM_STATUS, status);

        dataCD.update(PM_TBL, contentValues, PM_INVOICE+" = "+invoice, null);
        return true;
    }

    public boolean updateCostumer(String invoice, String costumer){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PM_COSTUMER, costumer);

        dataCD.update(PM_TBL, contentValues, PM_INVOICE+" = "+invoice, null);

        return true;
    }

    public Cursor getDataPesananMeja(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+PM_TBL, null);
        return res;
    }

    public Cursor getDataIdOrderPesananMeja(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+PM_TBL+"  order by "+PM_ORDER_ID+" desc limit 1", null);
        return res;
    }

    public boolean insertDataOrderanMeja(String id_order, String meja, String invoice, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(OM_ID, id_order);
        contentValues.put(OM_MEJA, meja);
        contentValues.put(OM_INVOICE, invoice);
        contentValues.put(OM_STATUS, status);

        long result = db.insert(OM_TBL, null, contentValues);
        if (result == -1 )
            return false;
        else
            return true;
    }

    public Cursor getDataOrderanMejaByStatusMeja(String meja, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+OM_TBL+" where "+OM_MEJA+" = "+meja+" and "+OM_STATUS+" = "+status+" order by "+OM_ID+" desc limit 1", null);
        return res;
    }

    public Integer deleteOrderanMeja(String invoice){
        SQLiteDatabase deletePesanan = this.getWritableDatabase();

        return deletePesanan.delete( OM_TBL, OM_INVOICE+" = ?", new String[]{invoice});
    }

    public Cursor getDataOrderanMeja(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+OM_TBL+" order by "+OM_ID+" desc limit 1", null);
        return res;
    }

    public boolean insertTaxRestauran(String key, int tax){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TAX_ID, key);
        contentValues.put(TAX_BESARAN, tax);

        long result = db.insert(TAX, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDataTax(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TAX, null);
        return res;
    }

    public boolean updateDataTax(String id, int tax){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAX_BESARAN, tax);

        dataCD.update(TAX, contentValues, TAX_ID+" = ?  ", new String[]{id});
        return true;
    }

    public boolean updateStatusTBL(String id, String status){
        SQLiteDatabase dataCD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OM_STATUS, status);

        dataCD.update(OM_TBL, contentValues, OM_INVOICE+" = ?  ", new String[]{id});
        return true;
    }

    public Integer deleteDataTax(){
        SQLiteDatabase deleteDataToko = this.getWritableDatabase();
        return deleteDataToko.delete( TAX, null, null );
    }

}
