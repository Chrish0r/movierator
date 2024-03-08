package com.movierator.movierator.tmdbApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class TMDBMovieApi extends TMDBApi<TMDBMovieResponse, TMDBMovie> {
  public TMDBMovieApi(RestTemplate restTemplate, TMDBConfig tmdbConfig) {
    super(restTemplate, tmdbConfig, "/movie", TMDBMovieResponse.class, TMDBMovie.class);
  }

  /**
   * @return A list of upcoming movies
   */
  public List<TMDBMovie> getUpcoming() {
    String urlTemplate = UriComponentsBuilder
        .fromUriString(this.geTmdbConfig().getUrl())
        .path(this.getEntityEndpoint())
        .path("/upcoming")
        .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
        .encode()
        .toUriString();

    ResponseEntity<TMDBMovieResponse> response = getRestTemplate().exchange(urlTemplate, HttpMethod.GET, getHttpEntity(), TMDBMovieResponse.class);

    TMDBMovieResponse tmdbMovieResponse = response.getBody();

    if(tmdbMovieResponse == null) {
      return new ArrayList<>();
    }

    return Arrays.asList(tmdbMovieResponse.results);
  }
}
