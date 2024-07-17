package com.example.vsgatestmobileapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vsgatestmobileapp1.databinding.ActivityMainBinding;
import com.example.vsgatestmobileapp1.ui.dailynotes.NoteEditorActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("is_logged_in", false);

        if (!isLoggedIn) {
            // Redirect to login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to NoteEditorActivity to create a new note
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calculator, R.id.nav_daily_notes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set user email and username in navigation header
        View headerView = navigationView.getHeaderView(0);
        TextView textViewUsername = headerView.findViewById(R.id.textViewUsername);
        TextView textViewUserEmail = headerView.findViewById(R.id.textViewUserEmail);
        String userEmail = preferences.getString("user_email", "User Email");
        String username = preferences.getString("user_username", "Username");
        textViewUserEmail.setText(userEmail);
        textViewUsername.setText(username);

        // Handle navigation item selection
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_sign_out) {
                    signOut();
                    return true;
                }
                return NavigationUI.onNavDestinationSelected(item, navController)
                        || MainActivity.super.onOptionsItemSelected(item);
            }
        });
    }

    private void signOut() {
        // Clear the login state
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_logged_in", false);
        editor.putString("user_email", null); // Clear the stored email
        editor.putString("user_username", null); // Clear the stored username
        editor.apply();

        // Redirect to login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
