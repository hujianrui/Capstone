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
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketFragment extends Fragment {

    private Button btPose, btDrop;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private DatabaseReference database;
    private RecyclerAdapter adapter;
    private List<String> sTickets;
    private String userId, sTid;

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

        Calendar cal = Calendar.getInstance();
        final int curYear = cal.get(Calendar.YEAR);
        final int curMonth = cal.get(Calendar.MONTH);
        final int curDay = cal.get(Calendar.DAY_OF_MONTH);
        int curHour = cal.get(Calendar.HOUR_OF_DAY);
        int curMinute = cal.get(Calendar.MINUTE);
        final String sCurTime = curHour + ":" + curMinute;

        //get user
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user  = dataSnapshot.child("User").child(userId).getValue(User.class);
                sTickets = user.getTicketID();
                for(int i = 0; i < sTickets.size(); i++){
                    String sTicket = sTickets.get(i);
                    Ticket ticket = dataSnapshot.child("Ticket").child(sTicket).getValue(Ticket.class);
                    String ticketDate = ticket.getDate();
                    String ticketTime = getYear(ticketDate) + buildTime(getMonth(ticketDate),getDay(ticketDate)) + ticket.getTime();
                    String curTime = curYear + buildTime(curMonth,curDay) + sCurTime;
                    if(ticketTime.compareTo(curTime) > 0){
                        tickets.add(ticket);
                    }
                }

                RecyclerView recyclerView = view.findViewById(R.id.rv_ticket);
                adapter = new RecyclerAdapter(tickets);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.addListenerForSingleValueEvent(mListener);

        btPose = view.findViewById(R.id.button_pose);
        btPose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PoseActivity.class);
                startActivity(intent);
            }
        });

        btDrop = view.findViewById(R.id.button_drop);
        btDrop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sTid = adapter.getSelectedTid();
                if(sTid != null){
                    updateUser();
                    updateTicket();
                    adapter.resetSelectedView();
                    for(int i = 0; i < tickets.size(); i++){
                        if(tickets.get(i).getTid().equals(sTid)){
                            tickets.remove(i);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Drop the Game", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(v.getContext(), "Please Select a Ticket", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private int getYear(String sDate){
        String[] ary = sDate.split(", ");
        return Integer.parseInt(ary[2]);
    }

    private int getMonth(String sDate){
        String[] ary = sDate.split(", ");
        String[] date = ary[1].split(" ");
        String[] months = new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        for(int i = 0; i < months.length; i++){
            if(date[0].equals(months[i])){
                return i;
            }
        }
        return -1;
    }

    private int getDay(String sDate){
        String[] ary = sDate.split(", ");
        String[] date = ary[1].split(" ");
        return Integer.parseInt(date[1]);
    }

    private String buildTime(int month, int day){
        String res = "";
        if(month < 10){
            res += '0';
        }
        res += month;
        if(day < 10){
            res += '0';
        }
        res += day;
        return res;
    }

    private void updateUser(){
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(userId).getValue(User.class);
                user.removeTicket(sTid);
                database.child("User").child(userId).setValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("User").addListenerForSingleValueEvent(mListener);
    }

    private void updateTicket(){
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Ticket ticket = dataSnapshot.child(sTid).getValue(Ticket.class);
                ticket.removeUser(userId);
                database.child("Ticket").child(sTid).setValue(ticket);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("Ticket").addListenerForSingleValueEvent(mListener);
    }

}
