package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DetailsMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);
        MeetingApiService apiService = DI.getMeetingApiService();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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

        String nameRoom = getString(R.string.room) + meeting.getLocation().getName();
        String nameDuration = getString(R.string.duration) + meeting.getDuration() + getString(R.string.minute);

        textViewName.setText(meeting.getName());
        colorMeeting.setImageTintList(ColorStateList.valueOf(meeting.getLocation().getColor()));
        textViewRoom.setText(nameRoom);
        textViewSubject.setText(meeting.getSubject());
        textViewDuration.setText(nameDuration);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
