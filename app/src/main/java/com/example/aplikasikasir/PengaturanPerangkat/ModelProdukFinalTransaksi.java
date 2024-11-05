package com.example.aplikasikasir.PengaturanPerangkat;

public class ModelProdukFinalTransaksi {

    String id_prooduk;
    String nama_produk;



    String harga_produk;
    int qty;

    public String getId_prooduk() {
        return id_prooduk;
    }

    public void setId_prooduk(String id_prooduk) {
        this.id_prooduk = id_prooduk;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
