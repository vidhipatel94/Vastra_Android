package com.esolution.vastrabasic.models;

import java.io.Serializable;

public class Catalogue implements Serializable {
    private static final long serialVersionUID = 1L;

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
