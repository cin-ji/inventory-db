package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that handles the program for adding parts to the Main Screen.
 */
public class AddPartController implements Initializable {
    public Button savePartButton;
    public Button cancelPartButton;
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public TextField partIDField;
    public TextField partNameField;
    public TextField partInventoryField;
    public TextField partPriceField;
    public TextField partMinField;
    public TextField partMaxField;
    public Label partLabel;
    public ToggleGroup tgroup;
    public TextField partLabelField;

    /**
     * Initializes to set the In-House Radio Button to be selected before the controller is run.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRadioButton.setSelected(true);
    }

    /**
     * Gets text for input fields from user.
     * Validates input values and throws an error for the user to make any correction.
     * Checks which radio button is clicked and changes the text labels where appropriate.
     * Adds all input values and returns to the Main Screen.
     * @param actionEvent savess part and return to main screen
     */
    public void onPartSave(ActionEvent actionEvent) {
        String error = "";
        try {
            int id = 0;
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
                inPart.setId(Main.getPartId());
                Inventory.addPart(inPart);
            }
            if (outsourcedRadioButton.isSelected()) {
                String companyName = partLabelField.getText();
                Outsourced outPart = new Outsourced(id, name, price, stock, min, max, companyName);
                outPart.setId(Main.getPartId());
                Inventory.addPart(outPart);
                if (companyName.isBlank()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Company Name cannot be blank.");
                    alert.showAndWait();
                    return;
                }
            }
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
     * Returns to the Main Screen if the Cancel button is clicked.
     * @param actionEvent returns to main screen
     * @throws IOException
     */
    public void onPartCancel (ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1452, 715);
            stage.setTitle("Add Part Form");
            stage.setScene(scene);
            stage.show();
    }

    /**
     * Sets the text when In-House radio button is selected.
     * @param actionEvent set text to machine id
     */
    public void onInHouse(ActionEvent actionEvent) {
        partLabel.setText("Machine ID");

    }

    /**
     * Sets the text when Outsourced radio button is selected.
     * @param actionEvent set text to company name
     */
    public void onOutsourced(ActionEvent actionEvent) {
        partLabel.setText("Company Name");
    }
}
