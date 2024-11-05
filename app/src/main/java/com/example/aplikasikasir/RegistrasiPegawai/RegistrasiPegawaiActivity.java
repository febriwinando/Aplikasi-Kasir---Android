package com.example.aplikasikasir.RegistrasiPegawai;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
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

import com.example.aplikasikasir.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrasiPegawaiActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    LinearLayout llKembali;
    String namaSales, kontakSales, emailSales, passwdSales, repasswdSales, levelUserData;
    TextInputEditText tietNamaSalesRegistrasi, tietKontakRegistrasi, tietEmailRegistrasi, tietPasswdRegistrasi, tietRePasswdRegistrasi;
    String[] levelUserStatus =  {"Administrator", "Waiters", "Barista"};
    ArrayAdapter<String> adapterLevelStatus;
    AutoCompleteTextView levelUser;
    TextInputLayout tietLevelUser;
    LinearLayout llDaftarkan;
    TextView tvDaftarkan, tvProgressDaftarkan;
    ProgressBar pbBtnDaftarkan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.black));

        setContentView(R.layout.activity_registrasi_pegawai);


        tietNamaSalesRegistrasi = findViewById(R.id.tietNamaSalesRegistrasi);
        tietKontakRegistrasi = findViewById(R.id.tietKontakRegistrasi);
        llKembali = findViewById(R.id.llKembali);
        tietEmailRegistrasi = findViewById(R.id.tietEmailRegistrasi);
        tietPasswdRegistrasi = findViewById(R.id.tietPasswdRegistrasi);
        tietRePasswdRegistrasi = findViewById(R.id.tietRePasswdRegistrasi);
        tietLevelUser = findViewById(R.id.tilLevelUser);
        levelUser = findViewById(R.id.levelUser);
        llDaftarkan = findViewById(R.id.llDaftarkan);
        tvDaftarkan = findViewById(R.id.tvDaftarkan);
        pbBtnDaftarkan = findViewById(R.id.pbBtnDaftarkan);
        tvProgressDaftarkan = findViewById(R.id.tvProgressDaftarkan);
        levelUser.setEnabled(false);

        adapterLevelStatus = new ArrayAdapter<>(RegistrasiPegawaiActivity.this, R.layout.list_item, levelUserStatus);
        levelUser.setAdapter(adapterLevelStatus);

        levelUser.setOnItemClickListener((parent, view12, position, id) -> {
            levelUserData = parent.getItemAtPosition(position).toString();
        });


        firebaseAuth = FirebaseAuth.getInstance();


        llKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llDaftarkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registrasi();
            }
        });
    }


    public void registrasi(){

        namaSales = tietNamaSalesRegistrasi.getText().toString();
        kontakSales = tietKontakRegistrasi.getText().toString();
        emailSales = tietEmailRegistrasi.getText().toString();
        passwdSales = tietPasswdRegistrasi.getText().toString();
        repasswdSales = tietRePasswdRegistrasi.getText().toString();

        if (namaSales.isEmpty()){
            tietNamaSalesRegistrasi.setError("Harap isi nama lengkap...");
        }else if (kontakSales.isEmpty()){
            tietKontakRegistrasi.setError("Harap isi kontak aktif...");
        }else if (emailSales.isEmpty()){
            tietEmailRegistrasi.setError("Harap isi email aktif...");
        } else if (passwdSales.isEmpty()) {
            tietPasswdRegistrasi.setError("Harap isi password...");
        } else if (repasswdSales.isEmpty()) {
            tietRePasswdRegistrasi.setError("Harap isi ulang password..;");
        } else if (passwdSales.length() < 6) {
            tietPasswdRegistrasi.setError("Password harus lebih dari 6 karakter...");
        } else if (levelUser.getText().toString().isEmpty()) {
            levelUser.setError("Harap tentukan level user");
        }
        else{
            if (passwdSales.equals(repasswdSales)){
                tvDaftarkan.setVisibility(View.GONE);
                pbBtnDaftarkan.setVisibility(View.VISIBLE);
                tvProgressDaftarkan.setVisibility(View.VISIBLE);
                register(namaSales,kontakSales, emailSales, passwdSales, levelUser.getText().toString());
            }else{
                tietRePasswdRegistrasi.setError("Password yang diisi tidak sama dengan yang sebelumnya...");
            }
        }

    }


    private void register(String username, String kontak, String email, String password, String level_user){

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    Toast.makeText(RegistrasiPegawaiActivity.this, userId, Toast.LENGTH_SHORT).show();
                    reference = FirebaseDatabase.getInstance().getReference("Pegawai").child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("nama_lengkap", username);
                    hashMap.put("kontak", kontak);
                    hashMap.put("email", email);
                    hashMap.put("level_user", level_user);
                    hashMap.put("image_url", "default");
                    hashMap.put("status", "offline");
                    hashMap.put("search", username.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                viewSukses();
                            }
                        }
                    });

                }else{
                    tvDaftarkan.setVisibility(View.VISIBLE);
                    pbBtnDaftarkan.setVisibility(View.GONE);
                    tvProgressDaftarkan.setVisibility(View.GONE);
                    Toast.makeText(RegistrasiPegawaiActivity.this, "Tidak dapat registrasi menggunakan email tersebut", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewSukses() {
        Dialog dialogSukses = new Dialog(RegistrasiPegawaiActivity.this, R.style.DialogStyle);
        dialogSukses.setContentView(R.layout.view_sukses_daftar);
        ImageView ivTutupDialog = dialogSukses.findViewById(R.id.ivTutup);

        ivTutupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tietNamaSalesRegistrasi.setText("");
                tietEmailRegistrasi.setText("");
                tietKontakRegistrasi.setText("");
                tietPasswdRegistrasi.setText("");
                tietRePasswdRegistrasi.setText("");
                tietNamaSalesRegistrasi.requestFocus();
                dialogSukses.dismiss();

            }
        });

        dialogSukses.show();
    }

}