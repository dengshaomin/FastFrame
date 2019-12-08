package com.code.codefram.demo;

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

import com.billy.cc.core.component.CC;
import com.code.fastframe.baseactivity.BasePermissionActivity;
import com.code.codefram.R;
import com.code.fastframe.utils.DPUtils;
import com.code.fastframe.utils.DensityUtil;

public class MainActivity extends BasePermissionActivity {

    private ListView lv;
    private List<Class> str_name = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        float x1 = DensityUtil.dip2px(2);

        float x2 = DPUtils.get(2);

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
        str_name.add(LazyViewPagerActivity.class);
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, str_name);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, str_name.get(position)));
            }
        });
        CC.obtainBuilder("TestComponent").setActionName("showActivity").build().call();
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
