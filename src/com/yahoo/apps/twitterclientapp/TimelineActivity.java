package com.yahoo.apps.twitterclientapp;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.twitterclientapp.fragments.HomeTimelineFragment;
import com.yahoo.apps.twitterclientapp.fragments.MentionsFragment;
import com.yahoo.apps.twitterclientapp.fragments.TweetsListFragment;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TimelineActivity extends FragmentActivity implements TabListener {
	private final int COMPOSE_REQUEST = 20;
	TweetsListFragment fragmentTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setNavTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	private void setNavTabs() {
		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.setDisplayShowTitleEnabled(true);
		Tab tabHome = ab.newTab().setText("Home");
		tabHome.setIcon(R.drawable.ic_action_home);
		tabHome.setTag("HomeTimelineFragment");
		tabHome.setTabListener(this);
		Tab tabMentions = ab.newTab().setText("Mentions")
				.setIcon(R.drawable.ic_action_mentions)
				.setTag("MentionsFragment").setTabListener(this);
		ab.addTab(tabHome, 0, true); // set selected
		ab.addTab(tabMentions, 1, false);
		// other ways ? :
		// tabHome.select();
		// ab.selectTab(tabHome);

	}

	private void fetchMentions() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flContainer, new MentionsFragment());
		ft.commit();
	}

	private void fetchHomeTimeline() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flContainer, new HomeTimelineFragment());
		ft.commit();
	}

	public void onReload(MenuItem mi) {
		Toast.makeText(this, "Refreshing Home Timeline...", Toast.LENGTH_SHORT).show();
		getActionBar().selectTab(getActionBar().getTabAt(0));
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
		i.putExtra("screen_name", "");
		startActivity(i);
	}

	public void onUserProfile(String screen_name) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("screen_name", screen_name);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST) {
			String tweet = data.getStringExtra("tweet");
			if (tweet.length() > 0) {
				// Toast.makeText(this, "got tweet " + tweet.toString(),
				// Toast.LENGTH_SHORT).show();
				TwitterClientApp.getRestClient().postTweet(tweet,
						new JsonHttpResponseHandler() {

							@Override
							public void onSuccess(JSONObject jsonTweet) {
								Log.d("DEBUG", "got on success");
								onReload(null);
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

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		onTabSelected(arg0, arg1);
	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		if (arg0.getTag().toString().equalsIgnoreCase("HomeTimelineFragment")) {
			fetchHomeTimeline();
		} else {
			fetchMentions();
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}

}
