package SmartSales;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import javafx.scene.image.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class reportWindow extends DBconnect {
    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    @FXML
    private Button btviewCost;

    @FXML
    private Button btPrintCost;

    @FXML
    private Button btViewsales;

    @FXML
    private Button btPrintSales;

    @FXML
    private Button btDone;



    @FXML
    void printSales(ActionEvent event) {

        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try {

                            pb.setVisible(true);
                            btPrintSales.setVisible(false);
                            btViewsales.setVisible(false);
                            btviewCost.setVisible(false);

                            JasperPrint jp;
                            Map param = new HashMap();
                            String jasperDocument="salesAccountsPdf.jasper";
                            String title="Powered BY AECleanCodes 0549822202";

                            try {
                                openConn(conn);
                                jp = JasperFillManager.fillReport(jasperDocument, param, ShareData.directConnection);
                                updateProgress(2,5);

                                JasperViewer jv = new JasperViewer(jp, false);
                                updateProgress(3,5);
                                jv.setTitle(title);
                                updateProgress(4,5);
                                jv.setVisible(true);
                                updateProgress(5,5);

                                pb.setVisible(false);
                                btPrintSales.setVisible(true);
                                btViewsales.setVisible(true);
                                btviewCost.setVisible(true);


                            } catch (JRException e) {
                                e.printStackTrace();
                            }


                            pb.setVisible(false);

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
    }

    @FXML
    void viewCost(ActionEvent event) {
        openWindowByClick(event,"viewCost.fxml");
    }

    @FXML
    void viewSales(ActionEvent event) {
        openWindowByClick(event,"viewSales.fxml");
    }

    @FXML
    void done(ActionEvent event){
        openWindowByClick(event,"adminMainWindow.fxml");
    }


    @FXML
    private ProgressBar pb;

    public  void initialize(){
        pb.setStyle("-fx-accent: blue");

        pb.setVisible(true);
        btPrintSales.setVisible(false);
        btViewsales.setVisible(false);
        btviewCost.setVisible(false);


        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        try {




                            deleteRecord("reportCost");
                            deleteRecord("reportSales");
                            deleteRecord("reportProfit");

                            updateProgress(1,5);


                            updateProfitSum();
                            getToday();
                            getWeekSales();
                            getLastWeek();
                            getMonthSales();
                            getLastMonth();
                            getYearSales();
                            getLastYearSales();
                            getOverall();

                            updateProgress(2,5);

                            getTodayCost();
                            getWeekCost();
                            getLastWeekCost();
                            getMonthCost();
                            getLastMonthCost();
                            getYearCost();
                            getLastYearCost();
                            getOverallCost();



                            updateProgress(3,5);


                            getTodayProfit();
                            getWeekSalesProfit();
                            getLastWeekProfit();
                            getMonthSalesProfit();
                            getLastMonthProfit();
                            getYearSalesProfit();
                            getLastYearSalesProfit();
                            getOverallProfit();


                            updateProgress(4,5);

                            reportSales();
                            reportCost();
                            reportProfit();


                            updateProgress(5,5);


                            pb.setVisible(false);
                            btPrintSales.setVisible(true);
                            btViewsales.setVisible(true);
                            btviewCost.setVisible(true);




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



    }


    public void reportProfit() {

        if (DBcon()) {

            try {

                qry ="insert into reportProfit(pToday,pThisWeek,pLastWeek," +
                        "pThisMonth,pLastMonth,pThisYear,pLastYear,pOverall ) " +
                        "values('"+ShareData.profit.get(0)+"', " +
                        "'"+ShareData.profit.get(1)+"'," +
                        "'"+ShareData.profit.get(2)+"'," +
                        "'"+ShareData.profit.get(3)+"'," +
                        "'"+ShareData.profit.get(4)+"'," +
                        "'"+ShareData.profit.get(5)+"'," +
                        "'"+ShareData.profit.get(6)+"'," +
                        "'"+ShareData.profit.get(7)+"')";

                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();

                }
            }
        }


    }

    public void reportSales() {

        if (DBcon()) {

            try {

                qry ="insert into reportSales(sToday,sThisWeek,sLastWeek," +
                        "sThisMonth,sLastMonth,sThisYear,sLastYear,sOverall ) " +
                        "values('"+ShareData.sales.get(0)+"', " +
                        "'"+ShareData.sales.get(1)+"'," +
                        "'"+ShareData.sales.get(2)+"'," +
                        "'"+ShareData.sales.get(3)+"'," +
                        "'"+ShareData.sales.get(4)+"'," +
                        "'"+ShareData.sales.get(5)+"'," +
                        "'"+ShareData.sales.get(6)+"'," +
                        "'"+ShareData.sales.get(7)+"')";

                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();

                }
            }
        }


    }


    public void reportCost() {

        if (DBcon()) {

            try {

                qry ="insert into reportCost(cToday,cThisWeek,cLastWeek," +
                        "cThisMonth,cLastMonth,cThisYear,cLastYear,cOverall ) " +
                        "values('"+ShareData.cost.get(0)+"', " +
                        "'"+ShareData.cost.get(1)+"'," +
                        "'"+ShareData.cost.get(2)+"'," +
                        "'"+ShareData.cost.get(3)+"'," +
                        "'"+ShareData.cost.get(4)+"'," +
                        "'"+ShareData.cost.get(5)+"'," +
                        "'"+ShareData.cost.get(6)+"'," +
                        "'"+ShareData.cost.get(7)+"')";

                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT UPDATE UCOST:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();

                }
            }
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
                ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            ShareData.sales .add("0.00");
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

                ShareData.sales .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {

            ShareData.sales .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {

            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));




            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));

            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
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

                ShareData.profit .add (String.valueOf(dp00.format(amt2)));


            }

        } catch (Exception e) {
            ShareData.profit.add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }





    private void getTodayCost() {

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

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));




            }
            else {
                ShareData.cost .add("0.00");
            }

        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }



    private void getWeekCost() {

        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from costView where " +
                "yearweek(time,1) = yearweek(curdate(),1) ";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));




            }
            else {
                ShareData.cost .add("0.00");
            }

        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }


