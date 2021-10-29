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

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.util.ArrayList;


public class showImagesInFolder extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private TextArea ta;

    @FXML
    private Button btOK;


    public void closeWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("showImagesInFolder.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);
            currentStage.close();
        }
        catch (Exception e){
        }
    }

    @FXML
    public void initialize(){
        ArrayList <String>list=new ArrayList<>();
        try {
            list=checkEmptyFolder();
            for (String i:list){
                if(!(i.equals("noImageList"))){
                    ta.appendText(i+"\n");
                }
            }
        }
        catch (Exception e){

        }
    }

    @FXML
    public boolean  hasNoImage(String sName){
        Blob img=null;
        InputStream inputStream=null;
        InputStreamReader inputStreamReader=null;
        File file=null;



        try {
            openConn(conn);
            st=conn.createStatement();
            qry="SELECT img from item where sName='"+sName+"'";
            rs=  st.executeQuery(qry);

            if(rs.next()){
                inputStream=rs.getBinaryStream("img");

            }
            try {
                inputStreamReader=new InputStreamReader(inputStream);
                return false;
            }
            catch (Exception ee){

                return true;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



@FXML

public ArrayList <String> checkEmptyFolder() {


    ShareData shareData = ShareData.getInstance();
    File file = new File(shareData.file.toString());
    System.out.println("CHECK FOLDER " + file);
    if (file.isDirectory()) {
        String[] avail = file.list();

        if (avail.length > 0) {

            String name;

            ArrayList <String>nameList=new ArrayList<>();


            for (String i:avail){
                name=removeFileExtension(i);
                nameList.add(name);
            }

            return nameList;
        }

        else {

        }
    }

    return null;
}



    public String removeFileExtension(String fileName){
        try {
            String rmx=getExtension(fileName).trim();

            int rx=rmx.length();
            StringBuilder name=new StringBuilder();
            name.append(fileName);
            int l=name.length();
            int endOfString= l- (rx+1);
            String validName=name.substring(0,endOfString);
            return validName;
        }
        catch (Exception e){

        }

        return null;
    }

}
