//package com.main;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.d.t.o.CountriesGetterSetter;
//import com.dharamraz.util.ConnectionDetector;
//import com.example.dharamraz.R;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CountriesList extends AppCompatActivity {
//    ListView lv;
//    private ProgressDialog progressDialog;
//    private ConnectionDetector connectionDetector;
//    private ArrayList<CountriesGetterSetter> arrayListCountries;
//    private CountriesGetterSetter countriesGetterSetter;
//    // ArrayAdapter<String> adapter;
//   // String[] names={"ram","mohan","shyam"};
//
//    @Override
//    protected void onCreate( Bundle savedInstanceState)      {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.contries_listview);
//    ( this).getSupportActionBar().hide();
//        lv=(ListView) findViewById(R.id.countries_lv);
//        arrayListCountries = new ArrayList<>();
//        //sessionManager = new SessionManager();
//        lv.setAdapter(new CountryAdapter(this, arrayListCountries));
//
//        lv.setOnItemClickListener(
//                new AdapterView.OnItemClickListener()
//                {
//                    @Override
//                    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
//                        String selectedSweet = lv.getItemAtPosition(position).toString();
//
//
//                        TextView textView = (TextView) view.findViewById(R.id.tv_country_name1);
//                        String text = textView.getText().toString();
//                        Toast.makeText(getApplicationContext(), "Selected item: " + text + " - " + position, Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//        );
//
//        // adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
//        //lv.setAdapter(adapter);
//        new CountriesListAsynkTask().execute("http://www.dharamraz.com/api/api.php?");
//
//        /*if (!isInternet()) {
//            Toast.makeText(
//                    CountriesList.this,
//                    "No internet connection, please try with internet connection.",
//                    Toast.LENGTH_LONG).show();
//            return;
//        } else {
//            new CountriesListAsynkTask().execute("http://www.dharamraz.com/api/api.php?");
//        }*/
//
//
//    }
//    public boolean isInternet() {
//        connectionDetector = new ConnectionDetector(CountriesList.this);
//        // Check if Internet present
//          return connectionDetector.isConnectingToInternet();
//    }
//
//class CountriesListAsynkTask extends AsyncTask<String,Void,JSONObject> {
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        progressDialog = new ProgressDialog(CountriesList.this);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setTitle("Please wait");
//        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Please wait");
//        progressDialog.show();
//
//    }
//
//    @Override
//    protected JSONObject doInBackground(String... args) {
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("module", "country"));
//        //  params.add(new BasicNameValuePair("id",""));
//        System.out.println("params..." + params.toString());
//        JSONObject json = new com.dharamraz.util.JsonParser().makeHttpRequest2(args[0],
//                "POST", params);
//        return json;
//    }
//
//    @Override
//    protected void onPostExecute(JSONObject jsonObject) {
//        super.onPostExecute(jsonObject);
//        progressDialog.dismiss();
//        String vStatus = "";
//        try {
//            arrayListCountries = new ArrayList<>();
//
//
//            JSONArray json_array_obj = jsonObject.getJSONArray("country");
//            for (int i = 0; i < json_array_obj.length(); i++) {
//                JSONObject object = json_array_obj.getJSONObject(i);
//                countriesGetterSetter = new CountriesGetterSetter();
//               // countriesGetterSetter.setId(object.getString("id"));
//                countriesGetterSetter.setName(object.getString("name"));
//               // countriesGetterSetter.setIso_code(object.getString("iso_code"));
//                arrayListCountries.add(countriesGetterSetter);
//            }
//            //sessionManager.notificationCount(0);
//        } catch (JSONException e1) {
//            //e1.printStackTrace();
//        }
//
//
//    }}}