package dao;

import java.util.List;
import entity.OrderDetail;

public interface OrderDetailDAO {
    boolean addOrderDetail(OrderDetail orderDetail);
    boolean updateOrderDetail(OrderDetail orderDetail);
    boolean deleteOrderDetail(int orderDetailId);
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}
