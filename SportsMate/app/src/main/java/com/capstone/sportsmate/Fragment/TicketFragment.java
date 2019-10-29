package com.capstone.sportsmate.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.capstone.sportsmate.Activity.PoseActivity;
import com.capstone.sportsmate.Activity.ProfileCreateActivity;
import com.capstone.sportsmate.Class.Ticket;
import com.capstone.sportsmate.Class.User;
import com.capstone.sportsmate.R;
import com.capstone.sportsmate.RecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketFragment extends Fragment {

    private Button btPose;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    DatabaseReference database;
    private List<String> sTickets;
    private String userId;

    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        database = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //get user
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user  = dataSnapshot.child("User").child(userId).getValue(User.class);
                sTickets = user.getTicketID();
                for(int i = 0; i < sTickets.size(); i++){
                    String sTicket = sTickets.get(i);
                    Ticket ticket = dataSnapshot.child("Ticket").child(sTicket).getValue(Ticket.class);
                    tickets.add(ticket);
                }

                RecyclerView recyclerView = view.findViewById(R.id.rv_ticket);
                RecyclerAdapter adapter = new RecyclerAdapter(tickets);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.addValueEventListener(mListener);

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
