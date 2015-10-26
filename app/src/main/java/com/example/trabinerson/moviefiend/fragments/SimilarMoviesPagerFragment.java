package com.example.trabinerson.moviefiend.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.R;

/**
 * A fragment that shows a slider between similar movies.
 */
public class SimilarMoviesPagerFragment extends Fragment {

    public static final String ARG_KEY_SIMILAR_MOVIES = "SimilarMovies";
    public static final int NUM_MOVIES = 5;
    public static final String FRAGMENT_FLAG = "SimilarMovies";

    private static final String LOG_TAG = SimilarMoviesPagerFragment.class.getSimpleName();
    private static final float ENLARGED_DOT_SCALE = 1.5f;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Movie[] mSimilarMovies;
    private ImageView[] mDotViews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.similar_movie_slide, container, false);

        // Get similar movies
        Parcelable[] parcel = getArguments().getParcelableArray(ARG_KEY_SIMILAR_MOVIES);
        mSimilarMovies = new Movie[parcel.length];
        for (int i = 0; i < parcel.length; i++) {
            mSimilarMovies[i] = (Movie)parcel[i];
        }
        Log.i(LOG_TAG, "Unbundled " + mSimilarMovies.length + " movies");

        // Keep dots in memory
        initProgressDots(rootView);

        // Instantiate a ViewPager and a PagerAdapter
        initPagerAndAdapter(rootView);

        return rootView;
    }

    private void initProgressDots(View rootView) {
        int[] dotIds = {
                R.id.imageview_dot1,
                R.id.imageview_dot2,
                R.id.imageview_dot3,
                R.id.imageview_dot4,
                R.id.imageview_dot5
        };
        mDotViews = new ImageView[mSimilarMovies.length];
        for (int i = 0; i < mSimilarMovies.length; i++) {
            mDotViews[i] = (ImageView) rootView.findViewById(dotIds[i]);
        }
        mDotViews[0].setScaleX(ENLARGED_DOT_SCALE);
        mDotViews[0].setScaleY(ENLARGED_DOT_SCALE);
    }

    private void initPagerAndAdapter(View rootView) {

        // Pager
        mPager = (ViewPager) rootView.findViewById(R.id.pager_similar_movies);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position, float positionOffset, int positionOffsetPixels) {

                float fullPosition = position + positionOffset;
                float rangeSize = ENLARGED_DOT_SCALE - 1;

                for (int i = 0; i < mDotViews.length; i++) {
                    ImageView dot = mDotViews[i];
                    float dist = fullPosition - i;
                    float absDist = Math.abs(dist);
                    if (absDist <= 1) {
                        float scale =  ENLARGED_DOT_SCALE - rangeSize * absDist;
                        dot.setScaleX(scale);
                        dot.setScaleY(scale);
                    }
                    else {
                        dot.setScaleX(1);
                        dot.setScaleY(1);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        // Adapter
        mPagerAdapter = new SimilarMoviesPagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }


    /**
     * A simple pager adapter that represents 5 MovieDetailsFragment objects, in sequence.
     */
    private class SimilarMoviesPagerAdapter extends FragmentStatePagerAdapter {

        public SimilarMoviesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MovieDetailsFragment.ARG_KEY_MOVIE, mSimilarMovies[position]);
            bundle.putBoolean(MovieDetailsFragment.ARG_KEY_SHOW_SIMILAR, false);
            bundle.putBoolean(MovieDetailsFragment.ARG_KEY_ANIMATE_RATING, false);
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mSimilarMovies.length;
        }
    }
}
