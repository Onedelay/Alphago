package com.alphago.alphago;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.model.CardBook;

public class CardViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTextView;
    private CardBook cardBook;

    public interface OnCardClickListener {
        void onCardClick(CardBook cardBook);
    }

    public CardViewHolder(View itemView, final OnCardClickListener listener){
        super(itemView);
        mImageView = (ImageView)itemView.findViewById(R.id.cardbook_list_img);
        mTextView = (TextView) itemView.findViewById(R.id.cardbook_list_label);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCardClick(cardBook);
            }
        });
    }

    public void bind(CardBook cardBook) {
        this.cardBook = cardBook;
        mImageView.setImageResource(cardBook.getImageResourceId());
        mTextView.setText(cardBook.getName());
    }
}
