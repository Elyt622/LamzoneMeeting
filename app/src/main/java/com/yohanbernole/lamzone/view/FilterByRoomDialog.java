package com.yohanbernole.lamzone.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.yohanbernole.lamzone.R;
import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class FilterByRoomDialog extends DialogFragment {
    ArrayList<Integer> selectedItems = new ArrayList<>();
    private MeetingApiService apiService = DI.getMeetingApiService();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedItems.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Filtrer par salle").setMultiChoiceItems(R.array.rooms, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    selectedItems.add(which);
                }
            }
        })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        RecyclerView rv = Objects.requireNonNull(getActivity()).findViewById(R.id.container);
                        MeetingRecyclerViewAdapter mAdapter2 = new MeetingRecyclerViewAdapter(apiService.filterMeetingByRoomId(selectedItems));
                        rv.setAdapter(mAdapter2);
                        mAdapter2.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}