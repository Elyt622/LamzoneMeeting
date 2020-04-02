package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.User;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    MeetingApiService mMeetingApiService;
    ArrayList<String> participants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        mMeetingApiService = DI.getMeetingApiService();
        setupActionBar();

        List<String> list = new ArrayList<>();
        for(int i = 0; i < mMeetingApiService.getMeetingRooms().size(); i++){
            list.add(mMeetingApiService.getMeetingRooms().get(i).getName());
        }
        Spinner mMeetingRoomSpinner = findViewById(R.id.spinner_meeting_room);
        ArrayAdapter<String> adapter = new ArrayAdapter<> (this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMeetingRoomSpinner.setAdapter(adapter);

        Button add_email_user = findViewById(R.id.button_add_user);
        final EditText editText_add_email = findViewById(R.id.edit_text_email_user_meeting);
        final TextView listUsers = findViewById(R.id.text_view_list_users);
        add_email_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String participant = editText_add_email.getText().toString();
                participants.add(participant);
                listUsers.setText(participants.get(0));
                editText_add_email.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
