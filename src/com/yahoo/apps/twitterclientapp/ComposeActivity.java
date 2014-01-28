package com.yahoo.apps.twitterclientapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void onCancel(View v) {
		setResult(RESULT_CANCELED);
		finish(); // closes the activity, pass data to parent		
	}
	
	public void onTweet(View v) {
		EditText tweet = (EditText)findViewById(R.id.etTweetInput);
		Intent data = new Intent();
		data.putExtra("tweet", tweet.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}
	

}
