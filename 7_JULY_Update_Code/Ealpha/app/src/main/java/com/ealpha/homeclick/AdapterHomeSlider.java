package com.ealpha.homeclick;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ealpha.R;
import com.ealpha.main.HomeFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ps.DTO.SliderDTO;

/**
 * Created by sys1 on 1/29/14.
 */
public class AdapterHomeSlider extends PagerAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<SliderDTO> sliderDTOs;
	private DisplayImageOptions options;

	public AdapterHomeSlider(Context context, ArrayList<SliderDTO> sliderDTOs) {
		this.context = context;
		this.sliderDTOs = sliderDTOs;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		return sliderDTOs.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.home_image_slider,
				container, false);
		ImageView club_image = (ImageView) viewLayout
				.findViewById(R.id.slider_image);
		try {
			ImageLoader.getInstance().displayImage(
					sliderDTOs.get(position).getSlider_image(), club_image,
					options);
		} catch (Exception e) {

		}
		club_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (HomeFragment.homeFragment != null) {
						HomeFragment.homeFragment
								.openGridfromSliderItemClick(position);
					}
				} catch (Exception e) {

				}
			}
		});
		((ViewPager) container).addView(viewLayout);
		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}
}
