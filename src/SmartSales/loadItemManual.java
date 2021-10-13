package SmartSales;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class loadItemManual extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private TableView<?> tbV1;

    @FXML
    private TableColumn<?, ?> col11;

    @FXML
    private TableColumn<?, ?> col12;

    @FXML
    private TableColumn<?, ?> col13;

    @FXML
    private TableView<?> tbV2;

    @FXML
    private TableColumn<?, ?> col21;

    @FXML
    private TableColumn<?, ?> col22;

    @FXML
    private TableColumn<?, ?> col23;

    @FXML
    private ImageView imgv;

    @FXML
    private TextField tf11;

    @FXML
    private TextField tf12;

    @FXML
    private TextField tf13;

    @FXML
    private Button bt1;

    @FXML
    private TextField tf21;

    @FXML
    private TextField tf22;

    @FXML
    private TextField tf23;

    @FXML
    private Button bt2;

    @FXML
    private Button btb;

    @FXML
    private Button btclose;

    @FXML
    private Button btc;

    @FXML
    private Button btd1;

    @FXML
    private Button btd2;

    @FXML
    void addItem1(ActionEvent event) {

    }

    @FXML
    void addItem2(ActionEvent event) {

    }

    @FXML
    void clearF(ActionEvent event) {
       tf21.clear();
       tf22.clear();
       tf23.clear();

    }

    @FXML
    void deleteAll(ActionEvent event) {

    }

    @FXML
    void deleteOne(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {
        try{
            root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("adminMainWindow.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
        }
        catch (Exception e){


        }
    }

    @FXML
    void goMainWindow(ActionEvent event) {
        try{
            root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("mainLock.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
        }
        catch (Exception e){


        }
    }

   
}
