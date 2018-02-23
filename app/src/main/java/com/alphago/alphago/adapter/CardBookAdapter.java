package com.alphago.alphago.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alphago.alphago.CardViewHolder;
import com.alphago.alphago.R;
import com.alphago.alphago.model.CardBook;

import java.util.ArrayList;
import java.util.List;

public class CardBookAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private List<CardBook> list = new ArrayList<>();
    private CardViewHolder.OnCardClickListener listener;
    private boolean type; // true인 경우 category

    public CardBookAdapter(CardViewHolder.OnCardClickListener listener, boolean type) {
        this.listener = listener;
        this.type = type;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book_list, parent, false);
        CardViewHolder vh = new CardViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if(type) holder.bindCat(list.get(position)); // true인 경우 category
        else holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CardBook> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
