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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {

    MeetingApiService mMeetingApiService;
    MultiAutoCompleteTextView addUsersWithCompletion;
    MeetingRoom meetingRoomChoose;
    String dateChoose, timeChoose;
    Date date;
    ArrayList<User> listParticipant = new ArrayList<>();

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
        DatePicker dateMeetingDatePicker = findViewById(R.id.date_picker_add_date);
        TimePicker timeMeetingTimePicker = findViewById(R.id.time_picker_add_date);
        final EditText subjectMeetingEditText = findViewById(R.id.edit_text_subject_meeting);
        Button addMeetingButton = findViewById(R.id.button_add_meeting);
        final EditText nameMeetingEditText = findViewById(R.id.edit_text_meeting_name);
        final RecyclerView rv = findViewById(R.id.recycler_view_user_participant);
        final EditText editTextMeetingDuration = findViewById(R.id.edit_text_meeting_duration);

        // *** Configure Date of Meeting *** //
        timeMeetingTimePicker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateMeetingDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                dateChoose = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        timeMeetingTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeChoose = hourOfDay + ":" + minute;
                try {
                    date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE).parse(dateChoose + " " + timeChoose);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // *** Configure Spinner with Meeting rooms name *** //
        List<String> list = new ArrayList<>();
        for(int i = 0; i < mMeetingApiService.getMeetingRooms().size(); i++){
            list.add(mMeetingApiService.getMeetingRooms().get(i).getName());
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
                if(!nameMeetingEditText.getText().toString().isEmpty() && date != null && meetingRoomChoose != null &&
                        !subjectMeetingEditText.getText().toString().isEmpty() && listParticipant.size() != 0 && !editTextMeetingDuration.getText().toString().isEmpty()) {
                    if(!checkDateIsValid(date, Integer.parseInt(editTextMeetingDuration.getText().toString()), meetingRoomChoose.getId()-1)){
                        Toast toast = Toast.makeText(v.getContext(), "La salle est indisponible pour ces horaires", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        mMeetingApiService.createMeeting(mMeetingApiService.getMeetings().size() + 1,
                                nameMeetingEditText.getText().toString(),
                                date,
                                meetingRoomChoose,
                                subjectMeetingEditText.getText().toString(),
                                listParticipant,
                                Integer.parseInt(editTextMeetingDuration.getText().toString()));
                        Toast toast = Toast.makeText(v.getContext(), "Réunion ajoutée", Toast.LENGTH_LONG);
                        toast.show();
                        finish();
                    }
                }
                else{
                    Toast toast = Toast.makeText(v.getContext(), "Veuillez saisir toutes les informations.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
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
