package com.yahoo.apps.twitterclientapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "cDAtVPdOH7i8MuBJ7Pbvlw";       // Change this
    public static final String REST_CONSUMER_SECRET = "AbyT9o8Am5Qsr26t4HlW03QE8mCTKDKt8HaPtPj2A"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterclientapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/home_timeline.json");
		client.get(url, null, handler);
	}
    
    public void getMentions(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/mentions_timeline.json");
		client.get(url, null, handler);
	}
    
    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
    	Log.d("DEBUG", "postTweet: "+ tweet);
    	String url = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
    	client.post(url, params, handler);
    }
    
    public void getUserInfo(String screen_name, AsyncHttpResponseHandler handler) {
    	String url;
    	if (screen_name.length() > 0) {
    		url = getApiUrl("users/show.json?screen_name=" + screen_name);
    	}
    	else {
    		 url = getApiUrl("account/verify_credentials.json");
    	}
		client.get(url, null, handler);
	}
    
    public void getUserStatuses(long id, AsyncHttpResponseHandler handler) {
    	String url = getApiUrl("statuses/show/" + String.valueOf(id) + ".json");
    	// also works as 
    	// String url = getApiUrl("statuses/show.json" + "?id="+String.valueOf(id));
    	client.get(url,  null, handler);
	}
    
    public void getUserTimeline(String screen_name, AsyncHttpResponseHandler handler) {
    	String url;
    	if (screen_name.length() > 0) {
    		url = getApiUrl("statuses/user_timeline.json?screen_name=" + screen_name);
    	}
    	else {
    		 url = getApiUrl("statuses/user_timeline.json");
    	}
		client.get(url, null, handler);
	}
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}