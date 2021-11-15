package SmartSales;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;

public class mainLock extends DBconnect {

    String DBName=null;

    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    String username=null;
    String password=null;
    boolean isSeller=false;
    boolean isAdmin=false;
    boolean isNewSeller=false;

    @FXML
    private Button btfp;

    @FXML
    private PasswordField ptf1;

    @FXML
    private PasswordField ptf2;

    @FXML
    private Button btL;

    @FXML
    private Button btD;

    @FXML
    private FontAwesomeIconView p2;

    @FXML
    private FontAwesomeIconView p1;

    @FXML
    private FontAwesomeIconView eye2;

    @FXML
    private FontAwesomeIconView eye1;

    @FXML
    private OctIconView imDel;

    @FXML
    private TextField tf2;

    @FXML
    private TextField tf1;

    @FXML
    void btExited(MouseEvent event) {

    }

    @FXML
    void canEntered(MouseEvent event) {

    }

    @FXML
    void canExit(MouseEvent event) {

    }



    @FXML
    void forgotPassWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("forgotPassword.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("forgotPassword.css").toExternalForm();
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
    void clearField(ActionEvent event) {
        ptf1.clear();
        ptf2.clear();
        tf1.clear();
        tf2.clear();

    }

    @FXML
    void clearPtf(MouseEvent event) {

    }

    @FXML
    void isMouseEntered(MouseEvent event) {

    }

    @FXML
    void off_1(MouseEvent event) {
        ptf1.setVisible(true);
        tf1.setVisible(false);
    }

    @FXML
    void off_2(MouseEvent event) {
        ptf2.setVisible(true);
        tf2.setVisible(false);
    }

    @FXML
    void show_1(MouseEvent event) {
        tf1.setText(ptf1.getText().trim());
        ptf1.setVisible(false);
        tf1.setVisible(true);
    }

    @FXML
    void show_2(MouseEvent event) {
        tf2.setText(ptf2.getText().trim());
        ptf2.setVisible(false);
        tf2.setVisible(true);
    }


    @FXML
    private Label lbFP;





    @FXML
    void submit(ActionEvent event) {
        if (validate()){

            if (isAdmin){

                try{
                    root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
                    currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    currentScene=new Scene(root);
                    String css=this.getClass().getResource("adminMainWindow.css").toExternalForm();
                    root.getStylesheets().add(css);
                    currentStage.setScene(currentScene);

                    currentStage.show();
                    root.requestFocus();
                }
                catch (Exception e){


                }

            }

        else     if (isSeller){
                System.out.println("isSeller");
            }

        else     if (isNewSeller){


                try {
                    root = FXMLLoader.load(getClass().getResource("sellerPassword.fxml"));
                    currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
                    currentScene=new Scene(root);
                    String css=this.getClass().getResource("sellerPassword.css").toExternalForm();
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
        }
    }





    public boolean validate(){

        tf1.setText(ptf1.getText().trim());
        tf2.setText(ptf2.getText().trim());


        String tf1s= tf1.getText().trim();
        String tf2s= tf2.getText().trim();

        username=tf1s;
        password=tf2s;

        if (tf1s.isEmpty()){
            ptf1.clear();

        }
        else  if (tf2s.isEmpty()){
            ptf2.clear();

        }
        else  if(isSeller()){
            isSeller=true;
        }
        else  if(isAdmin()){
            isAdmin=true;
        }
        else  if(isNewSeller()){
            isNewSeller=true;

        }

        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("WRONG LOGIN DETAILS!");
            alert.showAndWait();
            isAdmin=false;
            isSeller=false;
        }

        if (isAdmin){

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tCONGRATS!");
            alert.showAndWait();
            ptf1.clear();
            ptf2.clear();
            tf1.clear();
            tf2.clear();
            return true;
        }
        if (isSeller){

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tCONGRATS!");
            alert.showAndWait();
            ptf1.clear();
            ptf2.clear();
            tf1.clear();
            tf2.clear();
            return true;
        }
        if (isNewSeller){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tHi!\n\n\t"+DBName+"\n\n\t PLEASE SET YOUR USER NAME\n \t AND " +
                    "PASSWORD");
            alert.showAndWait();
            ptf1.clear();
            ptf2.clear();
            tf1.clear();
            tf2.clear();
            return true;
        }
        return false;
    }


    public boolean isSeller(){
        sellerHasRegistered();
        qry="SELECT name FROM password where name='"+username+"' and password= '"+password+"'";
        try {
            DBcon();
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){

                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean isNewSeller(){
        if (sellerHasRegistered()){
            return false;
        }

        qry="SELECT  name FROM seller where ID='"+username+"'";
        try {
            DBcon();
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){
                DBName=rs.getString("name");
                setN1(username,"00","00","00");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean sellerHasRegistered(){

        qry="SELECT seller.ID as ID FROM seller  inner join password on seller.ID=password.ID && seller.ID='"+username+"'";
        try {
            DBcon();
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){
             String ID=rs.getString("ID");


                System.out.println("in two tables: "+ID);

                return true;
            }
            else {
                System.out.println(" no match in two tables: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAdmin(){
        qry="SELECT name FROM admin where name='"+username+"' and password= '"+password+"'";

        try {
            DBcon();
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){

                String g=rs.getString("name");


                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
