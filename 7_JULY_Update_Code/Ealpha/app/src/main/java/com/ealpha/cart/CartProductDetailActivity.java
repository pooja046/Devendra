package com.ealpha.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ealpha.R;

public class CartProductDetailActivity extends Activity {
	private ImageView img_main_pda;
	private TextView tv_headline, tv_description, tv_rs;
	private Button btn_back_pda, btn_addToCart_pda;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_shipping_detail);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		img_main_pda = (ImageView) findViewById(R.id.viewpager_pp);
		// tv_brand_name_wishlist = (TextView)
		// findViewById(R.id.tv_brand_name_wishlist);
		tv_headline = (TextView) findViewById(R.id.txt_headline);
		// tv_rs = (TextView) findViewById(R.id.tv_rs);
		tv_description = (TextView) findViewById(R.id.txt_product_description);
		tv_rs = (TextView) findViewById(R.id.txt_rs);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart_product_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
