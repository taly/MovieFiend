package com.example.trabinerson.moviefiend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Fragment that shows list of movies now in theatres. Each movie can be drilled into for details.
 */
public class InTheatresFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.in_theatres_list, container, false);
        Movie[] data = {
                new Movie("The Martian", 8.2, "DUMMY"),
                new Movie("The Maze Runner", 6.9, "WHATEVER")
        };
        InTheatresAdapter adapter = new InTheatresAdapter(getActivity(), R.layout.list_item_in_theatres, data);
        ListView list = (ListView) rootView.findViewById(R.id.listview_in_theatres);
        list.setAdapter(adapter);
        return rootView;
    }

}
