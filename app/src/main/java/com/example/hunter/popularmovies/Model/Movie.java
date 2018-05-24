package com.example.hunter.popularmovies.Model;


public class Movie {
    private int id;
    private String movie_title;
    private String movie_poster;
    private String movie_plot;
    private String movie_rating;
    private String movie_releasedate;

    public Movie(int id, String movie_title, String movie_poster, String movie_plot, String movie_rating, String movie_releasedate) {
        this.id = id;
        this.movie_title = movie_title;
        this.movie_poster = movie_poster;
        this.movie_plot = movie_plot;
        this.movie_rating = movie_rating;
        this.movie_releasedate = movie_releasedate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMovie_plot() {
        return movie_plot;
    }

    public void setMovie_plot(String movie_plot) {
        this.movie_plot = movie_plot;
    }

    public String getMovie_rating() {
        return movie_rating;
    }

    public void setMovie_rating(String movie_rating) {
        this.movie_rating = movie_rating;
    }

    public String getMovie_releasedate() {
        return movie_releasedate;
    }

    public void setMovie_releasedate(String movie_releasedate) {
        this.movie_releasedate = movie_releasedate;
    }
}
