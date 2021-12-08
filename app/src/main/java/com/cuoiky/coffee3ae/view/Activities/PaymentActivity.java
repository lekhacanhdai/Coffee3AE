package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.Mon;
import com.cuoiky.coffee3ae.model.ThanhToan;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayPayment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;

    ArrayList<ChiTietDonDat> listChiTietDonDat;
    AdapterDisplayPayment adapterDisplayPayment;
    int  maban, tongtien;
    String ngaydat,nguoidat,tenban;
    private DatabaseReference databaseReference;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //region thuộc tính view
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_payment_TenBan = (TextView)findViewById(R.id.txt_payment_TenBan);
        TXT_payment_NgayDat = (TextView)findViewById(R.id.txt_payment_NgayDat);
        TXT_payment_TongTien = (TextView)findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = (Button)findViewById(R.id.btn_payment_ThanhToan);


        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tenban = intent.getStringExtra("tenban");

        if(maban!=0){
            TXT_payment_TenBan.setText(tenban);
            TXT_payment_NgayDat.setText(ngaydat);

        }

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChiTietDonDat");
        listChiTietDonDat = new ArrayList<ChiTietDonDat>();

        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,listChiTietDonDat);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    ChiTietDonDat data = item.getValue(ChiTietDonDat.class);
                    if(data.getDonDat().getBan().getMaBan()==maban)
                    {
                        tongtien = 0;
                        int sl = data.getSoLuong();
                        int giatien = Integer.parseInt(data.getMon().getGiaTien());
                        tongtien += sl*giatien;
                        listChiTietDonDat.add(data);
                    }


                }
                adapterDisplayPayment.notifyDataSetChanged();
                Log.d("Tongtien", ""+ tongtien);
                TXT_payment_TongTien.setText(String.valueOf(tongtien));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BTN_payment_ThanhToan.setOnClickListener(this::onClick);
        IMG_payment_backbtn.setOnClickListener(this::onClick);

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:

                DatabaseReference rootRef2 = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChiTietDonDat");
                rootRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren())
                        {
                            ChiTietDonDat data = item.getValue(ChiTietDonDat.class);
                            if(data.getDonDat().getBan().getMaBan()==maban)
                            {
//                                mon1[0] = data;
//                                Log.d("Mon", ""+ mon1[0].getTenMon());
//                                giatien = Integer.parseInt(mon1[0].getGiaTien());
//                                soluong = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
//                                tongtien = String.valueOf(giatien*soluong);
//                                id+=1;
//                                String tinhtrang = "false";
//                                DonDat donDat = new DonDat(id,tinhtrang,ngaydat,tongtien,  banAn1[0],nhanVien1[0]);
//                                ChiTietDonDat chiTietDonDat = new ChiTietDonDat(soluong,mon1[0],donDat);
//                                dataRef_detail.child(String.valueOf(id)).setValue(chiTietDonDat);
//                                databaseReference.child(String.valueOf(id)).setValue(donDat);
//                                Intent intent = new Intent(Amount_Menu_Activity.this, HomeActivity.class);
//                                startActivity(intent);


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });



                Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                startActivity(intent);




                break;
            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }
}