package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by trabinerson on 10/12/15.
 */
public class InTheatresAdapter extends CursorAdapter {

    public InTheatresAdapter(Context context, Cursor c, int flags) { super(context, c, flags); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_in_theatres, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView movieNameView = (TextView) view.findViewById(R.id.textview_movie_name);
        movieNameView.setText("Movie name");
    }
}
