package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.code.cframe.activity.BasePermissionActivity;

public class MainActivity extends BasePermissionActivity {

    private ListView lv;

    private List<String> str_name = new ArrayList<String>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        str_name.add("CListView");
        str_name.add("ImageUtils");
        str_name.add("SharedPreferencesUtils");
        str_name.add("SuperButton");
        str_name.add("FileUtils");
        str_name.add("RetrofitUtils");
        str_name.add("Banner");
        str_name.add("TabBar");
        str_name.add("TabLayout");
        str_name.add("FastListActivity");
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str_name);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(MainActivity.this, BaseRecyclerViewActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(MainActivity.this, FrescoActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(MainActivity.this, SPUtilsActivity.class));
                } else if (position == 3) {
                    startActivity(new Intent(MainActivity.this, SuperButtonActivity.class));
                } else if (position == 4) {
                    startActivity(new Intent(MainActivity.this, FileUtilsActivity.class));
                } else if (position == 5) {
                    startActivity(new Intent(MainActivity.this, RetrofitUtilsActivity.class));
                } else if (position == 6) {
                    startActivity(new Intent(MainActivity.this, BannerActivity.class));
                } else if (position == 7) {
                    startActivity(new Intent(MainActivity.this, TabBarActivity.class));
                } else if (position == 8) {
                    startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
                } else if (position == 9) {
                    startActivity(new Intent(MainActivity.this, FastListActivity.class));
                }
            }
        });
    }

    @Override
    public List<String> needPermissions() {
        return new ArrayList<String>() {{
            add(Manifest.permission.READ_EXTERNAL_STORAGE);
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(permission.CAMERA);
        }};
    }
}
