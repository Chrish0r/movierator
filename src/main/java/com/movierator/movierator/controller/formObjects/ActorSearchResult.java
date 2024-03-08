package com.movierator.movierator.controller.formObjects;

public class ActorSearchResult {
  private String name;
  private long id;

  public ActorSearchResult(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
