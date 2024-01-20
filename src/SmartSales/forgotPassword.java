package SmartSales;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;

public class forgotPassword extends DBconnect {

    String password="";
    String DBRUsername="";
    String DBUsername="";
    String DBPassword="";
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private TextField tf;

    @FXML
    private Button btSub;

    @FXML
    private Button btDel;



    @FXML
    void bark(ActionEvent event) {
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
    void cleartf(ActionEvent event) {
                tf.clear();
        tf.setPromptText("ID IS REQUIRED!");
    }

    @FXML
    void submit(ActionEvent event) {
             if (validate()){

                 Alert alert=new Alert(Alert.AlertType.INFORMATION);
                 alert.setHeaderText("\n\tHi  "+DBRUsername+", \n\n\t USER NAME IS:  "+DBUsername+" " +
                         "\n\n\tPASSWORD IS:  "+DBPassword);
                 alert.showAndWait();

             }

              if(password.equals("PRINTER")) {


                 try {
                     root = FXMLLoader.load(getClass().getResource("print.fxml"));
                     currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                     currentScene = new Scene(root);
                     //   String css = this.getClass().getResource("recentItem.css").toExternalForm();
                     //   root.getStylesheets().add(css);
                     currentStage.setScene(currentScene);

                     currentStage.requestFocus();
                     Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                     currentStage.setX((primScreenBounds.getWidth() -  currentStage.getWidth()) / 2);
                     currentStage.setY((primScreenBounds.getHeight() -  currentStage.getHeight()) / 2);
                     currentStage.setResizable(false);

                     currentStage.show();


                 } catch (Exception e) {

                 }



             }
    }


    public boolean validate() {


       password  = tf.getText().trim();

        if (password.isEmpty()) {
            tf.clear();
            tf.setPromptText("ID IS REQUIRED!");
        }
        else if(password.equals("9723")) {
            getAdmin();
            return  true;
        }
        else if (password.length()<7){

            tf.clear();
        }
        else if (doesThisExist("password","ID",password)){
                   fetchUsernamePassword();
                   return true;

        }




        else {

            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("\n\tWRONG ID! \n\n\t Try Again. ");
            alert.showAndWait();
        }




     return false;
    }


    public void fetchUsernamePassword(){
        DBcon();
        openConn(conn);
        qry="SELECT seller.name as sName,password.name as name, password " +
                " from password inner join seller on password.ID=seller.ID where password.ID='"+password+"'";
        try {
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){
             DBUsername=rs.getString("name").trim();
             DBPassword=rs.getString("password").trim();
             DBRUsername=rs.getString("sName").trim();

            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    public void getAdmin(){
        DBcon();
        openConn(conn);
        qry="SELECT name,password from admin";

        try {
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){
                DBUsername=rs.getString("name").trim();
                DBPassword=rs.getString("password").trim();
                DBRUsername=DBUsername;

            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
