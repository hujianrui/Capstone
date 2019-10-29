package com.capstone.sportsmate.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.capstone.sportsmate.Activity.HomeActivity;
import com.capstone.sportsmate.Class.Ticket;
import com.capstone.sportsmate.Class.User;
import com.capstone.sportsmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoseFragment extends Fragment {

    private EditText etZipCode;
    private Spinner srSkillLvl;
    private RadioGroup rgSpots;
    private RadioButton rbSpots;
    private TextView chooseTime, chooseDate;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private Button btSubmit;
    private Ticket ticket = new Ticket();
    private String userId;
    private String sTid; //key
    private String sSports, sLevel, sZipCode, sDate, sTime;
    private DatabaseReference database;
    private List<String> tickets;

    public PoseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pose, container, false);
        etZipCode = view.findViewById(R.id.editText_zipCode);

        // Spots Radio Group
        rgSpots = (RadioGroup) view.findViewById(R.id.rg_sports);
        rbSpots = (RadioButton) view.findViewById(rgSpots.getCheckedRadioButtonId());
        rgSpots.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSpots = (RadioButton) view.findViewById(checkedId);
            }
        });

        // Skill level Spinner
        srSkillLvl = view.findViewById(R.id.spinner_skilllvl);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.skill_level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srSkillLvl.setAdapter(adapter);
        srSkillLvl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sLevel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Date
        chooseDate = view.findViewById(R.id.tv_date);
        chooseDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int mouth = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String sDate = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
                        chooseDate.setText(sDate);
                    }
                }, year, mouth, day);
                datePickerDialog.show();
            }
        });


        // Time
        chooseTime = view.findViewById(R.id.tv_time);
        chooseTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        chooseTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute,false);
                timePickerDialog.show();
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //get user
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tickets = dataSnapshot.child(userId).getValue(User.class).getTicketID();
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("User").addValueEventListener(mListener);

        // Submit Button
        btSubmit = view.findViewById(R.id.button_submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int mouth = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                int second = cal.get(Calendar.SECOND);
                int millisecond = cal.get(Calendar.MILLISECOND);

                sTid = "" + year + mouth + day + hour + minute + second + millisecond + userId;
                sSports = rbSpots.getText().toString();
                sZipCode = etZipCode.getText().toString();
                sDate = chooseDate.getText().toString();
                sTime  = chooseTime.getText().toString();
                updateTicket();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateTicket(){

        if(!sSports.matches("")){
            ticket.setSports(sSports);
        }

        if(!sLevel.matches("")){
            ticket.setLevel(sLevel);
        }

        if(!sZipCode.matches("")){
            ticket.setZipCode(sZipCode);
        }

        if(!sDate.matches("")){
            ticket.setDate(sDate);
        }

        if(!sTime.matches("")){
            ticket.setTime(sTime);
        }

        ticket.addUser(userId);

        tickets.add(sTid);
        database.child("User").child(userId).child("ticketID").setValue(tickets);

        ticket.setTid(sTid);
        database.child("Ticket").child(sTid).setValue(ticket);
    }

}
