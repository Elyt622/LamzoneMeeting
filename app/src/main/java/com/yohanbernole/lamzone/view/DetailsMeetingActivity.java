package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.User;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.ArrayList;

public class DetailsMeetingActivity extends AppCompatActivity {
    ArrayList<String> emails = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);
        MeetingApiService apiService = DI.getMeetingApiService();

        // *** Bind View *** //
        ImageView colorMeeting = findViewById(R.id.details_meeting_color);
        TextView textViewName = findViewById(R.id.details_meeting_name);
        TextView textViewMail = findViewById(R.id.details_meeting_mail);
        TextView textViewDate = findViewById(R.id.details_meeting_date);
        TextView textViewRoom = findViewById(R.id.details_meeting_room);
        TextView textViewSubject = findViewById(R.id.details_meeting_subject);
        TextView textViewDuration = findViewById(R.id.details_meeting_duration);


        // *** Bind datas to views *** //
        long id = getIntent().getLongExtra("ID", -1);
        Meeting meeting = apiService.getMeeting(id);
        textViewName.setText(meeting.getName());
        colorMeeting.setImageTintList(ColorStateList.valueOf(meeting.getLocation().getColor()));
        textViewDate.setText(String.valueOf(meeting.getHours()));
        textViewRoom.setText(meeting.getLocation().getName());
        textViewSubject.setText(meeting.getSubject());

        for(User i : meeting.getUsers()){
            emails.add(i.getEmail());
        }
    }


}
