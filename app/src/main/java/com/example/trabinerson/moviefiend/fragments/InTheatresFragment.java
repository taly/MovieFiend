package com.example.trabinerson.moviefiend.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.trabinerson.moviefiend.InTheatresAdapter;
import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.loaders.InTheatresMoviesLoader;

/**
 * Fragment that contains the list of movies currently in theatres.
 */
public class InTheatresFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Movie[]> {

    public interface Callbacks {
        void onMovieClicked(Movie movie);
    }

    private static final int LOADER_ID = 1;

    private Callbacks mCallback;
    private ListView mMoviesList;
    private InTheatresAdapter mListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callbacks) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Preliminary
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.in_theatres_list, container, false);
        mListAdapter = new InTheatresAdapter(getActivity());
        mMoviesList = (ListView) rootView.findViewById(R.id.listview_in_theatres);

        // Set callback
        mMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mListAdapter.getMovieAtPosition(position);
                mCallback.onMovieClicked(movie);
            }
        });

        return rootView;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new InTheatresMoviesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader loader, Movie[] data) {
        mListAdapter.setData(data);
        mMoviesList.setAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) { }

}
