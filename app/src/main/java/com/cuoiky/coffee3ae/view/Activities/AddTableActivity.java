package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.AddCategoryLayoutBinding;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_addtable_tenban;
    Button BTN_addtable_TaoBan;
    BanAn banAn;
    private DatabaseReference databaseRef;
    private FirebaseAuth firebaseAuth;
    private int  id ;
    private boolean duocChon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_add_table);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            firebaseAuth.signInAnonymously();
        }

        
        TXTL_addtable_tenban = (TextInputLayout)findViewById(R.id.txtl_addtable_tenban);
        BTN_addtable_TaoBan = (Button)findViewById(R.id.btn_addtable_TaoBan);

        databaseRef = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("BanAn");

        Query query = databaseRef.limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BanAn banAn = snapshot.getValue(BanAn.class);
                id = banAn.getMaBan();

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

        banAn = new BanAn();
        BTN_addtable_TaoBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenBanAn = TXTL_addtable_tenban.getEditText().getText().toString();
                if(TenBanAn != null || TenBanAn.equals("")){
                    id += 1;
                    banAn = new BanAn(id,TenBanAn,duocChon);
                    databaseRef.child(String.valueOf(id)).setValue(banAn);

                   // boolean ktra = banAnDAO.ThemBanAn(sTenBanAn);
                    //trả về result cho displaytable
                    Intent intent = new Intent();
                   // intent.putExtra("ketquathem",ktra);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_addtable_tenban.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addtable_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addtable_tenban.setError(null);
            TXTL_addtable_tenban.setErrorEnabled(false);
            return true;
        }
    }
}