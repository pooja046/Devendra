package com.ealpha.homeclick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ealpha.support.NotificationAdapter;
import com.ealpha.support.NotificationDTO;
import com.ps.DTO.CartsDTO;
import com.ps.DTO.ProductDTO;
import com.ps.utility.ConnectionDetector;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;

public class NotificationActivity extends Activity {
    private ListView notification_list_view;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private ArrayList<NotificationDTO> notificationDTOs;
    private NotificationDTO notificationDTO;
    public static NotificationDTO notificationDTONew;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        initialize();
    }

    public void initialize() {
        notification_list_view = (ListView) findViewById(R.id.notification_list_view);
        notificationDTOs = new ArrayList<>();
        sessionManager = new SessionManager(this);
        notification_list_view.setAdapter(new NotificationAdapter(this, notificationDTOs));
        if (!isInternet()) {
            Toast.makeText(
                    NotificationActivity.this,
                    "No internet connection, please try with internet connection.",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            new getNotificationlistAsynchTask().execute("http://ealpha.com/mob/customers.php?");
        }
    }

    class getNotificationlistAsynchTask extends
            AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NotificationActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Please wait");
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("module", "notifications"));
            //  params.add(new BasicNameValuePair("id",""));
            System.out.println("params..." + params.toString());
            JSONObject json = new JSONParser().makeHttpRequest2(args[0],
                    "POST", params);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressDialog.dismiss();
            String vStatus = "";
            try {
                notificationDTOs = new ArrayList<>();
                try {
                    System.out.println("notification_list..." + jsonObject.toString());
                } catch (Exception e) {

                }
                vStatus = jsonObject.getString("status");
                if (vStatus.trim().equals("success")) {
                    JSONArray notificationJsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < notificationJsonArray.length(); i++) {
                        JSONObject notifi_object = notificationJsonArray.getJSONObject(i);
                        notificationDTO = new NotificationDTO();
                        notificationDTO.setId(notifi_object.getString("id"));
                        notificationDTO.setTitle(toTitleCase(notifi_object.getString("title")));
                        notificationDTO.setType(toTitleCase(notifi_object.getString("type")));
                        notificationDTO.setShort_dec(notifi_object.getString("short_decs"));
                        notificationDTO.setDescription(notifi_object.getString("description"));
                        notificationDTO.setImage(notifi_object.getString("image"));
                        notificationDTO.setItem_id(notifi_object.getString("item_id"));
                        notificationDTO.setStart_at(convertServerTimeToDeviceTime(notifi_object.getString("start_at")));
                        notificationDTO.setLink(notifi_object.getString("link"));
                        notificationDTOs.add(notificationDTO);
                    }
                    sessionManager.notificationCount(0);
                }
            } catch (Exception e) {

            }
            if (notificationDTOs != null) {
                if (notificationDTOs.size() > 0) {
                    notification_list_view.setAdapter(new NotificationAdapter(NotificationActivity.this, notificationDTOs));
                    notification_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            notificationDTONew = notificationDTOs.get(position);
                            if (notificationDTONew.getType().equalsIgnoreCase("product")) {
                                MainActivity.cartsDTO = new CartsDTO();
                                MainActivity.cartsDTO.setId_product(notificationDTONew.getItem_id());
                                MainActivity.cartsDTO.setProduct_name(notificationDTONew.getTitle());
                                MainActivity.cartsDTO.setUnit_price("0.0");
                                MainActivity.cartsDTO.setTotal_price("0.0");
                                MainActivity.cartsDTO.setProduct_img(notificationDTONew.getImage());
                                MainActivity.cartsDTO.setProduct_link(notificationDTONew.getLink());
                                MainActivity.cartsDTO.setQuantity(1);
                                MainActivity.cartsDTO.setAvailability("0");
                                Intent intent = new Intent(NotificationActivity.this, ProductDetailActivityPP.class);
                                intent.putExtra("product_link_popular", notificationDTONew.getLink());
                                startActivity(intent);
                            } else if (notificationDTONew.getType().equalsIgnoreCase("category")) {
                                Intent intent = new Intent(NotificationActivity.this, BannerClick.class);
                                intent.putExtra("banner_url", notificationDTONew.getLink() + "&start_limit=0&end_limit=20");
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(NotificationActivity.this, "no record found.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean isInternet() {
        connectionDetector = new ConnectionDetector(NotificationActivity.this);
        // Check if Internet present
        return connectionDetector.isConnectingToInternet();
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

    private String convertServerTimeToDeviceTime(String vDateTime) {
        String vConvertDateTime = vDateTime;
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsed = sourceFormat.parse(vDateTime); // => Date is in UTC now
            // TimeZone tz = TimeZone.getTimeZone("America/Chicago");
            SimpleDateFormat destFormat = new SimpleDateFormat("d MMM yyyy',' hh:mm a");
            destFormat.setTimeZone(TimeZone.getDefault());
            vConvertDateTime = destFormat.format(parsed);
        } catch (Exception e) {

        }
        return vConvertDateTime;
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