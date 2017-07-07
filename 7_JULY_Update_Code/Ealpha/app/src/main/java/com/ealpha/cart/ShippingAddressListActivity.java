package com.ealpha.cart;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.ealpha.R;
import com.ps.DTO.AddressDTO;
import com.ps.utility.SessionManager;

public class ShippingAddressListActivity extends Activity {
    private ListView shiping_address_list;
    private ArrayList<AddressDTO> address_arrayList;
    private SessionManager sessionManager;
    public static AddressDTO addressDTO_items;
    private AddressAdapter addressAdapter;
    public static int refreshlist = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping__address_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        initialize();
    }

    public void initialize() {
        shiping_address_list = (ListView) findViewById(R.id.shiping_address_list);
        // address_arrayList = new ArrayList<AddressDTO>();
        sessionManager = new SessionManager(this);
        setAddressListData();
        shiping_address_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                addressDTO_items = address_arrayList.get(arg2);
                startActivity(new Intent(ShippingAddressListActivity.this,
                        ShippingAddressListitemDetailActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshlist == 2) {
            refreshlist = 1;
            setAddressListData();
        }
    }

    public void setAddressListData() {
        try {
            address_arrayList = sessionManager.getAddressList();
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (address_arrayList == null) {
            address_arrayList = new ArrayList<>();
        }
        addressAdapter = new AddressAdapter(this,
                address_arrayList);
        shiping_address_list.setAdapter(addressAdapter);
    }

    public void checkUncheck(int posi) {
        if (address_arrayList.get(posi).is_selected()) {
            address_arrayList.get(posi).setIs_selected(false);
        } else {
            address_arrayList.get(posi).setIs_selected(true);
            sessionManager.setCustomerDetail(address_arrayList.get(posi));
            for (int i = 0; i < address_arrayList.size(); i++) {
                if (i != posi) {
                    address_arrayList.get(i).setIs_selected(false);
                }
            }
            sessionManager.setAddressList(address_arrayList);
        }
        addressAdapter.notifyDataSetChanged();
    }

    public void removeAddress(int posi) {
        removeAddressDialog(posi).show();
    }

    public void editAddress(int posi) {
        Intent intent = new Intent(this,
                ShippingAddressListitemDetailActivity.class);
        intent.putExtra("edit_posi", posi);
        startActivity(intent);
    }


    private AlertDialog removeAddressDialog(final int removePosition) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(
                ShippingAddressListActivity.this)
                .setTitle("Ealpha")
                .setMessage("Are you sure you want Remove this address ?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // your deleting code
                                dialog.dismiss();
                                address_arrayList.remove(removePosition);
                                if (address_arrayList.size() > 0) {
                                    address_arrayList.get(address_arrayList.size() - 1).setIs_selected(true);
                                    sessionManager.setCustomerDetail(address_arrayList.get(address_arrayList.size() - 1));
                                } else {
                                    sessionManager.setCustomerDetail(new AddressDTO());
                                }
                                sessionManager.setAddressList(address_arrayList);
                                addressAdapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        return myQuittingDialogBox;
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
