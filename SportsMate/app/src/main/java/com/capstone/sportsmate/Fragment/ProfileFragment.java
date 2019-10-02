package com.capstone.sportsmate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.capstone.sportsmate.Activity.ProfileCreateActivity;
import com.capstone.sportsmate.Class.User;
import com.capstone.sportsmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button btEdit;
    private TextView tvName, tvGender, tvZipCode, tvBadmination, tvBasketball, tvTennis;
    private String userId;

    private DatabaseReference database;
    private FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvGender = (TextView) view.findViewById(R.id.tv_gender);
        tvZipCode = (TextView) view.findViewById(R.id.tv_zipCode);
        tvBadmination = (TextView) view.findViewById(R.id.tv_badminton);
        tvBasketball = (TextView) view.findViewById(R.id.tv_basketball);
        tvTennis = (TextView) view.findViewById(R.id.tv_tennis);

        database = FirebaseDatabase.getInstance().getReference().child("User");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        ValueEventListener mListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvName.setText(dataSnapshot.child(userId).getValue(User.class).getName());
                tvGender.setText(dataSnapshot.child(userId).getValue(User.class).getGender());
                tvZipCode.setText(dataSnapshot.child(userId).getValue(User.class).getZipCode());
                tvBadmination.setText(dataSnapshot.child(userId).getValue(User.class).getBadminton());
                tvBasketball.setText(dataSnapshot.child(userId).getValue(User.class).getBasketball());
                tvTennis.setText(dataSnapshot.child(userId).getValue(User.class).getTennis());
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        };
        database.addValueEventListener(mListener);

        btEdit = view.findViewById(R.id.button_edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileCreateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}
