package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Inventory;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that handles the program for modifying parts.
 */
public class ModifyPartController implements Initializable {
    public Button savePartButton;
    public Button cancelPartButton;
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public TextField partIDField;
    public TextField partNameField;
    public TextField partInventoryField;
    public TextField partPriceField;
    public TextField partMaxField;
    public TextField partMinField;
    public TextField partLabelField;
    public Label partLabel;
    private Part part;

    /**
     * Initializes the part selected from the MainScreenController.
     * Sets the input fields with their corresponding values they originally had.
     * Checks whether the selected part is an instance of InHouse or Outsourced class.
     * Sets the text for their appropriate fields based on their identity provided by the radio buttons.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        part = MainScreenController.getPart();

        partIDField.setText(String.valueOf(part.getId()));
        partNameField.setText(part.getName());
        partInventoryField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMinField.setText(String.valueOf(part.getMin()));
        partMaxField.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            partLabelField.setText(String.valueOf(((InHouse) part).getMachineId()));
            partLabel.setText("Machine ID");

        } else {
            outsourcedRadioButton.setSelected(true);
            partLabelField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
            partLabel.setText("Company Name");

        }
    }

    /**
     * Gets text for input fields from user.
     * Validates input values and throws an error for the user to make any correction.
     * Checks which radio button is clicked and changes the text labels where appropriate.
     * Calls the Inventory update method and updates the part with a new object.
     * Returns to the Main Screen with the new, updated part objected if it was changed.
     * @param actionEvent saves modified part
     */
    public void onPartSave(ActionEvent actionEvent) {
        String error = "";
        try {
            int id = Integer.parseInt(partIDField.getText());

            String name = partNameField.getText();
            String pPrice = partPriceField.getText();
            String pStock = partInventoryField.getText();
            String pMin = partMinField.getText();
            String pMax = partMaxField.getText();

            error = "Price";
            double price = Double.parseDouble(pPrice);
            error = "Inventory";
            int stock = Integer.parseInt(pStock);
            error = "Min";
            int min = Integer.parseInt(pMin);
            error = "Max";
            int max = Integer.parseInt(pMax);

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
            if (inHouseRadioButton.isSelected()) {
                error = "Machine ID should be a number and";
                int machineId = Integer.parseInt(partLabelField.getText());
                InHouse inPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(id, inPart);
            }
            if (outsourcedRadioButton.isSelected()) {
                String companyName = partLabelField.getText();
                Outsourced outPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(id, outPart);
                if (companyName.isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Company Name cannot be blank.");
                    alert.showAndWait();
                    return;
                }
            }

            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Scene scene = new Scene(root, 1452, 715);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
     * @param actionEvent returns to main screen
     * @throws IOException
     */
    public void onPartCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1452, 715);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the text when In-House radio button is clicked.
     * @param actionEvent sets text to machine id
     */
    public void onInHouse(ActionEvent actionEvent) {
        partLabel.setText("Machine ID");
    }

    /**
     * Sets the text when Outsourced radio button is clicked.
     * @param actionEvent sets text to company name
     */
    public void onOutsourced(ActionEvent actionEvent) {
        partLabel.setText("Company Name");
    }
}
