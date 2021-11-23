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
import com.cuoiky.coffee3ae.model.Mon;
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

public class AddMenuActivity extends AppCompatActivity {

    private AddMenuLayoutBinding binding;
    private FirebaseAuth firebaseAuth;
    private final int SELECT_IMAGE_CODE = 1;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private DatabaseReference databaseRef;
    private static int  count = 1;

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
        binding.imgAddmenuThemHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_img = new Intent();
                intent_img.setAction(Intent.ACTION_GET_CONTENT);
                intent_img.setType("image/*");
                startActivityForResult(Intent.createChooser(intent_img, "Title"), SELECT_IMAGE_CODE);
            }
        });
        storage = FirebaseStorage.getInstance("gs://coffee3ae.appspot.com");
        storageRef = storage.getReferenceFromUrl("gs://coffee3ae.appspot.com/Mon");
        databaseRef = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
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
                                int maMon = count;
                                int maLoai = count;
                                String tenMon = binding.txtlAddmenuTenMon.getEditText().getText().toString();
                                String giaTien = binding.txtlAddmenuGiaTien.getEditText().getText().toString();
                                //  String loaiMon = binding.txtlAddmenuLoaiMon.getEditText().getText().toString();
                                String tinhtrang = "true";
                                //Mon mon  = new Mon(maMon,maLoai,tenMon,giaTien,tinhtrang,uri.toString());
                               // databaseRef.child(String.valueOf(maMon)).setValue(mon);
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
                binding.imgAddmenuThemHinh.setImageURI(uri);
                binding.imgAddmenuThemHinh.setImageBitmap(bitmap);

            }
            catch (IOException e){e.printStackTrace();}
        }
    }
}