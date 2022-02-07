package smartsales;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class sellerTotalSales extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private Label lbUser;

    @FXML
    private Label lbToday;

    @FXML
    private Label lbWeek;

    @FXML
    private Label lbLastWeek;

    @FXML
    private Label lbMonth;

    @FXML
    private Label lbLastMonth;

    @FXML
    private Label lbYear;

    @FXML
    private Label lbLastyear;

    @FXML
    private Label lbOverAll;

    @FXML
    private Button btOK;

    @FXML
    private Label lbMyOwn;

    @FXML
    private TextField tf;


    private  void run(){

        Task task=new Task() {
            @Override
            protected Object call() throws Exception {

                try {





                }
                catch (Exception e){
                    e.printStackTrace();
                }



                return null;
            }

        };

        ExecutorService executorService= Executors.newSingleThreadExecutor();

        executorService.execute(task);
        executorService.shutdown();
    }



    public  void initialize(){
        //deleteRecord("sales");

        lbUser.setText( "TOTAL SALES FOR  "+ShareData.currentUser_.toUpperCase());

        getToday();
        getWeekSales();
        getMonthSales();
        getYearSales();
        getLastYearSales();
        getOverall();
        getLastMonth();
        getLastWeek();


        TranslateTransition rt = new TranslateTransition();
        rt.setDuration(Duration.seconds(1));
        rt.setNode(lbToday);
        rt.setByX(80);
        rt.setAutoReverse(true);
        // rt.setDuration(Duration.millis(10000));
        rt.setCycleCount(TranslateTransition.INDEFINITE);
        rt.play();




    }





    @FXML
    void getMyOwnAmount(KeyEvent event) {
               lbMyOwn.setText("0.00");
        if (validateTextField()){
            getMyOwnData();
        }



    }


    int tfValue=0;
    private boolean validateTextField(){
        String i=tf.getText().trim();

        if (i.isEmpty()){
            return false;

        }
        else {
            try {
                tfValue=Integer.parseInt(i);
            }
            catch (Exception e){
             return false;
            }


        }


      return true;
    }


    @FXML
    void gotoSellerOtherWindow(ActionEvent event) {
       // openWindowByClick(event,"sellerOtherWindow.fxml");

        Parent rr;
        Stage stg1=new Stage();
        Scene sc1;

                try {
            rr = FXMLLoader.load(getClass().getResource("sellerTotalSales.fxml"));
                    stg1 =(Stage)((Node)event.getSource()).getScene().getWindow();
                   sc1=new Scene(rr);
                    stg1.setScene(sc1);

                  stg1.close();

        }
        catch (Exception e){

        }
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





String ID=ShareData.userID_;





    private void getToday() {

        DBcon();
        openConn(conn);
        qry = "select  sum(discountOnTotalCost)" +
                " as amt " +
                "from salesDistinctSalesID where date(time)=curdate() " +
                "and userID='"+ID+"'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbToday.setText(String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
              e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }



    private void getWeekSales() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID where " +
                "yearweek(time,1) = yearweek(curdate(),1)   and userID= '"+ID+"'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbWeek.setText(String.valueOf( dp00.format(amt2)));




            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }


//"+ID+"'

    private void getMonthSales() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID " +
                "where month(time) = month(curdate()) and year(time)=year(curdate())  and userID= '"+ID+"'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbMonth.setText(String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }

    private void getYearSales() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID " +
                "where year(time) = year(curdate())   and userID= '"+ID+"'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbYear.setText(String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }






    private void getLastYearSales() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from " +
                "salesDistinctSalesID where year(time)= year(date_sub(curdate(),interval 1 Year))" +
                " and userID='"+ID+"'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbLastyear.setText(String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }


    private void getOverall() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID" +
                " where userID='"+ID+"'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbOverAll.setText(String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }



    private void getLastMonth() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID where year(time)=year(current_date -interval 1 month) " +
                " and month(time)=month(current_date - interval 1 month) and userID='"+ID+"'  ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbLastMonth.setText(String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }



    private void getLastWeek() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID where yearweek(time)=yearweek(now()- interval 1 week)" +
                " and userID='"+ID+"'";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbLastWeek.setText(String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }

    private void getMyOwnData() {

        DBcon();
        openConn(conn);
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID where time >=date(now()) - interval "+tfValue+" day " +
                " and userID='"+ID+"' ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbMyOwn.setText(String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }
}
