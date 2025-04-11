package dao;

import java.util.List;
import entity.Inventory;

public interface InventoryDAO {
    boolean addInventory(Inventory inventory);
    boolean updateInventory(Inventory inventory);
    boolean deleteInventory(int inventoryID);
    Inventory getInventoryById(int inventoryID);
    List<Inventory> getAllInventory();
}
