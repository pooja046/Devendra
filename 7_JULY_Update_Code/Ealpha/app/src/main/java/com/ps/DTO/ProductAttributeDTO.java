package com.ps.DTO;

/**
 * Created by eclipse on 21/6/17.
 */

public class ProductAttributeDTO {
	private String key_value = "";
	private String att_size = "";
	private String att_size_value = "";
	private String att_color = "";
	private String att_color_value = "";

	public String getKey_value() {
		return key_value;
	}

	public void setKey_value(String key_value) {
		this.key_value = key_value;
	}

	public String getAtt_size() {
		return att_size;
	}

	public void setAtt_size(String att_size) {
		this.att_size = att_size;
	}

	public String getAtt_size_value() {
		return att_size_value;
	}

	public void setAtt_size_value(String att_size_value) {
		this.att_size_value = att_size_value;
	}

	public String getAtt_color() {
		return att_color;
	}

	public void setAtt_color(String att_color) {
		this.att_color = att_color;
	}

	public String getAtt_color_value() {
		return att_color_value;
	}

	public void setAtt_color_value(String att_color_value) {
		this.att_color_value = att_color_value;
	}

	@Override
	public String toString() {
		return ("key_value: " + this.key_value + " size: " + this.att_size
				+ " value: " + this.att_size_value + " color: "
				+ this.att_color + " value: " + this.att_color_value);
	}
}