package com.GridPack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.DTO.GamesDTO;
import com.DTO.LinkDTO;
import com.adapter.GamesAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.MyApplication;
import com.constants.Constants;
import com.example.dharamraz.R;
import com.main.Countries;
import com.main.MainActivity;
import com.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchNewActivity extends AppCompatActivity {
    private String vSearch_text = "", vCountry_Id = "";
    private Toolbar toolbar;
    private ProgressBar pleas_wait_view;
    private TextView no_record;
    private GridView game_grid_list;
    private ArrayList<GamesDTO> gamesDTOs;
    private GamesDTO gamesDTO;
    private GamesAdapter gamesAdapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initialize();
    }

    public void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pleas_wait_view = (ProgressBar) findViewById(R.id.pleas_wait_view);
        no_record = (TextView) findViewById(R.id.no_record);
        game_grid_list = (GridView) findViewById(R.id.game_grid_list);
        sessionManager = new SessionManager(this);
        try {
            vSearch_text = "";
            toolbar.setTitle("Search Games");
        } catch (Exception e) {
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpAdapter();
        if (vSearch_text.toString().trim().length() > 0) {
            getAllGamesByCategoryIdOrCountryIdsVolley();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                vSearch_text = query.toString();
//                if (vSearch_text.trim().length() > 0) {
//                    getAllGamesByCategoryIdOrCountryIdsVolley();
//                } else {
//                    Toast.makeText(SearchNewActivity.this, "Enter search text.", Toast.LENGTH_SHORT).show();
//                }
                getAllGamesByCategoryIdOrCountryIdsVolley();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void setUpAdapter() {
        gamesDTOs = new ArrayList<>();
        gamesAdapter = new GamesAdapter(this, gamesDTOs);
        game_grid_list.setAdapter(gamesAdapter);
        game_grid_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.vUrl = gamesDTOs.get(i).getSearch_link();
                startActivity(new Intent(SearchNewActivity.this, UrlActivity.class));
            }
        });
    }

    public void getAllGamesByCategoryIdOrCountryIdsVolley() {
        pleas_wait_view.setVisibility(View.VISIBLE);
        vCountry_Id = sessionManager.getCountryDetails().get(SessionManager.COUNTRY_ID);
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.SEARCH_GAME) + "&search_game=" + vSearch_text + "&country_id=" + vCountry_Id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (gamesDTOs != null) {
                            gamesDTOs.clear();
                        }
                        try {
                            System.out.println("response..." + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray gamesJSONArray = jsonObject.getJSONArray("Search");
                                for (int i = 0; i < gamesJSONArray.length(); i++) {
                                    JSONObject gamesJSONObject = gamesJSONArray.getJSONObject(i);
                                    gamesDTO = new GamesDTO();
                                    gamesDTO.setName(gamesJSONObject.getString("name"));
                                    gamesDTO.setImage(gamesJSONObject.getString("image"));
                                    gamesDTO.setCategory(gamesJSONObject.getString("category"));
                                    gamesDTO.setDesc(gamesJSONObject.getString("desc"));
                                    gamesDTO.setFeatured(gamesJSONObject.getString("featured"));
                                    gamesDTO.setSearch_link(gamesJSONObject.getString("link"));
                                    gamesDTOs.add(gamesDTO);
                                }
                            } catch (Exception e) {

                            }
                        } catch (Exception e) {
                            Toast.makeText(SearchNewActivity.this, "Server Communication failure.", Toast.LENGTH_SHORT).show();
                        }
                        pleas_wait_view.setVisibility(View.GONE);
                        if (gamesDTOs.size() > 0) {
                            game_grid_list.setVisibility(View.VISIBLE);
                            no_record.setVisibility(View.GONE);
                        } else {
                            no_record.setVisibility(View.VISIBLE);
                            game_grid_list.setVisibility(View.GONE);
                        }
                        try {
                            gamesAdapter.notifyDataSetChanged();
                            vSearch_text = "";
                        } catch (Exception e) {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pleas_wait_view.setVisibility(View.GONE);
                        no_record.setVisibility(View.VISIBLE);
                        game_grid_list.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
