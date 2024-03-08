package com.movierator.movierator.tmdbApi;

public abstract class TMDBResponse<T> {
  public int page;
  public int total_pages;
  public T[] results;
}
