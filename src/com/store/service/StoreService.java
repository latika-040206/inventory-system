package com.store.service;

import com.store.exception.InsufficientStockException;
import com.store.exception.ItemNotFoundException;
import com.store.model.Discountable;
import com.store.model.ElectronicsItem;
import com.store.model.Item;
import com.store.model.PerishableItem;

import java.io.*;
import java.util.*;

public class StoreService {
    private final String filePath;
    private final Map<String, Item> catalog = new LinkedHashMap<>();

    public StoreService(String filePath) {
        this.filePath = filePath;
        loadInventory();
    }

    private void loadInventory() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                double price = Double.parseDouble(parts[3]);
                int stock = Integer.parseInt(parts[4]);

                if ("ELECTRONICS".equalsIgnoreCase(type)) {
                    int warranty = Integer.parseInt(parts[5]);
                    catalog.put(id, new ElectronicsItem(id, name, price, stock, warranty));
                } else if ("PERISHABLE".equalsIgnoreCase(type)) {
                    String expiry = parts[5];
                    catalog.put(id, new PerishableItem(id, name, price, stock, expiry));
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println("[Warning] Failed parsing some records: " + ex.getMessage());
        }
    }

    public void saveInventory() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("TYPE,ID,NAME,PRICE,QUANTITY,EXTRA");
            bw.newLine();
            for (Item item : catalog.values()) {
                bw.write(item.toCsvRecord());
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("[Error] Failed persisting inventory: " + ex.getMessage());
        }
    }

    public void displayInventory() {
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-24s | %-8s | %-6s | %s\n", "ID", "Name", "Price", "Stock", "Details");
        System.out.println("-------------------------------------------------------------------------------");
        for (Item it : catalog.values()) {
            String extra = (it instanceof ElectronicsItem)
                    ? ((ElectronicsItem) it).getWarrantyMonths() + "m warranty"
                    : "Exp: " + ((PerishableItem) it).getExpirationDate();

            System.out.printf("%-6s | %-24s | $%-7.2f | %-6d | %s\n",
                    it.getId(), it.getName(), it.getUnitPrice(), it.getStock(), extra);
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    public void placeOrder(String itemId, int qty) throws ItemNotFoundException, InsufficientStockException {
        Item item = catalog.get(itemId);
        if (item == null) {
            throw new ItemNotFoundException("Product ID " + itemId + " does not exist.");
        }
        if (item.getStock() < qty) {
            throw new InsufficientStockException("Requested " + qty + " units, but only " + item.getStock() + " in stock.");
        }

        double grossCost = item.getUnitPrice() * qty;
        double discount = 0.0;
        if (item instanceof Discountable) {
            discount = ((Discountable) item).calculateDiscount(qty);
        }
        double netCost = grossCost - discount;

        item.reduceStock(qty);
        saveInventory();

        System.out.println("\n========== ORDER RECEIPT ==========");
        System.out.println("Item:      " + item.getName());
        System.out.println("Quantity:  " + qty);
        System.out.printf("Subtotal:  $%.2f\n", grossCost);
        System.out.printf("Discount:  -$%.2f\n", discount);
        System.out.printf("Total Due: $%.2f\n", netCost);
        System.out.println("===================================\n");
    }

    public void restockItem(String itemId, int addedQty) throws ItemNotFoundException {
        Item item = catalog.get(itemId);
        if (item == null) {
            throw new ItemNotFoundException("Product ID " + itemId + " not found.");
        }
        item.addStock(addedQty);
        saveInventory();
        System.out.println("[Success] Updated stock for " + item.getName() + ". Current: " + item.getStock());
    }
}