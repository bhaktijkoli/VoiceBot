package com.bhaktijkoli.voicebot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwell.speechrecognition.OnSpeechRecognitionListener;
import com.maxwell.speechrecognition.OnSpeechRecognitionPermissionListener;
import com.maxwell.speechrecognition.SpeechRecognition;

/**
 * Created by Bhaktij on 02/09/18.
 * Github: https://github.com/bhaktijkoli
 * Email: bhaktijkoli121@gmail.com
 */

public class MainActivity extends AppCompatActivity implements OnSpeechRecognitionPermissionListener, OnSpeechRecognitionListener {

    private String TAG = "MainActivity";
    private SpeechRecognition speechRecognition;
    private TextView tvStatus;
    private ImageView btnVoice;
    private Boolean isListening = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnVoice = (ImageView) findViewById(R.id.btnVoice);
        tvStatus.setText("");

        speechRecognition = new SpeechRecognition(this);
        speechRecognition.setSpeechRecognitionPermissionListener(this);
        speechRecognition.setSpeechRecognitionListener(this);

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isListening == false) {
                    speechRecognition.startSpeechRecognition();
                } else {
                    speechRecognition.stopSpeechRecognition();
                }
            }
        });
    }
    // Voice Recording Permission Granted
    @Override
    public void onPermissionGranted() {
        Log.i(TAG, "onPermissionGranted: ");
    }
    // Voice Recording Permission Denied
    @Override
    public void onPermissionDenied() {
        Log.i(TAG, "onPermissionDenied: ");
    }
    // Speech Recognition Started
    @Override
    public void OnSpeechRecognitionStarted() {
        Log.i(TAG, "OnSpeechRecognitionStarted: ");
        this.startSpeech();
    }
    // Speech Recognition Stopped
    @Override
    public void OnSpeechRecognitionStopped() {
        Log.i(TAG, "OnSpeechRecognitionStopped: ");
        this.stopSpeech();
    }
    // Speech Final Result
    @Override
    public void OnSpeechRecognitionFinalResult(String s) {
        Log.i(TAG, "OnSpeechRecognitionFinalResult: " + s);
        this.stopSpeech();
    }
    // Speech Current Result
    @Override
    public void OnSpeechRecognitionCurrentResult(String s) {
        Log.i(TAG, "OnSpeechRecognitionCurrentResult: "+s);
        tvStatus.setText("Recognrecognising your voice...");

    }
    // Speech Recognition Error
    @Override
    public void OnSpeechRecognitionError(int i, String s) {
        Log.i(TAG, "OnSpeechRecognitionError: " + i + " ," + s);
        tvStatus.setText("Didn't get you!");
        this.stopSpeech();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        speechRecognition.stopSpeechRecognition();
    }

    private void startSpeech() {
        isListening = true;
        btnVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_on));
        tvStatus.setText("Listening...");
    }
    private void stopSpeech() {
        isListening = false;
        btnVoice.setImageDrawable(getResources().getDrawable(R.drawable.voice_off));
        tvStatus.setText("");
    }
}
