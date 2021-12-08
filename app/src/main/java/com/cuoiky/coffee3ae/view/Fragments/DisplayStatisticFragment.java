package com.cuoiky.coffee3ae.view.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.view.Activities.AddCategoryActivity;
import com.cuoiky.coffee3ae.view.Activities.AddStaffActivity;
import com.cuoiky.coffee3ae.view.Activities.DetailStatisticActivity;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.view.Activities.PaymentActivity;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayStatistic;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DisplayStatisticFragment extends Fragment {

    GridView lvStatistic;
    DonDat donDat;
    ArrayList<ChiTietDonDat> listDonDat;
    AdapterDisplayStatistic adapterDisplayStatistic;
    FragmentManager fragmentManager;
    private DatabaseReference databaseReference;
    int madon, manv, maban;
    String ngaydat, tongtien,nguoidat,tenban;


    public DisplayStatisticFragment() {
        // Required empty public constructor
    }

    public static DisplayStatisticFragment newInstance(String param1, String param2) {
        DisplayStatisticFragment fragment = new DisplayStatisticFragment();
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
        View view = inflater.inflate(R.layout.display_statistic_fragment,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý thống kê");
        setHasOptionsMenu(true);

        lvStatistic = (GridView) view.findViewById(R.id.lvStatistic);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("ChiTietDonDat");
        listDonDat = new ArrayList<ChiTietDonDat>();

        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.order_item_row,listDonDat);
        lvStatistic.setAdapter(adapterDisplayStatistic);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren())
                {
                    ChiTietDonDat data = item.getValue(ChiTietDonDat.class);
                    listDonDat.add(data);
                }
                adapterDisplayStatistic.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madon = listDonDat.get(position).getDonDat().getMaDonDat();
                manv = listDonDat.get(position).getDonDat().getNhanVien().getMaNV();
                maban = listDonDat.get(position).getDonDat().getBan().getMaBan();
                ngaydat = listDonDat.get(position).getDonDat().getNgayDat();
                tongtien = listDonDat.get(position).getDonDat().getTongTien();
                nguoidat = listDonDat.get(position).getDonDat().getNhanVien().getHoTenNV();
                tenban = listDonDat.get(position).getDonDat().getBan().getTenBan();

                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class); // sua lai
                intent.putExtra("madon",madon);
                intent.putExtra("manv",manv);
                intent.putExtra("maban",maban);
                intent.putExtra("ngaydat",ngaydat);
                intent.putExtra("tongtien",tongtien);
                intent.putExtra("nguoidat",nguoidat);
                intent.putExtra("tenban",tenban);

                startActivity(intent);
            }
        });


        return view;
    }
}