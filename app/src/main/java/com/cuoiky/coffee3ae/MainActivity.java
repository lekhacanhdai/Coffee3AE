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
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
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
        BanAn banAn = new BanAn(5, "ban 5", true);
        LoaiMon loaiMon = new LoaiMon(1, "cafe", "url");
        Mon mon = new Mon(1, "Cafe sua", "1000000", "Con hang", "url", loaiMon);
        Quyen quyen = new Quyen(1, "Quan Ly");
        NhanVien nhanVien = new NhanVien(3, "Lê Khắc Anh Đài", "khacdai0801", "1234567", "khacdai0801@gmail.com", "012390139", "Nam", "08/01/2001", quyen);
        DonDat donDat = new DonDat(2, "Chưa thanh toán", "23/11/2021", "100000",  banAn, nhanVien);
        ChiTietDonDat chiTietDonDat = new ChiTietDonDat(10, mon, donDat);
//        mDatabase.child("BanAn").child("test").setValue(banAn);
//        mDatabase.child("ChiTietDonDat").child("test").setValue(chiTietDonDat);
        mDatabase.child("DonDat").child("test2").setValue(donDat);
//        mDatabase.child("LoaiMon").child("test").setValue(loaiMon);
//        mDatabase.child("Mon").child("test").setValue(mon);
//        mDatabase.child("NhanVien").child("test").setValue(nhanVien);
//        mDatabase.child("PhanQuyen").child(String.valueOf(1)).setValue(new Quyen(1, "Nhan Vien"));
//        mDatabase.child("ThanhToan").child(String.valueOf(1)).setValue(new ThanhToan("Cafe sua", 10, 100000, "url"));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
//        mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("DonDat");
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot item: snapshot.getChildren()){
//                    DonDat data = item.getValue(DonDat.class);
//                    System.out.println(data.getNhanVien().getHoTenNV());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

       /* mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("DonDat");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()){
                    DonDat data = item.getValue(DonDat.class);
                    System.out.println(data.getNhanVien().getHoTenNV());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
}
