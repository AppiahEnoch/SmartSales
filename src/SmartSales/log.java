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
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class log extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private TableView<LOGG> tbV;

    @FXML
    private TableColumn<LOGG, String> c1;

    @FXML
    private TableColumn<LOGG, String> c2;

    @FXML
    private TableColumn<LOGG, String> c3;

    @FXML
    private TableColumn<LOGG, String> C4;

    @FXML
    void closeWindow(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("log.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);

            currentStage.setScene(currentScene);

            currentStage.close();

        }
        catch (Exception e){

        }


    }



    Parent rr;
    Stage stage=new Stage();
    Scene sc1;


   public  void initialize(){

       c1.setCellValueFactory(new PropertyValueFactory<LOGG, String>("ID"));
       c2.setCellValueFactory(new PropertyValueFactory<LOGG, String>("name"));
       c3.setCellValueFactory(new PropertyValueFactory<LOGG, String>("inTime"));
       C4.setCellValueFactory(new PropertyValueFactory<LOGG, String>("outTime"));


                        loadTable();

    }







    public String loadTable() {
        String outTime="not";
           tbV.getItems().clear();

        qry = "SELECT *  FROM log order by inTime DESC";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
String id=rs.getString("ID").trim();
String name=rs.getString("name").trim();

String inTime=rs.getString("inTime").trim();
if (rs.getString("outTime")==null){
    outTime="NOT YET";
}
else {
    outTime=rs.getString("outTime").trim();
}




                tbV.getItems().addAll(new LOGG(name,id,inTime,outTime));
            }

        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }

   
}
