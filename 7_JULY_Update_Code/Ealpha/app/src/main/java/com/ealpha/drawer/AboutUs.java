package com.ealpha.drawer;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ealpha.R;
import com.ealpha.main.MainActivity;

public class AboutUs extends Fragment {
	TextView terms_of_service, privacy_policy, ratting, feedback;

	public AboutUs() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about_us, container,
				false);
		MainActivity.view_pagination_index = 1;
		ratting = (TextView) rootView.findViewById(R.id.ratting);
		ratting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "https://play.google.com/store/apps/details?id=com.ealpha&hl=en";

				Intent intent = new Intent(Intent.ACTION_VIEW);

				intent.setData(Uri.parse(url));
				startActivity(intent);

			}
		});
		feedback = (TextView) rootView.findViewById(R.id.feedback);
		feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent int_feedback = new Intent(getActivity(), Feedback.class);
				startActivity(int_feedback);

			}
		});

		terms_of_service = (TextView) rootView.findViewById(R.id.termsofsevice);

		terms_of_service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent int1 = new Intent(getActivity(), TermsOfServices.class);
				startActivity(int1);

			}
		});
		privacy_policy = (TextView) rootView.findViewById(R.id.privacy_policy);
		privacy_policy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent int2 = new Intent(getActivity(), PrivacyPolicy.class);
				startActivity(int2);
			}
		});
		return rootView;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Zeero/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case android.R.id.home:
			// app icon in action bar clicked; go home
			this.finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void finish() {
		// TODO Auto-generated method stub

	}

}
