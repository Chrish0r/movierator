package com.movierator.movierator.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.movierator.movierator.model.dto.RandomActivity;

@Component
public class ActivityRestAPITester {

	// represents client i.e., represents client request
	private final RestTemplate restTemplate;

	@Autowired
	public ActivityRestAPITester(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public RandomActivity testGetActivityAPI() {
		String url = "https://www.boredapi.com/api/activity";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		return this.restTemplate.exchange(url, HttpMethod.GET, requestEntity, RandomActivity.class).getBody();
	}

}
