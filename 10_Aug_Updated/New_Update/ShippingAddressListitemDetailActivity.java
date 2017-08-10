package com.ealpha.cart;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ealpha.R;
import com.ps.DTO.AddressDTO;
import com.ps.DTO.StateDTO;
import com.ps.utility.SessionManager;

import java.util.ArrayList;

public class ShippingAddressListitemDetailActivity extends Activity {
    public EditText name, address, contact_no, city, post_code;
    public Button cancel, save;
    private SessionManager sessionManager;
    private String[] states = {"Select State_0", "Andhra Pradesh_313", "Arunachal Pradesh_314", "Assam_315", "Bihar_316", "Chhattisgarh_317", "Goa_318", "Gujarat_319", "Haryana_320", "Himachal Pradesh_321", "Jammu and Kashmeer_322", "Jharkhand_323", "Karnataka_324", "Kerala_325", "Madhya Pradesh_326", "Maharashtra_327", "Manipur_328", "Meghalaya_329", "Mizoram_330", "Nagaland_331", "Orissa_332", "Punjab_333", "Rajasthan_334", "Sikkim_335", "Tamil Nadu_336", "Tripura_337", "Uttaranchal_338", "Uttar Pradesh_339", "West Bengal_340", "Andaman and Nicobar Islands_341", "Chandigarh_342", "Dadra and Nagar Haveli_343", "Daman and Diu_344", "Delhi_345", "Lakshadweep_346", "Pondicherry_347"};
    private Spinner state_spinner;
    private String vState_id = "", vState_Name = "";
    private ArrayList<StateDTO> stateDTOs;
    private StateDTO stateDTO;

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
        state_spinner = (Spinner) findViewById(R.id.state_spinner);
        cancel = (Button) findViewById(R.id.btn_cancel);
        save = (Button) findViewById(R.id.btn_save);
        sessionManager = new SessionManager(this);
        setStateStaticInArray();
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
                } else if (vState_id.toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Select State.", Toast.LENGTH_SHORT).show();
                } else if (city.getText().toString().trim().length() == 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Enter City.", Toast.LENGTH_SHORT).show();
                } else {
                    saveAddressDetail();
                }
            }
        });
        setAddressDetail();
    }

    public void setStateStaticInArray() {
        stateDTOs = new ArrayList<>();
        for (int i = 0; i < states.length; i++) {
            stateDTO = new StateDTO();
            try {
                stateDTO.setState_name(states[i].split("_")[0]);
                stateDTO.setState_id(states[i].split("_")[1]);
            } catch (Exception e) {

            }
            stateDTOs.add(stateDTO);
        }
    }

    public void setStateSpinnerValue() {
        ArrayAdapter<StateDTO> country_adapter = new ArrayAdapter<StateDTO>(this,
                R.layout.custom_textview_to_spinner, stateDTOs);
        state_spinner.setAdapter(country_adapter);
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StateDTO stateDTO = stateDTOs.get(i);
                vState_id = stateDTO.getState_id();
                vState_Name = stateDTO.getState_name();
                if (i != 0) {
                    Toast.makeText(ShippingAddressListitemDetailActivity.this, "Setected State Name: " + vState_Name + "\n" + "Setected State ID: " + vState_id, Toast.LENGTH_SHORT).show();
                } else {
                    vState_id = "";
                    vState_Name = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
//        state.setText(addressDTO
//                .getLocality());
        city.setText(addressDTO
                .getCity());
        setStateSpinnerValue();
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
        addressDTO.setLocality(vState_Name);
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
