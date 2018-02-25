package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.TestData;
import com.alphago.alphago.adapter.CardBookAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.CardBook;

import java.util.ArrayList;
import java.util.List;

public class CardBookListActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener {
    private TextView cat;
    private RecyclerView recyclerView;
    private CardBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book_list);

        long categoryId = getIntent().getLongExtra("categoryId", -1);
        String category = getIntent().getStringExtra("category");


        cat = (TextView) findViewById(R.id.cardbook_list_main_label);
        cat.setText(category);

        adapter = new CardBookAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        DbHelper dbHelper = new DbHelper(getBaseContext());
        adapter.setList(dbHelper.labelSelect(categoryId));
    }

    @Override
    public void onCardClick(Object data) {
        if (data instanceof CardBook) {
            Toast.makeText(this, "on click", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SelectImageDetail.class);
            intent.putExtra("label", ((CardBook) data).getName());
            startActivity(intent);
        }
    }
}
