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
import com.cuoiky.coffee3ae.databinding.AddCategoryLayoutBinding;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.view.Fragments.DisplayCategoryFragment;
import com.cuoiky.coffee3ae.viewmodel.DownloadImageTask;
import com.firebase.ui.database.FirebaseArray;
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

public class AddCategoryActivity extends AppCompatActivity {

    private AddCategoryLayoutBinding binding;
    private FirebaseAuth firebaseAuth;
    private final int SELECT_IMAGE_CODE = 1;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private DatabaseReference databaseRef;
    private int  id ;
    int maloai = 0;
    String url ;
    String tenloai;
    Bitmap bitmapold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_layout);
        binding = AddCategoryLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            firebaseAuth.signInAnonymously();
        }

        maloai = getIntent().getIntExtra("maloai",0);
        tenloai = getIntent().getStringExtra("tenloai");
        url = getIntent().getStringExtra("url");
        if(maloai!=0)
        {
            binding.txtlAddcategoryTenLoai.getEditText().setText(tenloai);
            new DownloadImageTask(binding.imgAddcategoryThemHinh).execute(url);
            binding.txtAddcategoryTitle.setText("S???a lo???i");

        }

        binding.imgAddcategoryBack.setOnClickListener(this::onClick);
        binding.imgAddcategoryThemHinh.setOnClickListener(this::onClick);

        storage = FirebaseStorage.getInstance("gs://coffee3ae.appspot.com");
        storageRef = storage.getReferenceFromUrl("gs://coffee3ae.appspot.com/LoaiMon");
        databaseRef = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LoaiMon");


        Query query = databaseRef.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LoaiMon loaiMon = snapshot.getValue(LoaiMon.class);
                id = loaiMon.getMaLoai();

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

        binding.btnAddcategoryTaoLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String imgname = "image" + calendar.getTimeInMillis() + ".PNG";
                StorageReference mountainsRef = storageRef.child(imgname);
                binding.imgAddcategoryThemHinh.setDrawingCacheEnabled(true);
                binding.imgAddcategoryThemHinh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.imgAddcategoryThemHinh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddCategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(!validateImage() || !validateName()){
                                    return;
                                }
                                String chucnang;
                                boolean ktra;

                                if(id >= 0 && maloai==0)
                                {
                                    ktra = true;
                                    chucnang = "themloai";
                                    id += 1;
                                    String TenLoai = binding.txtlAddcategoryTenLoai.getEditText().getText().toString();
                                    LoaiMon loaiMon = new LoaiMon(id,TenLoai,uri.toString());
                                    databaseRef.child(String.valueOf(id)).setValue(loaiMon);
                                    Intent intent = new Intent();
                                    intent.putExtra("ktra",ktra);
                                    intent.putExtra("chucnang",chucnang);
                                    setResult(RESULT_OK,intent);
                                    finish();

                                }
                                if(maloai!=0)
                                {
                                    ktra = true;
                                    chucnang = "sualoai";
                                    String TenLoai = binding.txtlAddcategoryTenLoai.getEditText().getText().toString();
                                    LoaiMon loaiMon = new LoaiMon(maloai,TenLoai,uri.toString());
                                    databaseRef.child(String.valueOf(maloai)).setValue(loaiMon);
                                    Intent intent = new Intent();
                                    intent.putExtra("ktra",ktra);
                                    intent.putExtra("chucnang",chucnang);
                                    setResult(RESULT_OK,intent);
                                    finish();

                                }

                            }
                        });


                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Uri uri = data.getData();
            try {
                Bitmap bitmap;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.imgAddcategoryThemHinh.setImageURI(uri);
                binding.imgAddcategoryThemHinh.setImageBitmap(bitmap);

            }
            catch (IOException e){e.printStackTrace();}
        }
    }


    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.img_addcategory_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right); //animation
                break;

            case R.id.img_addcategory_ThemHinh:
                Intent intent_img = new Intent();
                intent_img.setAction(Intent.ACTION_GET_CONTENT);
                intent_img.setType("image/*");
                startActivityForResult(Intent.createChooser(intent_img, "Title"), SELECT_IMAGE_CODE);    //m??? intent ch???n h??nh ???nh
                break;
        }
    }

    //region validate fields
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)binding.imgAddcategoryThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin ch???n h??nh ???nh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = binding.txtlAddcategoryTenLoai.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            binding.txtlAddcategoryTenLoai.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            binding.txtlAddcategoryTenLoai.setError(null);
            binding.txtlAddcategoryTenLoai.setErrorEnabled(false);
            return true;
        }
    }



}