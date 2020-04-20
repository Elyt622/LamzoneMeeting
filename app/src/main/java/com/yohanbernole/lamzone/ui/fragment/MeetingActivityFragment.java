package com.yohanbernole.lamzone.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;
import com.yohanbernole.lamzone.ui.AddMeetingActivity;
import com.yohanbernole.lamzone.ui.DetailsMeetingActivity;
import com.yohanbernole.lamzone.ui.event.LaunchActivityEvent;
import com.yohanbernole.lamzone.ui.adapter.MeetingRecyclerViewAdapter;
import com.yohanbernole.lamzone.ui.dialog.FilterByRoomDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MeetingActivityFragment extends Fragment{
    private RecyclerView rv;
    private MeetingApiService mApiService = DI.getMeetingApiService();
    private FilterByRoomDialog filterByRoom = new FilterByRoomDialog();
    private List<Meeting> meetings = new ArrayList<>();
    private int filterActive = 0;

    public MeetingActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_meeting_activity, container, false);
        rv = view.findViewById(R.id.container);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        initFilter(0);
        FloatingActionButton mAddMeetingButton;
        mAddMeetingButton = view.findViewById(R.id.meeting_activity_add_meeting_button);

        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddMeetingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        switch (item.getItemId()){
            case R.id.filter_by_date:
                filterActive = 1;
                filterByDateDialog();
                break;
            case R.id.filter_by_room:
                filterActive = 2;
                filterByRoom.show(fm, "Filter by Room");
                break;
            case R.id.reinitialize_filter:
                filterActive = 0;
                initFilter(filterActive);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFilter(int filterActive) {
        if(filterActive == 2) {
            meetings = mApiService.filterMeetingByRoomId(filterByRoom.selectedItems);
            MeetingRecyclerViewAdapter mAdapter2 = new MeetingRecyclerViewAdapter(meetings);
            rv.setAdapter(mAdapter2);
        }else if(filterActive == 0){
            MeetingRecyclerViewAdapter mAdapter = new MeetingRecyclerViewAdapter(mApiService.getMeetings());
            rv.setAdapter(mAdapter);
        }else if(filterActive == 1){
            MeetingRecyclerViewAdapter mAdapter1 = new MeetingRecyclerViewAdapter(meetings);
            rv.setAdapter(mAdapter1);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_meeting_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void filterByDateDialog() {
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                meetings = mApiService.filterMeetingByDate(year1, month1, dayOfMonth1);
                filterActive = 1;
                initFilter(filterActive);
            }}, year, month, day);
        datePickerDialog.show();
    }

    public void onEvent(LaunchActivityEvent event){
        assert getFragmentManager() != null;
        if(getFragmentManager().getFragments().size() == 2){
            if(!getFragmentManager().getFragments().get(1).isVisible()){
                Intent intent = new Intent(getActivity(), DetailsMeetingActivity.class);
                intent.putExtra("ID", event.getId());
                startActivity(intent);
            }
        }else{
            Intent intent = new Intent(getActivity(), DetailsMeetingActivity.class);
            intent.putExtra("ID", event.getId());
            startActivity(intent);
        }
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
}
