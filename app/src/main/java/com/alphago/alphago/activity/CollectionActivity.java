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

public class CollectionActivity extends NoStatusBarActivity implements CollectionViewHolder.OnCategoryClickListener {
    private RecyclerView recyclerView;
    private CollectionCatAdapter adapter;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        dbHelper = new DbHelper(getBaseContext());
        adapter = new CollectionCatAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.collection_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.setList(dbHelper.categoryAllSelect());
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
}
