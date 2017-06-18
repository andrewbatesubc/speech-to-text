package com.speech2text.plugins;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

public class Speech2TextListener implements RecognitionListener {
    private static final String TAG = "EPPZ_Alert_Fragment";
    private StringBuilder builder = new StringBuilder();
    public void onReadyForSpeech(Bundle params)
    {
        // Log.d(TAG, "onReadyForSpeech");
    }
    public void onBeginningOfSpeech()
    {
        //Log.d(TAG, "onBeginningOfSpeech");
    }
    public void onRmsChanged(float rmsdB)
    {
        //Log.d(TAG, "onRmsChanged");
    }
    public void onBufferReceived(byte[] buffer)
    {
        //Log.d(TAG, "onBufferReceived");
    }
    public void onEndOfSpeech()
    {
        //Log.d(TAG, "onEndofSpeech");
    }
    public void onError(int error)
    {
        Log.d(TAG,  "error " +  error);
        //mText.setText("error " + error);
    }
    public void onResults(Bundle results)
    {
        Log.d(TAG, "We got results!!");
        builder.setLength(0);
        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (int i = 0; i < data.size(); i++)
        {
            builder.append(" ");
            builder.append(data.get(i));
        }
        Log.d(TAG, builder.toString());
        Speech2Text.instance.collectAndSendTextResult(builder.toString());
    }
    public void onPartialResults(Bundle partialResults)
    {
        // Log.d(TAG, "onPartialResults");
    }
    public void onEvent(int eventType, Bundle params)
    {
        // Log.d(TAG, "onEvent " + eventType);
    }
}
