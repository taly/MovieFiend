package com.example.trabinerson.moviefiend;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String ACTIVITY_TAG = "MovieFiend";

    public static final String API_KEY = "3508e43ae4ba2bd4c216990c671291b5";
    public static final String API_KEY_QUERY_PARAM = "api_key";
    public static final String MOVIE_API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String IMAGES_API_BASE_URL = "http://image.tmdb.org/t/p/w500/";
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
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "Cancelling all requests");
        RequestQueueSingleton.getInstance(this).cancelAllRequests(ACTIVITY_TAG);
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
        String nowPlayingUrl = Uri.parse(MOVIE_API_BASE_URL).buildUpon()
                .appendPath(NOW_PLAYING_ENDPOINT)
                .appendQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .build().toString();

        // Build request
        String requestBody = null;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, nowPlayingUrl, requestBody,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "Got movie data from server");
                        Movie[] data = getMoviesFromResponse(response);
                        populateMoviesListView(data);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Error while parsing movies from server", error);
                    }
                });

        // Send request
        request.setTag(ACTIVITY_TAG);
        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    private Movie[] getMoviesFromResponse(JSONObject response) {
        try {
            JSONArray responseArray = response.getJSONArray(JSON_KEY_RESULTS);
            Log.i(LOG_TAG, "Found " + responseArray.length() + " movies");
            ArrayList<Movie> data = new ArrayList<>();
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject currentRaw = responseArray.getJSONObject(i);
                String name = currentRaw.getString(JSON_KEY_MOVIE_NAME);
                Double rating = currentRaw.getDouble(JSON_KEY_MOVIE_RATING);
                String posterPath = currentRaw.getString(JSON_KEY_POSTER_PATH);
                String posterUrl = Uri.parse(IMAGES_API_BASE_URL).buildUpon()
                        .appendEncodedPath(posterPath)
                        .build().toString();
                Log.i(LOG_TAG, "Poster URL for the movie '" + name + ": " + posterUrl);
                Movie currentMovie = new Movie(name, rating, posterUrl);
                data.add(currentMovie);
            }
            Movie[] dataArray = data.toArray(new Movie[data.size()]);
            return dataArray;
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Exception while processing movies from server", e);
                return null;
        }
    }

    private void populateMoviesListView(Movie[] data) {
        InTheatresAdapter adapter = new InTheatresAdapter(this, R.layout.list_item_in_theatres, data);
        mMoviesList.setAdapter(adapter);
    }
}
