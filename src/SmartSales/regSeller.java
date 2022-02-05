package SmartSales;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class regSeller extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private CheckBox checboxMakeAdmin;

    @FXML
    private AnchorPane fmAdminDetails;

    @FXML
    private TextField tfMobile;


    @FXML
    private TextField tfLocation;

    @FXML
    private TextField tfName;

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
    private Label lbSellerID;

    @FXML
    private TextField tfID;

    @FXML
    private Button btExit;


    @FXML
    private Button btEdit;
    @FXML
    void OpenEditSellerName(ActionEvent event) {



        try {
            root = FXMLLoader.load(getClass().getResource("EditSellerName.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("EditSellerName.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() -  currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() -  currentStage.getHeight()) / 2);
            currentStage.setResizable(false);
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("PRESS THE ENTER KEY AFTER EACH EDIT " +
                    "\n ELSE YOUR CHANGES WILL NOT TAKE EFFECT.");
            alert.showAndWait();
        }
        catch (Exception e){

        }

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

        }
        catch (Exception e){

        }
    }

    @FXML
    void setID_Visible(MouseEvent event) {

        tfID.setVisible(true);
        lbSellerID.setVisible(true);
        getID();
    }

    @FXML
    void clearFields(ActionEvent event) {
                tfName.clear();
                tfLocation.clear();
                tfMobile.clear();
                checboxMakeAdmin.setSelected(false);
    }

    @FXML
    void submit(ActionEvent event) {

        if (validate() ){


            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("CONGRATS!");
            alert.showAndWait();
            try {
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
    }


    String name=null;
    String location=null;
    String mobile=null;
    char prev='0';
    String ID=null;

    public  void initialize(){
        tfLocation.requestFocus();
    }

    public boolean validate() {
        getFocus1();
        String tf1 = tfName.getText().trim();
        String tf2 = tfLocation.getText().trim();
        String tf3 =tfMobile.getText().trim();
        String tf4 =tfID.getText().trim();

        if (tf3.contains("1"))

        if (checboxMakeAdmin.isSelected()){
            prev='1';
        }
        else {
            prev='0';
        }

        if (tf1.isEmpty()) {
            tfName.clear();

        } else if (tf2.isEmpty()) {
            tfLocation.clear();

        } else if (tf3.isEmpty()) {
            tfMobile.clear();

        }
        else if (!isNumeric(tf3)){
            tfMobile.clear();

        }
        else if ((tf3.length()<10)){
            tfMobile.clear();
        }
        else if ((tf3.length()>10)){
            tfMobile.clear();
        }
        else {

            name=tf1;
            location=tf2;
            mobile=tf3;
            ID=tf4;


           saveRecord();
            return true;
        }

        return false;
    }

    public boolean saveRecord(){

        if (DBcon()){

                try {

                    qry="INSERT INTO seller(ID,name,location,mobile,prev) values('"+ID+"','"+name+"','"+location+"','"+mobile+"','"+prev+"')";
                    st=conn.createStatement();
                    st.executeUpdate(qry);

                    return true;
                }
                catch (Exception e){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("COULD NOT SAVE DATA: "+e.getMessage());
                    alert.showAndWait();
                }





        }

        return false;
    }




    public boolean rExist(String r){
      r=r.trim();
        if (DBcon()){

            try {

                qry="SELECT ID from seller where id='"+r+"'";
                st=conn.createStatement();
                rs=st.executeQuery(qry);

                if (rs.next()){

                    return true;
                }

            }
            catch (Exception e){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT VERIFY r: "+e.getMessage());
                alert.showAndWait();
            }

        }

        return false;
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


        s1 = i + "" + cc1 + ii + iii + cc3 + cc2 + m + mm + cu;



        System.out.println("code= " + s1);

        return s1;
    }




    public void getID(){
        String ID=null;
        while (true){
            ID=getRandom();
            if (!rExist(ID) ) {
                tfID.setText(ID);
                break;
            }
        }




    }


public boolean isNumeric(String value){
        double v;
        try {
            v=Double.parseDouble(value);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
}






    void getFocus1(){

           if (tfLocation.getText().trim().isEmpty()){
            tfLocation.requestFocus();
        }

        else   if (tfMobile.getText().trim().isEmpty()){
            tfMobile.requestFocus();
        }







    }
}
