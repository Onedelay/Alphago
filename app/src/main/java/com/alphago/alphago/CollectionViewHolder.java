package com.alphago.alphago;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.model.Category;
import com.alphago.alphago.model.Collection;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by su_me on 2018-03-11.
 */

public class CollectionViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTextView;
    private Object data;

    public interface OnCategoryClickListener {
        void onCategoryClick(Object data);
    }

    public CollectionViewHolder(View itemView, final OnCategoryClickListener listener) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.collection_list_img);
        mTextView = (TextView) itemView.findViewById(R.id.collection_list_label);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(data);
            }
        });
    }

    public void bind(Category category) {
        this.data = category;
        int cat = (int) category.getId();
        switch (cat) {
            case 1:
                Picasso.with(itemView.getContext()).load(R.drawable.a_img_collect_anm).into(mImageView);
                break;
            case 2:
                Picasso.with(itemView.getContext()).load(R.drawable.b_img_collect_fur).into(mImageView);
                break;
            case 3:
                Picasso.with(itemView.getContext()).load(R.drawable.c_img_collect_food).into(mImageView);
                break;
            case 4:
                Picasso.with(itemView.getContext()).load(R.drawable.d_img_collect_sch).into(mImageView);
                break;
            case 5:
                Picasso.with(itemView.getContext()).load(R.drawable.e_img_collect_kch).into(mImageView);
                break;
            case 6:
                Picasso.with(itemView.getContext()).load(R.drawable.f_img_collect_bth).into(mImageView);
                break;
            case 7:
                Picasso.with(itemView.getContext()).load(R.drawable.g_img_collect_elec).into(mImageView);
                break;
            case 8:
                Picasso.with(itemView.getContext()).load(R.drawable.h_img_collect_room).into(mImageView);
                break;
            default:
                Picasso.with(itemView.getContext()).load(R.mipmap.ic_launcher).into(mImageView);
        }
        mTextView.setText(category.getLabel());
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
