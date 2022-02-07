package smartsales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Blob;

public class imgPop extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private ImageView imv;

    @FXML
    private Button btclose;

    @FXML
   public void initialize(){
        ShareData m = ShareData.getInstance();
        getImage(m.stringData1);



    }

    @FXML
    void goMainWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("imgPop.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
           // String css = this.getClass().getResource("recentItem.css").toExternalForm();
           // root.getStylesheets().add(css);
            currentStage.setScene(currentScene);
            currentStage.close();



        } catch (Exception e) {

        }

    }

    @FXML
    private int getImage(String sName) {
        Blob img = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        File file = null;

        try {
            DBcon();
            openConn(conn);
            st = conn.createStatement();
            qry = "SELECT img from item where sName= '" + sName + "'";
            rs = st.executeQuery(qry);

            if (rs.next()) {
                inputStream = rs.getBinaryStream("img");


            }

            if (inputStream == null) {

                imv.setImage(null);
                return 0;
            }
            inputStreamReader = new InputStreamReader(inputStream);

            if (inputStreamReader.ready()) {
                file = new File("item.png");
            }

            OutputStream os = new FileOutputStream(file);
            byte[] content = new byte[1024];

            while (inputStream.read(content) > 0) {
                os.write(content);
            }

            Image image1 = new Image(file.toURI().toString());
            imv.setImage(image1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
   
}
