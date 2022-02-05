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

public class sellerPassword extends DBconnect {

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

            String ID=getN1();

                try {
                    openConn(conn);
                    qry="INSERT INTO password(ID,name,password) values('"+ID+"','"+username+"','"+password+"')";

                    st=conn.createStatement();
                    st.executeUpdate(qry);
                    conn.close();
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
