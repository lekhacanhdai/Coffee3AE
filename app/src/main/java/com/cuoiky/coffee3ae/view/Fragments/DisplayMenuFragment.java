package com.cuoiky.coffee3ae.view.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
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

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.DisplayMenuLayoutBinding;
import com.cuoiky.coffee3ae.model.Mon;
import com.cuoiky.coffee3ae.view.Activities.AddMenuActivity;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayMenu1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMenuFragment extends Fragment {

    int maloai, maban;
    String tenloai,tinhtrang,url,tenmon,giatien;
    GridView gvDisplayMenu;

    private DatabaseReference databaseReference;
    private ArrayList<Mon> listMon;
    private AdapterDisplayMenu1 listMonAdapter;
    private DisplayMenuLayoutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themmon"))
                        {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSMon();
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

        View view = inflater.inflate(R.layout.display_menu_layout, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý thực đơn");

        Bundle bundle = getArguments();
        if(bundle !=null) {
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            url = bundle.getString("url");
            maban = bundle.getInt("maban");
        }

        gvDisplayMenu = view.findViewById(R.id.gvDisplayMenu);
        databaseReference = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Mon");
        listMon = new ArrayList<Mon>();
        HienThiDSMon();


        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
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

    //Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = listMon.get(vitri).getMaMon();
        tinhtrang = listMon.get(vitri).getTinhTrang();
        tenmon = listMon.get(vitri).getTenMon();
        giatien=listMon.get(vitri).getGiaTien();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                iEdit.putExtra("mamon",mamon);
                iEdit.putExtra("maLoai",maloai);
                iEdit.putExtra("tenmon",tenmon);
                iEdit.putExtra("tenLoai",tenloai);
                iEdit.putExtra("url",url);
                iEdit.putExtra("tinhtrang",tinhtrang);
                iEdit.putExtra("giatien",giatien);
                resultLauncherMenu.launch(iEdit);
                break;

            case R.id.itDelete:
                String delete = "Mon"+"/"+mamon;
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://coffee3ae-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = database.getReference(delete);
                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddMenu:
                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("maLoai",maloai);
                intent.putExtra("tenLoai",tenloai);
                intent.putExtra("url",url);
                resultLauncherMenu.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSMon()
    {
        listMonAdapter = new AdapterDisplayMenu1( getActivity(),R.layout.custom_layout_displaymenu,listMon);
        gvDisplayMenu.setAdapter(listMonAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(listMon!=null)
                {
                    listMon.clear();
                    listMonAdapter.notifyDataSetChanged();
                }
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
