package com.example.noworries.breweryeventapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noworries.breweryeventapp.api.EventRestClient;
import com.example.noworries.breweryeventapp.domain.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {

    private final Context context;
    private ArrayList<Event> events;

    private static class ViewHolder {
        private ImageView mEventImage;
        private TextView mEventTitle;
        private TextView mEventDate;
    }

    public EventListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        events = new ArrayList<>();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final Event event = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.event_row, null);
            viewHolder = new ViewHolder();
            viewHolder.mEventImage = (ImageView) convertView.findViewById(R.id.eventImageID);
            viewHolder.mEventTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.mEventDate = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(EventRestClient.BASE_URL + "/" + event.getImageUrl()).into(viewHolder.mEventImage);

        viewHolder.mEventTitle.setText(event.getTitle());
        viewHolder.mEventDate.setText(event.getStart());

        return convertView;
    }


}
