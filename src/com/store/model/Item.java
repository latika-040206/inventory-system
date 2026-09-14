package com.store.model;

public abstract class Item {
    private final String id;
    private final String name;
    private final double unitPrice;
    private int stock;

    public Item(String id, String name, double unitPrice, int stock) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public int getStock() { return stock; }

    public void reduceStock(int qty) {
        this.stock -= qty;
    }

    public void addStock(int qty) {
        this.stock += qty;
    }

    public abstract String toCsvRecord();
}