package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alphago.alphago.CollectionViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CollectionCatAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.CollectCategory;

import static com.alphago.alphago.Constants.LANGUAGE_KOR;
import static com.alphago.alphago.Constants.getLanguage;

public class CollectionActivity extends NoStatusBarActivity implements CollectionViewHolder.OnCategoryClickListener {
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        String lang = StartActivity.sharedPreferences.getString("Language","ENG");

        dbHelper = new DbHelper(getBaseContext());
        CollectionCatAdapter adapter = new CollectionCatAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.collection_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        adapter.setList(dbHelper.categoryAllSelect(LANGUAGE_KOR));
    }

    @Override
    public void onCategoryClick(Object data) {
        if(data instanceof CollectCategory){
            long catId = ((CollectCategory) data).getId();
            Intent intent = new Intent(getBaseContext(), CollectionListActivity.class);
            intent.putExtra("categoryId", catId);
            startActivity(intent);
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
