package com.capstone.sportsmate.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.capstone.sportsmate.Activity.HomeActivity;
import com.capstone.sportsmate.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCreateFragment extends Fragment {

    private EditText etZipCode;
    private RadioGroup rgSex;
    private RadioButton rbSex;
    private Spinner srBadminton, srBasketball, srTennis;
    private Button btSubmit;


    public ProfileCreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile_create, container, false);
        etZipCode = view.findViewById(R.id.editText_zipCode);

        rgSex = (RadioGroup) view.findViewById(R.id.rg_sex);
        rbSex = (RadioButton) view.findViewById(rgSex.getCheckedRadioButtonId());
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSex = (RadioButton) view.findViewById(checkedId);
            }
        });

        srBadminton = view.findViewById(R.id.spinner_badminton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.skill_level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srBadminton.setAdapter(adapter);
        srBadminton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        srBasketball = view.findViewById(R.id.spinner_Basketball);
        srBasketball.setAdapter(adapter);
        srBasketball.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        srTennis = view.findViewById(R.id.spinner_tennis);
        srTennis.setAdapter(adapter);
        srTennis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btSubmit = view.findViewById(R.id.button_submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
