package com.store.model;

public class PerishableItem extends Item implements Discountable {
    private final String expirationDate;

    public PerishableItem(String id, String name, double unitPrice, int stock, String expirationDate) {
        super(id, name, unitPrice, stock);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() { return expirationDate; }

    @Override
    public double calculateDiscount(int qty) {
        // 10% discount for orders of 5 or more units
        if (qty >= 5) {
            return (getUnitPrice() * qty) * 0.10;
        }
        return 0.0;
    }

    @Override
    public String toCsvRecord() {
        return "PERISHABLE," + getId() + "," + getName() + "," + getUnitPrice() + "," + getStock() + "," + expirationDate;
    }
}