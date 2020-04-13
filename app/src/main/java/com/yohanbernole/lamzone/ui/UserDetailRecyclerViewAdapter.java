package com.yohanbernole.lamzone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserDetailRecyclerViewAdapter  extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>{

    private final List<User> mUsers;

    UserDetailRecyclerViewAdapter(List<User> items) {
        mUsers = items;
    }

    @NonNull
    @Override
    public UserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new UserRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserRecyclerViewAdapter.ViewHolder holder, final int position) {
        User user = mUsers.get(position);
        holder.textViewUser.setText(user.getEmail());
        holder.imageButtonRemove.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}