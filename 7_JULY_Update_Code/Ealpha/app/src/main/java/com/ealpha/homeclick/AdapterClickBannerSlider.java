package com.ealpha.homeclick;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ealpha.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ps.DTO.ProductDTO;

public class AdapterClickBannerSlider extends BaseAdapter {
	private Context context;
	private ArrayList<ProductDTO> productDTOs;
	private ProductDTO productDTO;
	private DisplayImageOptions options;

	public AdapterClickBannerSlider(Context context,
			ArrayList<ProductDTO> productDTOs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.productDTOs = productDTOs;
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
		return productDTOs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return productDTOs.get(position);
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

			viewHolder.smallImgWish = (ImageView) convertView
					.findViewById(R.id.whish_icon2);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		productDTO = productDTOs.get(position);
		viewHolder.product_name.setText(productDTO.getProduct_name());
		viewHolder.product_price.setText(productDTO.getProduct_price());
		try {
			ImageLoader.getInstance().displayImage(productDTO.getProduct_img(),
					viewHolder.product_image, options);
		} catch (Exception e) {

		}
		if (productDTO.isWish_to_list()) {
			viewHolder.smallImgWish
					.setImageResource(R.drawable.whishlist_ic_white_red);
		} else {
			viewHolder.smallImgWish.setImageResource(R.drawable.remove_wish);
		}
		viewHolder.smallImgWish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SliderClick.sliderClick != null) {
					SliderClick.sliderClick.wistToList(position);
				}
				if (BannerClick.bannerClick != null) {
					BannerClick.bannerClick.wistToList(position);
				}
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		private TextView product_name;
		private TextView product_price;
		private ImageView product_image;
		private ImageView smallImgWish;
	}

}
