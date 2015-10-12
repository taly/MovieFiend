package com.example.trabinerson.moviefiend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapts an array of Movie instances to its corresponding ListView.
 */
public class InTheatresAdapter extends ArrayAdapter<Movie> {

    Context mContext;
    int mLayoutResourceId;
    Movie mData[] = null;

    public InTheatresAdapter(Context context, int layoutResourceId, Movie[] data) {
        super(context, layoutResourceId, data);
        this.mLayoutResourceId = layoutResourceId;
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MovieHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new MovieHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (MovieHolder)row.getTag();
        }

        Movie movie = mData[position];
        // TODO movie poster
        holder.mNameView.setText(movie.mName);
        holder.mRatingView.setText(Double.toString(movie.mRating));

        return row;
    }

    public static class MovieHolder {

        public final ImageView mPosterView;
        public final TextView mNameView;
        public final TextView mRatingView;

        public MovieHolder(View rootView) {
            mPosterView = (ImageView) rootView.findViewById(R.id.imageview_poster);
            mNameView = (TextView) rootView.findViewById(R.id.textview_movie_name);
            mRatingView = (TextView) rootView.findViewById(R.id.textview_movie_rating);
        }
    }
}
