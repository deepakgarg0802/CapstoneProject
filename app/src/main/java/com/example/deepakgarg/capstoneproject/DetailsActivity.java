package com.example.deepakgarg.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = getIntent().getBundleExtra("BUNDLE");

        if (savedInstanceState == null) {
            DetailsFragment myNewsFragment = new DetailsFragment();
            myNewsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_details, myNewsFragment)
                    .commit();
        }
    }
}
