package com.os;

public class SubCategoryModel {

    private String name ;
    private  int sets ;
    private String url ;
    public SubCategoryModel(){


    }
    public SubCategoryModel(String url, String name, int sets) {
        this.url = url;
        this.name = name;
        this.sets = sets;
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

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
