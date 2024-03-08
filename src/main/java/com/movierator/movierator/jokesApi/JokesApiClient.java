package com.movierator.movierator.jokesApi;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JokesApiClient {

	private RestTemplate restTemplate;;

	public JokesApiClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	public RandomJoke getRandomJoke() {
		String url = "https://official-joke-api.appspot.com/random_joke";

		ResponseEntity<RandomJoke> response = restTemplate.getForEntity(url, RandomJoke.class);

		return response.getBody();
	}
}
