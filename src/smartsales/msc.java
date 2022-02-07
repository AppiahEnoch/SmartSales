package smartsales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class msc extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private Text lb;

    @FXML
    private ProgressBar pb;


    @FXML
    private ComboBox<String> comb;

    @FXML
    private Button btOldReceipt;

    boolean done=false;
    @FXML
    void restoreDB(ActionEvent event) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "DO YOU REALLY WANT TO RESTORE THE DATABASE " +
                                "FROM PREVIOUS FILE?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                " RESTORE.",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("DATABASE RESTORE WARNING!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {




            pb.setVisible(true);
            lb.setVisible(true);



            Service service = new Service() {

                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            try {
                                updateProgress(3,5);




                                try {


                                    if (restore()){


                                     updateProgress(5,5);
                                              done=true;
                                    }
                                    else {

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                pb.setVisible(false);
                                lb.setVisible(false);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    };
                }
            };
            pb.progressProperty().bind(service.progressProperty());
            service.start();

            service.setOnSucceeded(e->{
               Stage dialog=new Stage();
               dialog.initStyle(StageStyle.UTILITY);
               Scene scene=new Scene(new Group( new Text(25,25,"\n\n\tDATABASE RESTORED SUCCESSFULLY!  \t\n\n")));
               dialog.setScene(scene);
               dialog.showAndWait();
            });






        }

    }

    @FXML
    void getIDWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("oldReceiptIDValidate.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);

            currentStage.setScene(currentScene);
            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void getAdminWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);

            currentStage.setScene(currentScene);
            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Parent r;
    Stage stage=new Stage();
    Scene scene2;

    @FXML
    void showLog(ActionEvent event) {


        try {
            r = FXMLLoader.load(getClass().getResource("log.fxml"));

            scene2=new Scene(r);

            stage.setScene(scene2);

            stage.show();
            r.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            stage.setResizable(false);
        }
        catch (Exception e){

        }




    }




    @FXML
        void showCashiers(ActionEvent event) {
        String ii=comb.getSelectionModel().getSelectedItem().toString().trim();

        if (!ii.isEmpty()){

            String[] i=ii.split("  ");
            ShareData.userID_=i[0];
            ShareData.currentUser_=i[1];

            comb.getSelectionModel().clearSelection();



                    try {
            r = FXMLLoader.load(getClass().getResource("sellerTotalSales.fxml"));

            scene2=new Scene(r);

            stage.setScene(scene2);

            stage.show();
            r.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                        stage.setResizable(false);
        }
        catch (Exception e){

        }

        }





    }



    @FXML
    void showCombo(ActionEvent event) {


        if (comb.isVisible()){
            comb.setVisible(false);
            comb.getSelectionModel().clearSelection();
        }
        else {
            comb.setVisible(true);
        }




    }

    public void initialize() {
        ObservableList<String> list = FXCollections.observableList(getCashiers());
        comb.setItems(list);


    }

    String cashier="";

    private ArrayList<String> getCashiers() {
        ArrayList<String> li = new ArrayList<>();
        qry = "SELECT  concat(ID,'  ',name) as cashier FROM smart.seller";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                cashier = rs.getString("cashier").trim();
                if (!cashier.isEmpty()) {
                    li.add(cashier);

                }



            }
        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();

        }

        return li;
    }

    @FXML
    void itemsGettingFinished(ActionEvent event){
        System.out.println("click");
        openWindowByClick(event,"itemGettingFinished.fxml");
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



    private   boolean restore() {

        String databaseName="Smart";
        String DBackupName = "backup.sql";
        String savePath = ShareData.pathToDesktop+"\\";
        String userName = ShareData.DBUserName;
        String password = ShareData.DBPassword;


        String[] executeCmd = new String[]{"mysql", "--user=" + userName, "--password=" + password, databaseName,"-e", " source "+savePath+DBackupName};
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup restored successfully");
                return true;
            }
            else {
                System.out.println("Could not restore the backup");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }





}