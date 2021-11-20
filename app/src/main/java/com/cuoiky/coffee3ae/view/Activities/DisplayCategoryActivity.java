
package com.cuoiky.coffee3ae.view.Activities;

import androidx.annotation.NonNull;
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
    private ArrayList<LoaiMon> dataArrayList;
    private AdapterDisplayCategory dataAdapter;
    private DisplayCategoryLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_category_layout);

        binding = DisplayCategoryLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        dataArrayList = new ArrayList<LoaiMon>();
        dataAdapter = new AdapterDisplayCategory(dataArrayList);

        binding.gvCategory.setLayoutManager(new LinearLayoutManager(this));
        binding.gvCategory.setAdapter(dataAdapter);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/LoaiMon/").getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    LoaiMon data = item.getValue(LoaiMon.class);
                    dataArrayList.add(data);
                    dataAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}