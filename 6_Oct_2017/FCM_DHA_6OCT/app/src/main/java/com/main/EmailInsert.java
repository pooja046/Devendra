package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dharamraz.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mohit on 8/12/2017.
 */

public class EmailInsert extends Activity {
    EditText et_insertMail;
    String et_insertMail_str;
    Button btn_submit;
  //  private MainDTO mainDTO;

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
                    //  new NewsLetterEmailAsynkTask().execute("http://www.dharamraz.com/api/api.php?module=newsletter");

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
//    class NewsLetterEmailAsynkTask extends AsyncTask<String, Void, JSONObject> {
//        ProgressDialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(EmailInsert.this);
//            dialog.setMessage("Please Wait...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... args) {
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("email", et_insertMail_str));
//
//            JSONObject json = new JsonParser().makeHttpRequest2(args[0],
//                    "POST", params);
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            super.onPostExecute(jsonObject);
//            String vStatus = "", vMessage = "not created.";
//            dialog.dismiss();
//            try {
//                //  JSONObject customer_object = null;
//                try {
//                    vStatus = jsonObject.getString("status");
//                    vMessage = jsonObject.getString("msg");
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//                if (vStatus.trim().equals("Success")) {
//
//
//                    Toast.makeText(EmailInsert.this, vMessage,
//                            Toast.LENGTH_SHORT).show();
//                    mainDTO = new MainDTO();
//                    mainDTO.setEmail(et_insertMail_str);
//                    //sessionManager.setUserDetail(mainDTO);
//
//                } else {
//                    Toast.makeText(EmailInsert.this, vMessage, Toast.LENGTH_SHORT)
//                            .show();
//
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//        }
//    }
}