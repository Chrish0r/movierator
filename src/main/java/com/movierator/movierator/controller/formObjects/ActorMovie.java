package com.movierator.movierator.controller.formObjects;

public class ActorMovie {
  public long id;
  public String character;
  public String title;
  public Float avgRating;

  public ActorMovie() {
  }

  public ActorMovie(long id, String character, String title, Float avgRating) {
    this.id = id;
    this.character = character;
    this.title = title;
    this.avgRating = avgRating;
  }
}
