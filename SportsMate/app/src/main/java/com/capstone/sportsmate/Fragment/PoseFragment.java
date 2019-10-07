package com.capstone.sportsmate.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.capstone.sportsmate.Activity.HomeActivity;
import com.capstone.sportsmate.R;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoseFragment extends Fragment {

    private Spinner srSkillLvl;
    private String sSkillLvl;
    private RadioGroup rgSpots;
    private RadioButton rbSpots;
    private TextView chooseTime, chooseDate;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private Button btSubmit;

    public PoseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pose, container, false);

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
                sSkillLvl = parent.getItemAtPosition(position).toString();
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

        // Submit Button
        btSubmit = view.findViewById(R.id.button_submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO
                updateTicket();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateTicket(){



    }

}
