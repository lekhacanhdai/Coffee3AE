package com.cuoiky.coffee3ae.view.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.view.Activities.AddCategoryActivity;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCategoryFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<LoaiMon> loaiMonList;
    private AdapterDisplayCategory1 loaiMonAdapter;
    private GridView gridView;
    FragmentManager fragmentManager;
    int maban,manv;
    boolean tinhtrang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themloai"))
                        {
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
                        }else {
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"S???a th??nh c??ng",Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.display_category_layout, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Qu???n l?? th???c ????n");

        fragmentManager = getActivity().getSupportFragmentManager();
        gridView = view.findViewById(R.id.rvCategory);

        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("LoaiMon");
        loaiMonList = new ArrayList<LoaiMon>();

        HienThiDSLoai();


        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            maban = bDataCategory.getInt("maban");
            manv = bDataCategory.getInt("manv");
            tinhtrang = bDataCategory.getBoolean("tinhtrang");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonList.get(position).getMaLoai();
                String url = loaiMonList.get(position).getHinhAnh();
                String tenLoai = loaiMonList.get(position).getTenLoai();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("url",url);
                bundle.putString("tenloai",tenLoai);
                bundle.putInt("maban",maban);
                bundle.putInt("manv",manv);
                bundle.putBoolean("tinhtrang",tinhtrang);
                Log.d("Ma_nv_catalogy", ""+manv);
                displayMenuFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_view,displayMenuFragment).addToBackStack("hienthiloai");
                transaction.commit();

            }
        });

         registerForContextMenu(gridView);

        return view;
    }

    //hi???n th??? contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //x??? l?? context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiMonList.get(vitri).getMaLoai();
        String tenloai = loaiMonList.get(vitri).getTenLoai();
        String url = loaiMonList.get(vitri).getHinhAnh();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddCategoryActivity.class);
                iEdit.putExtra("maloai",maloai);
                iEdit.putExtra("tenloai",tenloai);
                iEdit.putExtra("url",url) ;
                resultLauncherCategory.launch(iEdit);
                break;

            case R.id.itDelete:
                String delete = "LoaiMon"+"/"+maloai;
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = database.getReference(delete);
                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(),"X??a th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        return true;
    }

    //kh???i t???o n??t th??m lo???i
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //x??? l?? n??t th??m lo???i
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddCategory:
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                resultLauncherCategory.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void HienThiDSLoai()
    {
        loaiMonAdapter = new AdapterDisplayCategory1(getActivity(),R.layout.custom_layout_displaycategory,loaiMonList);
        gridView.setAdapter(loaiMonAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(loaiMonList != null){
                    loaiMonList.clear();
                    loaiMonAdapter.notifyDataSetChanged();
                }
                for (DataSnapshot item : snapshot.getChildren()) {
                    LoaiMon data = item.getValue(LoaiMon.class);
                    loaiMonList.add(data);
                    loaiMonAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }


}
