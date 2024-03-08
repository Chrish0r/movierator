package com.movierator.movierator.model.dto;

public class RandomActivity {
	
	String activity;
	String type;
	int participants;
	float price;
	String link;
	String key;
	float accessibility;
	
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getParticipants() {
		return participants;
	}
	public void setParticipants(int participants) {
		this.participants = participants;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public float getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(float accessibility) {
		this.accessibility = accessibility;
	}
	
	@Override
    public String toString() {
        return "("
        		+ "activity description: " + this.activity + "; "
        		+ "type: " + this.type + "; "
                + "suggested number of participants: " + this.participants
                + ")";
    }
	
}
