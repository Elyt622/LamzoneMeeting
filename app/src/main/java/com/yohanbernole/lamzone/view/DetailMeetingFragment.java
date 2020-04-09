package com.yohanbernole.lamzone.view;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailMeetingFragment extends Fragment {

    private MeetingApiService apiService = DI.getMeetingApiService();
    public DetailMeetingFragment() {
        // Required empty public constructor
    }

    public static DetailMeetingFragment newInstance() {
        DetailMeetingFragment fragment = new DetailMeetingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long id = 1;
        // *** Bind View *** //
        ImageView colorMeeting = view.findViewById(R.id.details_meeting_color);
        TextView textViewName = view.findViewById(R.id.details_meeting_name);
        RecyclerView recyclerViewMail = view.findViewById(R.id.details_recycler_view_meeting_mail);
        TextView textViewDate = view.findViewById(R.id.details_meeting_date);
        TextView textViewRoom = view.findViewById(R.id.details_meeting_room);
        TextView textViewSubject = view.findViewById(R.id.details_meeting_subject);
        TextView textViewDuration = view.findViewById(R.id.details_meeting_duration);

        if (getArguments() != null) {
            id = getArguments().getLong("ID", 1);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // *** Bind datas to views *** //
        Meeting meeting = apiService.getMeeting(id);

        UserDetailRecyclerViewAdapter adapter = new UserDetailRecyclerViewAdapter(meeting.getUsers());
        recyclerViewMail.setAdapter(adapter);
        recyclerViewMail.setLayoutManager(new LinearLayoutManager(getContext()));

        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(meeting.getHours());
        textViewDate.setText(date);

        textViewName.setText(meeting.getName());
        colorMeeting.setImageTintList(ColorStateList.valueOf(meeting.getLocation().getColor()));
        textViewRoom.setText("Salle " + meeting.getLocation().getName());
        textViewSubject.setText(meeting.getSubject());
        textViewDuration.setText("Durée " + meeting.getDuration() + " minutes");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_details_meeting, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
