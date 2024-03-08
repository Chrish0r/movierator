package com.movierator.movierator.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.movierator.movierator.model.User;

@SuppressWarnings("deprecation")
@Component
public class UserRestAPITester {
	
	// represents client i.e., represents client request
	private final RestTemplate restTemplate;
	
	@Autowired
    public UserRestAPITester(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
	public User testGetUserAPI(Long id) {
		String url = "http://localhost:8080/api/users/{id}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		
		return this.restTemplate
				.exchange(url, 
			    		  HttpMethod.GET, requestEntity, User.class, id)
			      .getBody();
	}
	
	@SuppressWarnings("unused")
	public List<User> testGetAllActiveUsersRestAPI() {
		String url = "http://localhost:8080/api/users/";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	 
	    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
	 
	    ResponseEntity<User[]> responseEntity =
	    		   restTemplate.getForEntity(url, User[].class);
	    User[] users = responseEntity.getBody();
	    
	    ObjectMapper mapper = new ObjectMapper();
	    
	    mapper.registerModule(new JSR310Module());
	    
	    return (List<User>) Arrays.stream(users)
	    		  .map(object -> mapper.convertValue(object, User.class))
	    		  .collect(Collectors.toList());
	}
}
