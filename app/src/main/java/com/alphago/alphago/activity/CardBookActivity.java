package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CategoryAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.LearningSelectionMethodDialog;
import com.alphago.alphago.model.Category;

import java.util.ArrayList;

import static com.alphago.alphago.fragment.LearningSelectionMethodDialog.TYPE_ALL;

public class CardBookActivity extends NoStatusBarActivity implements CardViewHolder.OnCardClickListener, LearningSelectionMethodDialog.OnLearningCategoryListener {
    private ImageView btnLearning;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private boolean isSelectMode = false;
    private ArrayList<Long> selectList = new ArrayList<Long>();
    DbHelper dbHelper;

    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        final Intent intent = new Intent(this, WordLearningActivity.class);
        dbHelper = new DbHelper(getBaseContext());

        lang = StartActivity.sharedPreferences.getString("Language","ENG");

        btnLearning = findViewById(R.id.btn_learning);
        /* 전체학습/카테고리학습 기능 */
        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSelectMode){
                    new LearningSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
                } else {
                    intent.putExtra("category_select_list", selectList);
                    intent.putExtra("learning_type", LearningSelectionMethodDialog.TYPE_ALBUM);
                    if(selectList.size() == 0) {
                        Toast.makeText(CardBookActivity.this, "학습할 목록이 없습니다.", Toast.LENGTH_SHORT).show();
                        cancelLearning(dbHelper);
                    } else {
                        startActivity(intent);
                        cancelLearning(dbHelper);
                    }
                }
            }
        });

//        btnLearning.setText("전체학습");
//        btnLearning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent.putExtra("learning_type", TYPE_ALL);
//                startActivity(intent);
//            }
//        });

        adapter = new CategoryAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.cardbook_grid);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setList(dbHelper.categorySelect(lang));

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
            /* 카테고리 선택 시
             * if selectMode : 학습할 카테고리 추가
             * else : 해당 카테고리 이미지들 보기 */
            long catId = ((Category) data).getId();
            if (!isSelectMode) {
                Intent intent = new Intent(getBaseContext(), CardBookListActivity.class);
                intent.putExtra("categoryId", catId);
                intent.putExtra("category", ((Category) data).getLabel());
                startActivity(intent);
            } else {
                if (!selectList.contains(catId)) {
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
        btnLearning.setBackgroundResource(R.drawable.icon_book);
    }

    @Override
    public void onBackPressed() {
        if (isSelectMode) {
            cancelLearning(dbHelper);
            Toast.makeText(this, "학습이 취소되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    public void cancelLearning(DbHelper dbHelper) {
        isSelectMode = false;
        btnLearning.setBackgroundResource(R.drawable.icon_learning);
        adapter.setList(dbHelper.categorySelect(lang));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isSelectMode) btnLearning.setBackgroundResource(R.drawable.icon_learning);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
