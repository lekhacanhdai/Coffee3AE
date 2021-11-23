package com.cuoiky.coffee3ae.view.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.DisplayMenuLayoutBinding;
import com.cuoiky.coffee3ae.model.Mon;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMenuActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<Mon> listMon;
    private AdapterDisplayMenu listMonAdapter;
    private DisplayMenuLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_menu_layout);

        binding = DisplayMenuLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
        listMon = new ArrayList<Mon>();
        listMonAdapter = new AdapterDisplayMenu(listMon);

        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMenu.setAdapter(listMonAdapter);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    Mon data = item.getValue(Mon.class);
                    listMon.add(data);

                }
                listMonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}