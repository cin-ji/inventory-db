package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Inventory class that models for Parts and Products.
 */
public class Inventory {
    /**
     * This creates an Observable list called allParts for all parts in the Inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * This creates an Observable list called allProducts for all products in the Inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * This method adds part objects to the observable list allParts.
     * @param newPart variable to be added the part list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * This method adds product objects to the observable list allProducts.
     * @param newProduct variable to be added to product list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * Looks up parts through all objects in the observable list allParts by their partId.
     * @param partId variable used to search parts using the Id
     * @return The object if found, otherwise null if not found.
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part p: allParts) {
            if(p.getId() == partId) {
                return p;
            }
        }
        return null;
    }
    /**
     * Looks up products through all objects in the observable list allProducts by their productId.
     * @param productId variable used to search products using the Id
     * @return The object if found, otherwise null if not found.
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product p: allProducts) {
            if(p.getId() == productId) {
                return p;
            }
        }
        return null;
    }
    /**
     * Looks up partial/full name in the observable list allParts by their name.
     * @param partName variable used to search parts using the name
     * @return A list of parts that contain the partial/full name that was searched.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        // creates a new list
        ObservableList<Part> namedPart = FXCollections.observableArrayList();
        // gets all parts from list

        for(Part part: allParts) {
            if(part.getName().contains(partName)) {
                namedPart.add(part);
            }

        }
        return namedPart;
    }
    /**
     * Looks up partial/full name in the observable list allProducts by their name.
     * @param productName variable used to search products using the name
     * @return A list of products that contain the partial/full name that was searched.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for(Product p: allProducts) {
            if(p.getName().contains(productName)) {
                namedProduct.add(p);
            }
        }
        return namedProduct;
    }

    /**
     * Updates part object at their specific index.
     * @param index variable used to determine the objects index
     * @param selectedPart the object at the parts table index
     */
    public static void updatePart(int index, Part selectedPart) {
        int i = -1;
        for (Part Part : getAllParts()) {
            i++;
            if (Part.getId() == index) {
                getAllParts().set(i, selectedPart);
            }
        }

    }

    /**
     * Updates product object at their specific index.
     * @param index variable used to determine the objects index
     * @param newProduct the object at the products table index
     */
    public static void updateProduct(int index, Product newProduct) {
        int i = -1;
        for (Product Product : getAllProducts()) {
            i++;
            if (Product.getId() == index) {
                getAllProducts().set(i, newProduct);
            }
        }
    }

    /**
     * Deletes selected part object.
     * @param selectedPart variable used to delete part
     * @return A bool value if part is deleted.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Deletes selected product object.
     * @param selectedProduct variable used to delete product
     * @return A bool value if product is deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Creates a list of all parts in Inventory.
     * @return A list of parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Creates a list of all products in Inventory.
     * @return A list of products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
