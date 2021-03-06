package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.media.ImageWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.databinding.ItemTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.Util;

import java.util.List;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    public static final String TAG = "Tweets_Adapter";
    private ItemTweetBinding binding;

    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView contentImage;
        TextView tvtimeStamp;
        ItemTweetBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            contentImage = itemView.findViewById(R.id.contentImage);
            tvtimeStamp =  itemView.findViewById(R.id.tvtimeStamp);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            Log.i(TAG, "body" + tweet.body);
            tvScreenName.setText((tweet.user.screenName));
            Glide.with(context).load(tweet.user.publicImageUrl).into(ivProfileImage);
            if (tweet.tweet_url.equals("none")) {
                contentImage.setVisibility(View.GONE);
            } else {
                contentImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.tweet_url).override(500, 500).centerCrop().transform(new RoundedCorners(30)).into(contentImage);
            }
            String timeStamp = Util.getRelativeTimeAgo(tweet.createdAt);
            tvtimeStamp.setText(timeStamp);

        }
    }
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }


}
