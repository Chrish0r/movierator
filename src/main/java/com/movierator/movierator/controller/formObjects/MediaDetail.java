package com.movierator.movierator.controller.formObjects;

import com.movierator.movierator.model.Media;
import com.movierator.movierator.model.MediaType;

public class MediaDetail extends Media {
  private Float avgRating;

  public MediaDetail(long id, MediaType type, String name, Float avgRating) {
    super(id, type, name);
    this.avgRating = avgRating;
  }

  public Float getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(Float avgRating) {
    this.avgRating = avgRating;
  }
}
