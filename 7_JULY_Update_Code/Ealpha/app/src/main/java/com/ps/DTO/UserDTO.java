package com.ps.DTO;

public class UserDTO {
	private String customer_id = "";

	private String first_title = "";
	private String first_name = "";
	private String last_name = "";
	private String email = "";
	private String birthday = "";
	private String password = "";

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public String getFirst_title() {
		return first_title;
	}

	public void setFirst_title(String first_title) {
		this.first_title = first_title;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
