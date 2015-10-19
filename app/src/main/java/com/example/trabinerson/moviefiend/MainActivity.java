package com.example.trabinerson.moviefiend;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie[]> {



    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;

    private ListView mMoviesList;
    private InTheatresAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare list
        mMoviesList = (ListView) findViewById(R.id.listview_in_theatres);
        mMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mListAdapter.getMovieAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable(DetailsActivity.INTENT_KEY_MOVIE, movie);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        // Init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MoviesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Movie[] data) {
        mListAdapter = new InTheatresAdapter(this, data);
        mMoviesList.setAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO (?)
    }
}
