package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main Screen class that handles the Part Table, Product Table, and their corresponding search methods.
 */
public class MainScreenController implements Initializable {
    public TableView<Part> partTable;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;
    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;
    public TableView<Product> productTable;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public TextField partLookupField;
    public TextField productLookupField;
    public Button exitButton;
    public static Part parts;
    private static Product products;

    /**
     * Initializes the Table View of Parts and Products.
     * Sets identity for each column.
     * Calls the Inventory class to get all Parts and Products and sets them in their respective tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // tied to getter from InHouse
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * Getter for parts.
     * @return Parts created
     */
    public static Part getPart(){
        return parts;
    }

    /**
     * Getter for products.
     * @return Products created
     */
    public static Product getProduct(){
        return products;
    }
    /**
     * Searches through the parts list given the user input by calling the lookupPart method from the Inventory class.
     * Throws a warning if part Id or name is not found.
     * Sets the items in an observable list if found.
     * @param actionEvent searches for parts using part id or partial/full part name
     */
    public void onPartLookup(ActionEvent actionEvent) {

        String searchPart = partLookupField.getText();
        try {
            ObservableList<Part> partList = Inventory.lookupPart(searchPart);

            if (partList.size() == 0) {
                int partId = Integer.parseInt(searchPart);
                Part partSearch = Inventory.lookupPart(partId);
                partList.add(partSearch);
                if (partSearch == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Part ID not found.");
                    alert.showAndWait();
                }
            }
            partTable.setItems(partList);
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Part name not found.");
                alert.showAndWait();
        }
    }

    /**
     * Searches through the products list given the user input by calling the lookupProduct method from the Inventory class.
     * Throws a warning if product Id or name is not found.
     * Sets the items in an observable list if found.
     * @param actionEvent searches for products using product id or partial/full product name
     */
    public void onProductLookup(ActionEvent actionEvent) {
        String searchProduct = productLookupField.getText();
        try {

            ObservableList<Product> productList = Inventory.lookupProduct(searchProduct);

            if (productList.size() == 0) {
                int productId = Integer.parseInt(searchProduct);
                Product productSearch = Inventory.lookupProduct(productId);
                productList.add(productSearch);
                if (productSearch == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Product ID not found.");
                    alert.showAndWait();
                }
            }
            productTable.setItems(productList);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Product name not found.");
            alert.showAndWait();
        }
    }

    /**
     * Loads the Add Part window.
     * @param actionEvent loads the add part form
     * @throws IOException
     */
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 823, 772);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the Modify Part window with the selected part.
     * Throws an warning if no part is selected and the Modify part button is clicked.
     * @param actionEvent loads the modify part form
     * @throws IOException
     */
    public void onModifyPartButton(ActionEvent actionEvent) throws IOException {

        parts = partTable.getSelectionModel().getSelectedItem();
        if (parts == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Select a part to modify.");
            alert.showAndWait();
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Modify Part Form");
            stage.show();
        }
    }

    /**
     * Deletes a part object on the Part Table.
     * Throws a warning if no part is selected and the Modify part button is clicked.
     * Throws a confirmation for the user to confirm deletion.
     * @param actionEvent deletes a selected part
     */
    public void onDeletePartButton(ActionEvent actionEvent) {
        parts = partTable.getSelectionModel().getSelectedItem();
        if (parts == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Select a part to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(parts);
            }
        }
    }

    /**
     * Loads the Add Product window.
     * @param actionEvent loads add product form
     * @throws IOException
     */
    public void onAddProductButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1684, 793);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the Modify Product window with the selected product.
     * Throws a warning if no part is selected and the Modify part button is clicked.
     * @param actionEvent loads modify product form
     * @throws IOException
     */
    public void onModifyProductButton(ActionEvent actionEvent) throws IOException {
        products = productTable.getSelectionModel().getSelectedItem();
        if (products == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Select a part to modify.");
            alert.showAndWait();
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Modify Product Form");
            stage.show();
        }
    }

    /**
     * Deletes a product object in the Product Table.
     * Throws a warning if no product is selected and the Modify product button is clicked.
     * Throws a confirmation for the user to confirm deletion.
     * Throws an error if the product selected for deletion has an associated part.
     * @param actionEvent deletes selected product
     */
    public void onDeleteProductButton(ActionEvent actionEvent) {
        products = productTable.getSelectionModel().getSelectedItem();
        if (products == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Select a product to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedList = products.getAllAssociatedParts();
                if (associatedList.isEmpty()) {
                    Inventory.deleteProduct(products);
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Error");
                    alert1.setContentText("Product has an associated part and cannot be deleted.");
                    alert1.showAndWait();
                }
            }
        }
    }

    /**
     * Exits the program.
     * @param actionEvent exists the program
     */
    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
