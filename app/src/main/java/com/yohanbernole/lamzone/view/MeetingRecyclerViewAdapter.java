package com.yohanbernole.lamzone.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder>{
    private final List<Meeting> mMeetings;
    private MeetingApiService mApiService;
    MeetingRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        mApiService = DI.getMeetingApiService();
        final Meeting meeting = mMeetings.get(position);

        Date date = meeting.getDate();
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.FRANCE);
        String strDate = dateFormat.format(date);
        strDate = strDate.replace(":","h");

        String nameMeeting = meeting.getName()
                + " - " + strDate
                + " - " + meeting.getSubject();

        String nameUsers = getUsersToPrint(meeting);

        nameUsers = getFormatString(nameUsers, 36);
        nameMeeting = getFormatString(nameMeeting, 30);

        holder.name.setText(nameMeeting);
        holder.meeting_users.setText(nameUsers);
        holder.image.setImageTintList(ColorStateList.valueOf(meeting.getLocation().getColor()));

        // *** Orientation Portrait *** //
        if(holder.orientation == 0)
        {
            holder.listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailsMeetingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID", meeting.getId());
                    intent.putExtras(bundle);
                    ActivityCompat.startActivity(v.getContext(), intent, null);
                }
            });
        }
        // *** Orientation Paysage *** //
        else if(holder.orientation == 1){
            holder.listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID", meeting.getId());
                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    DetailMeetingFragment detailMeetingFragment = new DetailMeetingFragment();
                    detailMeetingFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_fragment, detailMeetingFragment)
                            .commit();
                }
            });
        }

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.removeMeeting(meeting);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, meeting_users;
        ImageView image;
        ImageButton mDeleteButton;
        LinearLayout listItem;
        int orientation;
        ViewHolder(View view) {
            super(view);
            if(view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                orientation = 0;
            }
            else if (view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                orientation = 1;
            }
            name = view.findViewById(R.id.item_list_name);
            image = view.findViewById(R.id.item_list_color);
            mDeleteButton = view.findViewById(R.id.item_list_delete_button);
            meeting_users = view.findViewById(R.id.item_list_user);
            listItem = view.findViewById(R.id.item_meeting);
        }
    }

    private String getUsersToPrint(Meeting meeting){
        String nameUsers;
        if(meeting.getUsers().size() >= 2){
            nameUsers = meeting.getUsers().get(0).getEmail() + ", " + meeting.getUsers().get(1).getEmail();
        }else{
            nameUsers = meeting.getUsers().get(0).getEmail();
        }
        return nameUsers;
    }

    private String getFormatString(String str, int nb){
        if(str.length() > nb) {
            str = str.substring(0, nb) + "...";
        }
        return str;
    }
}
