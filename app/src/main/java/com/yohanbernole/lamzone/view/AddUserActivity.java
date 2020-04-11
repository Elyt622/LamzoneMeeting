package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {

    private MeetingApiService mApiService;
    EditText editTextUsername, editTextEmailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mApiService = DI.getMeetingApiService();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextEmailUser = findViewById(R.id.edit_text_user_email);
        Button buttonAddUser = findViewById(R.id.button_submit_new_user);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDataIsValid()) {
                    mApiService.createUser(mApiService.getUsers().size() + 1,
                            editTextUsername.getText().toString(),
                            editTextEmailUser.getText().toString());
                    Toast toast = Toast.makeText(v.getContext(), R.string.add_user, Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            }
        });
    }

    boolean checkDataIsValid() {
        //*** If email exist in the list email***//
        if(mApiService.getAllEmails().contains(editTextEmailUser.getText().toString())) {
            Toast toast = Toast.makeText(getBaseContext(), R.string.mail_exist, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        //*** If email or name are empty ***//
        else if (editTextUsername.getText().toString().isEmpty() || editTextUsername.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getBaseContext(), R.string.need_all_datas, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        //*** If email doesn't contain "@" and "." ***//
        else if(!editTextEmailUser.getText().toString().contains("@") || !editTextEmailUser.getText().toString().contains(".")) {
            Toast toast = Toast.makeText(getBaseContext(), R.string.invalid_mail, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
