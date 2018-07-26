package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CardAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.Card;

public class CardListActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book_list);

        long labelId = getIntent().getLongExtra("labelId", -1);
        String label = getIntent().getStringExtra("label");


        TextView cat = findViewById(R.id.cardbook_list_main_label);
        cat.setText(label);

        CardAdapter adapter = new CardAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        DbHelper dbHelper = new DbHelper(getBaseContext());
        adapter.setList(dbHelper.cardsSelect(labelId));
    }

    @Override
    public void onCardClick(Object data) {
        if (data instanceof Card) {
            Intent intent = new Intent(this, WordDetailActivity.class);
            intent.putExtra("label", ((Card) data).getLabel());
            intent.putExtra("filePath", ((Card) data).getFilePath());
            startActivity(intent);
        }
    }
}
