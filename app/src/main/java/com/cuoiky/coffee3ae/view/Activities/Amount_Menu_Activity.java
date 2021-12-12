package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.Mon;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.view.Fragments.DisplayCategoryFragment;
import com.cuoiky.coffee3ae.view.Fragments.DisplayTableFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Amount_Menu_Activity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_SoLuong;
    Button BTN_amountmenu_DongY;
    private int maban, mamon,manv,id,giatien,soluong;
    private static int manv_codinh;
    boolean tinhtrang_ban;
    String tongtien ;
    DonDat donDat;
    ChiTietDonDat chiTietDonDat;
    private final String tinhtrang = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        //Lấy đối tượng view
        TXTL_amountmenu_SoLuong = (TextInputLayout)findViewById(R.id.txtl_amountmenu_SoLuong);
        BTN_amountmenu_DongY = (Button)findViewById(R.id.btn_amountmenu_DongY);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        mamon = intent.getIntExtra("mamon",0);
        manv = intent.getIntExtra("manv",0);
        tinhtrang_ban = intent.getBooleanExtra("tinhtrang",false);
        manv_codinh = manv;
        DatabaseReference dataRef_detail = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChiTietDonDat");
        Query query = dataRef_detail.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChiTietDonDat chiTietDonDat = snapshot.getValue(ChiTietDonDat.class);
                id = chiTietDonDat.getDonDat().getMaDonDat();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("ID", ""+id);
        Log.d("MA NV", ""+ manv_codinh);


        //Log.d("BanAn_out", ""+ banAn1[0]);
        DatabaseReference rootRef1 = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NhanVien");
        NhanVien[] nhanVien1 = {new NhanVien()};
        rootRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    NhanVien data = item.getValue(NhanVien.class);
                    if(data.getMaNV() == manv_codinh)
                    {
                        nhanVien1[0] = data;
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BanAn");
        BanAn[] banAn1 = {new BanAn()};
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    BanAn data = item.getValue(BanAn.class);
                    if(data.getMaBan() == maban)
                    {
                        banAn1[0] = data;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());
        DatabaseReference rootRef2 = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
        BTN_amountmenu_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }
                Mon[] mon1 = {new Mon()};
                rootRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren())
                        {
                            Mon data = item.getValue(Mon.class);
                            if(data.getMaMon() == mamon)
                            {
                                 mon1[0] = data;
                                 Log.d("Mon", ""+ mon1[0].getTenMon());
                                 giatien = Integer.parseInt(mon1[0].getGiaTien());
                                 soluong = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                                 tongtien = String.valueOf(giatien*soluong);
                                 id+=1;
                                 BanAn banAn = new BanAn(maban,banAn1[0].getTenBan(),true);
                                 donDat = new DonDat(id,tinhtrang,ngaydat,tongtien,banAn,nhanVien1[0]);
                                 chiTietDonDat = new ChiTietDonDat(soluong,mon1[0],donDat);
                                 dataRef_detail.child(String.valueOf(id)).setValue(chiTietDonDat);
                                 rootRef.child(String.valueOf(maban)).setValue(banAn);
                                 dataRef_detail.removeEventListener(this);

                            }
                        }
                        Log.d("trangthai_datmon", ""+tinhtrang);
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });


    }
    //validate số lượng
    private boolean validateAmount(){
        String val = TXTL_amountmenu_SoLuong.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_amountmenu_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_amountmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else {
            TXTL_amountmenu_SoLuong.setError(null);
            TXTL_amountmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}