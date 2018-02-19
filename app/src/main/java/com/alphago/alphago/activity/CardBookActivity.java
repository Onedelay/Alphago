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
import com.alphago.alphago.adapter.CardBookAdapter;
import com.alphago.alphago.model.CardBook;

import java.util.ArrayList;
import java.util.List;

public class CardBookActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener{
    private Button btnHome;
    private Button btnLearning;
    private RecyclerView recyclerView;
    private CardBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book);

        btnHome = (Button) findViewById(R.id.btn_cd_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getBaseContext(), "홈 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardBookActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLearning = (Button) findViewById(R.id.btn_learning);
        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getBaseContext(), "학습하기 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardBookActivity.this, WordLearningActivity.class);
                startActivity(intent);
            }
        });

        adapter = new CardBookAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        List<CardBook> list = new ArrayList<>();
        list.add(new CardBook("A", R.mipmap.ic_launcher));
        list.add(new CardBook("B", R.mipmap.ic_launcher));
        list.add(new CardBook("C", R.mipmap.ic_launcher));
        list.add(new CardBook("D", R.mipmap.ic_launcher));
        list.add(new CardBook("E", R.mipmap.ic_launcher));
        list.add(new CardBook("F", R.mipmap.ic_launcher));
        list.add(new CardBook("G", R.mipmap.ic_launcher));
        adapter.setList(list);
    }

    @Override
    public void onCardClick(CardBook cardBook) {
        Toast.makeText(this, cardBook.getName(), Toast.LENGTH_SHORT).show();
    }
}
