package com.yohanbernole.lamzone.ui.adapter;

import android.content.res.ColorStateList;
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
import com.yohanbernole.lamzone.ui.event.LaunchActivityEvent;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder>{
    private final List<Meeting> mMeetings;
    private MeetingApiService mApiService;
    public MeetingRecyclerViewAdapter(List<Meeting> items) {
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

            holder.listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new LaunchActivityEvent(meeting.getId()));
                }
            });


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

        ViewHolder(View view) {
            super(view);
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
