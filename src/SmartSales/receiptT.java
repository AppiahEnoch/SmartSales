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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Optional;

public class receiptT extends DBconnect {

    Stage sst = new Stage();

    Scene scene;
    Parent r;
    @FXML
    Button btOk;

    @FXML
    Label lbAmount;
    @FXML
    Label lbChange;

    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    TextField tfcash;
    @FXML
    private TableView<ITEM2> tbv;

    @FXML
    private TableColumn<ITEM2, String> c;


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
    String getSelected() {

        ITEM2 colSelected = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected.getName();
        return n.toString();
    }


    @FXML
    void goBackR(ActionEvent event) {

        sst.close();

        try {
            root = FXMLLoader.load(getClass().getResource("viewItems.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
            String css = this.getClass().getResource("viewItems.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);

            loadItemManual ob = new loadItemManual();
            ob.writeAllNoImageItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        c1.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("name"));
        c2.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("qty"));
        c3.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("cost"));
        c4.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("price"));


        c1.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c4.setCellFactory(TextFieldTableCell.forTableColumn());


        getReceipt();

    }

    DecimalFormat dp2 = new DecimalFormat("0.00");


    private void getAmount() {

        DBcon();
        openConn(conn);
        qry = "select sum(amount) as gg from receipt";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("gg").trim();
                double amt2 = Double.parseDouble(amt);

                lbAmount.setText((String.valueOf(dp2.format(amt2))));


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

    private void getReceipt() {
        tbv.getItems().clear();
        DBcon();
        openConn(conn);
        qry = "select  sName,qty,amount,UPrice,time from receipt order by time desc";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);
            while (rs.next()) {

                DBitem = rs.getString("sName").trim();
                DBqty = rs.getString("qty").trim();
                DBCost = rs.getString("amount").trim();
                DBprice = rs.getString("UPrice").trim();

                String amount = null;
                double b = Double.parseDouble(DBCost);

                amount = dp2.format(b);


                tbv.getItems().addAll(new ITEM2(DBitem, DBqty, amount, DBprice));
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

        getAmount();
    }


    @FXML
    void getChange(KeyEvent event) {
        double cashIssued;
        double amount;
        double change;
        try {
            try {
                cashIssued = Double.parseDouble(tfcash.getText().trim());
                ;
            } catch (Exception e) {
                cashIssued = 0;
            }

            try {
                try {
                    amount = Double.parseDouble(lbAmount.getText().trim());
                } catch (Exception e) {
                    amount = 0;
                }

                if (cashIssued > 0 && amount > 0) {
                    change = cashIssued - amount;
                    DecimalFormat dp2 = new DecimalFormat("0.00");
                    lbChange.setText((String.valueOf(dp2.format(change))));
                } else {
                    lbChange.setText("#####");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editItem(TableColumn.CellEditEvent editEvent) {
        ITEM2 c = tbv.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected1.getName();
        Object id = colSelected1.getId();
        String nn = m.stringData1;
        updateItem("sName", n, nn);


    }

    @FXML
    void editQty(TableColumn.CellEditEvent editEvent) {
        ITEM2 c = tbv.getItems().get(editEvent.getTablePosition().getRow()).setQty(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n = colSelected1.getQty();
        Object id = colSelected1.getId();
        Object itemX = colSelected1.getName();
        String itemName = itemX.toString().trim();


        updateItem("qty", n, m.stringData1);

        updateQtyReceipt(itemName, Integer.parseInt(n.toString().trim()));

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

    public void updateQtyReceipt(String itemName, int qty) {
        DBcon();
        qry = "UPDATE receipt SET qty= " + qty + " where  sName= '" + itemName + "' ";
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

        getReceipt();
    }


    public void deleteSelectedItem() {
        openConn(conn);
        try {

            r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
            //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            //   r.getStylesheets().add(css);


            sst.close();
        } catch (Exception e) {

        }


        qry = "DELETE FROM receipt where sName='" + m.stringData1 + "'";
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
    void showPopImg(MouseEvent event) {

        ShareData m = ShareData.getInstance();
        m.stringData1 = getSelected();

        try {

            r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
            //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            //   r.getStylesheets().add(css);


            sst.close();
        } catch (Exception e) {

        }


        try {
            r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
            //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            //   r.getStylesheets().add(css);
            sst.setTitle("Smart Sales - AECleanCodes");
            sst.setScene(new Scene(r));
            r.requestFocus();


            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            sst.setX(primScreenBounds.getMinX() + primScreenBounds.getWidth() - 500);
            sst.setY(primScreenBounds.getMinY() + primScreenBounds.getHeight() - 300);

            sst.show();


        } catch (Exception e) {
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

                    getReceipt();

                    try {
                        root = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
                        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentScene = new Scene(root);
//                        String css = this.getClass().getResource("viewItems.css").toExternalForm();
//                        root.getStylesheets().add(css);
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

                    r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
                    //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
                    //   r.getStylesheets().add(css);


                    sst.close();
                } catch (Exception e) {

                }


                DBcon();
                qry = "DELETE FROM    receipt";
                try {
                    st = conn.createStatement();
                    st.executeUpdate(qry);


                    conn.close();
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText("ALL LOADED ITEMS  DELETED SUCCESSFULLY!");
                    alert1.showAndWait();

                    try {
                        root = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
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


    @FXML
    void closeReceipt(ActionEvent event) {


        try {

            r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
            //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            //   r.getStylesheets().add(css);


            sst.close();
        } catch (Exception e) {

        }


        try {
            root = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
            //   String css = this.getClass().getResource("recentItem.css").toExternalForm();
            //   root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.close();


        } catch (Exception e) {

        }


    }

    @FXML
    void openPreview(ActionEvent event) {


        try {
            r = FXMLLoader.load(getClass().getResource("printPreviewWindow.fxml"));
            //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            //   r.getStylesheets().add(css);
            sst.setTitle("Smart Sales - AECleanCodes");
            sst.setScene(new Scene(r));
            r.requestFocus();


            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            sst.setX((primScreenBounds.getWidth() - sst.getWidth()) / 2);
            sst.setY((primScreenBounds.getHeight() - sst.getHeight()) / 2);
            sst.setResizable(false);

            sst.show();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }





    @FXML
    void printReceipt(ActionEvent event) {

        ShareData.isPrint=true;

        try {

            try {
                r = FXMLLoader.load(getClass().getResource("printPreviewWindow.fxml"));
                sst = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(r);
                //   String css = this.getClass().getResource("recentItem.css").toExternalForm();
                //   root.getStylesheets().add(css);
                   sst.setScene(currentScene);

                sst.close();


            } catch (Exception e) {

            }



            ShareData.isPrint=false;
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
