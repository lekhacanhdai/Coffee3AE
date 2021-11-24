package com.cuoiky.coffee3ae.view.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayStaff;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayStaffFrament extends Fragment {

    GridView gvStaff;
    NhanVien nhanVien;
    ArrayList<NhanVien> nhanVienList;
    AdapterDisplayStaff adapterDisplayStaff;
    private DatabaseReference databaseReference;

    public DisplayStaffFrament() {
        // Required empty public constructor
    }

    public static DisplayStaffFrament newInstance(String param1, String param2) {
        DisplayStaffFrament fragment = new DisplayStaffFrament();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_staff_frament,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý nhân viên");
        setHasOptionsMenu(true);
        gvStaff = (GridView)view.findViewById(R.id.gvStaff) ;

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NhanVien");
        nhanVienList = new ArrayList<NhanVien>();

        adapterDisplayStaff = new AdapterDisplayStaff(nhanVienList,R.layout.custom_layout_displaystaff,getActivity());
        gvStaff.setAdapter(adapterDisplayStaff);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    NhanVien data = item.getValue(NhanVien.class);
                    nhanVienList.add(data);
                }
                adapterDisplayStaff.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}