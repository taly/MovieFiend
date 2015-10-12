package com.example.trabinerson.moviefiend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Movie[] data = getMoviesInTheatre();
        InTheatresAdapter adapter = new InTheatresAdapter(this, R.layout.list_item_in_theatres, data);
        ListView list = (ListView) findViewById(R.id.listview_in_theatres);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static Movie[] getMoviesInTheatre() {
        Movie[] data = {
                new Movie("The Martian", 8.2, "DUMMY"),
                new Movie("The Maze Runner", 6.9, "WHATEVER"),
                new Movie("Another Movie", 9.9, "ANOTHER")
        };
        return data;
    }
}
