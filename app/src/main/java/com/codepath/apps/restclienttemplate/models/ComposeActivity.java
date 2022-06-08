package com.codepath.apps.restclienttemplate.models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    EditText etCompose;
    Button btnTweet;
    public static final String TAG = "ComposeActivity";
    public static final int MAX_CHAR_COUNT  = 280;
    TwitterClient client;
    TextView tvcharCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApplication.getRestClient(this );

        etCompose = findViewById(R.id.etCompose);
        btnTweet =  findViewById(R.id.btnTweet);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = etCompose.getText().toString();
                if(tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tweetContent.length() > MAX_CHAR_COUNT){
                    Toast.makeText(ComposeActivity.this, "Sorry your tweet is too long", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ComposeActivity.this,tweetContent, Toast.LENGTH_LONG).show();
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSucess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published Tweet" +tweetContent);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "onFailure to publish tweet", throwable);
                    }
                });
            }
        });

        tvcharCount = findViewById(R.id.tvcharCount);

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                tvcharCount.setText(charSequence.length() + "/" + MAX_CHAR_COUNT);
                if(charSequence.length() > MAX_CHAR_COUNT){
                    btnTweet.setTextColor(Color.parseColor("#FF0000"));
                }
                else {
                    btnTweet.setTextColor(tvcharCount.getCurrentTextColor());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}