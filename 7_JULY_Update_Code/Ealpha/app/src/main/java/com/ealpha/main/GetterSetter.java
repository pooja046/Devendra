package com.ealpha.main;

public class GetterSetter {

	private String name_cat;

	private String image_cat;
	
	private String cat_link;
	private String description_cat;

	public GetterSetter() {
		// TODO Auto-generated constructor stub
	}

	public GetterSetter(String name_cat, String image_cat, String cat_link, String description_cat) {
		super();
		this.name_cat = name_cat;

		this.image_cat = image_cat;
		
		this.cat_link = cat_link;
		this.description_cat = description_cat;
	}

	public String getDescription_cat() {
		return description_cat;
	}

	public void setDescription_cat(String description_cat) {
		this.description_cat = description_cat;
	}

	public String getCat_link() {
		return cat_link;
	}

	public void setCat_link(String cat_link) {
		this.cat_link = cat_link;
	}

	public String getName_cat() {
		return name_cat;
	}

	public void setName_cat(String name_cat) {
		this.name_cat = name_cat;
	}

	public String getImage_cat() {
		return image_cat;
	}

	public void setImage_cat(String image_cat) {
		this.image_cat = image_cat;
	}

	
}