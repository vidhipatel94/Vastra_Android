package com.esolution.vastrabasic.models;

public class Catalogue {
    int id;
    String name;
    int designerId;

    Catalogue() {
    }

    public Catalogue(String name, int designerId) {
        this.name = name;
        this.designerId = designerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDesignerId() {
        return designerId;
    }
}
