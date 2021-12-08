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
    public int maban, mamon,manv,id,giatien,soluong;
    String tongtien;




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
        DatabaseReference dataRef_detail = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChiTietDonDat");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("DonDat");
        Query query = databaseReference.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DonDat donDat = snapshot.getValue(DonDat.class);
                id = donDat.getMaDonDat();

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
        //Log.d("BanAn_out", ""+ banAn1[0]);
        DatabaseReference rootRef1 = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NhanVien");
        NhanVien[] nhanVien1 = {new NhanVien()};
        rootRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    NhanVien data = item.getValue(NhanVien.class);
                    if(data.getMaNV() == manv)
                    {
                        nhanVien1[0] = data;
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference rootRef_mon = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
        Mon[] mon1 = {new Mon()};
        rootRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    Mon data = item.getValue(Mon.class);
                    if(data.getMaMon() == manv)
                    {
                        mon1[0] = data;
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

        BTN_amountmenu_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }

                DatabaseReference rootRef2 = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
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
                                 String tinhtrang = "true";
                                 DonDat donDat = new DonDat(id,tinhtrang,ngaydat,tongtien,  banAn1[0],nhanVien1[0]);
                                 ChiTietDonDat chiTietDonDat = new ChiTietDonDat(soluong,mon1[0],donDat);
                                 dataRef_detail.child(String.valueOf(id)).setValue(chiTietDonDat);
                                 databaseReference.child(String.valueOf(id)).setValue(donDat);
                                 Intent intent = new Intent(Amount_Menu_Activity.this, HomeActivity.class);
                                 startActivity(intent);


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
                Log.d("tongtien", ""+ tongtien);
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