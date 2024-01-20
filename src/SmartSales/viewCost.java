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

public class viewCost extends DBconnect {
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

    @FXML
    void getMyOwnAmount(KeyEvent event) {
if (validateTextField()){
    getMyOwnData();
}

    }

    @FXML
    void ok(ActionEvent event) {
        openWindowByClick(event,"reportWindow.fxml");
    }




   public  void initialize(){
       getToday();
       getWeekSales();
       getMonthSales();
       getYearSales();
       getLastYearSales();
       getOverall();
       getLastMonth();
       getLastWeek();
        
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
        qry = "select  sum(Cost)" +
                " as amt " +
                "from costView where date(time)=curdate() ";
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
        qry ="select sum(Cost) as amt from costView where " +
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
        qry ="select sum(Cost) as amt from CostView " +
                "where month(time) = month(curdate()) and year(time)=year(curdate())";

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
        qry ="select sum(Cost) as amt from costView " +
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
        qry ="select sum(Cost) as amt from " +
                "costView where year(time)= year(date_sub(curdate(),interval 1 Year))" ;

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
        qry ="select sum(Cost) as amt from costView" ;

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
        qry ="select sum(Cost) as amt from costView where year(time)=year(current_date -interval 1 month) " +
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
        qry ="select sum(Cost) as amt from costView where yearweek(time)=yearweek(now()- interval 1 week)" ;

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
        qry ="select sum(Cost) as amt from costView where time >=date(now()) - interval "+tfValue+" day " ;

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



}
