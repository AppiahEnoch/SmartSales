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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class recentItem extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private TableView<ITEM2> tbv;

    @FXML
    private TableColumn<ITEM2, String> c;

    @FXML
    private TableColumn<ITEM2, String> c1;

    @FXML
    private TableColumn<ITEM2, String>c2;

    @FXML
    private TableColumn<ITEM2, String> c3;

    @FXML
    private TableColumn<ITEM2, String> c4;
    @FXML
    private Button btd1;

    @FXML
    private Button btd2;

    @FXML
    void deleteAll(ActionEvent event) {

    }

    @FXML
    void deleteSelected(ActionEvent event) {

    }

    @FXML
    void goBackR(ActionEvent event) {

    }

    @FXML
    public void initialize(){


        c.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("ID"));
        c1.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("name"));

        c2.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("qty"));
        c3.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("cost"));
        c4.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("price"));

        c1.setCellFactory(TextFieldTableCell.forTableColumn());
        c2.setCellFactory(TextFieldTableCell.forTableColumn());
        c3.setCellFactory(TextFieldTableCell.forTableColumn());
        c4.setCellFactory(TextFieldTableCell.forTableColumn());

           getRecentItems();
    }


    private void getRecentItems(){
        DBcon();
        openConn(conn);
        qry="select sName,qty,cost,price from item1 order by time";

        try {
            st=conn.createStatement();
            rs=st.executeQuery(qry);


            while (rs.next()){
                DBitem= rs.getString("sName").trim();
                DBqty= rs.getString("qty").trim();
                DBCost= rs.getString("cost").trim();
                DBprice= rs.getString("price").trim();

                System.out.println(DBitem);

                tbv.getItems().addAll(new ITEM2(DBitem,DBqty,DBCost,DBprice));


            }

        }catch (Exception e){
e.printStackTrace();
        }
        finally {
            try {
                conn.close();

            }catch (Exception e){

            }
        }




    }


    @FXML
    void  editItem(TableColumn.CellEditEvent editEvent){
        ITEM2 c = tbv.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getName();
        Object item=colSelected1.getName();
        System.out.println(n);
    }    @FXML
    void  editQty(TableColumn.CellEditEvent editEvent){
        ITEM2 colSelected = tbv.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getQty();
        Object id=colSelected1.getId();
        System.out.println(n);
    }    @FXML
    void  editCost(TableColumn.CellEditEvent editEvent){
        ITEM2 colSelected = tbv.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getCost();
        Object id=colSelected1.getId();
        System.out.println(n);
    }    @FXML
    void  editPrice(TableColumn.CellEditEvent editEvent){
        ITEM2 colSelected = tbv.getItems().get(editEvent.getTablePosition().getRow()).setName(editEvent.getNewValue());
        ITEM2 colSelected1 = tbv.getSelectionModel().getSelectedItem();
        Object n=colSelected1.getPrice();
        Object id=colSelected1.getId();
        System.out.println(n);
    }


    public void updateItemName(Object newName,Object id){
        DBcon();
        qry="UPDATE item1 SET name='"+newName+"' where ID = '"+id+"'";
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
