package com.yahoo.apps.twitterclientapp;

import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.apps.twitterclientapp.models.User;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
	}

	private void loadProfileInfo() {
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("Profile: " + u.getScreenName());
				populateProfileHeader(u);
			}
		});
	}

	private void populateProfileHeader(User u) {
		ImageView iv = (ImageView) findViewById(R.id.ivProfile);
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFlws = (TextView) findViewById(R.id.tvFlws);
		TextView tvFling = (TextView) findViewById(R.id.tvFling);
		
		//iv.setImageURI(u.getProfileImageUrl());
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), iv);
		
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFlws.setText(u.getFollowersCount() + " Followers");
		tvFling.setText(u.getFriendsCount() + " Following");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
