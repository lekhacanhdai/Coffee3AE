package com.cuoiky.coffee3ae.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.FragmentHomeBinding;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory;
import com.cuoiky.coffee3ae.viewmodel.DonDatAdapter;
import com.cuoiky.coffee3ae.viewmodel.IClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private ArrayList<LoaiMon> loaiMonList;
    AdapterDisplayCategory loaiMonAdapter;

    private ArrayList<DonDat> listDonDat;
    private DonDatAdapter donDatAdapter;
    private DatabaseReference databaseRef;
    private DatabaseReference donDatRef;
    private FirebaseDatabase mDatabase;
    private TextView tvAllTypeHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);
        mDatabase = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseRef = mDatabase.getReference("LoaiMon");
        loaiMonList = new ArrayList<LoaiMon>();

        RecyclerView rvTypeMenuHome = view.findViewById(R.id.rv_type_menu_home);
        rvTypeMenuHome.setHasFixedSize(true);
        rvTypeMenuHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loaiMonAdapter = new AdapterDisplayCategory(loaiMonList, new IClickListener() {
            @Override
            public void onClickLoaiMon(LoaiMon loaiMon) {

            }
        });

        rvTypeMenuHome.setAdapter(loaiMonAdapter);

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


        RecyclerView rvDonDatHome = view.findViewById(R.id.rv_don_dat_honme);

        donDatRef = mDatabase.getReference("DonDat");
        listDonDat = new ArrayList<DonDat>();
        rvDonDatHome.setHasFixedSize(true);
        rvDonDatHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        donDatAdapter = new DonDatAdapter(listDonDat);

        rvDonDatHome.setAdapter(donDatAdapter);
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

        tvAllTypeHome = view.findViewById(R.id.tv_all_type_home);
        tvAllTypeHome.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int clickID = v.getId();

        switch (clickID){
            case R.id.tv_all_type_home:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.home_view, new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                break;
            default:
                break;

        }
    }
}