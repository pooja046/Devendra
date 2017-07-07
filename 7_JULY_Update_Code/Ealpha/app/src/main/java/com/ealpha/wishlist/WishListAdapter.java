package com.ealpha.wishlist;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ealpha.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ps.DTO.ProductGetterSetter;

public class WishListAdapter extends BaseAdapter {
	private static final int String = 0;
	ArrayList<ProductGetterSetter> productList;
	private Context context;
	LayoutInflater inflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public WishListAdapter(Context context,
			ArrayList<ProductGetterSetter> objects) {
		this.productList = objects;
		this.context = context;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		return productList.size();
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
		// convert view = design
		// View v = convertView;
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.wish_list_row, null);
			holder = new ViewHolder();
			holder.product_image = (ImageView) convertView
					.findViewById(R.id.product_image);
			holder.product_name = (TextView) convertView
					.findViewById(R.id.product_headline);
			holder.product_rs = (TextView) convertView
					.findViewById(R.id.tv_rs_wishlist);
			holder.availability = (TextView) convertView
					.findViewById(R.id.tv_available_soldout);
			holder.lay_wishlist = (LinearLayout) convertView
					.findViewById(R.id.lay_wishlist);
			holder.lay_addToCart = (LinearLayout) convertView
					.findViewById(R.id.lay_addToCart);
			holder.tv_addToCart = (TextView) convertView
					.findViewById(R.id.tv_addToCart);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.product_name.setText("Name: "
				+ productList.get(position).getName());

		int availability = 1;
		if (availability != 0) {
			try {
				holder.availability.setText("Avalable: "
						+ productList.get(position).getAvailability());
			} catch (Exception e) {

			}
		} else {
			try {
				holder.availability.setText("Sold Out: "
						+ productList.get(position).getAvailability());
			} catch (Exception e) {
			}
		}

		try {
			ImageLoader.getInstance().displayImage(
					productList.get(position).getImage(), holder.product_image,
					options);
		} catch (Exception e) {

		}
		try {
			holder.product_rs.setText(" " + productList.get(position).getRs());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (productList.get(position).isAdd_to_cart()) {
			holder.tv_addToCart.setText("Remove to Cart");
		} else {
			holder.tv_addToCart.setText("Add to Cart");
		}
		holder.lay_wishlist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					WishListFragment.myWishListFragment.removeAction(position);
				} catch (ClassCastException e) {
					// TODO: handle exception
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		holder.lay_addToCart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					WishListFragment.myWishListFragment
							.addToCartAction(position);
				} catch (ClassCastException e) {
					// TODO: handle exception
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		holder.product_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					WishListFragment.myWishListFragment
							.detailFromWishList(position);
				} catch (ClassCastException e) {
					// TODO: handle exception
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		return convertView;
	}

	static class ViewHolder {
		public ImageView product_image;
		public TextView id_product, product_name, product_rs, availability;
		private LinearLayout lay_wishlist, lay_addToCart;
		private TextView tv_qty_wishlist, tv_addToCart;
	}

}
