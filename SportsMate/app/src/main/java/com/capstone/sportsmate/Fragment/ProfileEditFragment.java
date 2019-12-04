package com.capstone.sportsmate.Fragment;


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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.capstone.sportsmate.Activity.HomeActivity;
import com.capstone.sportsmate.Class.User;
import com.capstone.sportsmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment {

    private DatabaseReference database;
    private User user;
    private String userId;
    private EditText etZipCode, etName;
    private Button btSubmit;
    private RadioGroup rgSex;
    private RadioButton rbSex;
    private Spinner srBadminton, srBasketball, srTennis;
    private String sName, sGender, sZipCode, sBadminton, sBasketball, sTennis;

    public ProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile_create, container, false);

        // Gender Radio Group
        rgSex = (RadioGroup) view.findViewById(R.id.rg_sex);
        rbSex = (RadioButton) view.findViewById(rgSex.getCheckedRadioButtonId());
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbSex = (RadioButton) view.findViewById(checkedId);
            }
        });

        // Badminton Spinner
        srBadminton = view.findViewById(R.id.spinner_badminton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.skill_level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srBadminton.setAdapter(adapter);
        srBadminton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sBadminton = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Basketball Spinner
        srBasketball = view.findViewById(R.id.spinner_Basketball);
        srBasketball.setAdapter(adapter);
        srBasketball.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sBasketball = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Tennis Spinner
        srTennis = view.findViewById(R.id.spinner_tennis);
        srTennis.setAdapter(adapter);
        srTennis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTennis = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user = dataSnapshot.child(userId).getValue(User.class);
                displayInfo(user, view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.child("User").addListenerForSingleValueEvent(mListener);

        // Submit Button
        btSubmit = view.findViewById(R.id.button_submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void displayInfo(User user, final View view){
        etZipCode = view.findViewById(R.id.editText_zipCode);
        etName = view.findViewById(R.id.editText_name);
        etZipCode.setText(user.getZipCode());
        etName.setText(user.getName());

        if(user.getGender().equals("Male")){
            RadioButton b = (RadioButton) view.findViewById(R.id.male);
            b.setChecked(true);
        }else{
            RadioButton b = (RadioButton) view.findViewById(R.id.female);
            b.setChecked(true);
        }

        displaySpinner(srBadminton, user.getBadminton());
        displaySpinner(srBasketball, user.getBasketball());
        displaySpinner(srTennis, user.getTennis());

    }

    private void displaySpinner(Spinner spinner, String skilllvl){
        if(skilllvl.equals("Beginner")){
            spinner.setSelection(0);
        }else if(skilllvl.equals("Intermediate")){
            spinner.setSelection(1);
        }else if(skilllvl.equals("Advanced")){
            spinner.setSelection(2);
        }else{
            spinner.setSelection(3);
        }
    }

    private void updateInfo(){
        sName = etName.getText().toString();
        sZipCode = etZipCode.getText().toString();
        sGender = rbSex.getText().toString();

        user.setName(sName);
        user.setZipCode(sZipCode);
        user.setGender(sGender);
        user.setBadminton(sBadminton);
        user.setBasketball(sBasketball);
        user.setTennis(sTennis);

        database.child("User").child(userId).setValue(user);
    }
}
