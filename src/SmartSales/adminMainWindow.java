package SmartSales;

import com.sun.xml.internal.bind.v2.schemagen.episode.Bindings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;




public class adminMainWindow extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private Button btRegisterSeller;

    @FXML
    private Button btLogOut;

    @FXML
    void openMainWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("mainLock.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() -  currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() -  currentStage.getHeight()) / 2);
            currentStage.setResizable(false);
        }
        catch (Exception e){

        }
    }

    @FXML
    void registerSeller(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("regSeller.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("regSeller.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() -  currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() -  currentStage.getHeight()) / 2);
            currentStage.setResizable(false);
        }
        catch (Exception e){


        }


    }





}
