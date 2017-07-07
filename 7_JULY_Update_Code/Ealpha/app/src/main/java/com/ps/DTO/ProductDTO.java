package com.ps.DTO;

public class ProductDTO {
	private String product_name = "";
	private String product_price = "";
	private String product_img = "";
	private String produc_smallImg_wish="";
	private String product_description = "";
	private String product_link = "";
	private String product_id = "";

	private String availability = "";

	public String getProduc_smallImg_wish() {
		return produc_smallImg_wish;
	}

	public void setProduc_smallImg_wish(String produc_smallImg_wish) {
		this.produc_smallImg_wish = produc_smallImg_wish;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	private boolean wish_to_list;

	public boolean isWish_to_list() {
		return wish_to_list;
	}

	public void setWish_to_list(boolean wish_to_list) {
		this.wish_to_list = wish_to_list;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_price() {
		return product_price;
	}

	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}

	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	public String getProduct_link() {
		return product_link;
	}

	public void setProduct_link(String product_link) {
		this.product_link = product_link;
	}

}
