package com.cuoiky.coffee3ae.view.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.model.NhanVien;
import com.cuoiky.coffee3ae.view.Activities.AddStaffActivity;
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
    FragmentManager fragmentManager;


    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        long ktra = intent.getLongExtra("ketquaktra",0);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themnv"))
                        {
                            if(ktra != 0){
                                HienThiDSNV();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra != 0){
                                HienThiDSNV();
                                Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_staff_frament,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý nhân viên");
        setHasOptionsMenu(true);
        gvStaff = (GridView)view.findViewById(R.id.gvStaff) ;


        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NhanVien");
        nhanVienList = new ArrayList<NhanVien>();

        HienThiDSNV();
        registerForContextMenu(gvStaff);

        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manv = nhanVienList.get(vitri).getMaNV();
        NhanVien nhanVienEdit = nhanVienList.get(vitri);


        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(),AddStaffActivity.class);
                iEdit.putExtra("manv",manv);
                iEdit.putExtra("hoten", nhanVienEdit.getHoTenNV());
                iEdit.putExtra("tendn", nhanVienEdit.getTenDN());
                iEdit.putExtra("matkhau", nhanVienEdit.getMatKhau());
                iEdit.putExtra("sdt", nhanVienEdit.getSdt());
                iEdit.putExtra("ngaysinh", nhanVienEdit.getNgaySinh());
                iEdit.putExtra("maquyen", nhanVienEdit.getQuyen().getMaQuyen());
                iEdit.putExtra("gioitinh", nhanVienEdit.getGioiTinh());
                iEdit.putExtra("email", nhanVienEdit.getEmail());

                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
//                boolean ktra = nhanVienDAO.XoaNV(manv);
                databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NhanVien");
                databaseReference.child(String.valueOf(manv)).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                                ,Toast.LENGTH_SHORT).show();
                    }
                });
                HienThiDSNV();
                /*if(ktra){
                    HienThiDSNV();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }*/
                break;
        }
        return true;
    }

    private void HienThiDSNV(){
        adapterDisplayStaff = new AdapterDisplayStaff(nhanVienList,R.layout.custom_layout_displaystaff,getActivity());
        gvStaff.setAdapter(adapterDisplayStaff);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (nhanVienList != null){
                    nhanVienList.clear();
                }
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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddStaff = menu.add(1,R.id.itAddStaff,1,"Thêm nhân viên");
        itAddStaff.setIcon(R.drawable.ic_baseline_add_24);
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddStaff:
                Intent iDangky = new Intent(getActivity(), AddStaffActivity.class);
                resultLauncherAdd.launch(iDangky);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}