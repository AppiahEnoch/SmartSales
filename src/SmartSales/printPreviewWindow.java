package SmartSales;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class printPreviewWindow extends DBconnect {

    @FXML
    private AnchorPane pane1;

    @FXML
    private Label lbCompany;

    @FXML
    private Label lbLocation;

    @FXML
    private Label lbMobile;

    @FXML
    private Label lbDate;

    @FXML
    private Label lbReceiptID;

    @FXML
    private TableView<RECEIPT> tbvReceipt;

    @FXML
    private TableColumn<RECEIPT, String> c1;

    @FXML
    private TableColumn<RECEIPT, String> c2;

    @FXML
    private TableColumn<RECEIPT, String>  c3;

    @FXML
    private TableColumn<RECEIPT, String> c4;

    @FXML
    private Label lbBillAmount;

    @FXML
    private Label lbAmountPaid;

    @FXML
    private Label lbChange;

    @FXML
    private Label lbUnderline1;

    String styleCompany= "-fx-fill:red;-fx-font-size:20px; -fx-font-weight:bold;";
    String styleDate= "-fx-fill:Black;-fx-font-size:13px; -fx-font-weight:bold;";
    String styleNO= "-fx-fill:Black;-fx-font-size:14px; -fx-font-weight:bold;";
    String styleContent= "-fx-fill:Black;-fx-font-size:11px";
    StringBuilder stringBuilder=new StringBuilder();
    Text txtContent=new Text();


    DecimalFormat dp2 = new DecimalFormat("0.00");


    @FXML
    private Stage currentStage;


    @FXML
    public void initialize(){


        c1.setCellValueFactory(new PropertyValueFactory<RECEIPT, String>("item"));
        c2.setCellValueFactory(new PropertyValueFactory<RECEIPT, String>("qty"));
        c3.setCellValueFactory(new PropertyValueFactory<RECEIPT, String>("price"));
        c4.setCellValueFactory(new PropertyValueFactory<RECEIPT, String>("total"));



                  setTaRubrics();
                  getReceipt();
ShareData m=ShareData.getInstance();




DecimalFormat dp2=new DecimalFormat("0.00");


        try {

            double io=m.amount;
            double cash=m.cash;
            double change=m.change;


            lbAmountPaid.setText("Cash Issued:   "+String.valueOf(dp2.format(cash)));
            lbBillAmount.setText("Bill Amount:   "+String.valueOf(dp2.format(io)));
            lbChange.setText("Change:           "+String.valueOf(dp2.format(change)));



        }
        catch (Exception e){
e.printStackTrace();
        }

        if (ShareData.isPrint){
            System.out.println("printiong...");
            ShareData.print(pane1);


        }
    }



    private void setTaRubrics(){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date=new Date();
        lbDate.setText(dateFormat.format(date));
        lbLocation.setText("Location: TANOSO");
        lbMobile.setText("Mobile: 0549822202");
        lbReceiptID.setText(getRandom());






        try {

//
//
//        //    tf.getChildren().addAll(txtCompany,txtLocation,
//                  txtDate,txtReceiptNo,txtCashier,
//                  txtLine,txtLabel,txtContent);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    private void getReceipt() {


        StringBuilder itemName=new StringBuilder();

        stringBuilder.delete(0,stringBuilder.length());
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

              tbvReceipt.getItems().addAll(new RECEIPT(DBitem,DBqty,DBprice,amount));
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
  txtContent.setText(stringBuilder.toString());



    }




    @FXML
    void closeWindow(ActionEvent event) {

        try {


            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (Exception e){
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


        s1 = "AEC"+ i + "" + cc1 + ii + iii + cc3 + cc2 + m + mm + cu;





        return s1;
    }
}
