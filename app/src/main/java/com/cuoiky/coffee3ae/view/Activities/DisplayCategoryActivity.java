
package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.DisplayCategoryLayoutBinding;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCategoryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<LoaiMon> loaiMonList;
    private AdapterDisplayCategory loaiMonAdapter;
    private DisplayCategoryLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_category_layout);

        binding = DisplayCategoryLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LoaiMon");
        loaiMonList = new ArrayList<LoaiMon>();
        loaiMonAdapter = new AdapterDisplayCategory(loaiMonList);

        binding.rvCategory.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCategory.setAdapter(loaiMonAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    LoaiMon data = item.getValue(LoaiMon.class);
                    loaiMonList.add(data);

                }
                loaiMonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SetupSuportActionBar() {
         ActionBar actionBar = getSupportActionBar();
         actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setDisplayShowCustomEnabled(true);
         actionBar.setCustomView(R.layout.support_menu_category);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}