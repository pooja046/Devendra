package com.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
    private ArrayList<HashMap<String, String>> countryHashMaps;
    private HashMap<String, String> countryHashMap;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contries_listview);
        initialize();
    }

    public void initialize() {
        countryHashMaps = new ArrayList<>();
        sessionManager = new SessionManager(this);
        countryListView = (ListView) findViewById(R.id.countries_lv);
        countryListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                        //Toast.makeText(Countries.this, "Selected Country ID: " + countryHashMaps.get(position).get("id") + "\n and \n" + "Country Name: " + countryHashMaps.get(position).get("name"), Toast.LENGTH_SHORT).show();
                        MainActivity.notifyMenu_lo = 2;
                        sessionManager.setCountryDetails(countryHashMaps.get(position).get("name"), countryHashMaps.get(position).get("id"));
                        finish();
                    }
                }
        );
        getAllCountryName();
    }

    public void getAllCountryName() {
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.COUNTRY_LIST),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response..." + response.toString());
                            countryHashMaps = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray countryJSONArray = jsonObject.getJSONArray("country");
                                for (int i = 0; i < countryJSONArray.length(); i++) {
                                    countryHashMap = new HashMap<>();
                                    JSONObject countryObject = countryJSONArray.getJSONObject(i);
                                    countryHashMap.put("id", countryObject.getString("id"));
                                    countryHashMap.put("name", countryObject.getString("name"));
                                    countryHashMap.put("iso_code", countryObject.getString("iso_code"));
                                    countryHashMaps.add(countryHashMap);
                                }
                            } catch (Exception e) {

                            }
                            ListAdapter adapter = new SimpleAdapter(Countries.this, countryHashMaps,
                                    R.layout.list_item, new String[]{"id", "name", "iso_code"},
                                    new int[]{R.id.id, R.id.name, R.id.iso_code});
                            countryListView.setAdapter(adapter);
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
