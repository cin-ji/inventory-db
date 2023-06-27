// Javadoc files located: Inventory Project\Inventory System\javadocs
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import java.io.IOException;


/**
 * This is the main class where the program starts.
 *
 * FUTURE FEATURE - I would create a separate class for the logical error checks to display error messages instead of creating them individually for each logical methods.
 *
 * RUNTIME ERROR - There was an error when I called the wrong attributes from the AddPartController because they had identical names. I fixed it by changing their names appropriately.
 *
 */
public class Main extends Application {
    /**
     * This sets partId all partId to 0 and can be accessed throughout the program.
     */
    public static int partId = 0;
    /**
     * This sets all productId to 0 and can be accessed throughout the program.
     */
    public static int productId = 0;
    /**
     * This method returns unique, incremental partId.
     * @return
     */
    public static int getPartId() {
        return ++partId;
    }
    /**
     * This method returns unique, incremental productId.
     * @return
     */
    public static int getProductId() {
        return ++productId;
    }
    /**
     * This is the start method.
     * This is where the first fxml form is initialized.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1452, 715);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This is the main of the program.
     * It will create new objects and launch the program.
     * @param args
     */
    public static void main(String[] args) {

        InHouse bolt = new InHouse(partId, "bolt", 2.99, 5, 2, 300, 4);
        partId = Main.getPartId();
        Inventory.addPart(bolt);

        Outsourced nail = new Outsourced(partId, "nail", .75, 50, 3, 500, "Nail Co");
        partId = Main.getPartId();
        Inventory.addPart(nail);

        Product tool = new Product(productId, "tool", 99, 3, 2, 4);
        productId = Main.getProductId();
        Inventory.addProduct(tool);

        Product phone = new Product(productId, "phone", 99, 3, 2, 4);
        productId = Main.getProductId();
        Inventory.addProduct(phone);

        launch();
    }
}
