package com.ealpha.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ealpha.R;
//import com.ps.DTO.ExpandableListDataSource;

public class Splash extends Activity {
    private static int spashscreenTiomOut = 4000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        context = this;
        //ExpandableListDataSource.getSubCategoryFromServer(context);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent in = new Intent(Splash.this, MainActivity.class);
                startActivity(in);
                finish();

            }
        }, spashscreenTiomOut);
    }
}