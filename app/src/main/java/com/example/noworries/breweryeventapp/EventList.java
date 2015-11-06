package com.example.noworries.breweryeventapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noworries.breweryeventapp.api.EventAPI;
import com.example.noworries.breweryeventapp.api.EventRestClient;
import com.example.noworries.breweryeventapp.domain.Event;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventList extends ListFragment {

    private EventListAdapter eventListAdapter;
    private List<Event> eventList;

    public EventList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        eventListAdapter = new EventListAdapter(getContext(), R.layout.event_row);
        EventRestClient eventRestClient = new EventRestClient();
        EventAPI eventAPI = eventRestClient.getApi();

        Call<List<Event>> allEvents = eventAPI.getAllEvents();
        allEvents.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                eventList = response.body();
                for (Event event : eventList) {
                    eventListAdapter.add(event);
                    Log.d("onResponse:", event.getTitle());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
