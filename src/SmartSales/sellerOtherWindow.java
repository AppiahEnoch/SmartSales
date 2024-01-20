package SmartSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class sellerOtherWindow extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private Button tbPassword;

    @FXML
    private Button btBark;

    @FXML
    void openChangePassword(ActionEvent event) {



        try {
            root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);

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
    void openSaleWindow(ActionEvent event) {


        try {
            root = FXMLLoader.load(getClass().getResource("salesWindow.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);

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
    void openOldReceipt(ActionEvent event) {


        try {
            root = FXMLLoader.load(getClass().getResource("oldReceiptIDValidate.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);

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
    void OpenUserSalesWindow(ActionEvent event) {
         openWindowByClick(event,"sellerTotalSales.fxml");
    }


    Parent rr;
    Stage stg1=new Stage();
    Scene sc1;



    @FXML
    void openWindowByClick(ActionEvent event, String fxml){




        try {
            rr = FXMLLoader.load(getClass().getResource(fxml));

            sc1=new Scene(rr);

            stg1.setScene(sc1);

            stg1.show();
            rr.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stg1.setX((primScreenBounds.getWidth() -  stg1.getWidth()) / 2);
            stg1.setY((primScreenBounds.getHeight() -  stg1.getHeight()) / 2);
            stg1.setResizable(false);
        }
        catch (Exception e){

        }
    }

}
