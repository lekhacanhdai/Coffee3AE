package com.cuoiky.coffee3ae.view.Fragments;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class DisplayMenuFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<Mon> listMon;
    private AdapterDisplayMenu listMonAdapter;
    private DisplayMenuLayoutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DisplayMenuLayoutBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        getActivity().setContentView(viewRoot);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
        listMon = new ArrayList<Mon>();
        listMonAdapter = new AdapterDisplayMenu(listMon);

        binding.rvMenu.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
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

        return inflater.inflate(R.layout.display_menu_layout, container, false);
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }


}
