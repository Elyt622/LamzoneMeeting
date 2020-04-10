package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.MeetingRoom;
import com.yohanbernole.lamzone.model.User;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    MeetingApiService mMeetingApiService;
    MultiAutoCompleteTextView addUsersWithCompletion;
    MeetingRoom meetingRoomChoose;
    Date dateChoose = null;
    ArrayList<User> listParticipant = new ArrayList<>();
    long dateInMillis = 0;
    int year1 = 0, month1, dayOfMonth1, hourOfDay1 = 0, minute1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        mMeetingApiService = DI.getMeetingApiService();
        setupActionBar();

        // *** Bind View *** //
        Button addEmailUserButton = findViewById(R.id.button_add_user);
        addUsersWithCompletion = findViewById(R.id.edit_text_email_user_meeting);
        Spinner meetingRoomSpinner = findViewById(R.id.spinner_meeting_room);
        final DatePicker dateMeetingDatePicker = findViewById(R.id.date_picker_add_date);
        TimePicker timeMeetingTimePicker = findViewById(R.id.time_picker_add_date);
        final EditText subjectMeetingEditText = findViewById(R.id.edit_text_subject_meeting);
        Button addMeetingButton = findViewById(R.id.button_add_meeting);
        final EditText nameMeetingEditText = findViewById(R.id.edit_text_meeting_name);
        final RecyclerView rv = findViewById(R.id.recycler_view_user_participant);
        final EditText editTextMeetingDuration = findViewById(R.id.edit_text_meeting_duration);

        // *** Configure Date of Meeting *** //
        timeMeetingTimePicker.setIs24HourView(true);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateMeetingDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                year1 = year;
                month1 = month;
                dayOfMonth1 = dayOfMonth;
                if(hourOfDay1 != 0){
                    dateChoose = getDate(year1, month1, dayOfMonth1, hourOfDay1, minute1);
                }
            }
        });
        timeMeetingTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hourOfDay1 = hourOfDay;
                minute1 = minute;
                if(year1 != 0){
                    dateChoose = getDate(year1, month1, dayOfMonth1, hourOfDay1, minute1);
                }
            }
        });

        // *** Configure Spinner with Meeting rooms name *** //
        List<String> list = new ArrayList<>();
        for(MeetingRoom room : mMeetingApiService.getMeetingRooms()){
            list.add(room.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<> (this, R.layout.spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingRoomSpinner.setAdapter(adapter);
        meetingRoomSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                meetingRoomChoose = mMeetingApiService.getMeetingRooms().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // *** Configure MultiCompletion Edit Text *** //
        ArrayAdapter adapterUsers = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMeetingApiService.getAllEmails());
        addUsersWithCompletion.setAdapter(adapterUsers);
        addUsersWithCompletion.setThreshold(1);
        addUsersWithCompletion.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // *** Configure Button add user *** //
        addEmailUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUserToMeeting(listParticipant);
                UserRecyclerViewAdapter adapterRv = new UserRecyclerViewAdapter(listParticipant);
                rv.setAdapter(adapterRv);
                rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
                addUsersWithCompletion.setText("");
            }
        });

        // *** Configure Button add meeting *** //
        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameMeetingEditText.getText().toString().isEmpty() && meetingRoomChoose != null && dateChoose != null &&
                        !subjectMeetingEditText.getText().toString().isEmpty() && listParticipant.size() != 0 && !editTextMeetingDuration.getText().toString().isEmpty()) {
                    if(!checkDateIsValid(dateChoose, Integer.parseInt(editTextMeetingDuration.getText().toString()), meetingRoomChoose.getId()-1)){
                        Toast toast = Toast.makeText(v.getContext(), R.string.unavailable_room, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        mMeetingApiService.createMeeting(mMeetingApiService.getMeetings().size() + 1,
                                nameMeetingEditText.getText().toString(),
                                dateChoose,
                                meetingRoomChoose,
                                subjectMeetingEditText.getText().toString(),
                                listParticipant,
                                Integer.parseInt(editTextMeetingDuration.getText().toString()));
                        Toast toast = Toast.makeText(v.getContext(), R.string.add_meeting, Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    }
                }
                else{
                    Toast toast = Toast.makeText(v.getContext(), R.string.need_all_datas, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private Date getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, hourOfDay, minute);
        dateInMillis = calendar.getTimeInMillis();
        return new Date(dateInMillis);
    }

    private Date getEndDateMeeting(Date date, int duration){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, duration);
        return calendar.getTime();
    }

    private Boolean checkDateIsValid(Date date, int duration, long idRoom){
        Date endDate = getEndDateMeeting(date, duration);
        List<Meeting> meetings = mMeetingApiService.filterMeetingByRoomId(idRoom);
        for(Meeting i: meetings) {
            if((date.after(i.getHours()) && date.before(getEndDateMeeting(i.getHours(), i.getDuration()))) ||
                    (endDate.after(i.getHours()) &&  endDate.before(getEndDateMeeting(i.getHours(), i.getDuration()))) ||
                    (i.getHours().after(date) && i.getHours().before(endDate)) ||
                    (getEndDateMeeting(i.getHours(), i.getDuration()).after(date) && getEndDateMeeting(i.getHours(), i.getDuration()).before(endDate))){
                return false;
            }
        }
        return true;
    }

    private void submitUserToMeeting(ArrayList<User> users){
        for(String i : mMeetingApiService.getAllEmails()){
            if(addUsersWithCompletion.getText().toString().contains(i) && !users.contains(mMeetingApiService.getUser(i))){
                    users.add(mMeetingApiService.getUser(i));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.add_new_user:
                Intent intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_meeting, menu);
        return true;
    }
}
