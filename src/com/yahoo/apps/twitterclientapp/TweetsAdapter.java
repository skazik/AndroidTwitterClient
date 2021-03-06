package com.yahoo.apps.twitterclientapp;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.apps.twitterclientapp.models.Tweet;

public class TweetsAdapter extends ArrayAdapter <Tweet>{

	public TweetsAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		ImageView iv = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), iv);

		TextView nv = (TextView) view.findViewById(R.id.tvName);
		String name = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color = '#777777'>@" +
				tweet.getUser().getScreenName() + "</font></small>";
		nv.setText(Html.fromHtml(name));
		
		TextView bv = (TextView) view.findViewById(R.id.tvBody);
		bv.setText(Html.fromHtml(tweet.getBody()));
		
		return view;
	}

}
