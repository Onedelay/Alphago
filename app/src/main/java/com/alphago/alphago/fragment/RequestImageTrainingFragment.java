package com.alphago.alphago.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
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

    public interface OnRequestTrainingListener {
        void onRequestTraining(String category, String label);
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

        rootView.findViewById(R.id.request_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String category = (String) spinner.getSelectedItem();
                final String requestLabel = requestImageName.getText().toString();

                AlphagoServer.getInstance().requestTrain(getContext(), requestImageFile, category.toLowerCase() + "_" + requestLabel, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response != null){
                            Toast.makeText(getContext(), "전송 완료", Toast.LENGTH_SHORT).show();
                            listener.onRequestTraining(category, requestLabel);
                        } else {
                            Toast.makeText(getContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });

            }
        });

        return builder.create();
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
}
