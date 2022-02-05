package SmartSales;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;

public class logAdmin extends DBconnect {

    public AnchorPane fmAdminDetails;
    String username="";
    String password="";

    public TextField tfBussiness;
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private Label lbPlease;

    @FXML
    private Button btSubmit;

    @FXML
    private FontAwesomeIconView fAdd;

    @FXML
    private Button btDel;

    @FXML
    private FontAwesomeIconView fAdd1;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private PasswordField passwordField3;

    @FXML
    private FontAwesomeIconView e1;

    @FXML
    private FontAwesomeIconView e2;

    @FXML
    private FontAwesomeIconView e3;

    @FXML
    private TextField textFieldUser;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldConfirmPassword;

    @FXML
    void delete(ActionEvent event) {

        passwordField2.clear();
        passwordField1.clear();
        passwordField3.clear();
    }

    @FXML
    void show_1(MouseEvent event) {
   textFieldUser.setText(passwordField1.getText().trim());
   passwordField1.setVisible(false);
   textFieldUser.setVisible(true);

    }

    @FXML
    void show_2(MouseEvent event) {
        textFieldPassword.setText(passwordField2.getText().trim());
        passwordField2.setVisible(false);
        textFieldPassword.setVisible(true);

    }

    @FXML
    void show_3(MouseEvent event) {
        textFieldConfirmPassword.setText(passwordField3.getText().trim());
        passwordField3.setVisible(false);
        textFieldConfirmPassword.setVisible(true);

    }



    @FXML
    void turnOff_1(MouseEvent event) {
        passwordField1.setVisible(true);
        textFieldUser.setVisible(false);
    }

    @FXML
    void turnOff_2(MouseEvent event) {
        passwordField2.setVisible(true);
        textFieldPassword.setVisible(false);
    }

    @FXML
    void turnOff_3(MouseEvent event) {
        passwordField3.setVisible(true);
        textFieldConfirmPassword.setVisible(false);
    }

    @FXML
    void submit(ActionEvent event) {
        if (validate()) {



             if (saveAdmindetails()){
                 // SWITCH WINDOW
                 try{
                     root = FXMLLoader.load(getClass().getResource("setBusinessName.fxml"));
                     currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                     currentScene=new Scene(root);
                     String css=this.getClass().getResource("setBusinessName.css").toExternalForm();
                     root.getStylesheets().add(css);
                     currentStage.setScene(currentScene);
                     System.out.println(" switched");
                     currentStage.show();
                     root.requestFocus();

                 }
                 catch (Exception e){


                 }

             }
        }
    }













    /// DETAILS









    public boolean validate(){
        getFocus1();
        textFieldUser.setText(passwordField1.getText().trim());
        textFieldPassword.setText(passwordField2.getText().trim());
        textFieldConfirmPassword.setText(passwordField3.getText().trim());

        String tf1=textFieldUser.getText().trim();
        String tf2=textFieldPassword.getText().trim();
        String tf3=textFieldConfirmPassword.getText().trim();

        if (tf1.isEmpty()){
            passwordField1.clear();

        }
        else         if (tf2.isEmpty()){
            passwordField2.clear();

        }
        else         if (tf3.isEmpty()){
            passwordField3.clear();

        }
        else  if(!(tf2.equals(tf3))){

            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PASSWORD MISMATCH!");
            alert.showAndWait();
            passwordField3.clear();
            textFieldUser.clear();
        }
        else {
            username=tf1;
            password=tf2;

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tCONGRATS!");
            alert.showAndWait();
            passwordField1.clear();
            passwordField2.clear();
            passwordField3.clear();
            textFieldUser.clear();
            textFieldPassword.clear();
            textFieldConfirmPassword.clear();
            return true;

        }


        return false;
    }

    public boolean saveAdmindetails(){

        if (DBcon()){

            if ( createTables()){
                try {
                    deleteRecord("admin");
                    openConn(conn);
                    qry="INSERT INTO admin(name,password) values('"+username+"','"+password+"')";
                    st=conn.createStatement();
                    st.executeUpdate(qry);

                    conn.close();
                    return true;
                }
                catch (Exception e){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("COULD NOT SAVE DATA KKK: "+e.getMessage());
                    alert.showAndWait();
                    try {
                        conn.close();
                    }
                    catch (Exception ee){

                    }
                }



            }



        }

     return false;
    }




