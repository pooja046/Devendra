package com.GridPack;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.dharamraz.R;
import com.main.MainActivity;

public class UrlActivity extends AppCompatActivity {
    private String vCat_Name = "";
    private Toolbar toolbar;
    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        initialize();
    }

    public void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        web_view = (WebView) findViewById(R.id.web_view);
        try {
            vCat_Name = MainActivity.categoryDTO.getCat_name();
            toolbar.setTitle(toTitleCase(vCat_Name.replace("_", " ")));
        } catch (Exception e) {
            toolbar.setTitle("All Games");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        openUrlInWebView();
    }

    private void openUrlInWebView() {
        web_view.setWebViewClient(new myWebClient());
        web_view.getSettings().setJavaScriptEnabled(true);
        if (MainActivity.vUrl != null) {
            if (MainActivity.vUrl.trim().length() > 0) {
                web_view.loadUrl(MainActivity.vUrl);
            } else {
                Toast.makeText(this, "Game not found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Game not found.", Toast.LENGTH_SHORT).show();
        }
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

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }

    // To handle &quot;Back&quot; key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) &&
                web_view.canGoBack()) {
            web_view.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
