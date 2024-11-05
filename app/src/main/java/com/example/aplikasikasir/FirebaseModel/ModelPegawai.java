package com.example.aplikasikasir.FirebaseModel;

public class ModelPegawai {
    String email, id, image_url, kontak, level_user, nama_lengkap, search, status;

    ModelPegawai(){

    }

    public ModelPegawai(String email, String id, String image_url, String kontak, String level_user, String nama_lengkap, String search, String status) {
        this.email = email;
        this.id = id;
        this.image_url = image_url;
        this.kontak = kontak;
        this.level_user = level_user;
        this.nama_lengkap = nama_lengkap;
        this.search = search;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getLevel_user() {
        return level_user;
    }

    public void setLevel_user(String level_user) {
        this.level_user = level_user;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
