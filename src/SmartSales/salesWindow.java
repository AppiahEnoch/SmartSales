package SmartSales;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Optional;

public class salesWindow extends DBconnect {
    ShareData m = ShareData.getInstance();
    boolean canDeleteFileNoImageList = true;
    String item;
    String price;
    String qty;

    int ctr = 0;
    boolean isUpdateMainItem = false;
    String itemToDelete = null;

    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;




    @FXML
    private TableView<ITEM> tbV;

    @FXML
    private TableColumn<ITEM, String> col1;

    @FXML
    private TableColumn<ITEM, String> col2;

    @FXML
    private TableColumn<ITEM, String> col3;

    @FXML
    TextField tf=new TextField();

    @FXML
    ImageView imageV;

    boolean regulate = true;

    public void initialize() {


        col1.setCellValueFactory(new PropertyValueFactory<ITEM, String>("item"));
        col2.setCellValueFactory(new PropertyValueFactory<ITEM, String>("qty"));
        col3.setCellValueFactory(new PropertyValueFactory<ITEM, String>("price"));


        //  getImage();
        startThread();

        if (isTableviewEmpty()) {
            imageV.setImage(null);
        }

    }





    @FXML
    void clearF(ActionEvent event) {


    }




    @FXML
    void goMainWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
            String css = this.getClass().getResource("mainLock.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
        } catch (Exception e) {


        }
    }







    @FXML
    void getSelected() {

        try {
            ITEM colSelected1 = tbV.getSelectionModel().getSelectedItem();
            Object item = colSelected1.getItem();
            Object qty = colSelected1.getQty();
            Object price = colSelected1.getPrice();
            itemToDelete = item.toString();

            tf.setText(item.toString());


        } catch (Exception e) {

        }
    }





    private void setFocus() {

    }




    private boolean isGoOn(String message) {
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        message,
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("CONFIRM ACTION!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            return true;
        }
        return false;
    }

    @FXML
    synchronized private boolean fillTbV2() {
                DBcon();
        openConn(conn);
        String i = "";

        try {


            while (m.continueItemSuggestion) {



                String keyword = tf.getText().trim();
                if (!(i.equals(keyword))) {
                    tbV.getItems().clear();
                }
                if (!(keyword.isEmpty())) {

                    qry = "SELECT distinct sName,qty,price" +
                            " from item " +
                            "where sName like '%" + keyword + "%' " +
                            "order by sName ASC";
                    try {
                        openConn(conn);
                        st = conn.createStatement();
                        rs = st.executeQuery(qry);

                        while (rs.next()) {
                            String s = rs.getString("sName").trim();
                            String q = rs.getString("qty").trim();

                            String p = rs.getString("price").trim();




                            if (!(i.equals(keyword))) {
                                tbV.refresh();
                                tbV.getItems().addAll(new ITEM(s, q, p));

                            }

                        }
                        i = keyword;


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    @FXML
    void startThread() {
        stopThread();

        m.continueItemSuggestion = true;
        new Thread(() -> fillTbV2()).start();
    }

    @FXML
    void stopThread() {
        m.continueItemSuggestion = false;
    }












    Stage sst = new Stage();
    Scene scene;
    Parent r;
    @FXML
    void showImages(ActionEvent event) {

        if (sst.isShowing()){
            sst.close();

        }

        try {
            r = FXMLLoader.load(getClass().getResource("showImagesInFolder.fxml"));
            String css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            r.getStylesheets().add(css);
            sst.setTitle("Smart Sales - AECleanCodes");
            sst.setScene(new Scene(r));
            r.requestFocus();
           // sst.initStyle(StageStyle.UTILITY);
            sst.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public boolean isTableviewEmpty() {
        try {
            ObservableList<ITEM> list = tbV.getItems();
            if (list.isEmpty()) {
                imageV.setImage(null);
                return true;
            }
        } catch (Exception e) {

        }

        return false;


    }

    @FXML
    void showLoadedItems(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("viewItems.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("viewItems.css").toExternalForm();
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
          e.printStackTrace();
        }
    }



}

