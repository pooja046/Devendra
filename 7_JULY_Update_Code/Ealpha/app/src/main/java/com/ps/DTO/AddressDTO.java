package com.ps.DTO;

/**
 * Created by eclipse on 21/12/16.
 */

public class AddressDTO {
	private String cust_fname = "";
	private String cust_lname = "";
	private String cust_address = "";
	private String cust_mobile_no = "";
	private String pin_code = "";
	private String locality = "";
	private String city = "";
	private String state = "";
	private String company = "";
	private String id_country = "";

	public boolean is_selected() {
		return is_selected;
	}

	public void setIs_selected(boolean is_selected) {
		this.is_selected = is_selected;
	}

	private String id_address_delivery = "";
	private String id_address_invoice = "";
	private boolean is_selected = false;
	private String total_shipping = "";

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getId_address_delivery() {
		return id_address_delivery;
	}

	public void setId_address_delivery(String id_address_delivery) {
		this.id_address_delivery = id_address_delivery;
	}

	public String getId_address_invoice() {
		return id_address_invoice;
	}

	public void setId_address_invoice(String id_address_invoice) {
		this.id_address_invoice = id_address_invoice;
	}

	public String getCust_fname() {
		return cust_fname;
	}

	public void setCust_fname(String cust_fname) {
		this.cust_fname = cust_fname;
	}

	public String getCust_lname() {
		return cust_lname;
	}

	public void setCust_lname(String cust_lname) {
		this.cust_lname = cust_lname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPin_code() {
		return pin_code;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}

	public String getCust_mobile_no() {
		return cust_mobile_no;
	}

	public void setCust_mobile_no(String cust_mobile_no) {
		this.cust_mobile_no = cust_mobile_no;
	}

	public String getCust_address() {
		return cust_address;
	}

	public void setCust_address(String cust_address) {
		this.cust_address = cust_address;
	}

	public String getId_country() {
		return id_country;
	}

	public void setId_country(String id_country) {
		this.id_country = id_country;
	}

	public String getTotal_shipping() {
		return total_shipping;
	}

	public void setTotal_shipping(String total_shipping) {
		this.total_shipping = total_shipping;
	}

}
