//package com.GridPack;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//
//import com.dharamraz.util.JsonParserr;
//import com.example.dharamraz.R;
//import com.d.t.o.HomeGetterSetter;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class ActivityGridExa extends AppCompatActivity {
//
//    private GridView gridView;
//    private ArrayList<HomeGetterSetter> list;
//    private ActivityGridExaAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.grid_view);
//        getSupportActionBar().hide();
//
//       /* Toolbar toolbar = ( Toolbar ) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//*/
//        /**
//         * Array List for Binding Data from JSON to this List
//         */
//        list = new ArrayList<>();
//        /**
//         * Binding that List to Adapter
//         */
//        adapter = new ActivityGridExaAdapter(this, list);
//
//        /**
//         * Getting List and Setting List Adapter
//         */
//        gridView = ( GridView ) findViewById(R.id.grid_view);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getName() + " => " + "", Snackbar.LENGTH_LONG).show();
//
//            }
//
//        });
//
//
//        new GetDataTask().execute();
//        /**
//         * Just to know onClick and Printing Hello Toast in Center.
//         */
//       /* Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Load JSON", Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();*/
//
//        /* FloatingActionButton fab = ( FloatingActionButton ) findViewById(R.id.fab);
//       fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(@NonNull View view) {
//
//                *//**
//         * Checking Internet Connection
//         *//*
//                if (InternetConnection.checkConnection(getApplicationContext())) {
//                    new GetDataTask().execute();
//                } else {
//                    Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });*/
//    }
//
//    /**
//     * Creating Get Data Task for Getting Data From Web
//     */
//    class GetDataTask extends AsyncTask<Void, Void, Void> {
//
//        ProgressDialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            /**
//             * Progress Dialog for User Interaction
//             */
//            dialog = new ProgressDialog(ActivityGridExa.this);
//            dialog.setTitle("Hey Wait Please...");
//            dialog.setMessage("I am getting your JSON");
//            dialog.show();
//        }
//
//        @Nullable
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            /**
//             * Getting JSON Object from Web Using okHttp
//             */
//            JSONObject jsonObject = JsonParserr.getDataFromWeb();
//
//
//            try {
//                /**
//                 * Check Whether Its NULL???
//                 */
//                if (jsonObject != null) {
//                    /**
//                     * Check Length...
//                     */
//                    if (jsonObject.length() > 0) {
//                        /**
//                         * Getting Array named "contacts" From MAIN Json Object
//                         */
//                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);
//
//                        /**
//                         * Check Length of Array...
//                         */
//                        int lenArray = array.length();
//                        if (lenArray > 0) {
//                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {
//
//                                /**
//                                 * Creating Every time New Object
//                                 * and
//                                 * Adding into List
//                                 */
//                                HomeGetterSetter model = new HomeGetterSetter();
//
//                                /**
//                                 * Getting Inner Object from contacts array...
//                                 * and
//                                 * From that We will get Name of that Contact
//                                 *
//                                 */
//                                JSONObject innerObject = array.getJSONObject(jIndex);
//                                String name = innerObject.getString(Keys.KEY_NAME);
//                                String image = innerObject.getString(Keys.KEY_PROFILE_PIC);
//
//                                /**
//                                 * Getting Object from Object "phone"
//                                 */
//                                // JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
//                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);
//
//                                model.setName(name);
//                                model.setImage(image);
//
//                                /**
//                                 * Adding name and phone concatenation in List...
//                                 */
//                                list.add(model);
//                            }
//                        }
//                    }
//                } else {
//
//                }
//            } catch (JSONException je) {
//                // Log.i(JsonParserr.TAG, "" + je.getLocalizedMessage());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            dialog.dismiss();
//            /**
//             * Checking if List size if more than zero then
//             * Update ListView
//             */
//            if (list.size() > 0) {
//                adapter.notifyDataSetChanged();
//            } else {
//                //  Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
//            }
//        }
//    }
//}