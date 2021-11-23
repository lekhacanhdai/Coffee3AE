package com.cuoiky.coffee3ae.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.FragmentHomeBinding;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory;

import com.cuoiky.coffee3ae.viewmodel.IClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ArrayList<LoaiMon> loaiMonList;
    AdapterDisplayCategory loaiMonAdapter;
    private DatabaseReference mDatabase;
    FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LoaiMon");
        loaiMonList = new ArrayList<LoaiMon>();

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        getActivity().setContentView(viewRoot);


        binding.rvTypeMenuHome.setHasFixedSize(true);
        binding.rvTypeMenuHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loaiMonAdapter = new AdapterDisplayCategory(loaiMonList, new IClickListener() {
            @Override
            public void onClickLoaiMon(LoaiMon loaiMon) {

            }
        });

        binding.rvTypeMenuHome.setAdapter(loaiMonAdapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()){
                    LoaiMon data = item.getValue(LoaiMon.class);
                    loaiMonList.add(data);
                }
                loaiMonAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}