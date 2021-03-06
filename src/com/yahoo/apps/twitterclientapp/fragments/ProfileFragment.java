package com.yahoo.apps.twitterclientapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.twitterclientapp.ProfileActivity;
import com.yahoo.apps.twitterclientapp.TwitterClientApp;
import com.yahoo.apps.twitterclientapp.models.Tweet;

public class ProfileFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ProfileActivity ac = (ProfileActivity) getActivity();
		TwitterClientApp.getRestClient().getUserTimeline(ac.getScreenName(),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						getAdapter().addAll(Tweet.fromJson(jsonTweets));
					}
				});
		
	}
}
