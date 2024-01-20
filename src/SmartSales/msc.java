package SmartSales;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.*;

import java.util.ArrayList;

public class msc extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private ComboBox<String> comb;

    @FXML
    private Button btOldReceipt;

    @FXML
    void getIDWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("oldReceiptIDValidate.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);

            currentStage.setScene(currentScene);
            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void getAdminWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);

            currentStage.setScene(currentScene);
            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Parent r;
    Stage stage=new Stage();
    Scene scene2;

    @FXML
    void showLog(ActionEvent event) {


        try {
            r = FXMLLoader.load(getClass().getResource("log.fxml"));

            scene2=new Scene(r);

            stage.setScene(scene2);

            stage.show();
            r.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            stage.setResizable(false);
        }
        catch (Exception e){

        }




    }




    @FXML
        void showCashiers(ActionEvent event) {
        String ii=comb.getSelectionModel().getSelectedItem().toString().trim();

        if (!ii.isEmpty()){

            String[] i=ii.split("  ");
            ShareData.userID_=i[0];
            ShareData.currentUser_=i[1];

            comb.getSelectionModel().clearSelection();



                    try {
            r = FXMLLoader.load(getClass().getResource("sellerTotalSales.fxml"));

            scene2=new Scene(r);

            stage.setScene(scene2);

            stage.show();
            r.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                        stage.setResizable(false);
        }
        catch (Exception e){

        }

        }





    }



    @FXML
    void showCombo(ActionEvent event) {


        if (comb.isVisible()){
            comb.setVisible(false);
            comb.getSelectionModel().clearSelection();
        }
        else {
            comb.setVisible(true);
        }




    }

    public void initialize() {
        ObservableList<String> list = FXCollections.observableList(getCashiers());
        comb.setItems(list);


    }

    String cashier="";

    private ArrayList<String> getCashiers() {
        ArrayList<String> li = new ArrayList<>();
        qry = "SELECT  concat(ID,'  ',name) as cashier FROM smart.seller";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                cashier = rs.getString("cashier").trim();
                if (!cashier.isEmpty()) {
                    li.add(cashier);

                }



            }
        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();

        }

        return li;
    }
}