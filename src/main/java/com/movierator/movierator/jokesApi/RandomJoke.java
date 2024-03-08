package com.movierator.movierator.jokesApi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"type",
	"setup",
	"punchline",
	"id"
})

public class RandomJoke {

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("setup")
	private String setup;
	
	@JsonProperty("punchline")
	private String punchline;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("type")
	public String getType() {
		return type;
	}
	
	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("setup")
	public String getSetup() {
		return setup;
	}

	@JsonProperty("punchline")
	public String getPunchline() {
		return punchline;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("setup")
	public void setSetup(String setup) {
		this.setup = setup;
	}

	@JsonProperty("punchline")
	public void setPunchline(String punchline) {
		this.punchline = punchline;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}
}
