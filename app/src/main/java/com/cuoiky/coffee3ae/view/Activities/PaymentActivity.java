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
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.model.ThanhToan;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayPayment;
import com.cuoiky.coffee3ae.viewmodel.DonDatAdapter;
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
    DonDat donDat;
    ChiTietDonDat chiTietDonDat;
    NhanVien nhanVien;
    BanAn banAn;

    ArrayList<ChiTietDonDat> listChiTietDonDat;
    AdapterDisplayPayment adapterDisplayPayment;
    int  maban, tongtien;
    String ngaydat,nguoidat,tenban,tongtien_final;
    String trangthai;
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

        tongtien = 0;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(listChiTietDonDat != null){
                    listChiTietDonDat.clear();
                    adapterDisplayPayment.notifyDataSetChanged();
                }
                for(DataSnapshot item:snapshot.getChildren())
                {

                    ChiTietDonDat data = item.getValue(ChiTietDonDat.class);
                    if(data.getDonDat().getBan().getMaBan()==maban && data.getDonDat().getTinhTrang()!="true")
                    {
                        listChiTietDonDat.add(data);
                        int sl = data.getSoLuong();
                        int giatien = Integer.parseInt(data.getMon().getGiaTien());
                        tongtien += sl*giatien;

                    }
                    adapterDisplayPayment.notifyDataSetChanged();
                }
                TXT_payment_TongTien.setText(String.valueOf(tongtien));
                Log.d("Tong tien", ""+tongtien);


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
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot item:snapshot.getChildren())
                        {
                            ChiTietDonDat data = item.getValue(ChiTietDonDat.class);
                            if(data.getDonDat().getBan().getMaBan()==maban )
                            {
                                  int id = data.getDonDat().getMaDonDat();
                                  Mon mon = data.getMon();
                                  int soluong = data.getSoLuong();
                                  banAn = data.getDonDat().getBan();
                                  nhanVien = data.getDonDat().getNhanVien();
                                  ngaydat = data.getDonDat().getNgayDat();
                                  trangthai = "true";
                                  tongtien_final = data.getDonDat().getTongTien();
                                  donDat = new DonDat(id,trangthai,ngaydat,tongtien_final,banAn,nhanVien);
                                  chiTietDonDat = new ChiTietDonDat(soluong,mon,donDat);
                                  databaseReference.child(String.valueOf(id)).setValue(chiTietDonDat);
                                  Log.d("vao ", ""+data.getDonDat().getTinhTrang());

                            }
                        }

                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

                break;
            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }
}