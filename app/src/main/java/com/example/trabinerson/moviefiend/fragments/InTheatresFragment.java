package com.example.trabinerson.moviefiend.fragments;

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
public class InTheatresFragment extends Fragment implements LoaderManager.LoaderCallbacks<Movie[]>{

    private static final int LOADER_ID = 1;

    private ListView mMoviesList;
    private InTheatresAdapter mListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get root view
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.in_theatres_list, container, false);

        // Prepare list
        mMoviesList = (ListView) rootView.findViewById(R.id.listview_in_theatres);
        mMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO this should all be defined in MainActivity
                Movie movie = mListAdapter.getMovieAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieDetailsFragment.ARG_KEY_MOVIE, movie);
                bundle.putBoolean(MovieDetailsFragment.ARG_KEY_ANIMATE_RATING, true);

                MovieDetailsFragment fragment = new MovieDetailsFragment();
                fragment.setArguments(bundle);

//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().add(fragment, "").commit();
//                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
//                intent.putExtras(bundle);


//                startActivity(intent);
            }
        });

        // Init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new InTheatresMoviesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader loader, Movie[] data) {
        mListAdapter = new InTheatresAdapter(getActivity(), data);
        mMoviesList.setAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO (?)
    }
}
