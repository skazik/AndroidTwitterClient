package com.yahoo.apps.twitterclientapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.twitterclientapp.models.Tweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineActivity extends Activity {
	private final int COMPOSE_REQUEST = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		fetchHomeTimeline();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	private void fetchHomeTimeline() {
		TwitterClientApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray jsonTweets) {
						ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
						ListView lv = (ListView) findViewById(R.id.lvTweets);
						TweetsAdapter twAdapter = new TweetsAdapter(
								getBaseContext(), tweets);
						lv.setAdapter(twAdapter);
					}
				});
	}

	public void onReload(MenuItem mi) {
		Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
		fetchHomeTimeline();
	}

	public void onCompose(MenuItem mi) {
		// Toast.makeText(this, "Composing...", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, ComposeActivity.class);
		// i.putExtra("title", "TO DO");
		startActivityForResult(i, COMPOSE_REQUEST);
	}

	public void onProfile(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST) {
			String tweet = data.getStringExtra("tweet");
			if (tweet.length() > 0) {
				// Toast.makeText(this, "got tweet " + tweet.toString(), Toast.LENGTH_SHORT).show();
				TwitterClientApp.getRestClient().postTweet(tweet,
						new JsonHttpResponseHandler() {

							@Override
							public void onSuccess(JSONObject jsonTweet) {
								Log.d("DEBUG", "got on success");
								fetchHomeTimeline();
							}

							@Override
							public void onFailure(Throwable arg0, JSONArray arg1) {
								Log.d("DEBUG", "got on failure");
								super.onFailure(arg0, arg1);
							}

							@Override
							protected void handleFailureMessage(Throwable arg0,
									String arg1) {
								Log.d("DEBUG", "got failure messgage: " + arg1);
								super.handleFailureMessage(arg0, arg1);
							}
						});
			}

		}
	}

}
