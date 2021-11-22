package com.cuoiky.coffee3ae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cuoiky.coffee3ae.databinding.ActivityMainBinding;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.Mon;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.model.Quyen;
import com.cuoiky.coffee3ae.model.ThanhToan;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.view.Activities.TestActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
//        mDatabase.child("BanAn").child(String.valueOf(1)).setValue(new BanAn(1, "Ban 1", false));
//        mDatabase.child("ChiTietDonDat").child(String.valueOf(1)).setValue(new ChiTietDonDat(1, 1, 10));
//        mDatabase.child("DonDat").child(String.valueOf(1)).setValue(new DonDat(1,1,1, "Con hang", "12/11/2021", "100000"));
//        mDatabase.child("LoaiMon").child(String.valueOf(2)).setValue(new LoaiMon(2, "Cafe2", "https://vietblend.vn/wp-content/uploads/2018/12/tra-sua-ngon.jpg"));
//        mDatabase.child("Mon").child(String.valueOf(1)).setValue(new Mon(1,1,"Cafe Sua", "10000", "Con", "url"));
//        mDatabase.child("NhanVien").child(String.valueOf(1)).setValue(new NhanVien(1, 1, "Le Khac Anh Dai",
//                "anhdai123", "123456", "khacdai0801@gmail.com", "0123454535", "Nam", "08/01/2001"));
//        mDatabase.child("PhanQuyen").child(String.valueOf(1)).setValue(new Quyen(1, "Nhan Vien"));
//        mDatabase.child("ThanhToan").child(String.valueOf(1)).setValue(new ThanhToan("Cafe sua", 10, 100000, "url"));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }
}