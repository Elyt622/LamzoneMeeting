package com.yohanbernole.lamzone.ui.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;
import com.yohanbernole.lamzone.ui.adapter.UserDetailRecyclerViewAdapter;
import com.yohanbernole.lamzone.ui.event.RefreshFragmentEvent;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DetailMeetingFragment extends Fragment {
    private ImageView colorMeeting;
    private TextView textViewName, textViewDate, textViewRoom, textViewSubject, textViewDuration;
    private RecyclerView recyclerViewMail;
    private MeetingApiService apiService = DI.getMeetingApiService();

    public DetailMeetingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_meeting, container, false);

        // *** Bind View *** //
        colorMeeting = view.findViewById(R.id.details_meeting_color);
        textViewName = view.findViewById(R.id.details_meeting_name);
        recyclerViewMail = view.findViewById(R.id.details_recycler_view_meeting_mail);
        textViewDate = view.findViewById(R.id.details_meeting_date);
        textViewRoom = view.findViewById(R.id.details_meeting_room);
        textViewSubject = view.findViewById(R.id.details_meeting_subject);
        textViewDuration = view.findViewById(R.id.details_meeting_duration);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if(Objects.requireNonNull(getActivity()).getIntent() != null) {
            long id = getActivity().getIntent().getLongExtra("ID", 1);
            initDetails(id);
        }

        return view;
    }

    public void onEvent(RefreshFragmentEvent event){
        initDetails(event.getId());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initDetails(long id) {
        // *** Bind data to views *** //
        Meeting meeting = apiService.getMeeting(id);

        UserDetailRecyclerViewAdapter adapter = new UserDetailRecyclerViewAdapter(meeting.getUsers());
        recyclerViewMail.setAdapter(adapter);
        recyclerViewMail.setLayoutManager(new LinearLayoutManager(getContext()));

        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).format(meeting.getDate());
        textViewDate.setText(date);

        String nameRoom = getString(R.string.room) + meeting.getLocation().getName();
        String duration = getString(R.string.duration) + meeting.getDuration() + getString(R.string.minute);

        textViewName.setText(meeting.getName());
        colorMeeting.setImageTintList(ColorStateList.valueOf(meeting.getLocation().getColor()));
        textViewRoom.setText(nameRoom);
        textViewSubject.setText(meeting.getSubject());
        textViewDuration.setText(duration);
    }
}
