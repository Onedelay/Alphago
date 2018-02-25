package com.alphago.alphago.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.R;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private List<Category> list = new ArrayList<>();
    private CardViewHolder.OnCardClickListener listener;

    public CategoryAdapter(CardViewHolder.OnCardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book_list, parent, false);
        CardViewHolder vh = new CardViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
