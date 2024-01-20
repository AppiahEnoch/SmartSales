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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class viewSales extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private Label lbUser;

    @FXML
    private Label lbUser1;

    @FXML
    private Label lbUser11;

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

    @FXML
    private Label lbToday1;

    @FXML
    private Label lbWeek1;

    @FXML
    private Label lbLastWeek1;

    @FXML
    private Label lbMonth1;

    @FXML
    private Label lbLastMonth1;

    @FXML
    private Label lbYear1;

    @FXML
    private Label lbLastyear1;

    @FXML
    private Label lbOverAll1;

    @FXML
    private Label lbMyOwn1;

    @FXML
    void getMyOwnAmount(KeyEvent event) {
if (validateTextField()){
    getMyOwnData();
    getMyOwnDataProfit();
}
    }

    @FXML
    void gotoSellerOtherWindow(ActionEvent event) {
        openWindowByClick(event,"reportWindow.fxml");
    }





   public  void initialize(){
        updateProfitSum();
       getToday();
       getWeekSales();
       getMonthSales();
       getYearSales();
       getLastYearSales();
       getOverall();
       getLastMonth();
       getLastWeek();

       getTodayProfit();
       getWeekSalesProfit();
       getMonthSalesProfit();
       getYearSalesProfit();
       getLastYearSalesProfit();
       getOverallProfit();
       getLastMonthProfit();
       getLastWeekProfit();
        
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






    private void getToday() {

        DBcon();
        openConn(conn);
        qry = "select  sum(discountOnTotalCost)" +
                " as amt " +
                "from salesDistinctSalesID where date(time)=curdate() " ;
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
                "yearweek(time,1) = yearweek(curdate(),1) ";

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
                "where month(time) = month(curdate()) and year(time)=year(curdate()) ";

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
                "where year(time) = year(curdate()) ";

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
                "salesDistinctSalesID where year(time)= year(date_sub(curdate(),interval 1 Year))" ;

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
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID" ;

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
                " and month(time)=month(current_date - interval 1 month) ";
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
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID where" +
                " yearweek(time)=yearweek(now()- interval 1 week)" ;

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
        qry ="select sum(discountOnTotalCost) as amt from salesDistinctSalesID " +
                "where time >=date(now()) - interval "+tfValue+" day " ;

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


    int tfValue=0;
    private boolean validateTextField(){
        lbMyOwn.setText("");
        lbMyOwn1.setText("");

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




    public void updateProfitSum() {

        if (DBcon()) {

            try {

                qry = "update sales set profitSum =(select profit from" +
                        " profitSum where sales.salesID=profitSum.salesID)";

                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE :" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();

                }
            }
        }


    }





    private void getTodayProfit() {

        DBcon();
        openConn(conn);
        qry = "select  sum(profitSum)" +
                " as amt " +
                "from salesDistinctSalesID where date(time)=curdate() " ;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbToday1.setText(String.valueOf(dp00.format(amt2)));




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



    private void getWeekSalesProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID where " +
                "yearweek(time,1) = yearweek(curdate(),1) ";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbWeek1.setText(String.valueOf( dp00.format(amt2)));




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

    private void getMonthSalesProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID " +
                "where month(time) = month(curdate()) and year(time)=year(curdate()) ";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbMonth1.setText(String.valueOf(dp00.format(amt2)));




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

    private void getYearSalesProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID " +
                "where year(time) = year(curdate()) ";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbYear1.setText(String.valueOf(dp00.format(amt2)));




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






    private void getLastYearSalesProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from " +
                "salesDistinctSalesID where year(time)= year(date_sub(curdate(),interval 1 Year))" ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbLastyear1.setText(String.valueOf(dp00.format(amt2)));

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


    private void getOverallProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID" ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbOverAll1.setText(String.valueOf(dp00.format(amt2)));

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



    private void getLastMonthProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID where year(time)=year(current_date -interval 1 month) " +
                " and month(time)=month(current_date - interval 1 month) ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbLastMonth1.setText(String.valueOf(dp00.format(amt2)));

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



    private void getLastWeekProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID where" +
                " yearweek(time)=yearweek(now()- interval 1 week)" ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbLastWeek1.setText(String.valueOf(dp00.format(amt2)));


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

    private void getMyOwnDataProfit() {

        DBcon();
        openConn(conn);
        qry ="select sum(profitSum) as amt from salesDistinctSalesID " +
                "where time >=date(now()) - interval "+tfValue+" day " ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                lbMyOwn1.setText(String.valueOf(dp00.format(amt2)));

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
