import java.util.*;
import java.util.regex.Pattern;

// Custom exception for invalid data
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

// Custom exception for incomplete orders
class IncompleteOrderException extends Exception {
    public IncompleteOrderException(String message) {
        super(message);
    }
}

// Custom exception for insufficient stock
class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}

public class TechShop {
    public static void main(String[] args) {
        System.out.println("Welcome to the Tech Shop!");
    }
}

class Customers{
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    public Customers(int customerID, String firstName, String lastName, String email, String phone, String address) throws InvalidDataException {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        setEmail(email);
        this.phone = phone;
        this.address = address;
    }

    public void setEmail(String email) throws InvalidDataException {
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            throw new InvalidDataException("Invalid email format");
        }
        this.email = email;
    }

    public int getCustomerID() {return customerID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getAddress() {return address;} 
}

class Products{
    private int productID;
    private String productName;
    private String description;
    private double price;

    public Products(int productID, String productName, String description, double price) throws InvalidDataException {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        setPrice(price);
    }

    public void setPrice(double price) throws InvalidDataException {
        if (price  < 0) {
            throw new InvalidDataException("Price cannot be negative");
        }
        this.price = price;
    }

    public int getProductID() {return productID;}
    public String getProductName() {return productName;}
    public String getDescription() {return description;}
    public double getPrice() {return price;}
}

class Orders {
    private int orderID;
    private Customers customer;
    private String orderDate;
    private double totalAmount;

    public Orders(int orderID, Customers customer, String orderDate, double totalAmount) throws IncompleteOrderException {
        if( customer == null) {
            throw new IncompleteOrderException("Order must hae an associated customer");
        }

        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getOrderID() {return orderID;}
    public Customers getCustomer() {return customer;}
    public String getOrderDate() {return orderDate;}
    public double getTotalAmount() {return totalAmount;}
}

class OrderDetails {
    private int orderDetailID;
    private Orders order;
    private Products product;
    private int quantity;

    public OrderDetails(int orderDetailID, Orders order, Products product, int quantity) throws IncompleteOrderException {
        if (order == null || product == null) {
            throw new IncompleteOrderException("Order and Product cannot be null");
        }
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public int getOrderDetailID() {return orderDetailID;}
    public Orders getOrder() {return order;}
    public Products getProduct() {return product;}
    public int getQuantity() {return quantity;}
}

class Inventory {
    private int inventoryID;
    private Products product;
    private int quantityInStock;
    private String lastStockUpdate;

    public Inventory(int inventoryID, Products product, int quantityInStock, String lastStockUpdate) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
        this.lastStockUpdate = lastStockUpdate;
    }

    public void removeFromInventory(int quantity) throws InsufficientStockException {
        if(quantity > quantityInStock) {
            throw new InsufficientStockException(" Not enough stock available");
        }

        this.quantityInStock -= quantity;
    }

    public int getInventoryID() {return inventoryID;}
    public Products getProduct() {return product;}
    public int getQuantityInStock() {return quantityInStock;}
    public String getLastStockUpdate() {return lastStockUpdate;}
}

class TechShopSystem {
    private List<Products> productlist = new ArrayList<>();
    private List<Orders> orderList = new ArrayList<>();
    private SortedMap<Integer, Inventory> inventoryMap = new TreeMap<>();

    public void addProduct(Products product) throws InvalidDataException {
        for (Products p : productlist) {
            if (p.getProductID() == product.getProductID()) {
                throw new InvalidDataException("Duplicate product ID");
            }
        }
        productlist.add(product);
    }

    public void addOrders(Orders order) {
        orderList.add(order);
    }

    public void sortOrderByDate() {
        orderList.sort(Comparator.comparing(Orders::getOrderDate));
    }

    public void addInventory(Inventory inventory) {
        inventoryMap.put(inventory.getInventoryID(), inventory);
    }

    public void searchProductByName(String name) {
        for (Products product : productlist) {
            if(product.getProductName().equalsIgnoreCase(name)) {
                System.out.println("Product found: " + product.getProductName());
            }
        }
    }
}