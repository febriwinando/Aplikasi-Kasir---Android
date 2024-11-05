package com.example.aplikasikasir.FirebaseModel;

public class ModelProdukFirebase {
    private String id_produk, barcode_produk, nama_produk, harga_produk, kategori_produk, satuan_produk, gambar_produk, key_firebase_produk;

    ModelProdukFirebase(){

    }

    public ModelProdukFirebase(String id_produk, String barcode_produk, String nama_produk, String harga_produk, String kategori_produk, String satuan_produk, String gambar_produk) {
        this.id_produk = id_produk;
        this.barcode_produk = barcode_produk;
        this.nama_produk = nama_produk;
        this.harga_produk = harga_produk;
        this.kategori_produk = kategori_produk;
        this.satuan_produk = satuan_produk;
        this.gambar_produk = gambar_produk;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getBarcode_produk() {
        return barcode_produk;
    }

    public void setBarcode_produk(String barcode_produk) {
        this.barcode_produk = barcode_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getKategori_produk() {
        return kategori_produk;
    }

    public void setKategori_produk(String kategori_produk) {
        this.kategori_produk = kategori_produk;
    }

    public String getSatuan_produk() {
        return satuan_produk;
    }

    public void setSatuan_produk(String satuan_produk) {
        this.satuan_produk = satuan_produk;
    }

    public String getGambar_produk() {
        return gambar_produk;
    }

    public void setGambar_produk(String gambar_produk) {
        this.gambar_produk = gambar_produk;
    }

    public String getKey_firebase_produk() {
        return key_firebase_produk;
    }

    public void setKey_firebase_produk(String key_firebase_produk) {
        this.key_firebase_produk = key_firebase_produk;
    }
}
