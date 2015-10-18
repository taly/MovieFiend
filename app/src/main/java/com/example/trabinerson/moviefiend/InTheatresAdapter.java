package com.example.trabinerson.moviefiend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Adapts an array of Movie instances to its corresponding ListView.
 */
public class InTheatresAdapter extends ArrayAdapter<Movie> {

    Context mContext;
    int mLayoutResourceId;
    Movie mData[] = null;

    public InTheatresAdapter(Context context,  Movie[] data) {
        super(context, R.layout.list_item_in_theatres, data);
        this.mLayoutResourceId = R.layout.list_item_in_theatres;
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MovieHolder holder = null;

        // Initialize row if necessary
        if (row == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new MovieHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (MovieHolder)row.getTag();
        }

        // Update row views
        Movie movie = mData[position];
        ImageLoader imageLoader = RequestQueueSingleton.getInstance(this.mContext).getImageLoader();
        holder.mNameView.setText(movie.mName);
        String rating = this.mContext.getString(R.string.rating, movie.mRating);
        holder.mRatingView.setText(rating);
        holder.mPosterView.setImageUrl(movie.mPosterUrl, imageLoader);

        return row;
    }

    public static class MovieHolder {

        public final NetworkImageView mPosterView;
        public final TextView mNameView;
        public final TextView mRatingView;

        public MovieHolder(View rootView) {
            mPosterView = (NetworkImageView) rootView.findViewById(R.id.imageview_poster);
            mNameView = (TextView) rootView.findViewById(R.id.textview_movie_name);
            mRatingView = (TextView) rootView.findViewById(R.id.textview_movie_rating);
        }
    }
}
