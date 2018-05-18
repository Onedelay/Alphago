package com.alphago.alphago.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
import com.alphago.alphago.dto.ResponeImageLabel;
import com.alphago.alphago.dto.ResponseRequestResult;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by su_me on 2018-04-04.
 */

public class RequestImageTrainingFragment extends DialogFragment {
    private ImageView requestImage;
    private File requestImageFile;
    private Spinner spinner;
    private EditText requestImageName;
    private OnRequestTrainingListener listener;
    private Button sendButton;
    FrameLayout frameLayout;

    public interface OnRequestTrainingListener {
        void onRequestTraining(String category, String label, String jaLabel, String chLabel, String korLabel, int cateId, int labelId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // 상태바 숨김
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View rootView = inflater.inflate(R.layout.request_image_training_fragment, null);
        builder.setView(rootView);

        spinner = rootView.findViewById(R.id.request_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        requestImageName = rootView.findViewById(R.id.request_label_name);

        requestImageFile = (File) getActivity().getIntent().getSerializableExtra("imageFile");
        requestImage = rootView.findViewById(R.id.request_image);
        if (requestImageFile.exists()) {
            Picasso.with(getActivity().getBaseContext())
                    .load(requestImageFile)
                    .centerInside()
                    .fit()
                    .into(requestImage);
        }

        sendButton = rootView.findViewById(R.id.request_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String category = (String) spinner.getSelectedItem();
                final String requestLabel = requestImageName.getText().toString().toLowerCase();

                showLoadingView(true);

                AlphagoServer.getInstance().requestTrain(getContext(), requestImageFile, category.toLowerCase() + "_" + requestLabel, new Callback<ResponseRequestResult>() {
                    @Override
                    public void onResponse(Call<ResponseRequestResult> call, Response<ResponseRequestResult> response) {
                        if(response != null){
                            Toast.makeText(getContext(), "전송 완료", Toast.LENGTH_SHORT).show();
                            listener.onRequestTraining(response.body().getCategory(), response.body().getEn(), response.body().getJa(), response.body().getZh_CN(), response.body().getKo(),
                                    response.body().getCAT_ID(), response.body().getLABEL_ID());
                        } else {
                            Toast.makeText(getContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseRequestResult> call, Throwable t) {
                        Toast.makeText(getContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });

            }
        });

        frameLayout = rootView.findViewById(R.id.frame_loading);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAttributes(params);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnRequestTrainingListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement OnRequestTrainingListener");
        }
    }

    private void showLoadingView(boolean show) {
        frameLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        frameLayout.setClickable(true);
        sendButton.setEnabled(false);
        sendButton.setTextColor(Color.LTGRAY);
        spinner.setEnabled(false);
    }
}
