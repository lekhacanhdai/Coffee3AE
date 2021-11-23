package com.cuoiky.coffee3ae.view.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.RegisterLayoutBinding;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.model.Quyen;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private RegisterLayoutBinding binding;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh;

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
                        gioiTinh = "Nữ"; break;
                    case R.id.rd_signup_Khac:
                        gioiTinh = "Khác"; break;
                }
                String ngaySinh = binding.dtSignupNgaySinh.getDayOfMonth() + "/" + (binding.dtSignupNgaySinh.getMonth() + 1)
                        +"/"+binding.dtSignupNgaySinh.getYear();

            }
        });
    }

    //Hàm quay về màn hình trước
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

    //Hàm chuyển màn hình khi hoàn thành
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
            binding.txtlSignupTenDN.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            binding.txtlSignupTenDN.setError("Không được cách chữ!");
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
            binding.txtlSignupEmail.setError("Email không hợp lệ!");
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
            binding.txtlSignupSDT.setError("Số điện thoại không hợp lệ!");
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
            binding.txtlSignupMatKhau.setError("Mật khẩu ít nhất 6 ký tự!");
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
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion
}