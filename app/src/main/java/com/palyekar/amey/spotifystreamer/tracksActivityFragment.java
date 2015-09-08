package com.palyekar.amey.spotifystreamer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class tracksActivityFragment extends Fragment {

    private List<String> l_spotify_list;
    private ArrayAdapter<String> l_spotifyadapter;
    private String l_artistname=null;

    public tracksActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if ((savedInstanceState != null) && (savedInstanceState.getString("ArtistName") != null)) {
            l_artistname = savedInstanceState.getString("ArtistName");
            Log.d("Album", "Albumxx" + l_artistname);
        }
        Log.d("Album", "onActivitycreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent myIntent = getActivity().getIntent();
        View rootview = inflater.inflate(R.layout.fragment_tracks, container, false);

        String[] l_data = new String[]{};

        SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
        l_artistname = settings.getString("ArtistName", "");

        if (myIntent!=null && myIntent.hasExtra("ArtistName")) {
            l_artistname = myIntent.getStringExtra("ArtistName");
            Log.d("Album", "Album2" + l_artistname);
        }

        spotifyAsyntcTask l_asynctask = new spotifyAsyntcTask();
        l_asynctask.execute(l_artistname);

        l_spotify_list = new ArrayList<String>(Arrays.asList(l_data));

        l_spotifyadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,l_spotify_list);

        ListView l_lview = (ListView) rootview.findViewById(R.id.tracks_list_view);
        l_lview.setAdapter(l_spotifyadapter);

        l_lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String l_selectedItem = parent.getItemAtPosition(position).toString();
                Intent myIntent = new Intent(getActivity(), player.class)
                        .putExtra("TrackName", l_selectedItem);
                startActivity(myIntent);
            }
        });

        return rootview;
    }


    public class spotifyAsyntcTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            l_spotifyadapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(String... params) {
            SpotifyApi l_api=new SpotifyApi();
            // l_api.setAccessToken(accessToken);
            SpotifyService l_spotify = l_api.getService();
       //     Tracks results = l_spotify.getArtistTopTrack(params[0]);
            Map<String, Object> options = new HashMap<>();
            options.put(SpotifyService.COUNTRY, Locale.getDefault().getCountry());
            Tracks results = l_spotify.getArtistTopTrack(params[0],options);
            List<Track> l_tracklist = results.tracks;
            l_spotify_list.clear();
            for (Track track : l_tracklist){
                String name = track.name;
                Log.d("Album", "Album" + name);
                l_spotify_list.add(name);
            }
            return null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ArtistName", l_artistname);
        editor.commit();
    }

}
