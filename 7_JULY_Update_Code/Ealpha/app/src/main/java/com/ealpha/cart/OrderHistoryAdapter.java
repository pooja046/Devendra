package com.ealpha.cart;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ealpha.R;
import com.ps.DTO.OrderHistoryDTO;

public class OrderHistoryAdapter extends BaseAdapter {
	private ArrayList<OrderHistoryDTO> orderHistoryDTOs;
	private OrderHistoryDTO historyDTO;
	private Context context;
	LayoutInflater inflater;

	public OrderHistoryAdapter(Context context,
			ArrayList<OrderHistoryDTO> orderHistoryDTOs) {
		this.orderHistoryDTOs = orderHistoryDTOs;
		this.context = context;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderHistoryDTOs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.orders_id_history_row, null);
			viewHolder = new ViewHolder();
			viewHolder.order_id = (TextView) convertView
					.findViewById(R.id.order_id);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			historyDTO = orderHistoryDTOs.get(position);
			viewHolder.order_id.setText(historyDTO.getId_());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}

	static class ViewHolder {
		public TextView order_id;
	}

}
