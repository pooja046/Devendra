package com.ealpha.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ealpha.R;
import com.ps.DTO.AddressDTO;
import com.ps.utility.SessionManager;

import java.util.ArrayList;

public class ShippingAddressListitemDetailActivity extends Activity {
    public EditText name, last_name, address, contact_no, city, post_code, state;
    public Button cancel, save;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresses_list_row);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        initialize();
    }

    public void initialize() {
        name = (EditText) findViewById(R.id.name_);
        // last_name = (EditText) findViewById(R.id.name_);
        address = (EditText) findViewById(R.id.address_);
        contact_no = (EditText) findViewById(R.id.contact_);
        city = (EditText) findViewById(R.id.city_);
        post_code = (EditText) findViewById(R.id.post_code_);
        state = (EditText) findViewById(R.id.state);
        cancel = (Button) findViewById(R.id.btn_cancel);
        save = (Button) findViewById(R.id.btn_save);
        sessionManager = new SessionManager(this);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (name.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter Customer Name.",
                            Toast.LENGTH_SHORT).show();
                } else if (address.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter Customer Address.", Toast.LENGTH_SHORT)
                            .show();
                } else if (contact_no.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter Mobile No.", Toast.LENGTH_SHORT).show();
                } else if (post_code.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter Pin Code.", Toast.LENGTH_SHORT).show();
                } else if (state.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter Address.", Toast.LENGTH_SHORT).show();
                } else if (city.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter City.", Toast.LENGTH_SHORT).show();
                } else {
                    saveAddressDetail();
                }
            }
        });
        setAddressDetail();
    }

    public void setAddressDetail() {
        int position = getIntent().getIntExtra("edit_posi", -1);
        AddressDTO addressDTO = sessionManager.getAddressList().get(position);
        name.setText(addressDTO
                .getCust_fname() + " " + addressDTO
                .getCust_lname());
        address.setText(addressDTO
                .getCust_address());
        contact_no.setText(addressDTO
                .getCust_mobile_no());
        post_code.setText(addressDTO
                .getPin_code());
        state.setText(addressDTO
                .getLocality());
        city.setText(addressDTO
                .getCity());
    }

    public void saveAddressDetail() {
        int position = getIntent().getIntExtra("edit_posi", -1);
        AddressDTO addressDTO = sessionManager.getAddressList().get(position);
        try {
            addressDTO.setCust_fname(name.getText().toString()
                    .trim().split(" ")[0]);
        } catch (Exception e) {

        }
        try {
            addressDTO.setCust_lname(name.getText().toString()
                    .trim().split(" ")[1]);
        } catch (Exception e) {

        }
        addressDTO.setCust_address(address.getText()
                .toString().trim());
        addressDTO.setCust_mobile_no(contact_no.getText().toString()
                .trim());
        addressDTO.setPin_code(post_code.getText().toString().trim());
        addressDTO.setLocality(state.getText().toString().trim());
        addressDTO.setCity(city.getText().toString().trim());
        ArrayList<AddressDTO> addressDTOs = sessionManager.getAddressList();
        addressDTOs.set(position, addressDTO);
        sessionManager.setAddressList(addressDTOs);
        ShippingAddressListActivity.refreshlist = 2;
        Toast.makeText(this, "Address Updated.", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
