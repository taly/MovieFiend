package com.example.trabinerson.moviefiend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that shows list of movies now in theatres. Each movie can be drilled into for details.
 */
//public class InTheatresFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
public class InTheatresFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.in_theatres_list, container, false);

        return rootView;
    }

}
