package SmartSales;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class setBusinessName extends DBconnect {

    public AnchorPane fmAdminDetails;
    String businessName ="";
    String businessLocation="";
    String mobile="";

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
    private TextField textFieldUser;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldConfirmPassword;

    @FXML
    void delete(ActionEvent event) {
        textFieldUser.clear();
        textFieldPassword.clear();
        textFieldConfirmPassword.clear();
    }

    @FXML
    void submit(ActionEvent event) {
        if (validate()) {
            saveRecord();
                 // SWITCH WINDOW
                 try{
                     root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
                     currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                     currentScene=new Scene(root);
                     String css=this.getClass().getResource("mainLock.css").toExternalForm();
                     root.getStylesheets().add(css);
                     currentStage.setScene(currentScene);

                     currentStage.show();
                     root.requestFocus();
                 }
                 catch (Exception e){

                 }
        }
    }

    /// DETAILS
    public boolean validate(){
        getFocus1();
        String tf1=textFieldUser.getText().trim();
        String tf2=textFieldPassword.getText().trim();
        String tf3=textFieldConfirmPassword.getText().trim();

        if (tf1.isEmpty()){
            textFieldUser.clear();
        }
        else         if (tf2.isEmpty()){
            textFieldPassword.clear();
        }
        else         if (tf3.isEmpty()){
            textFieldConfirmPassword.clear();
        }

        else {


            businessName =tf1;
            businessLocation=tf2;
            mobile=tf3;

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tCONGRATS!");
            alert.showAndWait();



            return true;
        }
        return false;
    }

    public boolean saveRecord(){
        deleteRecord("businessName");
        qry="INSERT INTO businessName(name,location,mobile) " +
                "values('"+ businessName +"','"+businessLocation+"',+'"+mobile+"')";


        if (DBcon()){
                try {
                    openConn(conn);
                    st=conn.createStatement();
                    st.executeUpdate(qry);
                    textFieldUser.clear();

                    textFieldPassword.clear();
                    textFieldConfirmPassword.clear();
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

        if (textFieldUser.getText().trim().isEmpty()){
            textFieldUser.requestFocus();
        }

        else   if (textFieldPassword.getText().trim().isEmpty()){
            textFieldPassword.requestFocus();
        }

        else   if (textFieldConfirmPassword.getText().trim().isEmpty()){
            textFieldConfirmPassword.requestFocus();
        }







    }
}
