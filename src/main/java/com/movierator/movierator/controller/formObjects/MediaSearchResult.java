package com.movierator.movierator.controller.formObjects;

public class MediaSearchResult {
  private String title;
  private long id;

  public MediaSearchResult(long id, String title) {
    this.id = id;
    this.title = title;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}
