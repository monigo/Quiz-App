package com.os;

public class CategoryModel {

    private String name ;
    private int parts ;
    private String url ;
    public CategoryModel(){


    }
    public CategoryModel(String url, String name, int sets) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParts() {
        return parts;
    }

    public void setParts(int parts) {
        this.parts = parts;
    }
}
