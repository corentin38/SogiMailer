package com.sogilis.sogimailer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SogiMailer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Receiving Intent !!");

        Log.d(TAG, "Finish  activity with result OK");
        setResult(RESULT_OK);
        finish();
    }
}
