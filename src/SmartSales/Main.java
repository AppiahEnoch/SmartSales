package SmartSales;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Connection;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        DBconnect dBconnect = new DBconnect();
        boolean DBExists = dBconnect.DBExists();
        Parent root;
        String css;
        if (DBExists) {
           // root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
            root = FXMLLoader.load(getClass().getResource("loadItemManual.fxml"));
         //   css = this.getClass().getResource("adminMainWindow.css").toExternalForm();
            css = this.getClass().getResource("loadItemManual.css").toExternalForm();

        } else {
            root = FXMLLoader.load(getClass().getResource("logAdmin.fxml"));
            css = this.getClass().getResource("logProgram.css").toExternalForm();
            logAdmin logAdmin = new logAdmin();
            logAdmin.createTables();
        }

        root.getStylesheets().add(css);
        primaryStage.setTitle("Smart Sales - AECleanCodes");
        primaryStage.setScene(new Scene(root));
        root.requestFocus();

        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setResizable(true);

    }


    public static void main(String[] args) {

        launch(args);

    }
}
