package com.example.noworries.breweryeventapp.api;

import com.example.noworries.breweryeventapp.domain.Event;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface EventAPI {
    @GET("/events")
    Call<List<Event>> getAllEvents();

    @GET("/events/{id}")
    Call<Event> getEventByID(@Path("id") String id);
}
