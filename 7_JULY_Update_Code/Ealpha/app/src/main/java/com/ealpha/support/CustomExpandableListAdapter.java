package com.ealpha.support;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ealpha.R;
import com.ps.utility.SessionManager;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private List<String> mExpandableListTitle;
	private Map<String, List<String>> mExpandableListDetail;
	private LayoutInflater mLayoutInflater;
	private SessionManager sessionManager;

	public CustomExpandableListAdapter(Context context,
			List<String> expandableListTitle,
			Map<String, List<String>> expandableListDetail) {
		mContext = context;
		mExpandableListTitle = expandableListTitle;
		mExpandableListDetail = expandableListDetail;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		sessionManager = new SessionManager(mContext);
	}

	@Override
	public Object getChild(int listPosition, int expandedListPosition) {
		return mExpandableListDetail
				.get(mExpandableListTitle.get(listPosition)).get(
						expandedListPosition);
	}

	@Override
	public long getChildId(int listPosition, int expandedListPosition) {
		return expandedListPosition;
	}

	@Override
	public View getChildView(int listPosition, final int expandedListPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String expandedListText = (String) getChild(listPosition,
				expandedListPosition);
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.list_item, null);
		}
		TextView expandedListTextView = (TextView) convertView
				.findViewById(R.id.expandedListItem);

		expandedListTextView.setText(expandedListText);

		return convertView;
	}

	@Override
	public int getChildrenCount(int listPosition) {
		return mExpandableListDetail
				.get(mExpandableListTitle.get(listPosition)).size();
	}

	@Override
	public Object getGroup(int listPosition) {
		return mExpandableListTitle.get(listPosition);
	}

	@Override
	public int getGroupCount() {
		return mExpandableListTitle.size();
	}

	@Override
	public long getGroupId(int listPosition) {
		return listPosition;
	}

	@Override
	public View getGroupView(int listPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		String listTitle = (String) getGroup(listPosition);
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.list_group_drawer,
					null);
		}
		TextView listTitleTextView = (TextView) convertView
				.findViewById(R.id.listTitle);
		ImageView indicator = (ImageView) convertView
				.findViewById(R.id.indicator);
		ImageView menu_icon = (ImageView) convertView
				.findViewById(R.id.menu_icon);
		listTitleTextView.setTypeface(null, Typeface.BOLD);
		listTitleTextView.setText(listTitle);
		if (getChildrenCount(listPosition) == 0) {
			indicator.setVisibility(View.GONE);
			if (listTitle.equals("Home Decor & Furnishing")
					|| listTitle.equals("Spiritual Items")) {
				menu_icon.setVisibility(View.INVISIBLE);
			} else {
				menu_icon.setVisibility(View.VISIBLE);
				if (listTitle.equals("Sign In") || listPosition == 0) {
					menu_icon.setImageResource(R.drawable.signin_drawer);
					menu_icon.setVisibility(View.VISIBLE);
				} else if (listTitle.equals("Home")) {
					menu_icon.setImageResource(R.drawable.ic_home);
					menu_icon.setVisibility(View.VISIBLE);
				} else if (listTitle.equals("WishList")) {
					menu_icon.setImageResource(R.drawable.wishlist);
					menu_icon.setVisibility(View.VISIBLE);
				} else if (listTitle.equals("Cart")) {
					menu_icon.setImageResource(R.drawable.add_to_cart2);
					menu_icon.setVisibility(View.VISIBLE);
				} else if (listTitle.equals("About Us")) {
					menu_icon.setImageResource(R.drawable.aboutus);
					menu_icon.setVisibility(View.VISIBLE);
				} else if (listTitle.equals("Log Out")) {
					menu_icon.setImageResource(R.drawable.logout);
					menu_icon.setVisibility(View.VISIBLE);
				} else if (mExpandableListTitle.size() - 1 == listPosition) {
					menu_icon.setVisibility(View.INVISIBLE);
				}
			}
		} else {
			menu_icon.setVisibility(View.INVISIBLE);
			indicator.setVisibility(View.VISIBLE);
			indicator.setImageResource(isExpanded ? R.drawable.minus_new
					: R.drawable.add_new);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int listPosition, int expandedListPosition) {
		return true;
	}

}
