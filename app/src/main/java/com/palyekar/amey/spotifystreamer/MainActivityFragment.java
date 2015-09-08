package com.palyekar.amey.spotifystreamer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.palyekar.amey.spotifystreamer.tracksActivity.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private List<String> l_spotify_list;
    private List<String> artistId = new ArrayList<String>();;
    private ArrayAdapter<String> l_spotifyadapter;
    private SearchView l_search;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View l_rootview = inflater.inflate(R.layout.fragment_main, container, false);

  /*      String[] l_data = {
                "First",
                "Second",
                "Third",
                "Fourth"
        };
*/
        String[] l_data = new String[]{};

        //SearchManager l_searchmanager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        //SearchView l_searchview = (SearchView) getActivity().findViewById(R.id.searchView);
        //l_searchview.setSearchableInfo(l_searchmanager.getSearchableInfo(getActivity().getComponentName()));
/*
        SearchView sv = (SearchView) getActivity().findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        Intent l_searchIntent = getActivity().getIntent();
        if (Intent.ACTION_SEARCH.equals(l_searchIntent.getAction())) {
            String query = l_searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getActivity(),query,Toast.LENGTH_SHORT).show();
            spotifyAsyntcTask l_asynctask = new spotifyAsyntcTask();
            l_asynctask.execute(query);
        }

*/


        spotifyAsyntcTask l_asynctask = new spotifyAsyntcTask();
        l_asynctask.execute("coldplay");

        l_spotify_list = new ArrayList<String>(Arrays.asList(l_data));
        //artistId =

        l_spotifyadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,l_spotify_list);

        ListView l_lview = (ListView) l_rootview.findViewById(R.id.list_view_fragment);
        l_lview.setAdapter(l_spotifyadapter);

        l_lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String l_selectedItem = artistId.get(position);
                Intent myIntent = new Intent(getActivity(), tracksActivity.class)
                        .putExtra("ArtistName", l_selectedItem);
                Log.d("Album", "Album1" + l_selectedItem);
                startActivity(myIntent);
            }
        });

        return l_rootview;
    }


    public class spotifyAsyntcTask extends AsyncTask <String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            l_spotifyadapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(String... params) {

            SpotifyApi l_api = new SpotifyApi();
            // l_api.setAccessToken(accessToken);
            SpotifyService l_spotify = l_api.getService();
            ArtistsPager results = l_spotify.searchArtists(params[0]);
            List<Artist> l_artistslist = results.artists.items;
            l_spotify_list.clear();
            artistId.clear();
            for (Artist element : l_artistslist){
                String name = element.name;
                Log.d("Album", "Album" + name + "" +element.id);
                if (name != null) {
                    l_spotify_list.add(name);
                }
               if (element.id != null ) {
                   artistId.add(element.id);
                   Log.d("Album", "Album" + element.id);
               }
    //            else {
 //                   artistId.add("");
    //            }
            }


            Log.d("hello", l_spotify_list.toString());

            return null;
        }
    }
}
