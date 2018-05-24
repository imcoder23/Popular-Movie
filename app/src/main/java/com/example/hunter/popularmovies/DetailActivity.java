package com.example.hunter.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final String URL_IMAGE_PATH = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView detail_moviePic = findViewById(R.id.imageView_moviePic);
        TextView movieName = findViewById(R.id.et_moviename);
        TextView movieDis = findViewById(R.id.textView_movieDescription);
        TextView movieRelease = findViewById(R.id.textView_year);
        TextView movieRating = findViewById(R.id.textView_rating);

        Intent intent = getIntent();
        String Title = intent.getStringExtra("title");
        String Poster = intent.getStringExtra("poster");
        String plot = intent.getStringExtra("plot");
        String rating = intent.getStringExtra("rating");
        String release = intent.getStringExtra("releaseDate");
//        String releaseFinal = release.substring(0,4);


        movieName.setText(Title);
        movieDis.setText(plot);
        movieRelease.setText(release);
        movieRating.setText(rating);

        Picasso.with(this)
                .load(URL_IMAGE_PATH.concat(Poster))
                .fit()
                .into(detail_moviePic);



    }
}
