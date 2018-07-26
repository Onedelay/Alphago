package com.alphago.alphago.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alphago.alphago.CollectionViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CollectionAdapter;
import com.alphago.alphago.database.DbHelper;

public class CollectionListActivity extends NoStatusBarActivity implements CollectionViewHolder.OnCategoryClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        String lang = StartActivity.sharedPreferences.getString("Language","ENG");
        DbHelper dbHelper = new DbHelper(getBaseContext());
        CollectionAdapter adapter = new CollectionAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.collection_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        long catId = getIntent().getLongExtra("categoryId",0);
        adapter.setList(dbHelper.collectionSelect(catId, lang));
    }

    @Override
    public void onCategoryClick(Object data) {
        // Do-nothing
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
