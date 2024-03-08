package com.movierator.movierator.tmdbApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class TMDBActorApi extends TMDBApi<TMDBActorResponse, TMDBActor> {
  public TMDBActorApi(RestTemplate restTemplate, TMDBConfig tmdbConfig) {
    super(restTemplate, tmdbConfig, "/person", TMDBActorResponse.class, TMDBActor.class);
  }

  /**
   * @return A list of movie the actor played in
   */
  public List<TMDBMovieCredit> getMovieCredits(long actorId) {
    String urlTemplate = UriComponentsBuilder
        .fromUriString(this.geTmdbConfig().getUrl())
        .path(this.getEntityEndpoint())
        .path("/" + actorId)
        .path("/movie_credits")
        .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
        .encode()
        .toUriString();

    ResponseEntity<TMDBMovieCreditResponse> response = getRestTemplate().exchange(urlTemplate, HttpMethod.GET, getHttpEntity(), TMDBMovieCreditResponse.class);

    TMDBMovieCreditResponse tmdbActorCreditResponse = response.getBody();

    if(tmdbActorCreditResponse == null) {
      return new ArrayList<>();
    }

    return Arrays.asList(tmdbActorCreditResponse.cast);
  } 
}
