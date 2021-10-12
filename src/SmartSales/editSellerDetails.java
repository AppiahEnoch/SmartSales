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


public class editSellerDetails extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private Button btExit;

    @FXML
    private Button btUpdate;

    @FXML
    private TableView<tbEditSellerDetails> tbV;

    @FXML
    private TableColumn<tbEditSellerDetails, String> colID;

    @FXML
    private TableColumn<tbEditSellerDetails, String> colName;

    @FXML
    private TableColumn<tbEditSellerDetails, String> colMobile;

    @FXML
    private TableColumn<tbEditSellerDetails, String> colLoc;
    @FXML
    private Button btDel;
    @FXML
    private Button btDel1;

    @FXML
    void deleteSelected(ActionEvent event) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "DO YOU REALLY WANT TO DELETE THE SELECTED RECORD?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                " DELETION.",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("DELETE SELECTED WARNING!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {


            try {
                tbEditSellerDetails colSelected1 = tbV.getSelectionModel().getSelectedItem();
                Object n=colSelected1.getName();
                Object ID=colSelected1.getID();
                System.out.println(n);
                System.out.println(ID);
                String ID2=ID.toString();
                deleteSelectedFromSellers(ID2);


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
                }
                catch (Exception e){

                }

            }
            catch (Exception e){

            }



        }
        else {

        }







    }

    @FXML
    void deleteAll(ActionEvent event) {
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "DO YOU REALLY WANT TO DELETE ALL SELLERS FROM THIS TABLE?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                " DELETION.",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("DELETE ALL WARNING!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {


            deleteAllFromSellers();

            tbV.getItems().clear();


        }
        else {

        }



    }

    @FXML
    void openAdminMainWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("adminMainWindow.css").toExternalForm();
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




    public void initialize() {

        tbV.setEditable(true);
        colName.setEditable(true);
        if (DBcon()) {
            try {
                qry = "SELECT ID,name,mobile,location FROM seller";

                st = conn.createStatement();
                rs = st.executeQuery(qry);
                while (rs.next()) {
                    String id = rs.getString("ID").trim();
                    String name = rs.getString("name").trim();
                    String mobile = rs.getString("mobile").trim();
                    String loc = rs.getString("location").trim();

                    colID.setCellValueFactory(new PropertyValueFactory<tbEditSellerDetails, String>("ID"));
                    colName.setCellValueFactory(new PropertyValueFactory<tbEditSellerDetails, String>("name"));
                    colMobile.setCellValueFactory(new PropertyValueFactory<tbEditSellerDetails, String>("mobile"));
                    colLoc.setCellValueFactory(new PropertyValueFactory<tbEditSellerDetails, String>("location"));

                    tbV.getItems().add(new tbEditSellerDetails(id,
                            name, mobile, loc));

                    colName.setCellFactory(TextFieldTableCell.forTableColumn());
                    colMobile.setCellFactory(TextFieldTableCell.forTableColumn());
                    colLoc.setCellFactory(TextFieldTableCell.forTableColumn());
                    colID.setCellFactory(TextFieldTableCell.forTableColumn());
                }


            } catch (Exception e) {
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


        }


    }

    @FXML
    public void editName(TableColumn.CellEditEvent editEvent) {
        tbEditSellerDetails colSelected = tbV.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());

        tbEditSellerDetails colSelected1 = tbV.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getName();
        Object ID=colSelected1.getID();
        updateSellerName(n,ID);

    }
    @FXML
    public void editMobile(TableColumn.CellEditEvent editEvent1) {
        tbEditSellerDetails colSelected = tbV.getItems().get(editEvent1.getTablePosition().getRow()).setMobile(editEvent1.getNewValue());

        tbEditSellerDetails colSelected1 = tbV.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getMobile();
        Object ID=colSelected1.getID();
        updateSellerMobile(n,ID);

    }

    @FXML
    public void editLocation(TableColumn.CellEditEvent editEvent1) {
        tbEditSellerDetails colSelected = tbV.getItems().get(editEvent1.getTablePosition().getRow()).setLocation(editEvent1.getNewValue());
        tbEditSellerDetails colSelected1 = tbV.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getLocation();
        Object ID=colSelected1.getID();
        updateSellerLoc(n,ID);
    }


public void updateSellerName(Object n,Object id){
        DBcon();
    qry="UPDATE seller SET name='"+n+"' where ID= '"+id+"'";
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



}
    public void updateSellerMobile(Object n,Object id){
        DBcon();
        qry="UPDATE seller SET mobile='"+n+"' where ID= '"+id+"'";
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



    }

    public void updateSellerLoc(Object n,Object id){
        DBcon();
        qry="UPDATE seller SET location='"+n+"' where ID= '"+id+"'";
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



    }


    public void deleteAllFromSellers(){
        DBcon();
        qry="DELETE FROM SELLER";
        try {
            st=conn.createStatement();
            st.executeUpdate(qry);


            conn.close();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ALL SELLERS DATA DELETED SUCCESSFULLY!");
            alert.showAndWait();
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



    }


    public void deleteSelectedFromSellers(String ID){
        DBcon();

        qry="DELETE FROM SELLER where ID="+ID;
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



    }



}