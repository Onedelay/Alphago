package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CategoryAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.LearningSelectionMethodDialog;
import com.alphago.alphago.model.Category;

import java.util.ArrayList;

public class CardBookActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener, LearningSelectionMethodDialog.OnLearningCategoryListener {
    private Button btnLearning;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private boolean isSelectMode = false;
    private ArrayList<Long> selectList = new ArrayList<Long>();
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book);

        final Intent intent = new Intent(this, WordLearningActivity.class);
        dbHelper = new DbHelper(getBaseContext());

        btnLearning = (Button) findViewById(R.id.btn_learning);
        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSelectMode){
                    new LearningSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
                } else {
                    intent.putExtra("category_select_list", selectList);
                    intent.putExtra("learning_type", LearningSelectionMethodDialog.TYPE_ALBUM);
                    startActivity(intent);
                    cancelLearning(dbHelper);
                }
            }
        });

        adapter = new CategoryAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.setList(dbHelper.categorySelect());

//        findViewById(R.id.btn_management).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(CardBookActivity.this, "이 버튼 없앨까 말까~!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onCardClick(Object data) {
        if (data instanceof Category) {
            long catId = ((Category) data).getId();
            if (!isSelectMode) {
                Intent intent = new Intent(getBaseContext(), CardBookListActivity.class);
                intent.putExtra("categoryId", catId);
                intent.putExtra("category", ((Category) data).getLabel());
                startActivity(intent);
            } else {
                if(!selectList.contains(catId)){
                    selectList.add(catId);
                    ((Category) data).setSelect(true);
                } else {
                    selectList.remove(catId);
                    ((Category) data).setSelect(false);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLearningCategory() {
        isSelectMode = true;
        btnLearning.setText("선택완료★");
    }

    @Override
    public void onBackPressed() {
        if(isSelectMode){
            cancelLearning(dbHelper);
        } else {
            super.onBackPressed();
        }
    }

    public void cancelLearning(DbHelper dbHelper){
        isSelectMode = false;
        btnLearning.setText("학습하기");
        adapter.setList(dbHelper.categorySelect());
        adapter.notifyDataSetChanged();
    }
}
