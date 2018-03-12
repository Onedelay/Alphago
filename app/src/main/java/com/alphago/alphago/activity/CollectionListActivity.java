package com.alphago.alphago.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alphago.alphago.CollectionViewHolder;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.adapter.CollectionAdapter;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.Collection;

import java.util.List;

public class CollectionListActivity extends NoStatusBarActivity implements CollectionViewHolder.OnCategoryClickListener {
    private RecyclerView recyclerView;
    private CollectionAdapter adapter;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);

        dbHelper = new DbHelper(getBaseContext());
        adapter = new CollectionAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.collection_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        long catId = getIntent().getLongExtra("categoryId",0);
        List<Collection> test = dbHelper.collectionSelect(catId);
        adapter.setList(test);
    }

    @Override
    public void onCategoryClick(Object data) {
        // Do-nothing
    }
}
