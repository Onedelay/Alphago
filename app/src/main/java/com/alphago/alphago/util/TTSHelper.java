package com.alphago.alphago.util;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import com.alphago.alphago.Constants;
import com.alphago.alphago.activity.ImageRecognitionActivity;

import java.util.HashMap;
import java.util.Locale;

import static com.alphago.alphago.Constants.LANGUAGE_CHI;
import static com.alphago.alphago.Constants.LANGUAGE_ENG;
import static com.alphago.alphago.Constants.LANGUAGE_JAP;

/**
 * # 사용하려는 액티비티에 꼭 onDestroy 메소드 오버라이드 필요 (shutdown() 넣어주기).
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

    public TTSHelper(Context context, final String language){
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                int lang = Constants.getLanguage(language);
                if(lang == LANGUAGE_JAP) tts.setLanguage(Locale.JAPANESE);
                else if(lang == LANGUAGE_CHI) tts.setLanguage(Locale.CHINA);
                else tts.setLanguage(Locale.ENGLISH);
            }
        });
    }

    public void speak(String label){
        String id = String.valueOf(context.hashCode());
        String volume = String.valueOf(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(label, TextToSpeech.QUEUE_FLUSH, null, id);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, id);
            map.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, volume);
            tts.speak(label, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    public void shutdown(){
        tts.shutdown();
    }
}
