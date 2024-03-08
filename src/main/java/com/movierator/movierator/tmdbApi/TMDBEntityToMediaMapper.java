package com.movierator.movierator.tmdbApi;

import java.util.ArrayList;
import java.util.List;

import com.movierator.movierator.model.Media;
import com.movierator.movierator.model.MediaType;

public class TMDBEntityToMediaMapper {
  public static List<Media> tmdbMovieResponseToMediaList(TMDBMovieResponse response) {
    List<Media> media = new ArrayList<>();
    for (TMDBMovie tmdbMovie : response.results) {
      media.add(TMDBEntityToMediaMapper.tmdbMovieToMedia(tmdbMovie));
    }
    return media;
  }

  public static List<Media> tmdbSeriesResponseToMediaList(TMDBSeriesResponse response) {
    List<Media> media = new ArrayList<>();
    for (TMDBSeries tmdbSeries : response.results) {
      media.add(TMDBEntityToMediaMapper.tmdbSeriesToMedia(tmdbSeries));
    }
    return media;
  }

  public static Media tmdbMovieToMedia(TMDBMovie movie) {
    return new Media(movie.id, MediaType.MOVIE ,movie.title);
  }

  public static Media tmdbSeriesToMedia(TMDBSeries series) {
    return new Media(series.id, MediaType.SERIES ,series.name);
  }
}
