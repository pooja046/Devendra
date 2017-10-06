package com.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DTO.CategoryDTO;
import com.GridPack.GamesActivity;
import com.adapter.CategoryAdapter;
import com.example.dharamraz.R;
import com.fragment.HomeFragment;
import com.util.SessionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static CategoryDTO categoryDTO;
    private MenuItem menuItem;
    public static int notifyMenu_lo = 1;
    private SessionManager sessionManager;
    public static String vUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    // initialize all vies by ids...
    public void initialize() {
        sessionManager = new SessionManager(this);
        setToolBarAndNevigationDrawer();
    }

    // setup toolbar...
    public void setToolBarAndNevigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Toast toast = Toast.makeText(MainActivity.this, "Insert Your E-mail Address", Toast.LENGTH_SHORT);
                toast.show();
                new CountDownTimer(3000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        toast.show();
                    }

                    public void onFinish() {
                        toast.cancel();
                    }
                }.start();
                Intent intent3 = new Intent(MainActivity.this, EmailInsert.class);
                startActivity(intent3);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView();
    }

    // home fragment set...
    public void displayView() {
        Fragment fragment = null;
        fragment = new HomeFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//addToBackStack(null);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Fragment fragment = null;
            fragment = new HomeFragment();
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//addToBackStack(null);
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
            }
        } else if (id == R.id.nav_slideshow) {
            MainActivity.categoryDTO = null;
            startActivity(new Intent(this, GamesActivity.class));
        } else if (id == R.id.nav_signin) {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_about_us) {
            Intent intent_about = new Intent(this, AboutUs.class);
            startActivity(intent_about);
        } else if (id == R.id.nav_logout) {
            if (sessionManager.isLogin()) {
                logoutDialog();
            } else {
                Toast.makeText(this, "You are not login.", Toast.LENGTH_SHORT).show();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (notifyMenu_lo == 2) {
            notifyMenu_lo = 1;
            notifyMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.action_country);
        if (sessionManager.getCountryDetails().get(SessionManager.COUNTRY_CODE).trim().length() > 0) {
            menuItem.setTitle(sessionManager.getCountryDetails().get(SessionManager.COUNTRY_CODE).trim());
        }
        return true;
    }

    public void notifyMenu() {
        if (sessionManager.getCountryDetails().get(SessionManager.COUNTRY_CODE).trim().length() > 0) {
            menuItem.setTitle(sessionManager.getCountryDetails().get(SessionManager.COUNTRY_CODE).trim());
        }
        // invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_country:
                startActivity(new Intent(MainActivity.this, Countries.class));
                break;
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // logout action dialog for testing...
    private void logoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("You want to logout from App.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Logout Successful.", Toast.LENGTH_SHORT).show();
//                        Intent intent3 = new Intent(MainActivity.this, HomeActivity.class);
//                        startActivity(intent3);
                        sessionManager.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }
}
