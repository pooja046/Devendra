package com.ealpha.cart;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ealpha.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ps.DTO.AddressDTO;

public class AddressAdapter extends BaseAdapter {
    ArrayList<AddressDTO> addressDTOs;
    private AddressDTO addressDTO;
    private Context context;
    LayoutInflater inflater;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public AddressAdapter(Context context, ArrayList<AddressDTO> addressDTOs) {
        this.addressDTOs = addressDTOs;
        this.context = context;
        inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return addressDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return addressDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View vi = convertView;
        try {
            if (convertView == null) {
                vi = inflater.inflate(
                        R.layout.shipping_address_list_activity, null);
                holder = new ViewHolder();
                holder.name = (TextView) vi
                        .findViewById(R.id.cust_name_list_activity);
                holder.address = (TextView) vi
                        .findViewById(R.id.cust_address_list_activity);
                holder.contact_no = (TextView) vi
                        .findViewById(R.id.cust_contact_no_list_activity);

                holder.city = (TextView) vi
                        .findViewById(R.id.cust_city_list_activity);
                holder.postcode = (TextView) vi
                        .findViewById(R.id.cust_postcode_list_activity);
                holder.state = (TextView) vi
                        .findViewById(R.id.cust_state_list_activity);

                holder.btn_edit = (Button) vi
                        .findViewById(R.id.btn_edit_list_activity);
                holder.btn_remove = (Button) vi
                        .findViewById(R.id.btn_remove_list_activity);
                holder.selected_address = (ImageView) vi
                        .findViewById(R.id.selected_address);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            addressDTO = addressDTOs.get(position);
            if (addressDTO.is_selected()) {
                holder.selected_address.setImageResource(R.drawable.check);
            } else {
                holder.selected_address.setImageResource(R.drawable.un_check);
            }
            holder.name.setText("Name:- "
                    + addressDTOs.get(position).getCust_fname() + " "
                    + addressDTOs.get(position).getCust_lname());

            holder.address.setText("Address:- "
                    + addressDTOs.get(position).getCust_address());

            holder.contact_no.setText("Contact no:- "
                    + addressDTOs.get(position).getCust_mobile_no());

            holder.city
                    .setText("City:- " + addressDTOs.get(position).getCity());

            holder.postcode.setText("Postcode:- "
                    + addressDTOs.get(position).getPin_code());

            holder.state.setText("State:- "
                    + addressDTOs.get(position).getLocality());

            holder.btn_edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        ((ShippingAddressListActivity) context).editAddress(position);
                    } catch (Exception e) {

                    }
                }
            });

            holder.btn_remove.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        ((ShippingAddressListActivity) context).removeAddress(position);
                    } catch (Exception e) {

                    }
                }
            });
            holder.selected_address.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ((ShippingAddressListActivity) context).checkUncheck(position);
                    } catch (Exception e) {

                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return vi;
    }

    static class ViewHolder {
        public TextView name, last_name, address, contact_no, city, postcode,
                state;
        public Button btn_remove, btn_edit;
        private ImageView selected_address;
    }

}
