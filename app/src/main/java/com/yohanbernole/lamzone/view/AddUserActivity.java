package com.yohanbernole.lamzone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {

    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mApiService = DI.getMeetingApiService();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final EditText editTextUsername = findViewById(R.id.edit_text_username);
        final EditText editTextEmailUser = findViewById(R.id.edit_text_user_email);
        Button buttonAddUser = findViewById(R.id.button_submit_new_user);
        final TextView textViewOtherMail = findViewById(R.id.text_view_need_other_email);

        textViewOtherMail.setVisibility(View.INVISIBLE);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mApiService.getEmails().contains(editTextEmailUser.getText().toString()) && editTextEmailUser.getText().toString().contains("@")) {
                    mApiService.createUser(mApiService.getUsers().size()+1,
                            editTextUsername.getText().toString(),
                            editTextEmailUser.getText().toString());
                    Toast toast = Toast.makeText(v.getContext(), "Utilisateur ajouté",Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
                else{
                    if(mApiService.getEmails().contains(editTextEmailUser.getText().toString())) {
                        textViewOtherMail.setVisibility(View.VISIBLE);
                    }
                    else if(!editTextEmailUser.getText().toString().contains("@")){
                        textViewOtherMail.setText(R.string.mail_invalid);
                        textViewOtherMail.setVisibility(View.VISIBLE);
                    }
                }
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
}
