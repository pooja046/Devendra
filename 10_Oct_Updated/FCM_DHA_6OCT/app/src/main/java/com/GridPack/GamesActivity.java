package com.GridPack;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.main.MainActivity;
import com.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamesActivity extends AppCompatActivity {
    private String vCat_Name = "", vCountry_Id = "";
    private Toolbar toolbar;
    private ProgressBar pleas_wait_view;
    private TextView no_record;
    private GridView game_grid_list;
    private ArrayList<GamesDTO> gamesDTOs;
    private GamesDTO gamesDTO;
    private LinkDTO linkDTO;
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
            vCat_Name = MainActivity.categoryDTO.getCat_name();
            toolbar.setTitle(toTitleCase(vCat_Name.replace("_", " ")));
        } catch (Exception e) {
            toolbar.setTitle("All Games");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpAdapter();
        getAllGamesByCategoryIdOrCountryIdsVolley();
    }

    private void setUpAdapter() {
        gamesDTOs = new ArrayList<>();
        gamesAdapter = new GamesAdapter(this, gamesDTOs);
        game_grid_list.setAdapter(gamesAdapter);
        game_grid_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.vUrl = gamesDTOs.get(i).getLinkDTO().getUrl();
                startActivity(new Intent(GamesActivity.this, UrlActivity.class));
            }
        });
    }

    public void getAllGamesByCategoryIdOrCountryIdsVolley() {
        pleas_wait_view.setVisibility(View.VISIBLE);
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.GAMES_BY_CAT_ID_OR_COUNTRY_ID),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response..." + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray gamesJSONArray = jsonObject.getJSONArray("games");
                                for (int i = 0; i < gamesJSONArray.length(); i++) {
                                    JSONObject gamesJSONObject = gamesJSONArray.getJSONObject(i);
                                    gamesDTO = new GamesDTO();
                                    gamesDTO.setId(gamesJSONObject.getString("id"));
                                    gamesDTO.setName(gamesJSONObject.getString("name"));
                                    gamesDTO.setImage(gamesJSONObject.getString("image"));
                                    gamesDTO.setCategory(gamesJSONObject.getString("category"));
                                    gamesDTO.setDesc(gamesJSONObject.getString("desc"));
                                    gamesDTO.setFeatured(gamesJSONObject.getString("featured"));
                                    linkDTO = new LinkDTO();
                                    try {
                                        JSONObject linkJSONObject = gamesJSONObject.getJSONObject("link");
                                        linkDTO.setUrl(linkJSONObject.getString("url"));
                                    } catch (Exception e) {

                                    }
                                    try {
                                        linkDTO.setUrl(gamesJSONObject.getString("url"));
                                    } catch (Exception e) {

                                    }
                                    gamesDTO.setLinkDTO(linkDTO);
                                    gamesDTOs.add(gamesDTO);
                                }
                                gamesAdapter.notifyDataSetChanged();
                                pleas_wait_view.setVisibility(View.GONE);
                                game_grid_list.setVisibility(View.VISIBLE);
                            } catch (Exception e) {

                            }
                        } catch (Exception e) {
                            Toast.makeText(GamesActivity.this, "Server Communication failure.", Toast.LENGTH_SHORT).show();
                        }
                        pleas_wait_view.setVisibility(View.GONE);
                        if (gamesDTOs.size() > 0) {
                            game_grid_list.setVisibility(View.VISIBLE);
                            no_record.setVisibility(View.GONE);
                        } else {
                            no_record.setVisibility(View.VISIBLE);
                            game_grid_list.setVisibility(View.GONE);
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
                params.put("category", vCat_Name);
                vCountry_Id = sessionManager.getCountryDetails().get(SessionManager.COUNTRY_ID);
                params.put("country_id", vCountry_Id);
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
