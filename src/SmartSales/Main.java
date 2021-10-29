package SmartSales;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.TabableView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.Connection;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //basic routines

        try {
            ShareData m=ShareData.getInstance();

            loadItemManual obj=new loadItemManual();
            obj.isItemInDatabase(obj.checkEmptyFolder());
            obj.writeAllNoImageItems();
            obj.getImagesFromFolder();
        }
        catch (Exception ee){

        }




        DBconnect dBconnect = new DBconnect();
        boolean DBExists = dBconnect.DBExists();
        Parent root;
        String css;
        if (DBExists) {
            //  root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
          //  root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
        //    root = FXMLLoader.load(getClass().getResource("loadItemManual.fxml"));

          //  root = FXMLLoader.load(getClass().getResource("showImagesInFolder.fxml"));
            root = FXMLLoader.load(getClass().getResource("emptySystemWindow.fxml"));



        //    css = this.getClass().getResource("mainLock.css").toExternalForm();
           // css = this.getClass().getResource("loadItemManual.css").toExternalForm();
          //  css = this.getClass().getResource("loadItemManual.css").toExternalForm();
           // css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            css = this.getClass().getResource("emptySystemWindow.css").toExternalForm();

        } else {
            root = FXMLLoader.load(getClass().getResource("logAdmin.fxml"));
            css = this.getClass().getResource("logProgram.css").toExternalForm();
            logAdmin logAdmin = new logAdmin();
            logAdmin.createTables();
        }

        root.getStylesheets().add(css);
        primaryStage.setTitle("Smart Sales - AECleanCodes");
        primaryStage.setScene(new Scene(root));
        root.requestFocus();



        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);



    }


    public static void main(String[] args) {

        launch(args);

    }


}
