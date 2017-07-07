package com.ealpha.drawer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ealpha.navigation.NavigationManager;

public class CustomerCare extends Activity {

	TextView call, whatsApp, email;
	Button toHome;
	ManageProfileFragment myFragment;
	private NavigationManager mNavigationManager;
	private int selectedPosition;
	private DrawerLayout mDrawerLayout;
	public static int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_care);

		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		toHome = (Button) findViewById(R.id.btn_tohome_customercare);
		call = (TextView) findViewById(R.id.tv_call_us);
		whatsApp = (TextView) findViewById(R.id.tv_whatsapp_no);
		email = (TextView) findViewById(R.id.email_care);
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:+91 731 6671111"));
				startActivity(intent);
			}
		});

		whatsApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PackageManager pm = getPackageManager();
				try {

					Intent waIntent = new Intent(Intent.ACTION_SEND);
					waIntent.setType("text/plain");
					String text = "TYPE YOUR TEXT ..";

					PackageInfo info = pm.getPackageInfo("com.whatsapp",
							PackageManager.GET_META_DATA);
					// Check if package exists or not. If not then code
					// in catch block will be called
					waIntent.setPackage("com.whatsapp");

					waIntent.putExtra(Intent.EXTRA_TEXT, text);
					startActivity(Intent.createChooser(waIntent, "Share with"));

				} catch (NameNotFoundException e) {
					Toast.makeText(getApplication(), "WhatsApp not Installed",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
						"mailto", "info@ealpha.com", null));
				intent.putExtra(Intent.EXTRA_SUBJECT, "");
				intent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(intent,
						"Choose an Email client :"));
			}
		});

		toHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_toHome = new Intent(getApplicationContext(),
						MainActivity.class);
				intent_toHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent_toHome);
				finish();
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; goto parent activity.
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}