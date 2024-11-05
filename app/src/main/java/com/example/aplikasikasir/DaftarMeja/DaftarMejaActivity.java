package com.example.aplikasikasir.DaftarMeja;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasikasir.Database.DatabaseKasir;
import com.example.aplikasikasir.R;
import com.example.aplikasikasir.Transaksi.TransaksiModelDuaActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class DaftarMejaActivity extends AppCompatActivity {


    DatabaseKasir databaseKasir;
    RecyclerView rvDaftarMeja;
    DaftarMejaAdapter daftarMejaAdapter;

    ArrayList<ModelMeja> modelMejas = new ArrayList<>();
    static ArrayList<String> daftarMeja = new ArrayList<>();

    ImageView kembaliDariDaftarMeja;
    TextView tvNotifMejaKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.hitamterang));
        window.setNavigationBarColor(getResources().getColor(R.color.navigasi));

        setContentView(R.layout.activity_daftar_meja);


        daftarMeja.clear();
        modelMejas.clear();

        databaseKasir = new DatabaseKasir(this);
        rvDaftarMeja= findViewById(R.id.rvDaftarMeja);
        kembaliDariDaftarMeja= findViewById(R.id.kembaliDariDaftarMeja);
        tvNotifMejaKosong= findViewById(R.id.tvNotifMejaKosong);

        Cursor tblJumlahMeja = databaseKasir.getJlahMeja();
        if (tblJumlahMeja.getCount() == 0){
            tvNotifMejaKosong.setVisibility(View.VISIBLE);
        }else{
            tvNotifMejaKosong.setVisibility(View.GONE);

            tblJumlahMeja.moveToNext();
            int jumlahmeja = Integer.parseInt(tblJumlahMeja.getString(1));

            for (int n =1 ; n<=jumlahmeja; n++){
                daftarMeja.add(String.valueOf(n));
            }

            modelMejas.addAll(getDaftarMeja());
            TampilkanDftarMeja();
        }


        TextInputEditText cariMeja = findViewById(R.id.tietCariNomorMeja);
        cariMeja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                daftarMejaAdapter.getFilter().filter(Objects.requireNonNull(cariMeja.getText()).toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kembaliDariDaftarMeja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    static ArrayList<ModelMeja> getDaftarMeja() {
        ArrayList<ModelMeja> list = new ArrayList<>();
        list.clear();
        for (int position = 0; position < daftarMeja.size(); position++) {
            ModelMeja modelMeja = new ModelMeja();
            modelMeja.setJmlhMeja(daftarMeja.get(position));
            list.add(modelMeja);
        }
        return list;
    }

    public void TampilkanDftarMeja(){
        rvDaftarMeja.setLayoutManager(new GridLayoutManager(this, 4));
        daftarMejaAdapter = new DaftarMejaAdapter(modelMejas, DaftarMejaActivity.this);
        rvDaftarMeja.setAdapter(daftarMejaAdapter);

        daftarMejaAdapter.setOnItemClickCallback(new DaftarMejaAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ModelMeja data) {
                Intent intentMeja = new Intent(DaftarMejaActivity.this, TransaksiModelDuaActivity.class);
                intentMeja.putExtra("nomeja", data.getJmlhMeja());
                startActivity(intentMeja);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}