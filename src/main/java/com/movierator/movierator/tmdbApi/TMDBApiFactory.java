package com.movierator.movierator.tmdbApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TMDBApiFactory {
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  @Autowired
  private TMDBConfig config;

  public TMDBMovieApi createForMovies() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBMovieApi(restTemplate, config);
  }

  public TMDBApi<TMDBSeriesResponse, TMDBSeries> createForSeries() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBApi<TMDBSeriesResponse, TMDBSeries>(restTemplate, config, "/tv", TMDBSeriesResponse.class, TMDBSeries.class);
  }

  public TMDBActorApi createForActors() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return new TMDBActorApi(restTemplate, config);
  }
}
