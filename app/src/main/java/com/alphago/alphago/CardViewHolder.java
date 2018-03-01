package com.alphago.alphago;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.model.Card;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;
import com.alphago.alphago.util.DefaultImageUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

public class CardViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTextView;
    private Object data;

    public interface OnCardClickListener {
        void onCardClick(Object data);
    }

    public CardViewHolder(View itemView, final OnCardClickListener listener) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.cardbook_list_img);
        mTextView = (TextView) itemView.findViewById(R.id.cardbook_list_label);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCardClick(data);
            }
        });
    }

    public void bind(Category category) {
        this.data = category;
        setView(category.getFilePath(), category.getLabel());
    }

    public void bind(CardBook cardBook) {
        this.data = cardBook;
        setView(cardBook.getFilePath(), cardBook.getName());
    }

    public void bind(Card card) {
        this.data = card;
        setView(card.getFilePath(),"");
    }

    private void setView(String path, String name) {
        if (path != null) {
            Picasso.with(itemView.getContext())
                    .load(new File(path))
                    .centerInside()
                    .fit()
                    .into(mImageView);
        } else {
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }
        mTextView.setText(name);
    }
}
