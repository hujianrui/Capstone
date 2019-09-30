package com.capstone.sportsmate.Fragment;


import android.content.Intent;
import android.net.Uri;
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
import com.capstone.sportsmate.Class.User;
import com.capstone.sportsmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCreateFragment extends Fragment {

    private EditText etZipCode, etName;
    private RadioGroup rgSex;
    private RadioButton rbSex;
    private Spinner srBadminton, srBasketball, srTennis;
    private Button btSubmit;
    private User userProfile = new User();
    private DatabaseReference database;
    private String sName, sGender, sZipCode, sBadminton, sBasketball, sTennis;

    public ProfileCreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile_create, container, false);
        etZipCode = view.findViewById(R.id.editText_zipCode);
        etName = view.findViewById(R.id.editText_name);

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

        // Submit Button
        btSubmit = view.findViewById(R.id.button_submit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = etName.getText().toString();
                sZipCode = etZipCode.getText().toString();
                sGender = rbSex.getText().toString();
                updateUserProfile();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void updateUserProfile(){

        if(!sName.matches("")){
            userProfile.setName(sName);
        }

        userProfile.setGender(sGender);

        if(!sZipCode.matches("")){
            userProfile.setZipCode(sZipCode);
        }

        userProfile.setBadminton(sBadminton);
        userProfile.setBasketball(sBasketball);
        userProfile.setTennis(sTennis);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userProfile.setUid(user.getUid());
        database.child(user.getUid()).setValue(userProfile);
    }

}
