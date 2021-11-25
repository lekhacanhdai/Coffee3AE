package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.model.ThanhToan;
import com.cuoiky.coffee3ae.view.Fragments.DisplayStatisticFragment;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayPayment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {


    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon,txt_detailstatistic_NgayDat,txt_detailstatistic_TenBan
            ,txt_detailstatistic_TenNV,txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, manv, maban;
    String ngaydat, tongtien,nguoidat,tenban;
    ChiTietDonDat chiTietDonDat;
    ArrayList<ChiTietDonDat> listChiTietDonDat;
    AdapterDisplayPayment adapterDisplayPayment;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_statistic);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        manv = intent.getIntExtra("manv",0);
        maban = intent.getIntExtra("maban",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getStringExtra("tongtien");
        nguoidat = intent.getStringExtra("nguoidat");
        tenban = intent.getStringExtra("tenban");


        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayDat = (TextView)findViewById(R.id.txt_detailstatistic_NgayDat);
        txt_detailstatistic_TenBan = (TextView)findViewById(R.id.txt_detailstatistic_TenBan);
        txt_detailstatistic_TenNV = (TextView)findViewById(R.id.txt_detailstatistic_TenNV);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChiTietDonDat");
        listChiTietDonDat = new ArrayList<ChiTietDonDat>();

        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,listChiTietDonDat);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);

        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayDat.setText(ngaydat);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");
            txt_detailstatistic_TenNV.setText(nguoidat);
            txt_detailstatistic_TenBan.setText(tenban);

        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    ChiTietDonDat data = item.getValue(ChiTietDonDat.class);
                    listChiTietDonDat.add(data);

                }
                adapterDisplayPayment.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailStatisticActivity.this, DisplayStatisticFragment.class);
                startActivity(intent);
            }
        });

    }

}