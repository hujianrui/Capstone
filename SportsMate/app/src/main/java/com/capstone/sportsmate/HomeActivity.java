package com.capstone.sportsmate;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                new ListFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;

                case R.id.nav_home:
                    selectedFragment = new ListFragment();

                    break;

                case R.id.nav_pose:
                    selectedFragment = new PoseFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                    selectedFragment).commit();
            return true;
        }
    };

}
