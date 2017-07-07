package com.ealpha.support;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ealpha.R;
import com.ealpha.main.ChildItemsFragment;
import com.ealpha.main.HomeFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ps.DTO.ProductDTO;

public class ProductAdapterGridChild extends BaseAdapter {
	private Context context;
	private ArrayList<ProductDTO> productArrayList;
	private ProductDTO productDTO;
	private DisplayImageOptions options;

	public ProductAdapterGridChild(Context context,
			ArrayList<ProductDTO> productDTOs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.productArrayList = productDTOs;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return productArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.banner_home_slider_grid_row, null);
			viewHolder = new ViewHolder();

			viewHolder.product_name = (TextView) convertView
					.findViewById(R.id.product_name2);
			viewHolder.product_price = (TextView) convertView
					.findViewById(R.id.product_price2);
			viewHolder.product_image = (ImageView) convertView
					.findViewById(R.id.product_image_pp2);
			viewHolder.whish_icon = (ImageView) convertView
					.findViewById(R.id.whish_icon2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		productDTO = productArrayList.get(position);

		viewHolder.product_name.setText(productDTO.getProduct_name());
		viewHolder.product_price.setText(productDTO.getProduct_price());
		if (productDTO.isWish_to_list()) {
			viewHolder.whish_icon
					.setImageResource(R.drawable.whishlist_ic_white_red);
		} else {
			viewHolder.whish_icon.setImageResource(R.drawable.remove_wish);
		}
		try {
			ImageLoader.getInstance().displayImage(productDTO.getProduct_img(),
					viewHolder.product_image, options);
		} catch (Exception e) {

		}
		viewHolder.whish_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (HomeFragment.homeFragment != null) {
					HomeFragment.homeFragment.wistToList(position);
				}
				if (ChildItemsFragment.childItemsFragment != null) {
					ChildItemsFragment.childItemsFragment.wistToList(position);
				}
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		private TextView product_name;
		private TextView product_price;
		private ImageView product_image, whish_icon;
	}

}
