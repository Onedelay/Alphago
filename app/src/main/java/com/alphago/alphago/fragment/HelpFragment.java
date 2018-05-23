package com.alphago.alphago.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alphago.alphago.R;
import com.squareup.picasso.Picasso;

public class HelpFragment extends Fragment {
    private ImageView imageView;
    private Button preButton;
    private Button nextButton;
    private  Button closeButton;

    private OnCloseListener listener;

    private final int[] HELP_IMAGES = {R.drawable.help1, R.drawable.help2, R.drawable.help3, R.drawable.help4, R.drawable.help5};
    private int count = 0;

    public interface OnCloseListener {
        void onClose();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.help_layout, container, false);
        imageView = rootView.findViewById(R.id.help_image);
        preButton = rootView.findViewById(R.id.btn_help_pre);
        nextButton = rootView.findViewById(R.id.btn_help_next);
        closeButton = rootView.findViewById(R.id.btn_help_close);

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) count--;
                Picasso.with(getActivity().getBaseContext())
                        .load(HELP_IMAGES[count])
                        .centerInside()
                        .fit()
                        .into(imageView);
                setPreButton();
                setNextButton();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count < HELP_IMAGES.length - 1) {
                    count++;
                } else if (count == HELP_IMAGES.length - 1) listener.onClose();

                Picasso.with(getActivity().getBaseContext())
                        .load(HELP_IMAGES[count])
                        .centerInside()
                        .fit()
                        .into(imageView);

                setPreButton();
                setNextButton();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClose();
            }
        });

        return rootView;
    }

    private void setPreButton() {
        if (count < 1) {
            preButton.setVisibility(View.GONE);
        } else {
            preButton.setVisibility(View.VISIBLE);
        }
    }

    private void setNextButton() {
        if (count == HELP_IMAGES.length) {
            nextButton.setVisibility(View.GONE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnCloseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRequestTrainingListener");
        }
    }
}
