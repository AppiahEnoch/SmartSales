package SmartSales;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


public class changePassword extends DBconnect {

    public AnchorPane fmAdminDetails;
    String username="";
    String password="";
    String initialCode="";

    public TextField tfBussiness;
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;
    @FXML
    private Button btPass;

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
    private TextField tfInitialCode;

    @FXML
    private TextField tfNewUserName;

    @FXML
    private TextField tfPassword;

    @FXML
    private PasswordField passwordFieldConfirm;
    @FXML
    private TextField tfConfirm;

    @FXML
    void delete(ActionEvent event) {

        passwordField2.clear();
        passwordField1.clear();
        passwordField3.clear();
        passwordFieldConfirm.clear();
    }

    @FXML
    void show_1(MouseEvent event) {
   tfInitialCode.setText(passwordField1.getText().trim());
   passwordField1.setVisible(false);
   tfInitialCode.setVisible(true);

    }

    @FXML
    void show_2(MouseEvent event) {
        tfNewUserName.setText(passwordField2.getText().trim());
        passwordField2.setVisible(false);
        tfNewUserName.setVisible(true);

    }

    @FXML
    void show_3(MouseEvent event) {
        tfPassword.setText(passwordField3.getText().trim());
        passwordField3.setVisible(false);
        tfPassword.setVisible(true);

    }



    @FXML
    void turnOff_1(MouseEvent event) {
        passwordField1.setVisible(true);
        tfInitialCode.setVisible(false);
    }

    @FXML
    void turnOff_2(MouseEvent event) {
        passwordField2.setVisible(true);
        tfNewUserName.setVisible(false);
    }

    @FXML
    void turnOff_3(MouseEvent event) {
        passwordField3.setVisible(true);
        tfPassword.setVisible(false);
    }

    @FXML
    void turnOff_4(MouseEvent event) {
        passwordFieldConfirm.setVisible(true);
        tfConfirm.setVisible(false);
    }

    @FXML
    void show_4(MouseEvent event) {
        tfConfirm.setText(passwordFieldConfirm.getText().trim());
        passwordFieldConfirm.setVisible(false);
        tfConfirm.setVisible(true);
    }

    @FXML
    void openMainLock(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("mainLock.css").toExternalForm();
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

        }
    }

    @FXML
    void submit(ActionEvent event) {
        if (validate()) {
             if (saveAdmindetails()){
                 // SWITCH WINDOW
                 try{
                     root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
                     currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                     currentScene=new Scene(root);
                     String css=this.getClass().getResource("mainLock.css").toExternalForm();
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
        tfInitialCode.setText(passwordField1.getText().trim());
        tfNewUserName.setText(passwordField2.getText().trim());
        tfPassword.setText(passwordField3.getText().trim());
        tfConfirm.setText(passwordFieldConfirm.getText().trim());


        String tf1= tfInitialCode.getText().trim();
        String tf2= tfNewUserName.getText().trim();
        String tf3= tfPassword.getText().trim();
        String tf4= tfConfirm.getText().trim();

        if (tf1.isEmpty()){
            passwordField1.clear();

        }
        else         if (tf2.isEmpty()){
            passwordField2.clear();

        }
        else         if (tf4.isEmpty()){
            passwordFieldConfirm.clear();
        }
        else         if (tf3.isEmpty()){
            passwordField3.clear();

        }
        else  if(!(tf3.equals(tf4))){

            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PASSWORD MISMATCH!");
            alert.showAndWait();
            passwordFieldConfirm.clear();
            tfConfirm.clear();
        }
        else {
            initialCode=tf1;
            username=tf2;
            password=tf3;
            return true;
        }
        return false;
    }

    public boolean saveAdmindetails(){
        if (DBcon()){
       if (doesThisExist("password","ID",initialCode) )  {
           try {
               openConn(conn);
               qry="UPDATE password SET name ='"+username+"', password= '"+password+"' where ID='"+initialCode+"'";
               st=conn.createStatement();
               st.executeUpdate(qry);
               conn.close();
               Alert alert=new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText("\t\tCONGRATS!");
               alert.showAndWait();
               passwordField1.clear();
               passwordField2.clear();
               passwordField3.clear();
               tfInitialCode.clear();
               tfNewUserName.clear();
               tfPassword.clear();
               return true;
           }
           catch (Exception e){
               Alert alert=new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("COULD NOT SAVE DATA: "+e.getMessage());
               alert.showAndWait();
               try {
                   conn.close();
               }
               catch (Exception ee){

               }
           }
       }
       else  if(doesThisExist("admin","name",initialCode)){
           deleteRecord("admin");
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
       else  {
               Alert alert=new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText("INVALID ID!\n PLEASE CONTACT ADMINISTRATOR FOR VALID ID");
               alert.showAndWait();
               tfInitialCode.clear();
               passwordField1.clear();
           }
       }

     return false;
    }
}
