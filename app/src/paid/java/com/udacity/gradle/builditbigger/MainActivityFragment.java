package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.R;

/**
 * Created by gene on 1/3/17.
 */

public class MainActivityFragment extends Fragment {

    private ProgressBar mProgressBar;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = (ProgressBar)root.findViewById(R.id.progressBar);

        Button tellJokeButton = (Button)root.findViewById(R.id.telljoke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                new TellJokeTask(mProgressBar).execute(getActivity());
            }
        });


        return root;
    }
}
