package com.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.DTO.CountryDTO;
import com.adapter.CountryAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.MyApplication;
import com.constants.Constants;
import com.example.dharamraz.R;
import com.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Countries extends AppCompatActivity {
    private ListView countryListView;
    private SessionManager sessionManager;
    private ArrayList<CountryDTO> countryDTOs;
    private CountryDTO countryDTO;
    private CountryAdapter countryAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contries_listview);
        initialize();
    }

    public void initialize() {
        sessionManager = new SessionManager(this);
        searchView = (SearchView) findViewById(R.id.search);
        countryListView = (ListView) findViewById(R.id.countries_lv);
        setupCountryView();
        countryListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                        //Toast.makeText(Countries.this, "Selected Country ID: " + countryAdapter.getItem(position).getId() + "\n and \n" + "Country Name: " + countryAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                        MainActivity.notifyMenu_lo = 2;
                        sessionManager.setCountryDetails(countryAdapter.getItem(position).getIso_code(), countryAdapter.getItem(position).getId());
                        finish();
                    }
                }
        );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        getAllCountryName();
    }


    private void setupCountryView() {
        countryDTOs = new ArrayList<>();
        countryAdapter = new CountryAdapter(this, countryDTOs);
        countryListView.setAdapter(countryAdapter);
    }

    public void getAllCountryName() {
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.COUNTRY_LIST),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response..." + response.toString());
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray countryJSONArray = jsonObject.getJSONArray("country");
                                for (int i = 0; i < countryJSONArray.length(); i++) {
                                    countryDTO = new CountryDTO();
                                    JSONObject countryObject = countryJSONArray.getJSONObject(i);
                                    countryDTO.setId(countryObject.getString("id"));
                                    countryDTO.setName(countryObject.getString("name"));
                                    countryDTO.setIso_code(countryObject.getString("iso_code"));
                                    countryDTOs.add(countryDTO);
                                }
                            } catch (Exception e) {

                            }
                            countryAdapter.notifyDataSetChanged();
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
                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }
}
