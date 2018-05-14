package com.alphago.alphago.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alphago.alphago.R;
import com.alphago.alphago.activity.StartActivity;

public class InitSettingFragment extends DialogFragment {
    private RadioGroup radioGroup;
    private boolean sw = false;
    private String language;
    private OnSettingLanguageListener listener;

    public interface OnSettingLanguageListener {
        void onSettingLanguage(String language);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // 상태바 숨김
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View rootView = inflater.inflate(R.layout.start_setting_fragment, null);
        builder.setView(rootView);

        rootView.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sw) nextSetting(rootView);
                else {
                    int id = radioGroup.getCheckedRadioButtonId();
                    switch (id) {
                        case R.id.japanese:
                            language = "JAP";
                            break;
                        case R.id.chinese:
                            language = "CHI";
                            break;
                        default:
                            language = "ENG";
                    }
                    listener.onSettingLanguage(language);
                    dismiss();
                }
            }
        });

        radioGroup = rootView.findViewById(R.id.language_radio);
        return builder.create();
    }

    private void nextSetting(View view) {
        view.findViewById(R.id.start_guide).setVisibility(View.GONE);
        view.findViewById(R.id.language_guide).setVisibility(View.VISIBLE);
        view.findViewById(R.id.language_radio).setVisibility(View.VISIBLE);
        sw = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnSettingLanguageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRequestTrainingListener");
        }
    }
}
