package com.ps.DTO;

public class ProductGetterSetter {

	private String image = "";
	private String name = "";
	private String rs = "";
	private String description = "";
	private String pdt_link = "";
	private String id_product = "";
	private String colors = "";
	private String sizes = "";
	private String quantity = "";
	private String availability = "";
	private String wish_to_cart = "";
	private String id_address_delivery = "";
	private String id_address_invoice = "";
	private String total_price = "";
	private String cart_id = "";
	private String customer_id = "";

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public ProductGetterSetter(String image, String name, String pdt_link,
			String rs, String description, String availability,
			String quantity, String id_product, String sizes, String colors,
			String wish_to_cart, String id_address_delivery,
			String id_address_invoice, String cart_id, String customer_id

	) {
		super();
		this.id_product = id_product;
		this.colors = colors;
		this.sizes = sizes;
		this.image = image;
		this.name = name;
		this.pdt_link = pdt_link;
		this.rs = rs;
		this.description = description;
		this.availability = availability;
		this.quantity = quantity;
		this.wish_to_cart = wish_to_cart;
		this.id_address_delivery = id_address_delivery;
		this.id_address_invoice = id_address_invoice;
		this.cart_id = cart_id;
		this.customer_id = customer_id;

	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	public String getWish_to_cart() {
		return wish_to_cart;
	}

	public void setWish_to_cart(String wish_to_cart) {
		this.wish_to_cart = wish_to_cart;
	}

	/*public String getId_address_delivery() {
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
	}*/

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	public String getId_product() {
		return id_product;
	}

	public void setId_product(String id_product) {
		this.id_product = id_product;
	}

	public ProductGetterSetter() {

		// TODO Auto-generated constructor stub
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPdt_link() {
		return pdt_link;
	}

	public void setPdt_link(String pdt_link) {
		this.pdt_link = pdt_link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean add_to_cart;

	public boolean isAdd_to_cart() {
		return add_to_cart;
	}

	public void setAdd_to_cart(boolean add_to_cart) {
		this.add_to_cart = add_to_cart;
	}

}
