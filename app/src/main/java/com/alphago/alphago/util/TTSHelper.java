package com.alphago.alphago.util;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import com.alphago.alphago.activity.ImageRecognitionActivity;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by su_me on 2018-03-01.
 */

public class TTSHelper {
    private TextToSpeech tts;
    private Context context;

    public TTSHelper(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });
    }

    public void speak(String label){
        String id = String.valueOf(context.hashCode());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(label, TextToSpeech.QUEUE_FLUSH, null, id);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, id);
            tts.speak(label, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    public void shutdown(){
        tts.shutdown();
    }
}
