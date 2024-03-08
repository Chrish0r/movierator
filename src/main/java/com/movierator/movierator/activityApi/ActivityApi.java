package com.movierator.movierator.activityApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.movierator.movierator.model.dto.RandomActivity;

@Controller
@RequestMapping("/api/activity")
public class ActivityApi {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// represents client i.e., represents client request
	private final RestTemplate restTemplate;

	@Autowired
	public ActivityApi(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@GetMapping("/")
	public String consumeRandomActivity(Model model) {
		String url = "https://www.boredapi.com/api/activity";
		
		// retrieves an representation from an object  of {@link RandomActivity}
		RandomActivity randomActivity = restTemplate.getForObject(url, RandomActivity.class);

		logger.info("The suggested random activity due to boredom: " + randomActivity.toString());
		
		model.addAttribute("randomActivity", randomActivity);

		return "/random-activity/suggested-random-activity";

	}

}
