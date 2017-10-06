package com.DTO;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by eclipse on 3/10/17.
 */

public class CategoryDTO {
    private String cat_name = "";
    private ArrayList<GamesDTO> gamesDTOs;

    public String getCat_name() {
        return cat_name;
    }
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public ArrayList<GamesDTO> getGamesDTOs() {
        return gamesDTOs;
    }

    public void setGamesDTOs(ArrayList<GamesDTO> gamesDTOs) {
        this.gamesDTOs = gamesDTOs;
    }

    public static Comparator<CategoryDTO> COMPARE_BY_CATEGORY_NAME = new Comparator<CategoryDTO>() {
        public int compare(CategoryDTO one, CategoryDTO other) {
            return one.cat_name.compareTo(other.cat_name);
        }
    };
}
