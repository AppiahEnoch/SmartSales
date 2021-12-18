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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class printPreviewWindow extends DBconnect {

    String styleCompany= "-fx-fill:red;-fx-font-size:20px; -fx-font-weight:bold;";
    String styleDate= "-fx-fill:Black;-fx-font-size:13px; -fx-font-weight:bold;";
    String styleNO= "-fx-fill:Black;-fx-font-size:14px; -fx-font-weight:bold;";

    public  TextArea ta;

    @FXML
    private Stage currentStage;


    @FXML
    private TextFlow tf;

    @FXML
    public void initialize(){
            setTaRubrics();

        if (ShareData.isPrint){
            System.out.println("printiong...");
            ShareData.print(tf);


        }
    }



    private void setTaRubrics(){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date date=new Date();



        try {
       tf.getChildren().clear();
          Text txtCompany=new Text();
          Text txtDate=new Text();
          Text txtReceiptNo=new Text();
          Text txtLocation=new Text();
          Text txtCashier=new Text();
            Text txtLine=new Text();
            Text txtLabel=new Text();

          txtCompany.setStyle(styleCompany);
          txtCompany.setUnderline(true);
          txtDate.setStyle(styleDate);
          txtReceiptNo.setStyle(styleNO);
          txtLocation.setStyle(styleNO);
          txtCashier.setStyle(styleNO);
          txtLine.setStyle(styleDate);
         txtLabel.setStyle(styleNO);


            txtCompany.setText("AECleanCodes\n");
            txtDate.setText(dateFormat.format(date)+"\n");
            txtReceiptNo.setText("Receipt ID: "+getRandom()+"\n");
            txtLocation.setText("Location: Tanoso \n");
            txtCashier.setText("Cashier: Appiah Enoch \n");
            txtLine.setText("____________________________________\n\n");
            txtLabel.setText("ITEM      QTY   PRICE   TOTAL\n");


          tf.getChildren().addAll(txtCompany,txtLocation,
                  txtDate,txtReceiptNo,txtCashier,txtLine,txtLabel);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    @FXML
    void closeWindow(ActionEvent event) {

        try {
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


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


        s1 = "AEC"+ i + "" + cc1 + ii + iii + cc3 + cc2 + m + mm + cu;





        return s1;
    }
}
