package com.movierator.movierator.tmdbApi;

public class TMDBMovie {
  public long id;
  public String title;
  public String release_date;

  public TMDBMovie() {
  }

  public TMDBMovie(long id, String title, String release_date) {
    this.id = id;
    this.title = title;
    this.release_date = release_date;
  }
}
