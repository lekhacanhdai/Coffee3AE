package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.AddMenuLayoutBinding;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.Mon;
import com.cuoiky.coffee3ae.viewmodel.DownloadImageTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddMenuActivity extends AppCompatActivity {

    private AddMenuLayoutBinding binding;
    private FirebaseAuth firebaseAuth;
    private final int SELECT_IMAGE_CODE = 1;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private DatabaseReference databaseRef;

    int maloai , mamon;
    String tenloai,url,tinhtrang,tenmon,giatien;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu_layout);

        binding = AddMenuLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            firebaseAuth.signInAnonymously();
        }

        Intent intent = getIntent();
        mamon = intent.getIntExtra("mamon",0);
        maloai = intent.getIntExtra("maLoai",0);
        tenloai = intent.getStringExtra("tenLoai");
        url = intent.getStringExtra("url");
        tinhtrang = intent.getStringExtra("tinhtrang");
        tenmon = intent.getStringExtra("tenmon");
        giatien = intent.getStringExtra("giatien");


        if(mamon != 0){
            binding.txtAddmenuTitle.setText(" Sửa Thực Đơn");
            binding.txtlAddmenuTenMon.getEditText().setText(tenmon);
            binding.txtlAddmenuGiaTien.getEditText().setText(giatien);
            binding.txtlAddmenuLoaiMon.getEditText().setText(tenloai);
            new DownloadImageTask(binding.imgAddmenuThemHinh).execute(url);
            if(tinhtrang.equals("true")){
                binding.rdAddmenuConMon.setChecked(true);
            }else {
                binding.rdAddmenuHetMon.setChecked(true);
            }

            binding.btnAddmenuThemMon.setText("Sửa món");
        }


        binding.txtlAddmenuLoaiMon.getEditText().setText(tenloai);

        storage = FirebaseStorage.getInstance("gs://coffee3ae.appspot.com");
        storageRef = storage.getReferenceFromUrl("gs://coffee3ae.appspot.com/Mon");
        databaseRef = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");

        Query query = databaseRef.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mon mon = snapshot.getValue(Mon.class);
                id = mon.getMaMon();

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
        binding.btnAddmenuThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String imgname = "image" + calendar.getTimeInMillis() + ".PNG";
                StorageReference mountainsRef = storageRef.child(imgname);
                binding.imgAddmenuThemHinh.setDrawingCacheEnabled(true);
                binding.imgAddmenuThemHinh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.imgAddmenuThemHinh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddMenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                if( !validateName() | !validatePrice()){
                                    return;
                                }
                                boolean ktra ;
                                String chucnang;
                                if(id>0 && mamon==0){
                                    ktra=true;
                                    chucnang = "themmon";
                                    id+=1;
                                    String tenMon = binding.txtlAddmenuTenMon.getEditText().getText().toString();
                                    String giaTien = binding.txtlAddmenuGiaTien.getEditText().getText().toString();
                                    String tinhtrang = "true";
                                    switch (binding.rgAddmenuTinhTrang.getCheckedRadioButtonId()){
                                        case R.id.rd_addmenu_ConMon: tinhtrang = "true";   break;
                                        case R.id.rd_addmenu_HetMon: tinhtrang = "false";  break;
                                    }
                                    LoaiMon loaiMon = new LoaiMon(maloai,tenloai,url);
                                    Mon mon  = new Mon(id,tenMon,giaTien,tinhtrang,uri.toString(),loaiMon);
                                    databaseRef.child(String.valueOf(id)).setValue(mon);
                                    Intent intent = new Intent();
                                    intent.putExtra("ktra",ktra);
                                    intent.putExtra("chucnang",chucnang);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                    return;

                                }
                                if(mamon!=0){
                                    ktra=true;
                                    chucnang = "suamon";
                                    String tenMon = binding.txtlAddmenuTenMon.getEditText().getText().toString();
                                    String giaTien = binding.txtlAddmenuGiaTien.getEditText().getText().toString();
                                    switch (binding.rgAddmenuTinhTrang.getCheckedRadioButtonId()){
                                        case R.id.rd_addmenu_ConMon: tinhtrang = "true";   break;
                                        case R.id.rd_addmenu_HetMon: tinhtrang = "false";  break;
                                    }
                                    LoaiMon loaiMon = new LoaiMon(maloai,tenloai,url);
                                    Mon mon  = new Mon(mamon,tenMon,giaTien,tinhtrang,uri.toString(),loaiMon);
                                    databaseRef.child(String.valueOf(mamon)).setValue(mon);
                                    Intent intent = new Intent();
                                    intent.putExtra("ktra",ktra);
                                    intent.putExtra("chucnang",chucnang);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                     return;
                                }
                            }
                        });


                    }
                });
            }
        });
        binding.imgAddmenuThemHinh.setOnClickListener(this::onClick);
        binding.imgAddmenuBack.setOnClickListener(this::onClick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Uri uri = data.getData();
            try {
                Bitmap bitmap;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.imgAddmenuThemHinh.setImageURI(uri);
                binding.imgAddmenuThemHinh.setImageBitmap(bitmap);

            }
            catch (IOException e){e.printStackTrace();}
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.img_addmenu_ThemHinh:
                Intent intent_img = new Intent();
                intent_img.setAction(Intent.ACTION_GET_CONTENT);
                intent_img.setType("image/*");
                startActivityForResult(Intent.createChooser(intent_img, "Title"), SELECT_IMAGE_CODE);
                break;

            case R.id.img_addmenu_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }



    private boolean validateName(){
        String val = binding.txtlAddmenuTenMon.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            binding.txtlAddmenuTenMon.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            binding.txtlAddmenuTenMon.setError(null);
            binding.txtlAddmenuTenMon.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = binding.txtlAddmenuGiaTien.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            binding.txtlAddmenuGiaTien.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            binding.txtlAddmenuGiaTien.setError("Giá tiền không hợp lệ");
            return false;
        }else {
            binding.txtlAddmenuGiaTien.setError(null);
            binding.txtlAddmenuGiaTien.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}