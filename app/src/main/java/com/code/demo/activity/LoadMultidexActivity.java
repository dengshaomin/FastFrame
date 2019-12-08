package com.code.demo.activity;

import java.io.File;
import java.io.IOException;

import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import com.code.demo.R;


public class LoadMultidexActivity extends AppCompatActivity {

    public static final String MULTIDEX_FLAG_FILE = "MULTIDEX_FLAG_FILE";
    public static final String MULTIDEX_INSTALLED_FLAG_FILE = "MULTIDEX_INSTALLED_FLAG_FILE";
    String path = null;
    String installedPath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_multidex);
        path = getIntent().getStringExtra(MULTIDEX_FLAG_FILE);
        installedPath = getIntent().getStringExtra(MULTIDEX_INSTALLED_FLAG_FILE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                MultiDex.install(LoadMultidexActivity.this);
                try {
                    //模拟加载耗时
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    new File(installedPath).createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        //不响应返回键
    }
}
