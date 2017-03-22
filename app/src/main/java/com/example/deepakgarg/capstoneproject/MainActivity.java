package com.example.deepakgarg.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NewsFragment moviesFragment = new NewsFragment();
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, moviesFragment)
                .commit();
        }

    }
}
