package com.store.model;

public class ElectronicsItem extends Item implements Discountable {
    private final int warrantyMonths;

    public ElectronicsItem(String id, String name, double unitPrice, int stock, int warrantyMonths) {
        super(id, name, unitPrice, stock);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() { return warrantyMonths; }

    @Override
    public double calculateDiscount(int qty) {
        // 5% discount if ordering 3 or more electronic items
        if (qty >= 3) {
            return (getUnitPrice() * qty) * 0.05;
        }
        return 0.0;
    }

    @Override
    public String toCsvRecord() {
        return "ELECTRONICS," + getId() + "," + getName() + "," + getUnitPrice() + "," + getStock() + "," + warrantyMonths;
    }
}