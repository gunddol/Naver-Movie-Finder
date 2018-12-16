package com.example.gunhome.moviefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.InputStream;
import java.net.URL;

public class Movie_card {
    String title;
    String year;
    String director;
    String actor;
    String image;
    String rating;
    String link;

    public Movie_card(String title, String year, String director, String actor, String image, String rating, String link) {
        this.title = title;;
        this.year = year;
        this.director = director;
        this.actor = actor;
        this.image = image;
        this.rating = rating;
        this.link = link;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
