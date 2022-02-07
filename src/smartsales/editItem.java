package smartsales;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;

public class editItem extends DBconnect {

    Stage sst=new Stage();

    Scene scene;
    Parent r;

    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private TableView<ITEM2> tbv;

    @FXML
    private TableColumn<ITEM2, String> c;

    @FXML
    private TableColumn<ITEM2, String> c5;

    @FXML
    private TableColumn<ITEM2, String> c1;

    @FXML
    private TableColumn<ITEM2, String> c2;

    @FXML
    private TableColumn<ITEM2, String> c3;

    @FXML
    private TableColumn<ITEM2, String> c4;
    @FXML
    private Button btd1;

    @FXML
    private Button btd2;

@FXML
    String  getSelected(){

        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getName();



        return n.toString();
    }


    @FXML
    void goBackR(ActionEvent event) {

        sst.close();

        try {
            root = FXMLLoader.load(getClass().getResource("emptySystemWindow.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
            String css = this.getClass().getResource("emptySystemWindow.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);

            loadItemManual ob=new loadItemManual();
            ob.writeAllNoImageItems();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        c1.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("name"));
        c2.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("qty"));
        c3.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("cost"));
        c4.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("price"));
        c5.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("time"));

        c1.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c4.setCellFactory(TextFieldTableCell.forTableColumn());

        getRecentItems();
    }


    private void getRecentItems() {
        DBcon();
        openConn(conn);
        qry = "select  sName,qty,cost,price,time from item order by time";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                DBitem = rs.getString("sName").trim();
                DBqty = rs.getString("qty").trim();
                DBCost = rs.getString("cost").trim();
                DBprice = rs.getString("price").trim();
                DBtime = rs.getString("time").trim();

                System.out.println(DBitem);

                tbv.getItems().addAll(new ITEM2(DBitem, DBqty, DBCost, DBprice, DBtime));


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }


    }


    @FXML
    void editItem(TableColumn.CellEditEvent editEvent) {
        ITEM2 c = tbv.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());

        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected1.getName();
        Object id = colSelected1.getId();
        String nn=m.stringData1;
        updateItem("sName", n, nn);


    }

    @FXML
    void editQty(TableColumn.CellEditEvent editEvent) {
        ITEM2 c = tbv.getItems().get(editEvent.getTablePosition().getRow()).setQty(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected1.getQty();
        Object id = colSelected1.getId();
        updateItem("qty", n, m.stringData1);

    }

    @FXML
    void editCost(TableColumn.CellEditEvent editEvent) {
        ITEM2 colSelected = tbv.getItems().get(editEvent.getTablePosition().getRow()).setCost(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected1.getCost();
        Object id = colSelected1.getId();
        updateItem("cost", n, m.stringData1);

    }

    @FXML
    void editPrice(TableColumn.CellEditEvent editEvent) {
        ITEM2 colSelected = tbv.getItems().get(editEvent.getTablePosition().getRow()).setPrice(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected1.getPrice();
        updateItem("price", n, m.stringData1);

    }

    public void updateItem(String column, Object newName, Object id) {
        DBcon();
        qry = "UPDATE item SET " + column + "='" + newName + "' where sName = '" + id + "'";
        try {
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    public void deleteSelectedItem() {
        openConn(conn);
        System.out.println("m.stringData1:"+m.stringData1);
        qry = "DELETE FROM item where sName='" + m.stringData1+"'";
        try {
            st = conn.createStatement();
            st.executeUpdate(qry);


            conn.close();

        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    ShareData m = ShareData.getInstance();

    @FXML
    void showPopImg(MouseEvent event){

        ShareData m = ShareData.getInstance();
        m.stringData1=getSelected();


            if (sst.isShowing()){
               sst.close();

            }

            try {
                r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
                //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
                //   r.getStylesheets().add(css);
                sst.setTitle("Smart Sales - AECleanCodes");
                sst.setScene(new Scene(r));
                r.requestFocus();


                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                sst.setX(primScreenBounds.getMinX()+primScreenBounds.getWidth()-500);
                sst.setY(primScreenBounds.getMinY()+primScreenBounds.getHeight()-300);

                sst.show();


            sst.getOnCloseRequest();

            }
            catch (Exception e){
                e.printStackTrace();
            }


    }


    @FXML
    void deleteSelected(ActionEvent event) {

        if (!isTableviewEmpty()) {


            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "DO YOU REALLY WANT TO DELETE THE SELECTED RECORD?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                    " DELETION.",
                            ButtonType.YES,
                            ButtonType.NO);
            alert.setTitle("DELETE SELECTED WARNING!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.YES) {


                try {
                    deleteSelectedItem();

                    try {
                        root = FXMLLoader.load(getClass().getResource("editItem.fxml"));
                        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentScene = new Scene(root);
                        String css = this.getClass().getResource("editItem.css").toExternalForm();
                        root.getStylesheets().add(css);
                        currentStage.setScene(currentScene);

                        currentStage.show();
                        root.requestFocus();
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
                        currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
                        currentStage.setResizable(false);


                    } catch (Exception e) {

                    }

                } catch (Exception e) {

                }


            } else {

            }


        }


    }

    public boolean isTableviewEmpty() {
        ObservableList<ITEM2> list = tbv.getItems();
        if (list.isEmpty()) {

            return true;
        }


        return false;

    }

    @FXML
    public void deleteAll(ActionEvent event) {



        if (!isTableviewEmpty()){


            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "DO YOU REALLY WANT TO DELETE THE SELECTED RECORD?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                    " DELETION.",
                            ButtonType.YES,
                            ButtonType.NO);
            alert.setTitle("DELETE SELECTED WARNING!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.YES) {


                DBcon();
                qry = "DELETE FROM    item";
                try {
                    st = conn.createStatement();
                    st.executeUpdate(qry);


                    conn.close();
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText("ALL LOADED ITEMS  DELETED SUCCESSFULLY!");
                    alert1.showAndWait();





                    try {
                        root = FXMLLoader.load(getClass().getResource("editItem.fxml"));
                        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentScene = new Scene(root);
                     //   String css = this.getClass().getResource("recentItem.css").toExternalForm();
                     //   root.getStylesheets().add(css);
                        currentStage.setScene(currentScene);

                        currentStage.show();
                        root.requestFocus();
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
                        currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
                        currentStage.setResizable(false);
                    } catch (Exception e) {

                    }



                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }



        }

        }



    @FXML
    private ImageView imv;




}
