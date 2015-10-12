package com.example.trabinerson.moviefiend;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String API_KEY = "3508e43ae4ba2bd4c216990c671291b5";
    public static final String API_KEY_QUERY_PARAM = "api_key";
    public static final String MOVIE_API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String NOW_PLAYING_ENDPOINT = "now_playing";

    public static final String JSON_KEY_RESULTS = "results";
    public static final String JSON_KEY_MOVIE_NAME = "original_title";
    public static final String JSON_KEY_MOVIE_RATING = "vote_average";
    public static final String JSON_KEY_POSTER_PATH = "poster_path";

    private ListView mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesList = (ListView) findViewById(R.id.listview_in_theatres);
        fetchAndUpdateMovies();
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

    private void fetchAndUpdateMovies() {

        // Build URL
        Uri nowPlayingUrl = Uri.parse(MOVIE_API_BASE_URL).buildUpon()
                .appendPath(NOW_PLAYING_ENDPOINT)
                .appendQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .build();
        String url = nowPlayingUrl.toString();

        // Build request
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i(LOG_TAG, "Got movie data from server");
                        try {
                            JSONArray results = response.getJSONArray(JSON_KEY_RESULTS);
                            Log.i(LOG_TAG, "Found " + results.length() + " movies");
                            ArrayList<Movie> data = new ArrayList<>();

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject currentRaw = results.getJSONObject(i);
                                String name = currentRaw.getString(JSON_KEY_MOVIE_NAME);
                                Double rating = currentRaw.getDouble(JSON_KEY_MOVIE_RATING);
                                String posterPath = currentRaw.getString(JSON_KEY_POSTER_PATH); // TODO use
                                Movie currentMovie = new Movie(name, rating, posterPath);
                                data.add(currentMovie);
                            }
                            Movie[] dataArray = data.toArray(new Movie[data.size()]);
                            populateMoviesListView(dataArray);
                        }
                        catch (JSONException e) {
                            Log.e(LOG_TAG, "Exception while processing movies from server", e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Error while parsing movies from server", error);
                    }
                });

        // Send request
        // TODO move queue to singleton
        queue.add(request);
    }

    private void populateMoviesListView(Movie[] data) {
        InTheatresAdapter adapter = new InTheatresAdapter(this, R.layout.list_item_in_theatres, data);
        mMoviesList.setAdapter(adapter);
    }
}
