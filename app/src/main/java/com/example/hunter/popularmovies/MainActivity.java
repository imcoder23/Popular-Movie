package com.example.hunter.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hunter.popularmovies.Adapter.movieAdapter;
import com.example.hunter.popularmovies.Model.Movie;
import com.example.hunter.popularmovies.Model.SettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements movieAdapter.OnItemClickListener,SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView mrecyclerView;
    private movieAdapter adapter;
    private List<Movie> moviesList;
    private RequestQueue mRequestQueue;
    private TextView errorView;
    private Button btn_reload;
    // --Commented out by Inspection (5/28/2018 10:01 AM):private final String defaultOrder = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mrecyclerView = findViewById(R.id.rv_movieList);
        mrecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        moviesList = new ArrayList<>();
        errorView = findViewById(R.id.show_error);
        btn_reload = findViewById(R.id.btn_error);
        mRequestQueue = Volley.newRequestQueue(this);

        if(!check_ConnectionStatus()){
            showgagets();
            return;
        }
        checkSortOrder();
        }

        @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void parseJson(String sort) {
       Log.d("parseSort",sort);

       String Url = getUrl(sort);
//        String Url ="https://api.themoviedb.org/3/movie/popular?api_key=";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i = 0; i < jsonArray.length(); i++ ){
                                JSONObject result = jsonArray.getJSONObject(i);

                                int Movie_Id = result.getInt("id");
                                String MOVIE_TITLE = result.getString("title");
//                                Log.d("title",MOVIE_TITLE);
                                String MOVIE_POSTER = result.getString("poster_path");
//                                Log.d("Poster",MOVIE_POSTER);
                                String MOVIE_PLOT = result.getString("overview");
//                                Log.d("plot",MOVIE_PLOT);
                                String MOVIE_RATING = result.getString("vote_average");
//                                Log.d("Rating",MOVIE_RATING);
                                String MOVIE_RELEASE_DATE = result.getString("release_date");
//                                Log.d("release Date",MOVIE_RELEASE_DATE);

                                moviesList.add(new Movie(Movie_Id,MOVIE_TITLE,MOVIE_POSTER,MOVIE_PLOT,MOVIE_RATING,MOVIE_RELEASE_DATE));
                            }

                            adapter = new movieAdapter(MainActivity.this,moviesList);
                            mrecyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(MainActivity.this);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Internet/Communication Error!", Toast.LENGTH_SHORT).show();
                    showgagets();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                        showgagets();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                        showgagets();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                        showgagets();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                        showgagets();
                }
            }
        });
        mRequestQueue.add(request);


    }

    private String getUrl(String sort) {
        final String Api_key = ""; //PUT Your API KEY HERE
        final String movie_api = "api_key";
        final String base_Url = "https://api.themoviedb.org/3/movie/";
        URL url = null;
        if (Api_key.isEmpty()) {
            Toast.makeText(this, "Please Input your API KEY First", Toast.LENGTH_LONG).show();
        } else {
            Uri uri = Uri.parse(base_Url)
                    .buildUpon()
                    .appendPath(sort)
                    .appendQueryParameter(movie_api, Api_key)
                    .build();
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
            return String.valueOf(url);

    }

    private void showgagets() {
        errorView.setVisibility(View.VISIBLE);
        btn_reload.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.sort_menu){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

        if(!check_ConnectionStatus()){
                mrecyclerView.setVisibility(View.INVISIBLE);
                showgagets();
                return;
        }

        Intent detailIntent = new Intent (this,DetailActivity.class);
        Movie clickedItem = moviesList.get(position);
        detailIntent.putExtra("title",clickedItem.getMovie_title());
        detailIntent.putExtra("poster",clickedItem.getMovie_poster());
        detailIntent.putExtra("plot",clickedItem.getMovie_plot());
        detailIntent.putExtra("rating",clickedItem.getMovie_rating());
        detailIntent.putExtra("releaseDate",clickedItem.getMovie_releasedate());
        startActivity(detailIntent);
    }



    private boolean check_ConnectionStatus(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if( null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnectedOrConnecting();
        }
        return false;
    }

    public void reload(View view) {
            if(check_ConnectionStatus()){
                errorView.setVisibility(View.INVISIBLE);
                btn_reload.setVisibility(View.INVISIBLE);
                mrecyclerView.setVisibility(View.VISIBLE);
                mRequestQueue = Volley.newRequestQueue(this);
                checkSortOrder();
            }else{
                Toast.makeText(this,"Check Internet/Network",Toast.LENGTH_LONG).show();
            }
}

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String sortOrder = sharedPreferences.getString(this.getString(R.string.sort_order_key), this.getString(R.string.most_popular));
        if(sortOrder.equals(this.getString(R.string.most_popular))){
            super.recreate();
            parseJson("popular");
        }else{
            super.recreate();
            parseJson("top_rated");
        }
    }

    private void checkSortOrder() {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String sortOrder = sharedPreferences.getString(this.getString(R.string.sort_order_key), this.getString(R.string.most_popular));
            if(sortOrder.equals(this.getString(R.string.most_popular))){
                parseJson("popular");
            }else{
                parseJson("top_rated");
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
}
