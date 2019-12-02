package com.capstone.sportsmate.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.capstone.sportsmate.Fragment.ListFragment;
import com.capstone.sportsmate.Fragment.ProfileFragment;
import com.capstone.sportsmate.R;
import com.capstone.sportsmate.Fragment.TicketFragment;
import com.google.firebase.auth.FirebaseAuth;

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                            selectedFragment).commit();
                    break;

                case R.id.nav_home:
                    selectedFragment = new ListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                            selectedFragment).commit();
                    break;

                case R.id.nav_ticket:
                    selectedFragment = new TicketFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                            selectedFragment).commit();
                    break;

                case R.id.nav_logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent messageIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(messageIntent);
                    break;
            }
            return true;
        }
    };

}
