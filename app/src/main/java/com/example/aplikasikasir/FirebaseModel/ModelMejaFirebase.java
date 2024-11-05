package com.example.aplikasikasir.FirebaseModel;

public class ModelMejaFirebase {
    String jumlah_meja, key;

    ModelMejaFirebase(){

    }
    public ModelMejaFirebase(String jumlah_meja) {
        this.jumlah_meja = jumlah_meja;
    }

    public String getJumlah_meja() {
        return jumlah_meja;
    }

    public void setJumlah_meja(String jumlah_meja) {
        this.jumlah_meja = jumlah_meja;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
