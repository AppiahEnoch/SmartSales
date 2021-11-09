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
                " PRIMARY KEY,qty int,cost double,price double,img LONGBLOB)";

        String item1= "CREATE TABLE IF NOT EXISTS " +
                "item1(id int primary key auto_Increment, fName varchar(400),sName varchar(400)" +
                ",  qty int,cost double,price double,time timestamp  DEFAULT CURRENT_TIMESTAMP)";

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


}
