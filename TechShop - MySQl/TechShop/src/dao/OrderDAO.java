package dao;

import entity.Order;
import java.util.List;

public interface OrderDAO {
    boolean placeOrder(Order order);
    boolean updateOrderStatus(int orderId, String status);
    List<Order> getOrdersByCustomerId(int customerId);
    List<Order> getAllOrders();

}
