package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.RegisterLayoutBinding;
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

public class RegisterActivity extends AppCompatActivity {

    private RegisterLayoutBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh;
    private int id;

    public static final String BUNDLE = "BUNDLE";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterLayoutBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        database = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mDatabase = database.getReference("NhanVien");

        Query query = mDatabase.limitToLast(1);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                id = nhanVien.getMaNV();
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



        binding.btnSignupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFullName() | !validateUserName() | !validateEmail() | !validatePhone() | !validatePassWord() | !validateGender() | !validateAge()){
                    return;
                }
                switch (binding.rgSignupGioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_signup_Nam:
                        gioiTinh = "Nam"; break;
                    case R.id.rd_signup_Nu:
                        gioiTinh = "N???"; break;
                    case R.id.rd_signup_Khac:
                        gioiTinh = "Kh??c"; break;
                }
                String ngaySinh = binding.dtSignupNgaySinh.getDayOfMonth() + "/" + (binding.dtSignupNgaySinh.getMonth() + 1)
                        +"/"+binding.dtSignupNgaySinh.getYear();
                NhanVien newNhanVien = new NhanVien();
                newNhanVien.setQuyen(new Quyen(2,"Nhan vien"));
                newNhanVien.setTenDN(binding.txtlSignupTenDN.getEditText().getText().toString());
                newNhanVien.setMatKhau(binding.txtlSignupMatKhau.getEditText().getText().toString());
                newNhanVien.setHoTenNV(binding.txtlSignupHoVaTen.getEditText().getText().toString());
                newNhanVien.setEmail(binding.txtlSignupEmail.getEditText().getText().toString());
                newNhanVien.setGioiTinh(gioiTinh);
                newNhanVien.setNgaySinh(ngaySinh);
                newNhanVien.setSdt(binding.txtlSignupSDT.getEditText().getText().toString());
                newNhanVien.setMaNV(id+1);

                Log.d("in ra id", ""+ id);
                mDatabase.child(Integer.toString(id+1)).setValue(newNhanVien)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    //H??m quay v??? m??n h??nh tr?????c
    public void backFromRegister(View view){

        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(binding.layoutRegister,"transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    //H??m chuy???n m??n h??nh khi ho??n th??nh
    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateFullName(){
        String val = binding.txtlSignupHoVaTen.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            binding.txtlSignupHoVaTen.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            binding.txtlSignupHoVaTen.setError(null);
            binding.txtlSignupHoVaTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = binding.txtlSignupTenDN.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            binding.txtlSignupTenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            binding.txtlSignupTenDN.setError("Ph???i nh??? h??n 50 k?? t???");
            return false;
        }else if(!val.matches(checkspaces)){
            binding.txtlSignupTenDN.setError("Kh??ng ???????c c??ch ch???!");
            return false;
        }
        else {
            binding.txtlSignupTenDN.setError(null);
            binding.txtlSignupTenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = binding.txtlSignupEmail.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            binding.txtlSignupEmail.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            binding.txtlSignupEmail.setError("Email kh??ng h???p l???!");
            return false;
        }
        else {
            binding.txtlSignupEmail.setError(null);
            binding.txtlSignupEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = binding.txtlSignupSDT.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            binding.txtlSignupSDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            binding.txtlSignupSDT.setError("S??? ??i???n tho???i kh??ng h???p l???!");
            return false;
        }
        else {
            binding.txtlSignupSDT.setError(null);
            binding.txtlSignupSDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = binding.txtlSignupMatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            binding.txtlSignupMatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            binding.txtlSignupMatKhau.setError("M???t kh???u ??t nh???t 6 k?? t???!");
            return false;
        }
        else {
            binding.txtlSignupMatKhau.setError(null);
            binding.txtlSignupMatKhau.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateGender(){
        if(binding.rgSignupGioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"H??y ch???n gi???i t??nh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = binding.dtSignupNgaySinh.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"B???n kh??ng ????? tu???i ????ng k??!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion
}