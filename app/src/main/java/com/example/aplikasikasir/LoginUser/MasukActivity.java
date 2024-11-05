package com.example.aplikasikasir.LoginUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasikasir.Dashboard.DashboardActivity;
import com.example.aplikasikasir.R;
import com.example.aplikasikasir.RegistrasiPegawai.RegistrasiPegawaiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MasukActivity extends AppCompatActivity {
    TextInputEditText tietEmailLogin, tietPasswdLogin;

    Button btnLogin;
    private ProgressDialog progressDialog;
    String email, passwd;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView tvRegistrasi, tvMasuk, tvProgressMasuk;
    LinearLayout llBtnLogin;
    ProgressBar pbBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        hideSystemUI();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.black));

        setContentView(R.layout.activity_masuk);


        tvRegistrasi = findViewById(R.id.tvRegistrasi);

        tvRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasukActivity.this, RegistrasiPegawaiActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent intent = new Intent(MasukActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        tietEmailLogin = findViewById(R.id.tietEmailLogin);
        tietPasswdLogin = findViewById(R.id.tietPasswdLogin);
        llBtnLogin = findViewById(R.id.llBtnLogin);
        pbBtnLogin = findViewById(R.id.pbBtnLogin);
        tvMasuk = findViewById(R.id.tvMasuk);
        tvProgressMasuk = findViewById(R.id.tvProgressMasuk);

        llBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = tietEmailLogin.getText().toString();
                passwd = tietPasswdLogin.getText().toString();


                if (email.isEmpty()){
                    tietEmailLogin.setError("Email tidak boleh kosong...");
                } else if (passwd.isEmpty()) {
                    tietPasswdLogin.setError("Password tidak boleh kosong...");
                }else{
                    tvMasuk.setVisibility(View.GONE);
                    pbBtnLogin.setVisibility(View.VISIBLE);
                    tvProgressMasuk.setVisibility(View.VISIBLE);

                    login(email, passwd);
                }
            }
        });
    }



    public void login(String email, String passwd){
        firebaseAuth.signInWithEmailAndPassword(email, passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(MasukActivity.this, DashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        }else{
                            tvMasuk.setVisibility(View.VISIBLE);
                            pbBtnLogin.setVisibility(View.GONE);
                            tvProgressMasuk.setVisibility(View.GONE);
                            Toast.makeText(MasukActivity.this, "Autentikasi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_LAYOUT_FLAGS
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}