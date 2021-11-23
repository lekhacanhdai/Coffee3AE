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
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory;

import com.cuoiky.coffee3ae.viewmodel.DonDatAdapter;
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

    private ArrayList<DonDat> listDonDat;
    private DonDatAdapter donDatAdapter;
    private DatabaseReference databaseRef;
    private DatabaseReference donDatRef;
    private FirebaseDatabase mDatabase;
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

        mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseRef = mDatabase.getReference("LoaiMon");
        loaiMonList = new ArrayList<LoaiMon>();

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        getActivity().setContentView(viewRoot);


        binding.rvTypeMenuHome.setHasFixedSize(true);
        binding.rvTypeMenuHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loaiMonAdapter = new AdapterDisplayCategory(loaiMonList);

        binding.rvTypeMenuHome.setAdapter(loaiMonAdapter);

        databaseRef.addValueEventListener(new ValueEventListener() {
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

        donDatRef = mDatabase.getReference("DonDat");
        listDonDat = new ArrayList<DonDat>();
        binding.rvDonDatHonme.setHasFixedSize(true);
        binding.rvDonDatHonme.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        donDatAdapter = new DonDatAdapter(listDonDat);

        binding.rvDonDatHonme.setAdapter(donDatAdapter);
        donDatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    DonDat donDat = item.getValue(DonDat.class);
                    listDonDat.add(donDat);
                }
                donDatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}