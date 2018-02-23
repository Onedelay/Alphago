package com.alphago.alphago.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

import org.w3c.dom.Text;

public class SelectImageDetail extends NoStatusBarActivity {
    private TextView detailLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image_detail);

        String label = getIntent().getStringExtra("label");
        detailLabel = (TextView) findViewById(R.id.detail_label);
        detailLabel.setText(label);

    }
}
