package com.ealpha.main;

import android.app.Fragment;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ealpha.R;

public class SearchActivity extends ListActivity {

	SearchActivity searchActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());

		searchActivity = this;
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// call detail activity for clicked entry
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doSearch(query);
		}
	}

	private void doSearch(String queryStr) {
		// get a Cursor, prepare the ListAdapter
		// and set it
	}

	public class SearchFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View rootview = inflater.inflate(R.layout.home_grid_view,
					container, false);

			return rootview;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {

		case R.id.home:
			// startActivity(new Intent(this, MainActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}