package com.example.hunter.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunter.popularmovies.Model.Movie;
import com.example.hunter.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.movieHolder> {
    private final String Image_path = "http://image.tmdb.org/t/p/w185";
    private final Context mcontext;
    private final List<Movie> movielist;
    private com.example.hunter.popularmovies.Adapter.movieAdapter.OnItemClickListener mListner;

    public interface OnItemClickListener{
        void onItemClick(int position);

        // void Resume();
    }

    public void setOnItemClickListener(OnItemClickListener listner){
                mListner = listner;
    }

    public movieAdapter(Context mcontext, List<Movie> movielist) {
        this.mcontext = mcontext;
        this.movielist = movielist;

    }


    @NonNull
    @Override
    public movieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.iv_list_movie_poster,parent,false);
        return new movieHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull movieHolder holder, int position) {
            Movie currentItem = movielist.get(position);
            String Imageurl = currentItem.getMovie_poster();
            String ImdbRating = currentItem.getMovie_rating();
//        Log.d("!stRat",ImdbRating);
            Picasso.with(mcontext)
                .load(Image_path.concat(Imageurl))
                .fit()
                .into(holder.mimageView);
        holder.tv_ImdbRating.setText(ImdbRating);
    }

    @Override
    public int getItemCount() {
        return movielist.size();
    }


    public class movieHolder extends RecyclerView.ViewHolder{
        final ImageView mimageView;
        final TextView tv_ImdbRating;

        movieHolder(View itemView) {
            super(itemView);
         mimageView = itemView.findViewById(R.id.imageView_movieposter);
         tv_ImdbRating = itemView.findViewById(R.id.textView_imdb);

         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(mListner != null){
                     int position = getAdapterPosition();
                     if(position != RecyclerView.NO_POSITION){
                         mListner.onItemClick(position);
                     }
                 }
             }
         });
        }
    }




}
