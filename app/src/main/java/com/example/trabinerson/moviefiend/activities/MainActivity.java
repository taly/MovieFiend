package com.example.trabinerson.moviefiend.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.trabinerson.moviefiend.R;
import com.example.trabinerson.moviefiend.fragments.InTheatresFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set fragment (if necessary)
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            Fragment listFragment = new InTheatresFragment();
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, listFragment, "").commit();
        }
    }

}
