package com.example.noworries.breweryeventapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noworries.breweryeventapp.api.EventAPI;
import com.example.noworries.breweryeventapp.api.EventRestClient;
import com.example.noworries.breweryeventapp.domain.Event;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.eventDetailToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("ID");

            EventAPI eventAPI = new EventRestClient().getApi();
            Call<Event> event = eventAPI.getEventByID(id);

            event.enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Response<Event> response, Retrofit retrofit) {
                    Event event = response.body();

                    Log.e("Event: ", event.toString());

                    ImageView eventImage = (ImageView) findViewById(R.id.eventDetailImageId);
                    Picasso
                            .with(getApplicationContext())
                            .load(EventRestClient.BASE_URL + "/" + event.getImageUrl()).into(eventImage);

                    TextView eventTitle = (TextView) findViewById(R.id.eventDetailTitle);
                    eventTitle.setText(event.getTitle());

                    TextView eventDate = (TextView) findViewById(R.id.eventDetailDate);
                    eventDate.setText(event.getStart());

                    TextView eventDetails = (TextView) findViewById(R.id.eventDetailDescription);
                    eventDetails.setText(event.getDescription());
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    public void onBuyClick(View view) {
        String url = "http://thebrewery.tictail.com/";
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        startActivity(webIntent);
    }
}
