package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for product class.
 */
public class Product {
    /**
     * An observable list of products called associatedParts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for a Product object.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock  = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for the observable list of associated parts.
     * @return The list of associated parts.
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * Setter for the observable list of associated parts.
     * @param associatedParts variable for associated parts
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * Getter for product Id.
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for product Id.
     * @param id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for product name.
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for product name.
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for product price.
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for product price.
     * @param price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for product stock.
     * @return Stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for product stock.
     * @param stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for product minimum value.
     * @return Minimum value
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for product minimum value.
     * @param min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for product maximum value.
     * @return Maximum value
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for product maximum value.
     * @param max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds object to the observable list of associated parts.
     * @param part variable to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes an object from observable list of associated parts.
     * @param selectedAssociatedPart variable to remove
     * @return A boolean value if associated part is deleted.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Gets all associated parts from an object.
     * @return Associated parts associated with the product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
