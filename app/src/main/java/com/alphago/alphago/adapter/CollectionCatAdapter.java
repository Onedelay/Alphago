package com.alphago.alphago.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphago.alphago.CollectionViewHolder;
import com.alphago.alphago.R;
import com.alphago.alphago.model.CollectCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su_me on 2018-03-11.
 */

public class CollectionCatAdapter extends RecyclerView.Adapter<CollectionViewHolder> {
    private List<CollectCategory> list = new ArrayList<>();
    private CollectionViewHolder.OnCategoryClickListener listener;

    public CollectionCatAdapter(CollectionViewHolder.OnCategoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list, parent, false);
        CollectionViewHolder vh = new CollectionViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CollectCategory> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
