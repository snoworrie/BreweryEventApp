package com.example.noworries.breweryeventapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.noworries.breweryeventapp.api.EventAPI;
import com.example.noworries.breweryeventapp.api.EventRestClient;
import com.example.noworries.breweryeventapp.domain.Event;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventListFragment extends ListFragment {

    private List<Event> eventList;
    private EventListAdapter eventListAdapter;

    private ListFragmentItemClickListener itemClickListener;

    public interface ListFragmentItemClickListener {
        void onListFragmentItemClick(int position, String id);
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
                    Log.e("onResponse:", event.getTitle());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });

        setListAdapter(eventListAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String eventId = ((Event)l.getItemAtPosition(position)).getId();
        itemClickListener.onListFragmentItemClick(position, eventId);
        super.onListItemClick(l, v, position, id);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        ListView listView = getListView();
        listView.setDivider(null);
        View footerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        listView.addFooterView(footerView);

        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            itemClickListener = (ListFragmentItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemClickListener = null;
    }

}
