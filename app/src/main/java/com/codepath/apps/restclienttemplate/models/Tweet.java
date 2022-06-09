package com.codepath.apps.restclienttemplate.models;

import android.nfc.Tag;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public User user;
    public String createdAt;
    public String tweet_url;
    public static String TAG = "MESS";

    //empty constructor for the Parcel library
    public Tweet(){

    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user  = User.fromJson(jsonObject.getJSONObject("user"));

        if(!jsonObject.getJSONObject("entities").has("media")){
            tweet.tweet_url = "none";
            Log.i(TAG, "no pics");
        }
        else{
            tweet.tweet_url = jsonObject.getJSONObject("entities").getJSONArray("media")
                    .getJSONObject(0).getString("media_url");
            Log.i(TAG, "has pics" + tweet.tweet_url);
        }

        return tweet;
    }

    public static List<Tweet> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i =0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }


        return tweets;
    }


}
