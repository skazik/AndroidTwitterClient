package com.yahoo.apps.twitterclientapp.fragments;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.twitterclientapp.TwitterClientApp;
import com.yahoo.apps.twitterclientapp.models.Tweet;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterClientApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						getAdapter().addAll(Tweet.fromJson(jsonTweets));
					}
				});
	}
	
}
