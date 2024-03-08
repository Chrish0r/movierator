package com.movierator.movierator.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.movierator.movierator.model.Media;
import com.movierator.movierator.tmdbApi.TMDBApi;
import com.movierator.movierator.tmdbApi.TMDBApiFactory;
import com.movierator.movierator.tmdbApi.TMDBEntityToMediaMapper;
import com.movierator.movierator.tmdbApi.TMDBMovie;
import com.movierator.movierator.tmdbApi.TMDBMovieResponse;
import com.movierator.movierator.tmdbApi.TMDBSeries;
import com.movierator.movierator.tmdbApi.TMDBSeriesResponse;

/* 
 * I decided against fetching all movies from TMDB and storing them in the database,
 * because it is much more efficient to query TMDB directly.
 */

@Component
public class MediaRepository implements Repository<Media, Long> {
  private TMDBApi<TMDBMovieResponse, TMDBMovie> movieApi;
  private TMDBApi<TMDBSeriesResponse, TMDBSeries> seriesApi;

  public MediaRepository(TMDBApiFactory tmdbApiFactory) {
    this.movieApi = tmdbApiFactory.createForMovies();
    this.seriesApi = tmdbApiFactory.createForSeries();
  }

  public List<Media> findByNameContaining(String nameFragment) {
    TMDBMovieResponse movieResponse = movieApi.search(nameFragment);
    TMDBSeriesResponse seriesResponse = seriesApi.search(nameFragment);

    List<Media> media = new ArrayList<>();
    media.addAll(TMDBEntityToMediaMapper.tmdbMovieResponseToMediaList(movieResponse));
    media.addAll(TMDBEntityToMediaMapper.tmdbSeriesResponseToMediaList(seriesResponse));

    return media;
  }

  public Optional<Media> findById(long id) {
    Optional<TMDBMovie> movie = movieApi.findById(id);

    if (movie.isPresent()) {
      return Optional.of(TMDBEntityToMediaMapper.tmdbMovieToMedia(movie.get()));
    } else {
      Optional<TMDBSeries> series = seriesApi.findById(id);

      if (series.isPresent()) {
        return Optional.of(TMDBEntityToMediaMapper.tmdbSeriesToMedia(series.get()));
      } else {
        return Optional.empty();
      }
    }
  }
}
