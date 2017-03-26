package com.example.deepakgarg.capstoneproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.deepakgarg.capstoneproject.data.FavouritesTable;

public class FavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        if (savedInstanceState == null) {
            FavFragment favFragment = new FavFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_fav, favFragment)
                    .commit();
        }
        /*Cursor cursor = getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<FavDBHelper> testRows = FavouritesTable.getRows(cursor, true);
        for (FavDBHelper element : testRows)
            Toast.makeText(this, element.url, Toast.LENGTH_SHORT).show();*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            getContentResolver().delete(FavouritesTable.CONTENT_URI, null, null);
        }
        return super.onOptionsItemSelected(item);
    }
}