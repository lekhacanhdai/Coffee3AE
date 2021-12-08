package com.cuoiky.coffee3ae.view.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.ThanhToan;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayPayment;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    ImageView IMG_payment_backbtn;
    TextView TXT_payment_TenBan, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonDat donDatDAO;
    BanAn banAnDAO;
    ThanhToan thanhToanDAO;
    ArrayList<ThanhToan> thanhToanDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long tongtien = 0;
    int maban, madondat;
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

        BTN_payment_ThanhToan.setOnClickListener(this::onClick);
        IMG_payment_backbtn.setOnClickListener(this::onClick);

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:


                break;
            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }
}