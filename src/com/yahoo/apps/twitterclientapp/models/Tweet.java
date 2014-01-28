package com.yahoo.apps.twitterclientapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends BaseModel{
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public String getBody() {
		return getString("text");
	}
	
	public long getId() {
		return getLong("id");
	}
	
	public boolean isFavorited() {
		return getBoolean("favorited");
	}
	
	public boolean isRetweeted() {
		return getBoolean("retweeted");
	}
	
	public static Tweet fromJson(JSONObject json) {
		Tweet t = new Tweet();
		try {
			t.jsonObject = json;
			t.user = User.fromJson(json.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return t;
	}
	
	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet> (jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		
		return tweets;
	}
	
}
