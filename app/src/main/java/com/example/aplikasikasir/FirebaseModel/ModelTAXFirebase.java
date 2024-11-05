package com.example.aplikasikasir.FirebaseModel;

public class ModelTAXFirebase {
    String tax, key;

    ModelTAXFirebase(){

    }

    public ModelTAXFirebase(String tax) {
        this.tax = tax;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
