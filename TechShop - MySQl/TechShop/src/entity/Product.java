package entity;

public class Product {
    private int productId;
    private String productName;
    private String descriptions;
    private double price;
    private String category;

    public Product(int productId, String productName, String descriptions, double price, String category) {
        this.productId = productId;
        this.productName = productName;
        this.descriptions = descriptions;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductID: " + productId + ", Name: " + productName + ", Description: " + descriptions +
               ", Price: " + price + ", Category: " + category;
    }
}
