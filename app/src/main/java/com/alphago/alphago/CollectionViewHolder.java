package com.alphago.alphago;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.model.CollectCategory;
import com.alphago.alphago.model.Collection;
import com.alphago.alphago.util.DefaultImageUtil;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by su_me on 2018-03-11.
 */

public class CollectionViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTextView;
    private CircularProgressBar circularProgressBar;
    private Object data;

    public interface OnCategoryClickListener {
        void onCategoryClick(Object data);
    }

    public CollectionViewHolder(View itemView, final OnCategoryClickListener listener) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.collection_list_img);
        mTextView = itemView.findViewById(R.id.collection_list_label);
        circularProgressBar = itemView.findViewById(R.id.progress_bar);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(data);
            }
        });
    }

    public void bind(CollectCategory category) {
        this.data = category;
        Picasso.with(itemView.getContext())
                .load(DefaultImageUtil.getCollectionImage(category.getId()))
                .into(mImageView);
        mTextView.setText(category.getLabel());
        circularProgressBar.setProgress(category.getAchievementRate());
    }

    public void bind(Collection collection){
        this.data = collection;

        itemView.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        if(collection.getCollected() == 1 && collection.getFilePath() != null){
            Picasso.with(itemView.getContext())
                    .load(new File(collection.getFilePath()))
                    .centerInside()
                    .fit()
                    .into(mImageView);
            mTextView.setTextColor(Color.BLACK);
        } else {
            Picasso.with(itemView.getContext())
                    .load(R.drawable.img_empty)
                    .centerInside()
                    .fit()
                    .into(mImageView);
            mTextView.setTextColor(Color.GRAY);
        }
        mTextView.setText(collection.getName());
    }
}
