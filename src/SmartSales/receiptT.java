package SmartSales;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.print.PrinterJob;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

import java.awt.print.PageFormat;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private TextField tfDiscount;

    @FXML
    private Button btNoReceipt;

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
        ShareData.goSales=false;

        c1.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("name"));
        c2.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("qty"));
        c3.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("cost"));
        c4.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("price"));


        c1.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c4.setCellFactory(TextFieldTableCell.forTableColumn());

        getReceipt();

        if (ShareData.oldReceipt){
            getDataForOldReceipt();
            processBulkDiscount();

        }

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
                ShareData.originalBill=String.valueOf(dp2.format(amt2));


            }

        } catch (Exception e) {
          //  e.printStackTrace();
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
                    m.amount = amount;
                    m.change = change;
                    m.cash = cashIssued;
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


    void getBillAmount() {
        double amount=0;
            try {
                amount = Double.parseDouble(lbAmount.getText().trim());
                m.amount = amount;
            } catch (Exception e) {
                amount = 0;
            }





    }



    void getChange() {


        double cashIssued;
        double amount;
        double change;
        try {
            try {
                cashIssued = Double.parseDouble(tfcash.getText().trim());
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
                    m.amount = amount;
                    m.change = change;
                    m.cash = cashIssued;
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



    public void updateCurrentUser() {
        deleteRecord("currentUser");

        if (DBcon()) {
            openConn(ShareData.directConnection);
            try {

                qry = "Insert into currentUser(ID,fName)  values( '" + ShareData.userID_ + "','" +ShareData.currentUser_+ "')";
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




    @FXML
    void closeReceipt(ActionEvent event) {

        if (ShareData.oldReceipt){
            ShareData.oldReceipt=false;
            updateCurrentUser();
        }





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



            insertCusChange();
            updatedDiscountOnTotalCost();
              getNewSalesID();

        openConn(ShareData.directConnection);
        ShareData.preView("customerReport.jasper","Powered BY AECleanCodes 0549822202");


    }


    @FXML
    void printReceipt(ActionEvent event) {
                 prePrintWork();

                 print();









        try {
            r = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
            sst = (Stage) ((Node) event.getSource()).getScene().getWindow();
            sst.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    private void prePrintWork(){

        if (countRecord("receipt","sname")>0){
                getNewSalesID();

            if (m.amount<1){
                getBillAmount();
            }

            insertCusChange();
            updatedDiscountOnTotalCost();
            ShareData.currentRandom_="";
            updateUCostInReceipt();



             if ( ! ShareData.oldReceipt){
               //  deleteRecord("sales");
                 sendReportToSales();
             }


            tbv.getItems().clear();




        }


    }




String  time;
    String  totalBill;
    String  cashIssued;


    public void selectReceipt() {

        qry = "SELECT sName,qty,UPrice,time,totalBill,cashIssued FROM receipt";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {

                DBitem = rs.getString("sName").trim();
                DBqty = rs.getString("qty").trim();
                DBprice = rs.getString("UPrice").trim();
                time=rs.getString("time").trim();
                totalBill=rs.getString("totalBill").trim();
                cashIssued=rs.getString("cashIssued").trim();


            } else {
                System.out.println(" no match in two tables: ");
            }
        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
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
                        " amount,profit,time,totalBill,cashIssued,CusChange," +
                        " discountAmount, discountOnTotalCost,discountNumber )" +
                        " SELECT  userID, salesID, sName, qty,Ucost,Uprice,amount," +
                        " profit,time,totalBill,cashIssued,CusChange," +
                        " discountAmount, discountOnTotalCost,discountNumber" +
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
                    e.printStackTrace();
                }
            }
        }


    }

    @FXML
    void noPrinting(ActionEvent event) {






        Task task=new Task() {
            @Override
            protected Object call() throws Exception {

                try {




                    prePrintWork();
                    deleteRecord("receipt");
                    getNewSalesID();




                }
                catch (Exception e){
                    e.printStackTrace();
                }



                return null;
            }

        };

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(task);
        executorService.shutdown();























        try {
            r = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
            sst = (Stage) ((Node) event.getSource()).getScene().getWindow();

            sst.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

double bulkDiscount;
    double bulkDiscountR;
    private boolean validateDiscount(){
        String i=tfDiscount.getText().trim();
        if (i.isEmpty()){
            getAmount();
            return false;
        }
        else {
            try {
               double ii=Double.parseDouble(i);

               if (ii==0){
                   getAmount();
               }

               bulkDiscount=ii/100;
               bulkDiscountR=ii;
               return true;

            }
            catch (Exception e){
                getAmount();
               return false;
            }


        }

    }

    double billAmount;
    private boolean validateBillAmount(){
        String i=ShareData.originalBill;
        if (i.isEmpty()){
            return false;
        }
        else {
            try {
                billAmount=Double.parseDouble(i);


               return  true;
            }
            catch (Exception e){
                return false;
            }


        }



    }


    private double getLbAmount(){
        String i=lbAmount.getText().trim();
        double x=0.00;
        if (i.isEmpty()){

        }
        else {
            try {
                x=Double.parseDouble(i);

            }
            catch (Exception e){

            }


        }


        return x;
    }
    double discount=ShareData.discount;
    @FXML
    void processBulkDiscount(KeyEvent event) {
             if (validateDiscount() && validateBillAmount()){

                  discount=billAmount * bulkDiscount;
                 double discountedAmount=billAmount-discount;
                 lbAmount.setText(String.valueOf(dp2.format(discountedAmount)));

                 getChange();


             }
    }


    void processBulkDiscount() {
        if (validateDiscount() && validateBillAmount()){

            discount=billAmount * bulkDiscount;
            double discountedAmount=billAmount-discount;
            lbAmount.setText(String.valueOf(dp2.format(discountedAmount)));

            getChange();


        }
    }

    public void updatedDiscountOnTotalCost() {

        if (DBcon()) {

            try {

                qry = "update receipt set discountOnTotalCost="+dp2.format(getLbAmount())+", " +
                        "discountAmount="+dp2.format(discount)+", discountNumber= "+bulkDiscountR+"  where not sname= '' ";
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
                    ee.printStackTrace();

                }
            }
        }


    }

    @FXML
    private Button btBack;

    public  void print(){


        Task task=new Task() {
            @Override
            protected Object call() throws Exception {

                try {
                 String jp;
                    Map param = new HashMap();
                    try {

                        openConn(ShareData.directConnection);

                        jp = JasperFillManager.fillReportToFile("customerReport.jasper"
                                , param, ShareData.directConnection);


                        if (jp!=null){
                            JasperPrintManager.printReport(jp,false);

                        }
                        else {

                        }



                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }



                return null;
            }

        };
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(task);
        executorService.shutdown();

        deleteRecord("receipt");
        deleteRecord("currentinvoice");
        getNewSalesID();
    }



  private  void   getDataForOldReceipt(){

          qry = "SELECT discountOnTotalCost,discountAmount ,CusChange,cashIssued,discountNumber  from receipt";

          try {
              DBcon();
              st = conn.createStatement();
              rs = st.executeQuery(qry);

              if (rs.next()) {
               String cash  = rs.getString("cashIssued").trim();
                  String change = rs.getString("CusChange").trim();
                  String discountTotalCost = rs.getString("discountOnTotalCost").trim();
                  String discountNumber = rs.getString("discountNumber").trim();

                  lbChange.setText(change);
                  lbAmount.setText(discountTotalCost);
                  tfDiscount.setText(discountNumber);
                  tfcash.setText(cash);


                  conn.close();

              } else {
              conn.close();
              }
          } catch (Exception e) {
              System.out.println("here");
              e.printStackTrace();

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

    public void updatedReceiptSetSalesID() {

        if (DBcon()) {

            try {

                qry = "update receipt set salesID= '"+ShareData.currentRandom_+"'";
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
                    ee.printStackTrace();

                }
            }
        }


    }


    private void getNewSalesID(){

        deleteRecord("currentinvoice");
       openConn(ShareData.directConnection);
            ShareData.currentRandom_=getRandom().trim();
            while ((doesThisExist("invoiceID","ID",ShareData.currentRandom_) )){
                ShareData.currentRandom_=getRandom().trim();
            }
            insertNewInvoiceID();

            updatedReceiptSetSalesID();

    }



    @FXML
    void showOldReceipt(ActionEvent event) {

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

}