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
import com.alphago.alphago.adapter.CategoryAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.LearningSelectionMethodDialog;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CardBookActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener {
    private Button btnLearning;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

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

        adapter = new CategoryAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        DbHelper dbHelper = new DbHelper(getBaseContext());
        adapter.setList(dbHelper.categorySelect());
    }

    @Override
    public void onCardClick(Object data) {
        if (data instanceof Category) {
            Intent intent = new Intent(getBaseContext(), CardBookListActivity.class);
            intent.putExtra("categoryId", ((Category) data).getId());
            intent.putExtra("category", ((Category) data).getLabel());
            startActivity(intent);
        }
    }
}
