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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private static int  count = 1;

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
        binding.imgAddcategoryThemHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_img = new Intent();
                intent_img.setAction(Intent.ACTION_GET_CONTENT);
                intent_img.setType("image/*");
                startActivityForResult(Intent.createChooser(intent_img, "Title"), SELECT_IMAGE_CODE);
            }
        });
        storage = FirebaseStorage.getInstance("gs://coffee3ae.appspot.com");
        storageRef = storage.getReferenceFromUrl("gs://coffee3ae.appspot.com/LoaiMon");
        databaseRef = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LoaiMon");
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
                                int maloai = count;
                                String TenLoai = binding.txtlAddcategoryTenLoai.getEditText().getText().toString();
                                LoaiMon loaiMon = new LoaiMon(maloai,TenLoai,uri.toString());
                                // String uploadId = databaseRef.push().getKey();
                                databaseRef.child(String.valueOf(maloai)).setValue(loaiMon);
                                count++;

                            }
                        });


                    }
                });
            }
        });
    }
    private void signInAnonymously() {
        firebaseAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
//                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
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


}