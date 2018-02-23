package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.TestData;
import com.alphago.alphago.adapter.CardBookAdapter;
import com.alphago.alphago.fragment.LearningSelectionMethodDialog;
import com.alphago.alphago.model.CardBook;

import java.util.ArrayList;
import java.util.List;

public class CardBookActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener {
    private Button btnLearning;
    private RecyclerView recyclerView;
    private CardBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book);

        btnLearning = (Button) findViewById(R.id.btn_learning);
        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LearningSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        adapter = new CardBookAdapter(this, true);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        List<CardBook> list = new ArrayList<>();

        list.add(new CardBook(TestData.dataLabel[0], TestData.dataCat[0], R.drawable.tmp_dog));
        list.add(new CardBook(TestData.dataLabel[3], TestData.dataCat[3], R.drawable.tmp_apple));
        list.add(new CardBook(TestData.dataLabel[7], TestData.dataCat[7], R.drawable.tmp_bed));
        list.add(new CardBook(TestData.dataLabel[10], TestData.dataCat[10], R.drawable.tmp_tomato));

        adapter.setList(list);
    }

    @Override
    public void onCardClick(CardBook cardBook) {
        Intent intent = new Intent(getBaseContext(), CardBookListActivity.class);
        intent.putExtra("category",cardBook.getCategory());
        startActivity(intent);
    }
}