    public boolean createTables(){

        String admin = "CREATE TABLE IF NOT EXISTS " +
                "admin(name varchar(100) NOT NULL,password varchar(100) " +
                "NOT NULL,time timestamp  DEFAULT CURRENT_TIMESTAMP)";


        String businessName = "CREATE TABLE IF NOT EXISTS " +
                "businessName(name varchar(100) NOT NULL,location varchar(100) " +
                ",mobile varchar(20))";

        String seller = "CREATE TABLE IF NOT EXISTS " +
                "seller(ID varchar(400) primary key, name varchar(100) NOT NULL,location varchar(100) " +
                ",mobile varchar(20),prev varchar(1) default null)";

        String password= "CREATE TABLE IF NOT EXISTS " +
                "password(ID varchar(400), name varchar(255) NOT NULL,password varchar(255)," +
                "CONSTRAINT FK_sellerPassword FOREIGN KEY(ID) references seller(ID)" +
                "ON UPDATE CASCADE ON DELETE CASCADE)";



        String data= "CREATE TABLE IF NOT EXISTS " +
                "data(n1 varchar(400),n2 int,s1 varchar(255),s2 varchar(255))";

        String tmpL1= "CREATE TABLE IF NOT EXISTS " +
                "tmpL1(ID int,item varchar(255),qty int,cost double,price double)";

        String item= "CREATE TABLE IF NOT EXISTS " +
                "item(fName varchar(400),sName varchar(400)" +
                " PRIMARY KEY,qty int,cost double,price double,img LONGBLOB,time timestamp  " +
                "DEFAULT CURRENT_TIMESTAMP," +
                " usedQty int,  availQty int as (qty-usedQty), " +
                "declareFinishing int default 30," +
                " availPercentage int as ((availQty/qty)*100))";



        String item1= "CREATE TABLE IF NOT EXISTS " +
                "item1(id int primary key auto_Increment, fName varchar(400),sName varchar(400)" +
                ",  qty int,cost double,price double,time timestamp  DEFAULT CURRENT_TIMESTAMP)";


        String receipt= "CREATE TABLE IF NOT EXISTS " +
                "receipt(userID varchar(400),salesID varchar(400),sName varchar(400) PRIMARY KEY" +
                ",  qty int,UCost double, UPrice double," +
                " amount double as (qty*UPrice)," +
                "profit double as((UPrice-UCost)*qty),time timestamp " +
                " DEFAULT CURRENT_TIMESTAMP," +
                "totalBill  double DEFAULT 1.00," +
                " discountOnTotalCost  double DEFAULT 2.00, " +
                "discountAmount  double DEFAULT 3.00," +
                "cashIssued  double(7,2)," +
                "CusChange double as (cashIssued-totalBill )," +
                "discountNumber  double DEFAULT 4.00) ";




        String sales= "CREATE TABLE IF NOT EXISTS " +
                "sales(userID varchar(400), salesID varchar(400),sName varchar(400)" +
                ",  qty int,UCost double, UPrice double," +
                " amount double," +
                "profit double,time timestamp " +
                " DEFAULT CURRENT_TIMESTAMP," +
                "totalBill  double DEFAULT 0.00," +
                " discountOnTotalCost  double DEFAULT 2.00," +
                " discountAmount  double DEFAULT 0.00," +
                "cashIssued  double DEFAULT 0.00," +
                "CusChange double(7,2)," +
                "discountNumber  double DEFAULT 0.00 ," +
                " profitSum double DEFAULT 0.00 )";


        String invoiceID = "CREATE TABLE IF NOT EXISTS " +
                "invoiceID(ID varchar(100) NOT NULL)";

        String currentInvoice = "CREATE TABLE IF NOT EXISTS " +
                "currentInvoice(ID varchar(100) NOT NULL)";

        String currentUser = "CREATE TABLE IF NOT EXISTS " +
                "currentUser( ID varchar(100), fName varchar(100), sName varchar(100))";

        String log = "CREATE TABLE IF NOT EXISTS " +
                "log( ID varchar(100),name varchar(100),inTime timestamp DEFAULT CURRENT_TIMESTAMP," +
                " outTime timestamp)";

        String salesDistinctSalesID = "create " +
                "or replace  view  salesDistinctSalesID " +
                "as select * from sales group by salesID";

        String salesView = "create or replace view salesView as" +
                " SELECT * FROM smart.sales";

        String costView = "CREATE or replace view costView as select * from item1";

        String profitSum = "create or replace view profitSum as SELECT " +
                "salesID, sum(profit) as profit  FROM smart.sales group by sales.salesID";


        String reportCost = "CREATE TABLE IF NOT EXISTS " +
                "reportCost(cToday varchar(100),cThisWeek varchar(100),cLastWeek varchar(100), " +
                "cThisMonth varchar(100), cLastMonth varchar(100), cThisYear varchar(100), cLastYear varchar(100)," +
                "cOverall varchar(100))";

        String reportSales = "CREATE TABLE IF NOT EXISTS " +
                "reportSales(sToday varchar(100),sThisWeek varchar(100),sLastWeek varchar(100), " +
                "sThisMonth varchar(100), sLastMonth varchar(100), sThisYear varchar(100), sLastYear varchar(100)," +
                "sOverall varchar(100))";

        String reportProfit = "CREATE TABLE IF NOT EXISTS " +
                "reportProfit (pToday varchar(100),pThisWeek varchar(100),pLastWeek varchar(100), " +
                "pThisMonth varchar(100), pLastMonth varchar(100), pThisYear varchar(100), pLastYear varchar(100)," +
                "pOverall varchar(100))";

        String pdfView = "create or replace view pdfView as select * from reportcost,reportsales,reportprofit,businessName";



        if (DBcon()){
            try {
               openConn(conn);
                st=conn.createStatement();
                st.execute(admin);
                st.execute(businessName);
                st.execute(seller);
                st.execute(password);
                st.execute(tmpL1);
                st.execute(data);
                st.execute(item);
                st.execute(item1);
                st.execute(sales);
                st.execute(receipt);
                st.execute(invoiceID);
                st.execute(currentInvoice);
                st.execute(currentUser);
                st.execute(salesDistinctSalesID);
                st.execute(log);
                st.execute(salesView);
                st.execute(costView);
                st.execute(profitSum);
                st.execute(reportCost);
                st.execute(reportSales);
                st.execute(reportProfit);
                st.execute(pdfView);



                conn.close();
              return true;
            }
            catch (Exception e){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("DATABASE ERROR:"+e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                }
                catch (Exception ee){

                }
            }


        }

return false;
    }

    void getFocus1(){

        if (passwordField1.getText().trim().isEmpty()){
            passwordField1.requestFocus();
        }

        else   if (passwordField2.getText().trim().isEmpty()){
            passwordField2.requestFocus();
        }

        else   if (passwordField3.getText().trim().isEmpty()){
            passwordField3.requestFocus();
        }







    }
}
