package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.AddStaffLayoutBinding;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.model.Quyen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class AddStaffActivity extends AppCompatActivity implements View.OnClickListener {
    private AddStaffLayoutBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private ArrayList<NhanVien> listNV;
    String gioiTinh;
    Quyen editQuyen;
    public int idLast;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");
    int manv = 0, quyen = 0;
    long ktra;
    int id;
    String tendn;
    String matkhau;
    String ngaysinh;
    String gioitinh;
    String email;
    String sdt;
    int maquyen;
    String hoten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddStaffLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        database = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mDatabase = database.getReference("NhanVien");
        listNV = new ArrayList<NhanVien>();

        manv = getIntent().getIntExtra("manv", 0);  //Lay ma nhan vien tu displaystaff
        tendn = getIntent().getStringExtra("tendn");
        matkhau = getIntent().getStringExtra("matkhau");
        email = getIntent().getStringExtra("email");
        sdt = getIntent().getStringExtra("sdt");
        ngaysinh = getIntent().getStringExtra("ngaysinh");
        gioitinh = getIntent().getStringExtra("gioitinh");
        hoten = getIntent().getStringExtra("hoten");
        maquyen = getIntent().getIntExtra("maquyen",2);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listNV != null){
                    listNV.clear();
                }
                for (DataSnapshot item:snapshot.getChildren()){
                    NhanVien nhanVien = item.getValue(NhanVien.class);
                    listNV.add(nhanVien);
                    idLast = nhanVien.getMaNV();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Query query = mDatabase.limitToLast(1);
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
//                idLast = nhanVien.getMaNV();
//                Log.d("trong query", ""+idLast);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        if (manv != 0){
            binding.txtAddstaffTitle.setText("Sửa nhân viên");
//            NhanVien editNhanVien = listNV.get(manv);

            //Hien thi thong tin nhan vien
            binding.txtlAddstaffHoVaTen.getEditText().setText(hoten);
            binding.txtlAddstaffTenDN.getEditText().setText(tendn);
            binding.txtlAddstaffMatKhau.getEditText().setText(matkhau);
            binding.txtlAddstaffEmail.getEditText().setText(email);
            binding.txtlAddstaffSDT.getEditText().setText(sdt);
//            String gioiTinh = editNhanVien.getGioiTinh();
            if (gioitinh.equals("Nam")){
                binding.rdAddstaffNam.setChecked(true);
            } else if (gioiTinh.equals("Nu")){
                 binding.rdAddstaffNu.setChecked(true);
            } else {
                binding.rdAddstaffKhac.setChecked(true);
            }

            if (maquyen == 1){
                binding.rdAddstaffQuanLy.setChecked(true);
            } else {
                binding.rdAddstaffNhanVien.setChecked(true);
            }

            // Hien thi ngay sinh
            String date = ngaysinh;
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]);
            int year = Integer.parseInt(items[2]);
            binding.dtAddstaffNgaySinh.updateDate(year, month, day);
            binding.btnAddstaffThemNV.setText("Sửa nhân viên");
        }

        binding.btnAddstaffThemNV.setOnClickListener(this::onClick);
        binding.imgAddstaffBack.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String chucnang;
        switch (id){
            case R.id.btn_addstaff_ThemNV:
                if( !validateAge() | !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() |
                        !validatePermission() | !validatePhone() | !validateUserName()){
                    return;
                }
                // Lay du lieu tu view
                String hoTen = binding.txtlAddstaffHoVaTen.getEditText().getText().toString();
                String tenDN = binding.txtlAddstaffTenDN.getEditText().getText().toString();
                String matKhau = binding.txtlAddstaffMatKhau.getEditText().getText().toString();
                String soDT = binding.txtlAddstaffSDT.getEditText().getText().toString();
                String email = binding.txtlAddstaffEmail.getEditText().getText().toString();

                String ngaySinh = binding.dtAddstaffNgaySinh.getDayOfMonth() + "/" + (binding.dtAddstaffNgaySinh.getMonth() + 1)
                        +"/"+binding.dtAddstaffNgaySinh.getYear();

                if (binding.rdAddstaffNhanVien.isChecked() == true){
                    editQuyen = new Quyen(2, "Nhan vien");
                } else if ( binding.rdAddstaffQuanLy.isChecked() == true){
                    editQuyen = new Quyen(1, "Quan ly");
                }

                if (binding.rdAddstaffNam.isChecked() == true){
                    gioiTinh = "Nam";
                } else if (binding.rdAddstaffNu.isChecked() == true){
                    gioiTinh = "Nu";
                } else if ( binding.rdAddstaffKhac.isChecked() == true){
                    gioiTinh = "Khac";
                }

                NhanVien editNhanVien = new NhanVien();
                editNhanVien.setMaNV(manv);
                editNhanVien.setHoTenNV(hoTen);
                editNhanVien.setMatKhau(matKhau);
                editNhanVien.setSdt(soDT);
                editNhanVien.setEmail(email);
                editNhanVien.setGioiTinh(gioiTinh);
                editNhanVien.setQuyen(editQuyen);
                editNhanVien.setNgaySinh(ngaySinh);
                editNhanVien.setTenDN(tenDN);
                if (manv != 0){
                    mDatabase.child(Integer.toString(manv)).setValue(editNhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    ktra = manv;
                    chucnang = "sua";
                } else {

                    editNhanVien.setMaNV(idLast+1);
                    mDatabase.child(Integer.toString(idLast+1)).setValue(editNhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                    ktra = idLast + 1;
                    chucnang = "themnv";
                }

                Log.d("Kiem tra", ""+ktra);
                Log.d("chuc nang", ""+chucnang);
                Intent intent = new Intent();
                intent.putExtra("ketquaktra", ktra);
                intent.putExtra("chucnang", chucnang);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.img_addstaff_back:
                finish();
                Log.d("noti", "back lai");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }
    //region Validate field
    private boolean validateFullName(){
        String val = binding.txtlAddstaffHoVaTen.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            binding.txtlAddstaffHoVaTen.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            binding.txtlAddstaffHoVaTen.setError(null);
            binding.txtlAddstaffHoVaTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = binding.txtlAddstaffTenDN.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            binding.txtlAddstaffTenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            binding.txtlAddstaffTenDN.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            binding.txtlAddstaffTenDN.setError("Không được cách chữ!");
            return false;
        }
        else {
            binding.txtlAddstaffTenDN.setError(null);
            binding.txtlAddstaffTenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = binding.txtlAddstaffEmail.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            binding.txtlAddstaffEmail.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            binding.txtlAddstaffEmail.setError("Email không hợp lệ!");
            return false;
        }
        else {
            binding.txtlAddstaffEmail.setError(null);
            binding.txtlAddstaffEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = binding.txtlAddstaffSDT.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            binding.txtlAddstaffSDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            binding.txtlAddstaffSDT.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            binding.txtlAddstaffSDT.setError(null);
            binding.txtlAddstaffSDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = binding.txtlAddstaffMatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            binding.txtlAddstaffMatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            binding.txtlAddstaffMatKhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            binding.txtlAddstaffMatKhau.setError(null);
            binding.txtlAddstaffMatKhau.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateGender(){
        if(binding.rgAddstaffQuyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private boolean validatePermission(){
        if (binding.rgAddstaffQuyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Hãy chọn quyền", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = binding.dtAddstaffNgaySinh.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion
}