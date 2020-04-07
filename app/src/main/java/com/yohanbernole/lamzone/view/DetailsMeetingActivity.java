package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.User;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DetailsMeetingActivity extends AppCompatActivity {
    ArrayList<String> emails = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);
        MeetingApiService apiService = DI.getMeetingApiService();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // *** Bind View *** //
        ImageView colorMeeting = findViewById(R.id.details_meeting_color);
        TextView textViewName = findViewById(R.id.details_meeting_name);
        RecyclerView recyclerViewMail = findViewById(R.id.details_recycler_view_meeting_mail);
        TextView textViewDate = findViewById(R.id.details_meeting_date);
        TextView textViewRoom = findViewById(R.id.details_meeting_room);
        TextView textViewSubject = findViewById(R.id.details_meeting_subject);
        TextView textViewDuration = findViewById(R.id.details_meeting_duration);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());




        // *** Bind datas to views *** //
        long id = getIntent().getLongExtra("ID", -1);
        Meeting meeting = apiService.getMeeting(id);

        UserDetailRecyclerViewAdapter adapter = new UserDetailRecyclerViewAdapter(meeting.getUsers());
        recyclerViewMail.setAdapter(adapter);
        recyclerViewMail.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(meeting.getHours());
        textViewDate.setText(date);

        textViewName.setText(meeting.getName());
        colorMeeting.setImageTintList(ColorStateList.valueOf(meeting.getLocation().getColor()));
        textViewRoom.setText("Salle " + meeting.getLocation().getName());
        textViewSubject.setText(meeting.getSubject());
        textViewDuration.setText("Durée " + String .valueOf(meeting.getDuration()) + " minutes");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