//"+ID+"'

    private void getMonthCost() {

        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from CostView " +
                "where month(time) = month(curdate()) and year(time)=year(curdate())";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);
            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));
            }
            else {
                ShareData.cost .add("0.00");
            }
        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }

    private void getYearCost() {

        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from costView " +
                "where year(time) = year(curdate()) ";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));
            }
            else {
                ShareData.cost .add("0.00");
            }

        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }






    private void getLastYearCost() {

        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from " +
                "costView where year(time)= year(date_sub(curdate(),interval 1 Year))" ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));

            }
            else {
                ShareData.cost .add("0.00");
            }

        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }


    private void getOverallCost() {

        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from costView" ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);
                ShareData.cost .add (String.valueOf(dp00.format(amt2)));

            }
            else {
                ShareData.cost .add("0.00");
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



    private void getLastMonthCost() {


        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from costView where year(time)=year(current_date -interval 1 month) " +
                " and month(time)=month(current_date - interval 1 month) ";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));

            }
            else {
                ShareData.cost .add("0.00");
            }

        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();



        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }



    private void getLastWeekCost() {

        DBcon();
        openConn(conn);
        qry ="select sum(Cost) as amt from costView where yearweek(time)=yearweek(now()- interval 1 week)" ;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                String amt = rs.getString("amt").trim();
                double amt2 = Double.parseDouble(amt);

                ShareData.cost .add (String.valueOf(dp00.format(amt2)));

            }
            else {
                ShareData.cost .add("0.00");
            }

        } catch (Exception e) {
            ShareData.cost .add("0.00");
            e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {
e.printStackTrace();
            }
        }

    }

}
