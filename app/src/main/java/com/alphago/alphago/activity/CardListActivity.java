package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CardAdapter;
import com.alphago.alphago.adapter.CardBookAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.CardBook;

import org.w3c.dom.Text;

public class CardListActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener {
    private TextView cat;
    private RecyclerView recyclerView;
    private CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book_list);

        long labelId = getIntent().getLongExtra("labelId", -1);
        String label = getIntent().getStringExtra("label");


        cat = (TextView) findViewById(R.id.cardbook_list_main_label);
        cat.setText(label);

        adapter = new CardAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        DbHelper dbHelper = new DbHelper(getBaseContext());
        adapter.setList(dbHelper.cardsSelect(labelId));
    }

    @Override
    public void onCardClick(Object data) {
        // Do nothing.
    }
}
