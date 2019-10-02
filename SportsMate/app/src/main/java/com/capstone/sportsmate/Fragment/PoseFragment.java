package com.capstone.sportsmate.Fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.capstone.sportsmate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoseFragment extends Fragment {

    private Spinner srSkillLvl;
    private String sSkillLvl;
    private RadioGroup rgSpots;
    private RadioButton rbSpots;
    private TextView chooseTime;
    private TimePickerDialog timePickerDialog;

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


        // Time
        chooseTime = view.findViewById(R.id.tv_time);
        chooseTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        chooseTime.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);
                timePickerDialog.show();
            }
        });


        return view;
    }

}
