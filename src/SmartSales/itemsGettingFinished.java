package SmartSales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;

public class itemsGettingFinished extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private Label lb1;

    @FXML
    private TextField tf;

    @FXML
    private Label lb2;

    @FXML
    private TableView<FINISH> tbv;

    @FXML
    private TableColumn<FINISH, String> c1;

    @FXML
    private TableColumn<FINISH, String> c2;

    @FXML
    private TableColumn<FINISH, String> c3;

    @FXML
    private TableColumn<FINISH, String> c4;

    @FXML
    private TableColumn<FINISH, String> c5;

    @FXML
    private Button btDone;

    @FXML
    void changeFinishingPercentage(KeyEvent event) {
        if (validateTextField()){
            updateFinishingPercentage();
        }

    }

    @FXML
    void done(ActionEvent event) {
        openWindowByClick(event,"msc.fxml");
    }



   public  void initialize(){
        getPercentageItemFinishing();

        c1.setCellValueFactory(new PropertyValueFactory<FINISH,String>("name"));
       c2.setCellValueFactory(new PropertyValueFactory< FINISH, String>("totalQty"));
       c3.setCellValueFactory(new PropertyValueFactory<FINISH , String>("usedQty"));
       c4.setCellValueFactory(new PropertyValueFactory<FINISH , String>("availableQty"));
       c5.setCellValueFactory(new PropertyValueFactory<FINISH , String>("percentage"));


                        fillTable();

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



    public void updateFinishingPercentage(){
        DBcon();



        qry="UPDATE item SET declareFinishing ="+tfValue;

        try {
            st=conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();


        }
        finally {
            try {
                conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        fillTable();
   getPercentageItemFinishing();
    }


    int tfValue=0;
    private boolean validateTextField(){
        String i=tf.getText().trim();

        if (i.isEmpty()){
            return false;

        }
        else {
            try {
                tfValue=Integer.parseInt(i);
            }
            catch (Exception e){
                return false;
            }


        }


        return true;
    }



    public void getPercentageItemFinishing() {

        qry = "SELECT declareFinishing as p from item limit 1";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {

               String v  = rs.getString("p").trim();

                  tf.setPromptText(v+"%");



            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public void fillTable() {

        tbv.getItems().clear();

        qry = "SELECT * FROM smart.item where declareFinishing>=availPercentage";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {

                String name  = rs.getString("sName").trim();
                String totalQty  = rs.getString("qty").trim();
                String usedQty  = rs.getString("usedQty").trim();
                String availQty  = rs.getString("availQty").trim();
                String percentage  = rs.getString("availPercentage").trim();

                tbv.getItems().addAll(new FINISH(name,totalQty,usedQty,availQty,percentage));




            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
   
}
