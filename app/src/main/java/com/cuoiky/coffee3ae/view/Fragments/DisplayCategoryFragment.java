package com.cuoiky.coffee3ae.view.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cuoiky.coffee3ae.MainActivity;
import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.DisplayCategoryLayoutBinding;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.view.Activities.DisplayMenuActivity;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory;
import com.cuoiky.coffee3ae.viewmodel.IClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCategoryFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<LoaiMon> loaiMonList;
    private AdapterDisplayCategory loaiMonAdapter;
    private DisplayCategoryLayoutBinding binding;
    FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.display_category_layout, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý thực đơn");

        fragmentManager = getActivity().getSupportFragmentManager();

        binding = DisplayCategoryLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        getActivity().setContentView(viewRoot);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LoaiMon");
        loaiMonList = new ArrayList<LoaiMon>();
        loaiMonAdapter = new AdapterDisplayCategory(loaiMonList, new IClickListener() {
            @Override
            public void onClickLoaiMon(LoaiMon loaiMon) {
                int maloai = loaiMon.getMaLoai();
                String url = loaiMon.getHinhAnh();
                String tenLoai = loaiMon.getTenLoai();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("url",url);
                bundle.putString("tenloai",tenLoai);
                displayMenuFragment.setArguments(bundle);
                System.out.println(tenLoai);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_view,displayMenuFragment);
                transaction.commit();


            }
        });

        binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
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



        return view;
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }


}
