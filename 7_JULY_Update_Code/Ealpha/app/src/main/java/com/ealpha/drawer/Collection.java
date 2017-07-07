package com.ealpha.drawer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ealpha.R;
import com.ealpha.main.MainActivity;

public class Collection extends Fragment {

	public Collection() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.cartlist, container, false);
		MainActivity.view_pagination_index = 1;
		return rootView;
	}

}
