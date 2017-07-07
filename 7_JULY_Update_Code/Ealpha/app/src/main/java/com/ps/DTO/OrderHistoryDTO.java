package com.ps.DTO;

public class OrderHistoryDTO {
	private String id_ = "";

	private String payment = "";
	private String reference = "";
	private String date = "";
	private String totalPrice = "";
	private String stats = "";

	public OrderHistoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderHistoryDTO(String reference, String date, String totalPrice,
			String stats, String id_, String payment) {
		super();
		this.id_ = id_;
		this.reference = reference;
		this.date = date;
		this.totalPrice = totalPrice;
		this.stats = stats;
		this.payment = payment;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

}
