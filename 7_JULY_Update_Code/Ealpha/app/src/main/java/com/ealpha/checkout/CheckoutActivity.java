package com.ealpha.checkout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ealpha.R;
import com.ealpha.cart.CartFragment;

public class CheckoutActivity extends Activity {
	private WebView checkout_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		initialize();
	}

	public void initialize() {
		checkout_view = (WebView) findViewById(R.id.checkout_view);
		startWebView("http://securepay.alphainfonet.com/abill/HdfcSslAPI.php?ap_invoice_id=101&ap_amount="
				+ CartFragment.total_amount);
	}

	private void startWebView(String url) {
		checkout_view.setWebViewClient(new WebViewClient() {
			ProgressDialog progressDialog;

			// If you will not use this method url links are opeen in new brower
			// not in webview
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			// Show loader on url load
			@Override
			public void onLoadResource(WebView view, String url) {
				if (progressDialog == null) {
					// in standard case YourActivity.this
					progressDialog = new ProgressDialog(CheckoutActivity.this);
					progressDialog.setMessage("Please wait...");
					progressDialog.show();
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				try {
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
						progressDialog = null;
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

		});
		// Javascript inabled on webview
		checkout_view.getSettings().setJavaScriptEnabled(true);
		checkout_view.loadUrl(url);
	}

	// Open previous opened link from history on webview when back button
	// pressed

	@Override
	// Detect when the back button is pressed
	public void onBackPressed() {
		if (checkout_view.canGoBack()) {
			checkout_view.goBack();
		} else {
			// Let the system handle the back button
			super.onBackPressed();
		}
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
