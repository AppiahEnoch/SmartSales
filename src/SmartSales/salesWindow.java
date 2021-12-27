package SmartSales;

import javafx.application.Platform;
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
        ShareData.hitRun=true;
        ShareData.currentRandom_=getRandom().trim();
        while ((doesThisExist("invoiceID","ID",ShareData.currentRandom_) )){
            ShareData.currentRandom_=getRandom().trim();
        }

        insertNewInvoiceID();


tbV.setEditable(true);
col2.setEditable(true);
        col1.setCellValueFactory(new PropertyValueFactory<ITEM, String>("item"));
        col2.setCellValueFactory(new PropertyValueFactory<ITEM, String>("qty"));
        col3.setCellValueFactory(new PropertyValueFactory<ITEM, String>("price"));

        col1.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setCellFactory(TextFieldTableCell.forTableColumn());


                     deleteRecord("receipt");




                startThread();

        if (isTableviewEmpty()) {
            imageV.setImage(null);
        }


tf.requestFocus();
    }





    @FXML
    void showPopImg(MouseEvent event){


              getSelected("");





        try {

            r = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
            //   String   css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            //   r.getStylesheets().add(css);


            sst.close();
        }
        catch (Exception e){

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




        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public void insertCusChange() {

        if (DBcon()) {

            try {

                qry = "Update receipt set totalBill = " + m.amount + ",cashIssued= " + m.cash;
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {

                }
            }
        }


    }

    public void updateUCostInReceipt() {

        if (DBcon()) {

            try {

                qry = "update receipt " +
                        "set receipt.UCost=(select item.cost from item where " +
                        "item.sName=receipt.sName ) where not  receipt.sName =''";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {

                }
            }
        }


    }


    public void sendReportToSales() {

        if (DBcon()) {

            try {

                qry = "INSERT INTO sales ( userID,salesID, sName, qty,Ucost,Uprice," +
                        " amount,profit,time,totalBill,cashIssued,CusChange)" +
                        " SELECT  userID, salesID, sName, qty,Ucost,Uprice,amount," +
                        " profit,time,totalBill,cashIssued,CusChange " +
                        " FROM receipt";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {

                }
            }
        }


    }



    private void prePrintWork(){
        insertCusChange();
        ShareData.currentRandom_="";
        updateUCostInReceipt();

        // deleteRecord("sales");
        sendReportToSales();
        deleteRecord("receipt");



    }



    @FXML
    void goMainWindow(ActionEvent event) {

        if (ShareData.hitRun){
          deleteNewInvoiceID();

        }

        if (countRecord("receipt","sName")>0){


            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "YOU HAVE ITEMS ON THE RECEIPT.\n" +
                                    "DID YOU  SELL THEM?",
                            ButtonType.YES,
                            ButtonType.NO);
            alert.setTitle("ITEMS ARE ON RECEIPT.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.YES) {

                /// ALL PRINT STUFF
                
                prePrintWork();

                try {
                    r = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
                    sst = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    sst.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }


        }





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
    boolean fillTbV2(KeyEvent event) {
        String changedItem="";
        String changedQuantity="";
        try {
            ITEM colSelected1 = tbV.getSelectionModel().getSelectedItem();
            Object n=colSelected1.getItem();
            Object ID=colSelected1.getQty();
             changedItem=n.toString().trim();
            changedQuantity=ID.toString();
            System.out.println("changedItem: "+changedItem);
            System.out.println("changedQuantity: "+changedQuantity);
        }
        catch (Exception ignore){

        }





                DBcon();
        openConn(conn);
        String i = "";

        try {
                String keyword = tf.getText().trim();
                    tbV.getItems().clear();

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
                            q="1";

                            String p = rs.getString("price").trim();


                            if (!(changedItem.trim().isEmpty())){
                                if (changedItem.equals(s)){
                                    q=changedQuantity;
                                }
                            }


                                tbV.getItems().addAll(new ITEM(s, q, p));

                            System.out.println("fet:");

                        }



                    } catch (Exception e) {
                        e.printStackTrace();

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

    }

    @FXML
    void stopThread() {
        m.continueItemSuggestion = false;
    }



@FXML
    public void editQty(TableColumn.CellEditEvent editEvent) {
    ITEM c = tbV.getItems().get(editEvent.getTablePosition().getRow()).setQty(editEvent.getNewValue());
        ITEM colSelected1 = tbV.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getQty();
        Object ID=colSelected1.getItem();

    System.out.println(" edit:"+n.toString());



    }







    Stage sst = new Stage();
    Scene scene;
    Parent r;
    @FXML
    void showReceipt() {


        try {
            r = FXMLLoader.load(getClass().getResource("receiptT.fxml"));

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            sst.setX((primScreenBounds.getWidth() - sst.getWidth()) / 2);
            sst.setY((primScreenBounds.getHeight() - sst.getHeight()) / 2);
           sst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {

            r = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
            sst.setTitle("Smart Sales - AECleanCodes");
            sst.setScene(new Scene(r));

           // sst.initStyle(StageStyle.UTILITY);
            sst.show();


            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            sst.setX((primScreenBounds.getWidth() - sst.getWidth()) / 2);
            sst.setY((primScreenBounds.getHeight() - sst.getHeight()) / 2);



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


    @FXML
    public void addToReceipt(ActionEvent event) {

            getSelected();

        tf.clear();
        tbV.getSelectionModel().clearSelection();

         tbV.getItems().clear();
    }
    void getSelected() {

        try {
            ITEM colSelected1 = tbV.getSelectionModel().getSelectedItem();
            String item = colSelected1.getItem().toString().trim();
            String qty = colSelected1.getQty().toString().trim();
            String price = colSelected1.getPrice().toString().trim();


        Object object=    tbV.getSelectionModel().getSelectedIndex();
            m.intData1=Integer.parseInt(object.toString());




            boolean isWrong=false;

            try {

               if (qty.isEmpty()){
                 isWrong=true;
               }

                if (price.isEmpty()){
                    isWrong=true;
                }



                if (!isWrong){
                    m.stringData1=item.toString();
                    double p=Double.parseDouble(price);
                    int q=Integer.parseInt(qty);


                    insertIntoReceipt(item,q,p);
                    ShareData.hitRun=false;
                }

            }
            catch (Exception ignore){

            }



        } catch (Exception e) {
e.printStackTrace();
        }


    }

    void getSelected(String forImage) {

        try {
            ITEM colSelected1 = tbV.getSelectionModel().getSelectedItem();
            String item = colSelected1.getItem().toString().trim();



            Object object=    tbV.getSelectionModel().getSelectedIndex();
            m.intData1=Integer.parseInt(object.toString());





                    m.stringData1=item.toString();





            }
            catch (Exception ignore){

            }



        }





    public void insertIntoReceipt(String item, int qty, double price) {
        DBcon();
        openConn(conn);
        try {
            qry = "INSERT ignore INTO receipt ( userID, salesID,  sName,qty,UPrice)" +
                    " values('" + ShareData.userID_ + "',  '" + ShareData.currentRandom_ + "','"
                    + item + "','" + qty + "','" + price + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
            m.continueItemSuggestion=true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }
    }

    public String getRandom() {
        String s1 = "";


        char cc1;
        char cc2;
        char cc3;

        Double c1 = (Math.random() * 100);
        Double c2 = Math.random() * 200;
        Double c3 = Math.random() * 200;

        int r1 = (int) Math.round(c1);
        int r2 = (int) Math.round(c2);
        int r3 = (int) Math.round(c3);

        int m = (int) (Math.random() * 50);

        int mm = (int) (Math.random() * 9770);

        int u = (int) (Math.random() * 100);

        int ctr = 0;

        while (u < 64) {
            ctr++;
            u++;

            if (ctr % 3 == 0) {
                u += ctr;
            }
        }

        int ctr1 = 0;

        while (u > 90) {
            ctr1--;
            u--;
            if (ctr % 5 == 0) {
                u -= ctr1;
            }
        }

        if (u < 1) {
            u = 87;
        }


        char cu = (char) u;


        while (m > 9) {
            m--;
        }

        while (r1 < 64) {
            r1++;
        }
        while (r1 > 122) {
            r1--;
        }

        while (r2 < 64) {
            r2++;
        }
        while (r2 > 122) {
            r2--;
        }
        while (r3 < 64) {
            r3++;
        }
        while (r3 > 122) {
            r3--;
        }
        while (r1 > 90 && r1 < 97) {
            r1--;
        }
        while (r2 > 90 && r2 < 97) {
            r2--;
        }
        while (r3 > 90 && r3 < 97) {
            r3--;
        }

        if (r1 == r2) {
            r1 = r1 + m;
        }

        if (r3 == r2) {
            r3++;
        }


        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);

        if (r3==123){
            r3=66;
        }

        cc1 = (char) r1;
        cc2 = (char) r2;
        cc3 = (char) r3;


        System.out.println(cc1);
        System.out.println(cc2);
        System.out.println(cc3);




        Double n1 = 0.0;
        Double n2;
        Double n3;
        n1 = Math.random() * 100;
        n2 = Math.random() * 80;
        n3 = Math.random() * 1000;

        int i;
        int ii;
        int iii;
        i = (int) Math.round(n1);
        ii = (int) Math.round(n2);
        iii = (int) Math.round(n3);


        s1 = ""+ cc1 + ii + iii + cc3 + cc2 + m + mm + cu +i;





        return s1;
    }

    public void insertNewInvoiceID() {

        if (DBcon()) {
            openConn(ShareData.directConnection);
            try {

                qry = "Insert into invoiceID (ID)  values( '" + ShareData.currentRandom_+ "')";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }

       insertCurrentInvoiceID();
    }



    public void insertCurrentInvoiceID() {
          deleteRecord("currentInvoice");
        if (DBcon()) {
            openConn(ShareData.directConnection);
            try {

                qry = "Insert into currentInvoice (ID)  values( '" + ShareData.currentRandom_+ "')";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }


    }



    public void deleteNewInvoiceID() {
        DBcon();

        try {
            qry = "DELETE  FROM invoiceID where ID= '"+ShareData.currentRandom_+"'";
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }
    }
}

