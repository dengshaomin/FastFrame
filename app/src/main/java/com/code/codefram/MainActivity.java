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

import com.code.cframe.baseactivity.BasePermissionActivity;

public class MainActivity extends BasePermissionActivity {

    private ListView lv;

    private List<Class> str_name = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        str_name.add(BaseRecyclerSimpleActivity.class);
        str_name.add(FrescoActivity.class);
        str_name.add(SPUtilsActivity.class);
        str_name.add(SuperButtonActivity.class);
        str_name.add(FileUtilsActivity.class);
        str_name.add(RetrofitUtilsActivity.class);
        str_name.add(BannerActivity.class);
        str_name.add(TabBarActivity.class);
        str_name.add(TabLayoutActivity.class);
        str_name.add(FastRecycleSimpleActivity.class);
        str_name.add(FastTitleSimpleActivity.class);
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, str_name);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, str_name.get(position)));
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
