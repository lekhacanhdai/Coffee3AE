package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.LoginLayoutBinding;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private LoginLayoutBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private NhanVien nhanVienChecked;
    private ArrayList<NhanVien> listNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginLayoutBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        listNV = new ArrayList<>();



        /*mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NhanVien");
        NhanVien nhanVien = new NhanVien(2,1,"Le Khac Anh Dai","anhdai0801","123456","1asdf@gmail.com","0987654456", "Nam", "08/01/2001");
        mDatabase.child(Integer.toString(nhanVien.getMaNV())).setValue(nhanVien);*/

        database = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mDatabase = database.getReference("NhanVien");
        nhanVienChecked = new NhanVien();
        listNV.clear();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item:snapshot.getChildren()){
                    NhanVien nhanVien = item.getValue(NhanVien.class);
                    listNV.add(nhanVien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.btnLoginDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName() | !validatePassWord()){
                    return;
                }
                String user = binding.txtlLoginTenDN.getEditText().getText().toString().trim();
                String password = binding.txtlLoginMatKhau.getEditText().getText().toString().trim();
//                mDatabase = database.getReference("NhanVien");

                for ( NhanVien item:listNV){
                    if (item.getTenDN().equals(user) && item.getMatKhau().equals(password)){
                        Log.d("user la", item.getTenDN());
                        Log.d("pass la", item.getMatKhau());
                        nhanVienChecked = item;
                        break;
                    }
                }
                if (nhanVienChecked.getMaNV()!=0){
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("maquyen", nhanVienChecked.getQuyen().getMaQuyen());
                    Log.d("taggg", ""+nhanVienChecked.getQuyen().getTenQuyen());

                    editor.commit();

                    //Gửi dữ liệu sang trang chủ
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("tendn", binding.txtlLoginTenDN.getEditText().getText().toString().trim());
                    intent.putExtra("manv", nhanVienChecked.getMaNV());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

    private boolean validateUserName(){
        String val = binding.txtlLoginTenDN.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            binding.txtlLoginTenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            binding.txtlLoginTenDN.setError(null);
            binding.txtlLoginTenDN.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassWord(){
        String val = binding.txtlLoginMatKhau.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            binding.txtlLoginMatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            binding.txtlLoginMatKhau.setError(null);
            binding.txtlLoginMatKhau.setErrorEnabled(false);
            return true;
        }
    }
}