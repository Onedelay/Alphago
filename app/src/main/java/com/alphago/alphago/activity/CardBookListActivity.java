package com.alphago.alphago.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CardBookAdapter;
import com.alphago.alphago.database.DbHelper;

public class CardBookListActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener {
    private TextView cat;
    private RecyclerView recyclerView;
    private CardBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book_list);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        String lang = StartActivity.sharedPreferences.getString("Language","ENG");

        long categoryId = getIntent().getLongExtra("categoryId", -1);
        String category = getIntent().getStringExtra("category");


        cat = (TextView) findViewById(R.id.cardbook_list_main_label);
        cat.setText(category);

        adapter = new CardBookAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        DbHelper dbHelper = new DbHelper(getBaseContext());
        adapter.setList(dbHelper.cardbookSelect(categoryId, lang));
    }

    @Override
    public void onCardClick(Object data) {
//        if (data instanceof CardBook) {
//            Intent intent = new Intent(this, CardListActivity.class);
//            intent.putExtra("label", ((CardBook) data).getName());
//            intent.putExtra("labelId", ((CardBook) data).getId());
//            startActivity(intent);
//        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
