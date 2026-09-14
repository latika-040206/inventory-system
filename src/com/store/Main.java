package com.store;

import com.store.exception.InsufficientStockException;
import com.store.exception.ItemNotFoundException;
import com.store.service.StoreService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StoreService service = new StoreService("data/inventory.csv");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- INVENTORY & ORDER MANAGEMENT SYSTEM ---");
            System.out.println("1. List All Products");
            System.out.println("2. Purchase Item");
            System.out.println("3. Restock Product");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    service.displayInventory();
                    break;

                case "2":
                    System.out.print("Enter Product ID: ");
                    String buyId = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Enter Quantity: ");
                    try {
                        int buyQty = Integer.parseInt(scanner.nextLine().trim());
                        if (buyQty <= 0) {
                            System.out.println("[Error] Quantity must be positive.");
                            break;
                        }
                        service.placeOrder(buyId, buyQty);
                    } catch (NumberFormatException e) {
                        System.out.println("[Error] Please enter a valid numerical quantity.");
                    } catch (ItemNotFoundException | InsufficientStockException e) {
                        System.out.println("[Error] " + e.getMessage());
                    }
                    break;

                case "3":
                    System.out.print("Enter Product ID: ");
                    String restockId = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Enter Stock to Add: ");
                    try {
                        int addQty = Integer.parseInt(scanner.nextLine().trim());
                        if (addQty <= 0) {
                            System.out.println("[Error] Added quantity must be positive.");
                            break;
                        }
                        service.restockItem(restockId, addQty);
                    } catch (NumberFormatException e) {
                        System.out.println("[Error] Please enter a valid number.");
                    } catch (ItemNotFoundException e) {
                        System.out.println("[Error] " + e.getMessage());
                    }
                    break;

                case "4":
                    System.out.println("Saving state and exiting. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("[Error] Invalid selection. Choose between 1 and 4.");
            }
        }
        scanner.close();
    }
}