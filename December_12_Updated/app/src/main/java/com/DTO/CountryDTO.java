package com.DTO;

/**
 * Created by eclipse on 11/12/17.
 */

public class CountryDTO {
    private String id = "";
    private String name = "";
    private String iso_code = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }
}
