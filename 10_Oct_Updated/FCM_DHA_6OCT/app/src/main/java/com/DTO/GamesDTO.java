package com.DTO;

import java.util.Comparator;

/**
 * Created by eclipse on 3/10/17.
 */

public class GamesDTO {
    private String id = "";
    private String name = "";
    private String image = "";
    private String category = "";
    private String desc = "";
    private String featured = "";
    private LinkDTO linkDTO;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public LinkDTO getLinkDTO() {
        return linkDTO;
    }

    public void setLinkDTO(LinkDTO linkDTO) {
        this.linkDTO = linkDTO;
    }

    public static Comparator<GamesDTO> GAMES_BY_CATEGORY_NAME = new Comparator<GamesDTO>() {
        public int compare(GamesDTO one, GamesDTO other) {
            return one.category.compareTo(other.category);
        }
    };
}
