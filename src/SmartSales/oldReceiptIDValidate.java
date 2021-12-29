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
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class oldReceiptIDValidate extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private TextField tf;

    @FXML
    private Button btExit;

    @FXML
    private Button btSubmit;

    @FXML
    void goToMsc(ActionEvent event) {
        if (ShareData.isAdmin){

            try {



                root = FXMLLoader.load(getClass().getResource("msc.fxml"));
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
        else if (ShareData.goSales){


            try {



                root = FXMLLoader.load(getClass().getResource("salesWindow.fxml"));
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
        else {

            try {



                root = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
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






    }






    @FXML
    void submit(ActionEvent event) {
         if (getData()) {

             try {
                 root = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
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
    }


String userInput="";
    private boolean textInTf(){
        String i=tf.getText().trim();
        if (i.isEmpty()){
            return false;
        }
        if (i.length()<7){
            return false;
        }
        else {
            userInput=i;
        }

        return true;
    }


    private boolean firstValidate(){
        if (!textInTf()){
          return  false;
        }
        qry = "SELECT ID from invoiceID where ID='"+userInput+"'";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                return true;
            } else {
            return false;
            }
        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
        }

        return false;
    }

    private boolean getData(){
        if (firstValidate()){
            tf.clear();
        if (sendSalesToReport()){
            getOldSeller();

            if (countRecord("currentUser","ID")<1){
                getOldSellerAdmin();
            }
            return true;
        }
        }
        else {
       tf.clear();
       tf.setPromptText("WRONG ID");
       return false;
        }

        return false;
    }



    public boolean sendSalesToReport() {
        ShareData.oldReceipt=true;
        deleteRecord("receipt");

        if (DBcon()) {

            try {

                qry = "INSERT INTO receipt ( userID,salesID, sName, qty,Ucost,Uprice," +
                        " time,totalBill,cashIssued," +
                        " discountAmount, discountOnTotalCost,discountNumber )" +
                        " SELECT  userID, salesID, sName, qty,Ucost,Uprice," +
                        " time,totalBill,cashIssued," +
                        " discountAmount, discountOnTotalCost,discountNumber" +
                        " FROM sales where salesID='"+userInput+"'";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    e.printStackTrace();
                }
            }
        }


        if (countRecord("receipt","salesID")>0){
            return true;
        }


        return false;
    }



private void getOldSeller(){
        deleteRecord("currentUser");
    if (DBcon()) {
        openConn(ShareData.directConnection);

        try {

            qry = "INSERT INTO currentUser ( ID,fName )" +
                    "  SELECT  ID, name" +
                    "     FROM seller inner join sales on seller.ID=sales.userID and" +
                    " sales.salesID= '"+userInput+"'";

            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
            alert.showAndWait();
            try {
                conn.close();
            } catch (Exception ee) {
                e.printStackTrace();
            }
        }
    }

}




    private void getOldSellerAdmin(){
        deleteRecord("currentUser");
        if (DBcon()) {
            openConn(ShareData.directConnection);

            try {

                qry = "INSERT INTO currentUser ( ID,fName )" +
                        "  SELECT  name, password" +
                        "     FROM admin inner join sales on admin.password=sales.userID and" +
                        " sales.salesID= '"+userInput+"'";

                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    e.printStackTrace();
                }
            }
        }

    }
   
}


