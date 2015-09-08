package com.palyekar.amey.spotifystreamer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class playerFragment extends Fragment {

    public playerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent myIntent = getActivity().getIntent();
        String l_trackname=null;
        View rootview = inflater.inflate(R.layout.fragment_player, container, false);


        if (myIntent!=null && myIntent.hasExtra("TrackName")) {
            l_trackname = myIntent.getStringExtra("TrackName");
            Log.d("Album", "Album2" + l_trackname);
        }

        TextView l_tv_trackName = (TextView) rootview.findViewById(R.id.tv_playerFragment_trackName);
        l_tv_trackName.setText(l_trackname);

        return rootview;
    }
}
