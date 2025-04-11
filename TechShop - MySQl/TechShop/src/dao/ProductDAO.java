package dao;

import java.util.List;
import entity.Product;

public interface ProductDAO {
    boolean addProduct(Product product);
    Product getProductById(int productId);
    List<Product> getAllProducts();
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
}
