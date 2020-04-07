package com.yohanbernole.lamzone.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
        final User user = mUsers.get(position);
        holder.textViewUser.setText(user.getEmail());
        holder.imageButtonRemove.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUser;
        ImageButton imageButtonRemove;
        ViewHolder(View view) {
            super(view);
            textViewUser = view.findViewById(R.id.text_view_email_fragment_user);
            imageButtonRemove = view.findViewById(R.id.image_button_remove_fragment_user);
        }
    }
}