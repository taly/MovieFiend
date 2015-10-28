package com.example.trabinerson.moviefiend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.example.trabinerson.moviefiend.Movie;
import com.example.trabinerson.moviefiend.MovieDetailsFragment;
import com.example.trabinerson.moviefiend.R;

/**
 * An activity that shows a slider between similar movies.
 */
public class SimilarMoviesPagerActivity extends FragmentActivity {

    public static final String INTENT_KEY_SIMILAR_MOVIES = "SimilarMovies";
    public static final int NUM_MOVIES = 5;

    private static final String LOG_TAG = SimilarMoviesPagerActivity.class.getSimpleName();
    private static final float ENLARGED_DOT_SCALE = 1.5f;
    private static final int DOT_ANIMATION_DURATION = 200;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Movie[] mSimilarMovies;
    private ImageView[] mDotViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.similar_movie_slide);

        // Get movies
        Intent intent = getIntent();
        Parcelable[] parcel = intent.getExtras().getParcelableArray(INTENT_KEY_SIMILAR_MOVIES);
        mSimilarMovies = new Movie[parcel.length];
        for (int i = 0; i < parcel.length; i++) {
            mSimilarMovies[i] = (Movie)parcel[i];
        }
        Log.i(LOG_TAG, "Unbundled " + mSimilarMovies.length + " movies");

        // Keep dots in memory
        initProgressDots();

        // Instantiate a ViewPager and a PagerAdapter
        initPagerAndAdapter();
    }

    private void initProgressDots() {
        int[] dotIds = {
                R.id.imageview_dot1,
                R.id.imageview_dot2,
                R.id.imageview_dot3,
                R.id.imageview_dot4,
                R.id.imageview_dot5
        };
        mDotViews = new ImageView[mSimilarMovies.length];
        for (int i = 0; i < mSimilarMovies.length; i++) {
            mDotViews[i] = (ImageView) findViewById(dotIds[i]);
        }
        mDotViews[0].setScaleX(ENLARGED_DOT_SCALE);
        mDotViews[0].setScaleY(ENLARGED_DOT_SCALE);
    }

    private void initPagerAndAdapter() {

        // Pager
        mPager = (ViewPager) findViewById(R.id.pager_similar_movies);
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
        mPagerAdapter = new SimilarMoviesPagerAdapter(getSupportFragmentManager());
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
