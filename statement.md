# Project Statement: Inventory & Order Processing System

## 1. Problem Statement
Retail small businesses frequently encounter challenges with manual stock reconciliation, resulting in inventory discrepancies, omitted replenishment cycles, and calculation errors during customer checkout. Enterprise ERP software is resource-heavy, expensive, and overly complex for lightweight terminal environments. A modular, zero-dependency console utility is needed to track product lifecycles, apply dynamic volume discounting, and persist state atomically across sessions.

## 2. Scope of the Project
- Maintain in-memory catalog data for diverse goods (Electronics and Perishable items).
- Process client purchase transactions with automated discount logic and receipt generation.
- Provide replenishment routines to update current stock levels.
- Synchronize persistent application state to disk via structured CSV records.
- Run purely as a CLI tool with zero external third-party library dependencies.

## 3. Target Users
- Small-scale retail store operators and cashiers.
- Warehouse staff conducting fast terminal-based catalog inspections.
- Headless system operators requiring a lightweight inventory manager.

## 4. High-Level Features
- Product Catalog Browser: Formatted display of inventory, warranty terms, and expiration dates.
- Automated Checkout Engine: Real-time stock validation and tiered volume discounting.
- Stock Restocking Utility: Direct updates to on-hand inventory levels.
- Exception-Resistant CLI: Defensive handling of negative quantities, bad IDs, and format errors.
- Persistent Flat-File Storage: Disk-backed synchronization using `data/inventory.csv`.
