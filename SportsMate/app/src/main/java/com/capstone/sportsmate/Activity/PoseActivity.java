package com.capstone.sportsmate.Activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.capstone.sportsmate.Fragment.PoseFragment;
import com.capstone.sportsmate.Fragment.ProfileCreateFragment;
import com.capstone.sportsmate.R;

public class PoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fragment selectedFragment = new PoseFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                selectedFragment).commit();
    }
}
