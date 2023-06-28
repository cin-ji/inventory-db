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
import main.Main;
import model.*;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import model.Part;

/**
 * Controller class that handles the program for adding products to the Main Screen.
 */
public class AddProductController implements Initializable {

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

    /**
     * Initializes the Table View of parts and associated parts.
     * Sets identity for each column.
     * Calls the Inventory class to get all Parts and sets them in the Part Table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // tied to getter from InHouse
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

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
     * Adds parts from the Parts Table to the Associated Parts Table.
     * Throws an error if no parts is selected and the Add event is called.
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
     * Removes an associated part from the Associated Part Table.
     * Throws an error if no associated parts is selected and the Remove event is called.
     * Throws a confirmation for the user to confirm deletion.
     * @param actionEvent removes associated part
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
     * Calls the Main for the unique Id method for the product.
     * Adds the product object to the Inventory class and returns to the Main Screen.
     * @param actionEvent saves modified product
     */
    public void onSave(ActionEvent actionEvent) {
        String error = "";
        try {
            int id = 0;
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
            newProduct.setId(Main.getProductId());
            Inventory.addProduct(newProduct);
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
     * Returns to the Main Screen if Cancel button is clicked.
     * @param actionEvent returns to main screen
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

