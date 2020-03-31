package com.yohanbernole.lamzone.view;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder>{
    private final List<Meeting> mMeetings;
    MeetingApiService mApiService;
    MeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mApiService = DI.getMeetingApiService();
        final Meeting meeting = mMeetings.get(position);
        String nameMeeting = meeting.getName()
                + " - " + DateFormat.getTimeInstance(3, Locale.FRANCE).format(meeting.getHours())
                + " - " + meeting.getSubject();

        String nameUsers = meeting.getUsers().get(0).getEmail()
                + ", "
                + meeting.getUsers().get(1).getEmail();

        if(nameUsers.length() > 36) {
            nameUsers = nameUsers.substring(0, 36) + "...";
        }

        if(nameMeeting.length() > 30) {
            nameMeeting = nameMeeting.substring(0, 30) + "...";
        }

        holder.name.setText(nameMeeting);
        holder.meeting_users.setText(nameUsers);
        holder.image.setBackgroundColor(meeting.getLocation().getColor());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeetings.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, meeting_users;
        ImageView image;
        ImageButton mDeleteButton;


        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_list_name);
            image = view.findViewById(R.id.item_list_color);
            mDeleteButton = view.findViewById(R.id.item_list_delete_button);
            meeting_users = view.findViewById(R.id.item_list_user);
        }
    }
}
