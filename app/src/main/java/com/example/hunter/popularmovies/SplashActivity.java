package com.example.hunter.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by HuNter on 5/1/2018.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
