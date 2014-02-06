package com.yahoo.apps.twitterclientapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.twitterclientapp.R;
import com.yahoo.apps.twitterclientapp.TweetsAdapter;
import com.yahoo.apps.twitterclientapp.TwitterClientApp;
import com.yahoo.apps.twitterclientapp.models.Tweet;
import com.yahoo.apps.twitterclientapp.models.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class TweetsListFragment extends Fragment {
	TweetsAdapter twAdapter;
	ListView lvItems;
	ArrayList<Tweet> tweets;

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// here is when fragment is displayed on the screen and
		// activity exists - so we can access to it's elements by calling:
		// getActivity().findViewById(R.id.xxx)
		tweets = new ArrayList<Tweet>();
		lvItems = (ListView) getActivity().findViewById(R.id.lvTweets);
		twAdapter = new TweetsAdapter(getActivity(), tweets);
		lvItems.setAdapter(twAdapter);
		setupListViewListener();
	}
	
	public TweetsAdapter getAdapter() {
		return twAdapter;
	}

	private void setupListViewListener() {
		lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				//Log.d("DEBUG", "got click on " + tweets.get(position).getId()); 
				TwitterClientApp.getRestClient().getUserInfo(tweets.get(position).getId(),
						new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						// Log.d("DEBUG", json.toString());
						User u = User.fromJson(json);
						Log.d("DEBUG", "got click on " + u.getName() + "_" + u.getScreenName());
					}
				});
			}
		});
	}

}
