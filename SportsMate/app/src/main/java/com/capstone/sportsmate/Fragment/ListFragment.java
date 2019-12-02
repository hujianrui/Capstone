package com.capstone.sportsmate.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.capstone.sportsmate.Activity.HomeActivity;
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
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private DatabaseReference database;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private RecyclerAdapter adapter;
    private Button btPick;
    private String userId;
    private String sTid; //key
    private Ticket ticket;
    private User user;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        database = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //get user
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Ticket post = postSnapshot.getValue(Ticket.class);
                    tickets.add(post);
                }

                RecyclerView recyclerView = view.findViewById(R.id.rv_list);
                adapter = new RecyclerAdapter(tickets);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("Ticket").addValueEventListener(mListener);
        
        // Pick Button
        btPick = view.findViewById(R.id.button_pick);
        btPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTid = adapter.getSelectedTid();
                if(sTid != null){
                    updateUser();
                    updateTicket();
                    adapter.resetSelectedView();
                    Toast.makeText(v.getContext(), "Joined the Game", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(v.getContext(), "Please Select a Ticket", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void updateUser(){
        // get user
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(userId).getValue(User.class);
                user.addTicket(sTid);
                database.child("User").child(userId).setValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("User").addListenerForSingleValueEvent(mListener);
    }

    private void updateTicket(){
        //get ticket
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ticket = dataSnapshot.child(sTid).getValue(Ticket.class);
                ticket.addUser(userId);
                database.child("Ticket").child(sTid).setValue(ticket);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("Ticket").addListenerForSingleValueEvent(mListener);
    }

}
