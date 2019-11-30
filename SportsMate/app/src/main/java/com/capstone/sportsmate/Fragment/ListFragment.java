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
    private Button btPick;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        database = FirebaseDatabase.getInstance().getReference().child("Ticket");
        //userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //get user
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Ticket post = postSnapshot.getValue(Ticket.class);
                    tickets.add(post);
                }

                RecyclerView recyclerView = view.findViewById(R.id.rv_list);
                RecyclerAdapter adapter = new RecyclerAdapter(tickets);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.addValueEventListener(mListener);


        // Pick Button
        btPick = view.findViewById(R.id.button_pick);
        btPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Joined the Game", Toast.LENGTH_LONG).show();
                //updateTicket();
                RecyclerAdapter.resetSelectedView();
            }
        });



        return view;
    }

}
