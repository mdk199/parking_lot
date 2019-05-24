package com.parking.entity;


/**
 * Identify the parking space type
 */
public class Type {

    private String type;

    private Dimension dimension;

    public Type(String type) {
        this.type = type;
    }

    public Type(String type, Dimension dimension) {
        this.type = type;
        this.dimension = dimension;
    }

    public String getType() {
        return type;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
