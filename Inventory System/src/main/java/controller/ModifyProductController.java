package controller;

import javafx.collections.FXCollections;
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
import model.*;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import model.Part;
/**
 * Controller class that handles the program for modifying products and adding/removing associated parts to the product.
 */
public class ModifyProductController implements Initializable {
    public TextField idField;
    public TextField nameField;
    public TextField inventoryField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public Button addPartButton;
    public Button removePart;
    public Button saveButton;
    public Button cancelButton;
    public TextField partLookupField;
    public TableView<Part> partTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;
    public TableColumn associatedIdColumn;
    public TableColumn associatedNameColumn;
    public TableColumn associatedInventoryColumn;
    public TableColumn associatedPriceColumn;
    public TableView<Part> associatedPartTable;
    private ObservableList<Part> associatedList = FXCollections.observableArrayList();
    private Product product;

    /**
     * Initializes the product selected from the MainScreenController along with Parts Table and Associated Parts Table.
     * Sets the input fields for the Product form with the corresponding values they originally had.
     * Sets items in the Associated Parts Table with all the associated parts connected to the selected Product.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = MainScreenController.getProduct();
        associatedList = product.getAllAssociatedParts();

        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        inventoryField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));

        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // tied to getter from InHouse
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedList);
        associatedIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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
     * Adds selected part to the Associated Parts Table.
     * Throws a warning if no part is selected and the Add event is called.
     * @param actionEvent adds selected part to associated part table
     */
    public void onAddPart(ActionEvent actionEvent) {
        Part selected = partTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Select a part to add.");
            alert.showAndWait();
        } else {
            associatedList.add(selected);
            associatedPartTable.setItems(associatedList);
        }
    }

    /**
     * Removes an associated part in the Associated Parts table.
     * Throws a warning if no part is selected and the delete event is called.
     * Throws a confirmation to confirm deletion of the associated part.
     * @param actionEvent removes associated parts from associated parts table
     */
    public void onRemovePart(ActionEvent actionEvent) {
        Part parts = associatedPartTable.getSelectionModel().getSelectedItem();
        if (parts == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Select a part to delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Do you want to remove this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedList.remove(parts);
            }
        }
    }

    /**
     * Gets text for input fields from user.
     * Validates input values and throws an error for the user to make any correction.
     * Creates an instance of a product object and adds any associated parts.
     * Calls the Inventory update method and updates the product with a new object.
     * Returns to the Main Screen with the new, updated product objected if it was changed.
     * @param actionEvent saves modified product
     */
    public void onSave(ActionEvent actionEvent) {
        String error = "";
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String pprice = priceField.getText();
            String pstock = inventoryField.getText();
            String pmin = minField.getText();
            String pmax = maxField.getText();

            error = "Price";
            double price = Double.parseDouble(pprice);
            error = "Inventory";
            int stock = Integer.parseInt(pstock);
            error = "Min";
            int min = Integer.parseInt(pmin);
            error = "Max";
            int max = Integer.parseInt(pmax);

            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Name cannot be blank.");
                alert.showAndWait();
                return;
            }
            if (min > stock || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min value should be less than Inventory. Inventory should be between Min and Max values.");
                alert.showAndWait();
                return;
            }
            Product newProduct = new Product(id, name, price, stock, min, max);
            for (Part part : associatedList) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.updateProduct(id, newProduct);
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1452, 715);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(error + " must be a valid value and cannot be blank.");
            alert.showAndWait();
        }
    }

    /**
     * Returns to the Main Screen when Cancel button is clicked.
     * @param actionEvent returns to the main screen
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1452, 715);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

}

