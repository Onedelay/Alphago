package com.alphago.alphago.adapter;

import android.support.annotation.NonNull;
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

    public CardBookAdapter(CardViewHolder.OnCardClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book_list, parent, false);
        return new CardViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(list.get(position));
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
