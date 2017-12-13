package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DTO.CategoryDTO;
import com.DTO.GamesDTO;
import com.adapter.CategoryAdapter;
import com.adapter.GamesHomeAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.MyApplication;
import com.constants.Constants;
import com.example.dharamraz.R;
import com.util.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mohit on 8/12/2017.
 */

public class EmailInsert extends Activity {
    private EditText et_insertMail;
    private String et_insertMail_str;
    private Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nwes_letter);
        et_insertMail = (EditText) findViewById(R.id.et_insertEmail);
        btn_submit = (Button) findViewById(R.id.btn_submitEmail);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_insertMail.setError(null);
                et_insertMail_str = et_insertMail.getText().toString();
                if (!isEmailValid(et_insertMail.getText().toString())) {
                    et_insertMail.setError("Please Enter valid Email ID");
                } else {
                    sendAnEmail();
                }
            }
        });

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
                                Toast.makeText(EmailInsert.this, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EmailInsert.this, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
}