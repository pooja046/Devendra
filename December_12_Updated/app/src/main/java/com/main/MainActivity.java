package com.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.DTO.CategoryDTO;
import com.GridPack.GamesActivity;
import com.GridPack.SearchNewActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.MyApplication;
import com.constants.Constants;
import com.example.dharamraz.R;
import com.fragment.FragmentDrawer;
import com.fragment.HomeFragment;
import com.util.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentDrawer.FragmentDrawerListener {
    public static CategoryDTO categoryDTO;
    private MenuItem menuItem;
    public static int notifyMenu_lo = 1;
    private SessionManager sessionManager;
    public static String vUrl = "";
    private ImageView facebook, twiter, gplus;
    private NavigationView navigationView;
    public static MainActivity mainActivity;
    private DrawerLayout drawerLayout;
    private FragmentDrawer drawerFragment;
    private String et_insertMail_str = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    // initialize all vies by ids...
    public void initialize() {
        mainActivity = this;
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
//                Intent intent3 = new Intent(MainActivity.this, EmailInsert.class);
//                startActivity(intent3);
                showDialogForEmailBox();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//        navigationView = (NavigationView) finFradViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        drawerLayout.setScrimColor(Color.parseColor("#7D7D7D"));
        displayView();
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        if (FragmentDrawer.nevigationNavDrawerItems.get(position).getPosition().trim().equals("Home")) {
            displayView();
        } else if (FragmentDrawer.nevigationNavDrawerItems.get(position).getPosition().trim().equals("About Us")) {
            Intent intent_about = new Intent(this, AboutUs.class);
            startActivity(intent_about);
        } else if (FragmentDrawer.nevigationNavDrawerItems.get(position).getPosition().trim().equals("Share App")) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else {
            MainActivity.categoryDTO = HomeFragment.categoryDTOs.get(Integer.parseInt(FragmentDrawer.nevigationNavDrawerItems.get(position).getPosition()));
            startActivity(new Intent(this, GamesActivity.class));
        }
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
        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Toast.makeText(mainActivity, "" + id, Toast.LENGTH_SHORT).show();
        if (id == R.id.nav_home) {
            Fragment fragment = null;
            fragment = new HomeFragment();
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//addToBackStack(null);
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
            }
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else if (id == R.id.nav_about_us) {
            Intent intent_about = new Intent(this, AboutUs.class);
            startActivity(intent_about);
        } else {
//            MainActivity.categoryDTO = categoryDTOs.get(item.getItemId());
//            startActivity(new Intent(this, GamesActivity.class));
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_country:
                startActivity(new Intent(MainActivity.this, Countries.class));
                break;
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this, SearchNewActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void showDialogForEmailBox() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        // set dialog message
        alertDialogBuilder
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                if (userInput.getText().toString().trim().length() == 0) {
                                    Toast.makeText(MainActivity.this, "Enter email id.", Toast.LENGTH_SHORT).show();
                                } else if (!isEmailValid(userInput.getText().toString().trim())) {
                                    Toast.makeText(MainActivity.this, "Enter a valid email id.", Toast.LENGTH_SHORT).show();
                                } else {
                                    et_insertMail_str = userInput.getText().toString().trim();
                                    dialog.dismiss();
                                    sendAnEmail();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void sendAnEmail() {
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.NEWS_LETTER),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String vMessage = "";
                            System.out.println("response..." + response.toString());
                            //  {"status":"Success","msg":"Subscription preferences updated"}
                            JSONObject jsonObject = new JSONObject(response);
                            vMessage = jsonObject.getString("status");
                            if (vMessage.trim().equals("Success")) {
                                Toast.makeText(MainActivity.this, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", et_insertMail_str);
                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }


    // logout action dialog for testing...
    private void logoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("You want to logout from App.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Logout Successful.", Toast.LENGTH_SHORT).show();
                        sessionManager.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            try {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return sb.toString().trim();
    }


}
