package smartsales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.sql.DriverManager;
import java.sql.Statement;

public class databasePassword extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private PasswordField tf1;

    @FXML
    private PasswordField tf2;

    @FXML
    private Button bt;

    void getFocus1(){

        if (tf1.getText().trim().isEmpty()){
            tf1.requestFocus();
        }

        else   if (tf2.getText().trim().isEmpty()){
                tf2.requestFocus();
        }





    }

    @FXML
    void   validate(ActionEvent event) {
        getFocus1();

        if (validate1()){

            if (DBcon()){

                tf1.clear();
                tf2.clear();

                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESS!");
                alert.setHeaderText("CONGRATS! LOGIN  IS SUCCESSFUL");
                alert.showAndWait();

                createFile();

                     if(DBExists()){
                    openWindowByClick(event,"mainLock.fxml");  
                }
                else {
                    openWindowByClick(event,"logAdmin.fxml");
                }








            }
            else {


              Alert alert=new Alert(Alert.AlertType.ERROR);
              alert.setTitle("WRONG DATABASE DETAILS");
              alert.setHeaderText("YOUR LOGIN DETAILS ARE NOT ACCEPTED!");
              alert.showAndWait();

            }
        }



    }

    String userName=null;
    String userPassword=null;

   boolean validate1(){
        userName=tf1.getText().trim();
        userPassword=tf2.getText().trim();

        if((userName.isEmpty())||(userPassword.isEmpty())){


            return false;
        }
        else {

            return true;
        }
   }





    @FXML
    void clearField(ActionEvent event) {
      tf1.clear();
           tf2.clear();
    }

    String fileName=ShareData.fileName;

    boolean createFile(){
       try {

    

           File userFile = new File(ShareData.pathToAllInternalFiles +fileName);



           // check if file does not exist then create it
           if (!userFile.exists()) {
               userFile.createNewFile();

               savePasswordToText();
               return true;
           }
           else {


           }


       }
       catch (Exception e){
          return false;
       }



       return false;
    }



    boolean savePasswordToText(){
        try {

            File userFile = new File(ShareData.pathToAllInternalFiles+ fileName);
            PrintWriter writeToFile = new PrintWriter(new BufferedWriter(new FileWriter( userFile, true)));
            writeToFile.append(userName+" "+userPassword);

            writeToFile.close();

            return true;

        }
        catch (Exception e){
            e.printStackTrace();
        }


       return false;
    }


//   openWindowByClick(event,"msc.fxml");

    @FXML
    void openWindowByClick(ActionEvent event, String fxml){
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
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





    public boolean DBcon() {

        Statement st;
        String smartSales = "Smart";
        String url2 = "jdbc:mysql://localhost:3306/" + smartSales;
        String qrydb = "CREATE DATABASE IF NOT EXISTS " + smartSales;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //server URL
            String url = "jdbc:mysql://localhost/";
            conn = DriverManager.getConnection(url, userName,userPassword);
            st = conn.createStatement();
            st.execute(qrydb);

            conn = DriverManager.getConnection(url, userName,userPassword);
            st.execute(qrydb);
            ShareData.directConnection=conn;
            return true;
        } catch (Exception e) {
       
        }
        return false;
    }






}
