package com.movierator.movierator.tmdbApi;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class TMDBApi<T extends TMDBResponse<U>, U> {
  private String entityEndpoint;
  private HttpEntity<String> httpEntity;
  private RestTemplate restTemplate;

  private TMDBConfig config;
  private Class<T> tmdbResponseClass;
  private Class<U> tmdbResponseEntityClass;

  TMDBApi(RestTemplate restTemplate, TMDBConfig tmdbConfig, String entityEndpoint, Class<T> tmdbResponseClass,
      Class<U> tmdbResponseEntityClass) {
    this.restTemplate = restTemplate;
    this.config = tmdbConfig;
    this.tmdbResponseClass = tmdbResponseClass;
    this.tmdbResponseEntityClass = tmdbResponseEntityClass;

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + config.getApiToken());
    httpEntity = new HttpEntity<>(headers);

    this.entityEndpoint = entityEndpoint;
  }

  public T search(String searchTerm) {
    String urlTemplate = UriComponentsBuilder
        .fromUriString(config.getUrl())
        .path("/search")
        .path(this.entityEndpoint)
        .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
        .queryParam("page", 1)
        .queryParam("query", searchTerm)
        .encode()
        .toUriString();

    // Maybe pagingation should be handled here. But it itsn't important as a good
    // search term narrows down enough the result

    ResponseEntity<T> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity, this.tmdbResponseClass);

    return response.getBody();
  }

  public Optional<U> findById(long id) {
    String urlTemplate = UriComponentsBuilder
        .fromUriString(config.getUrl())
        .path(this.entityEndpoint)
        .path("/" + id)
        .queryParam("language", LocaleContextHolder.getLocale().getLanguage())
        .encode()
        .toUriString();

    try {
      ResponseEntity<U> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, httpEntity,
          this.tmdbResponseEntityClass);

      return Optional.of(response.getBody());
    } catch (HttpClientErrorException e) {
      return Optional.empty();
    }
  }

  protected HttpEntity<String> getHttpEntity() {
    return httpEntity;
  }

  protected RestTemplate getRestTemplate() {
    return restTemplate;
  }

  protected TMDBConfig geTmdbConfig() {
    return config;
  }

  protected String getEntityEndpoint() {
    return entityEndpoint;
  }
}
