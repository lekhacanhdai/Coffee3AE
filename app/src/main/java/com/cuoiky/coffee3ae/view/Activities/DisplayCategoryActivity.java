
package com.cuoiky.coffee3ae.view.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.LoaiMon;
import com.cuoiky.coffee3ae.viewmodel.AdapterDisplayCategory;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class DisplayCategoryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<LoaiMon> dataArrayList;
    private AdapterDisplayCategory dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_category_layout);


    }
}