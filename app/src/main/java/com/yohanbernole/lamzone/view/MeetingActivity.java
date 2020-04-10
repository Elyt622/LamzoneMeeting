package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    MeetingApiService mApiService = DI.getMeetingApiService();
    FilterByRoomDialog filterByRoom = new FilterByRoomDialog();
    FilterByDateDialog filterByDate = new FilterByDateDialog();
    List<Meeting> meetings = new ArrayList<>();
    RecyclerView rv;
    int filterActive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        rv = findViewById(R.id.container);
        rv.setLayoutManager(new LinearLayoutManager(this));
        initFilter(0);
        FloatingActionButton mAddMeetingButton;
        mAddMeetingButton = findViewById(R.id.meeting_activity_add_meeting_button);

        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter_by_date:
                filterActive = 1;
                filterByDate.show(getSupportFragmentManager(), "Filter by Date");
                break;
            case R.id.filter_by_room:
                filterActive = 2;
                filterByRoom.show(getSupportFragmentManager(), "Filter by Room");
                break;
            case R.id.reinitialize_filter:
                filterActive = 0;
                initFilter(filterActive);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFilter(int filterActive) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meeting_filter, menu);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        meetings = mApiService.filterMeetingByDate(year, month, dayOfMonth);
        filterActive = 1;
        initFilter(filterActive);
    }
}
