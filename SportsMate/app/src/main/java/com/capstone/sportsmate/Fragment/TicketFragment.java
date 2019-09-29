package com.capstone.sportsmate.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capstone.sportsmate.Activity.PoseActivity;
import com.capstone.sportsmate.Activity.ProfileCreateActivity;
import com.capstone.sportsmate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketFragment extends Fragment {

    private Button btPose;

    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ticket, container, false);


        btPose = view.findViewById(R.id.button_pose);
        btPose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PoseActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
