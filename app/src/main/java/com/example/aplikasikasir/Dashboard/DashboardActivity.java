package com.example.aplikasikasir.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasikasir.DaftarMeja.DaftarMejaActivity;
import com.example.aplikasikasir.FirebaseModel.ModelPegawai;
import com.example.aplikasikasir.Laporan.LaporanTransaksiActivity;
import com.example.aplikasikasir.LoginUser.MasukActivity;
import com.example.aplikasikasir.PengaturanPerangkat.PengaturanPerangkatActivity;
import com.example.aplikasikasir.R;
import com.example.aplikasikasir.Tagihan.TagihanActivity;
import com.example.aplikasikasir.TambahProduk.TambahProdukActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class DashboardActivity extends AppCompatActivity {
    PieChart pieChart;
    TextView tvR, tvPython, tvCPP, tvJava, tvUser;
    RelativeLayout rlTransaksi;
    CardView cvOrder, cvBelanja, cvDataCafe, cvHutang, cvPengaturan, cvTambahProduk, cvOmset, cvKeluar;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ProgressBar pbKeluar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.hitamterang));
        window.setNavigationBarColor(getResources().getColor(R.color.navigasi));

        setContentView(R.layout.activity_dashboard);

        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        tvCPP = findViewById(R.id.tvCpp);
        tvJava = findViewById(R.id.tvJava);
        pieChart = findViewById(R.id.piechart);
        rlTransaksi = findViewById(R.id.rlTransaksi);
        cvDataCafe = findViewById(R.id.cvDataCafe);
        cvBelanja = findViewById(R.id.cvBelanja);
        cvOrder = findViewById(R.id.cvOrder);
        cvKeluar = findViewById(R.id.cvKeluar);

        cvHutang = findViewById(R.id.cvHutang);
        cvPengaturan = findViewById(R.id.cvPengaturan);
        cvTambahProduk = findViewById(R.id.cvTambahProduk);
        cvOmset = findViewById(R.id.cvOmset);
        tvUser = findViewById(R.id.tvUser);
        pbKeluar = findViewById(R.id.pbKeluar);
        setData();

        rlTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, LaporanTransaksiActivity.class));
            }
        });

        cvHutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TagihanActivity.class));

            }
        });

        cvTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TambahProdukActivity.class));
            }
        });

        cvPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPengaturanPeragkat = new Intent(DashboardActivity.this, PengaturanPerangkatActivity.class);
                startActivity(intentPengaturanPeragkat);
            }
        });

        cvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTambahProduk = new Intent(DashboardActivity.this, DaftarMejaActivity.class);
                startActivity(intentTambahProduk);
            }
        });

        cvKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbKeluar.setVisibility(View.VISIBLE);
                handlerProgressDialog();
//                AuthUI.getInstance()
//                        .signOut(DashboardActivity.this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            public void onComplete(@NonNull Task<Void> task) {
//                                // user is now signed out
//                                startActivity(new Intent(DashboardActivity.this, MasukActivity.class));
//                                finish();
//                            }
//                        });
            }

        });
    }

    public void handlerProgressDialog(){
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DashboardActivity.this, MasukActivity.class));
        }, 1500);

    }

    private void setData()
    {

        // Set the percentage of language used
        tvR.setText(Integer.toString(40));
        tvPython.setText(Integer.toString(30));
        tvCPP.setText(Integer.toString(5));
        tvJava.setText(Integer.toString(25));

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt(tvR.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt(tvPython.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        Integer.parseInt(tvJava.getText().toString()),
                        Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        dataUser();
    }

    public void dataUser(){
        reference = FirebaseDatabase.getInstance().getReference("Pegawai").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelPegawai pegawai = snapshot.getValue(ModelPegawai.class);
                tvUser.setText(pegawai.getNama_lengkap().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}