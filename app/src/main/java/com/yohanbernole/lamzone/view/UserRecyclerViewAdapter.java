package com.yohanbernole.lamzone.view;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.model.Meeting;
import com.yohanbernole.lamzone.model.User;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>{
    private final List<User> mUsers;
    private MeetingApiService mApiService;

    UserRecyclerViewAdapter(List<User> items) {
        mUsers = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mApiService = DI.getMeetingApiService();
        final User user = mUsers.get(position);
        holder.textViewUser.setText(user.getEmail());

        holder.imageButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsers.remove(user);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       TextView textViewUser;
       ImageButton imageButtonRemove;
        ViewHolder(View view) {
            super(view);
            textViewUser = view.findViewById(R.id.text_view_email_fragment_user);
            imageButtonRemove = view.findViewById(R.id.image_button_remove_fragment_user);
        }
    }
}
