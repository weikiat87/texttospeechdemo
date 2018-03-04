package com.demo.wk.texttospeechdemo;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView voiceInput;
    private TextView speakButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final String API_KEY = "YOUR_API_KEY";
   /*  // todo API_KEY should not be stored in plain sight
    private static final String API_KEY = "MY_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.text_view);
        final Handler textViewHandler = new Handler();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(API_KEY)
                        .build();
                Translate translate = options.getService();
                final Translation translation =
                        translate.translate("Hello World",
                                Translate.TranslateOption.targetLanguage("de"));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView != null) {
                            textView.setText(translation.getTranslatedText());
                        }
                    }
                });
                return null;
            }
        }.execute();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceInput = (TextView) findViewById(R.id.voiceInput);
        speakButton = (TextView) findViewById(R.id.btnSpeak);

        speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                askSpeechInput();
            }
        });    }

    // Showing google speech input dialog

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Daryl 是小狗!");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Receiving speech input

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    final Handler textViewHandler = new Handler();

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            TranslateOptions options = TranslateOptions.newBuilder()
                                    .setApiKey(API_KEY)
                                    .build();
                            Translate translate = options.getService();
                            final Translation translation = translate.translate(result, Translate.TranslateOption.targetLanguage("de"));
                            textViewHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (voiceInput != null) {
                                        voiceInput.setText(translation.getTranslatedText());
                                    }
                                }
                            });
                            return null;
                        }
                    }.execute();
                }
                break;
            }

        }
    }
}