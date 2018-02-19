package com.alphago.alphago;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by su_me on 2018-02-04.
 */

public class NoStatusBarActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
