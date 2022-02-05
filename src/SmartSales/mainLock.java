package SmartSales;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.animation.Animation;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainLock extends DBconnect {

    String DBName = null;

    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;

    String username = null;
    String password = null;
    boolean isSeller = false;
    boolean isAdmin = false;
    boolean isNewSeller = false;

    @FXML
    private Button btfp;

    @FXML
    private PasswordField ptf1;

    @FXML
    private PasswordField ptf2;

    @FXML
    private Button btL;

    @FXML
    private Button btD;

    @FXML
    private FontAwesomeIconView p2;

    @FXML
    private FontAwesomeIconView p1;

    @FXML
    private FontAwesomeIconView eye2;

    @FXML
    private FontAwesomeIconView eye1;

    @FXML
    private OctIconView imDel;

    @FXML
    private TextField tf2;

    @FXML
    private TextField tf1;


    @FXML
    private Label lbSalesToday;

    @FXML
    private Label lbSalesToday1;


    @FXML
    void btExited(MouseEvent event) {

    }

    @FXML
    void canEntered(MouseEvent event) {

    }

    @FXML
    void canExit(MouseEvent event) {

    }


    @FXML
    void forgotPassWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("forgotPassword.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
            String css = this.getClass().getResource("forgotPassword.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
            currentStage.setResizable(false);
        } catch (Exception e) {

        }

    }


    @FXML
    void clearField(ActionEvent event) {
        ptf1.clear();
        ptf2.clear();
        tf1.clear();
        tf2.clear();

    }

    @FXML
    void clearPtf(MouseEvent event) {

    }

    @FXML
    void isMouseEntered(MouseEvent event) {

    }

    @FXML
    void off_1(MouseEvent event) {
        ptf1.setVisible(true);
        tf1.setVisible(false);
    }

    @FXML
    void off_2(MouseEvent event) {
        ptf2.setVisible(true);
        tf2.setVisible(false);
    }

    @FXML
    void show_1(MouseEvent event) {
        tf1.setText(ptf1.getText().trim());
        ptf1.setVisible(false);
        tf1.setVisible(true);
    }

    @FXML
    void show_2(MouseEvent event) {
        tf2.setText(ptf2.getText().trim());
        ptf2.setVisible(false);
        tf2.setVisible(true);
    }


    @FXML
    private Label lbFP;


    @FXML
    void submit(ActionEvent event) {
        if (validate()) {

            if (isAdmin) {
                try {

                    updateCurrentUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    ShareData.isAdmin = true;
                    root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
                    currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentScene = new Scene(root);
                    String css = this.getClass().getResource("adminMainWindow.css").toExternalForm();
                    root.getStylesheets().add(css);
                    currentStage.setScene(currentScene);

                    currentStage.setTitle("SmartSales    Current User: " + ShareData.currentUser_.toUpperCase());
                    currentStage.show();
                    root.requestFocus();
                } catch (Exception e) {


                }

            } else if (isSeller) {
                ShareData.isAdmin = false;
                try {
                    getUserDetails();
                    updateCurrentUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    root = FXMLLoader.load(getClass().getResource("salesWindow.fxml"));
                    currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentScene = new Scene(root);
//                    String css=this.getClass().getResource("sellerPassword.css").toExternalForm();
//                    root.getStylesheets().add(css);
                    currentStage.setScene(currentScene);
                    currentStage.setTitle("SmartSales    Current User: " + ShareData.currentUser_.toUpperCase());

                    currentStage.show();
                    root.requestFocus();
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
                    currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
                    currentStage.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            } else if (isNewSeller) {

                ShareData.isAdmin = false;
                try {
                    root = FXMLLoader.load(getClass().getResource("sellerPassword.fxml"));
                    currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentScene = new Scene(root);
                    String css = this.getClass().getResource("sellerPassword.css").toExternalForm();
                    root.getStylesheets().add(css);
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

            login();
        }
    }


    public boolean validate() {
getFocus1();
        tf1.setText(ptf1.getText().trim());
        tf2.setText(ptf2.getText().trim());


        String tf1s = tf1.getText().trim();
        String tf2s = tf2.getText().trim();

        username = tf1s;
        password = tf2s;

        if (tf1s.isEmpty()) {
            ptf1.clear();

        } else if (tf2s.isEmpty()) {
            ptf2.clear();

        } else if (isSeller()) {
            isSeller = true;
        } else if (isAdmin()) {
            isAdmin = true;
        } else if (isNewSeller()) {
            isNewSeller = true;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("WRONG LOGIN DETAILS!");
            alert.showAndWait();
            isAdmin = false;
            isSeller = false;
        }

        if (isAdmin) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tCONGRATS!");
            alert.showAndWait();
            ptf1.clear();
            ptf2.clear();
            tf1.clear();
            tf2.clear();
            return true;
        }
        if (isSeller) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tCONGRATS!");
            alert.showAndWait();
            ptf1.clear();
            ptf2.clear();
            tf1.clear();
            tf2.clear();
            return true;
        }
        if (isNewSeller) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("\t\tHi!\n\n\t" + DBName + "\n\n\t PLEASE SET YOUR USER NAME\n \t AND " +
                    "PASSWORD");
            alert.showAndWait();
            ptf1.clear();
            ptf2.clear();
            tf1.clear();
            tf2.clear();
            return true;
        }
        return false;
    }


    public boolean isSeller() {
        sellerHasRegistered();
        qry = "SELECT name FROM password where name='" + username + "' and password= '" + password + "'";
        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {

                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean isNewSeller() {
        if (sellerHasRegistered()) {
            return false;
        }

        qry = "SELECT  name FROM seller where ID='" + username + "'";
        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                DBName = rs.getString("name");
                setN1(username, "00", "00", "00");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean sellerHasRegistered() {

        qry = "SELECT seller.ID as ID FROM seller  inner join password on seller.ID=password.ID && seller.ID='" + username + "'";
        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                String ID = rs.getString("ID");


                System.out.println("in two tables: " + ID);

                return true;
            } else {
                System.out.println(" no match in two tables: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAdmin() {
        qry = "SELECT name FROM admin where name='" + username + "' and password= '" + password + "'";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {

                username = rs.getString("name").trim();
                userID = password;

                ShareData.currentUser_ = username.trim();
                ShareData.userID_ = userID.trim();


                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }


    String userName = "";
    String userID = "";


    private void getUserDetails() {
        sellerHasRegistered();
        qry = "SELECT seller.ID, seller.name, seller.mobile from seller" +
                " inner join password on seller.ID=password.ID where password.name" +
                "='" + username + "' and password= '" + password + "'";
        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                userID = rs.getString("ID").trim();
                username = rs.getString("name").trim();
                ShareData.currentUser_ = username;
                ShareData.userID_ = userID;


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public void updateCurrentUser() {
        deleteRecord("currentUser");

        if (DBcon()) {
            openConn(ShareData.directConnection);
            try {

                qry = "Insert into currentUser(ID,fName)  values( '" + userID + "','" + username + "')";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }


    }



    public void initialize() {
        logOut(ShareData.userID_);

        getTodaySales();
        TranslateTransition rt = new TranslateTransition();
        rt.setDuration(Duration.seconds(1));
        rt.setNode(lbSalesToday);
        rt.setByX(80);
        rt.setAutoReverse(true);
        // rt.setDuration(Duration.millis(10000));
        rt.setCycleCount(TranslateTransition.INDEFINITE);
        rt.play();


        TranslateTransition rt2 = new TranslateTransition();
        rt2.setByX(100);
        rt2.setByY(100);
        rt2.setDuration(Duration.seconds(3));
        rt2.setNode(lbSalesToday1);
        rt2.setAutoReverse(true);
        rt2.setCycleCount(Animation.INDEFINITE);
        rt2.play();


        Task task=new Task() {
            @Override
            protected Object call() throws Exception {

                try {

                    updateQtyInItem();

                    updateUsedQtyInItem();
                    updateFinishingPercentage();
                    createExcelLoadItemsFile();



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

    DecimalFormat dp2 = new DecimalFormat("0.00");

    private void getTodaySales() {

        DBcon();
        openConn(conn);
        qry = "select sum(discountOnTotalCost) as totalSales from salesDistinctSalesID where date(time)=curdate()";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);


            while (rs.next()) {
                String amt = rs.getString("totalSales").trim();
                double amt2 = Double.parseDouble(amt);


                lbSalesToday1.setText( "GHS "+   (String.valueOf(dp2.format(amt2))));



            }

        } catch (Exception e) {
            //  e.printStackTrace();
        } finally {
            try {
                conn.close();

            } catch (Exception e) {

            }
        }

    }


    public void login() {

    if (DBcon()) {
        openConn(ShareData.directConnection);
        try {

            qry = "Insert into log(ID,Name)  values( '" + userID + "','" + username + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("COULD NOT SAVE DATA:" + e.getMessage());
            alert.showAndWait();
            try {
                conn.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }





    }





    public void logOut( String oldID){

        DBcon();

        Date date=new Date();
        long time=date.getTime();
        Timestamp ts=new Timestamp(time);

        qry="UPDATE log SET outTime='"+ts+"' where outTime is null and ID= '"+oldID+"'";

        try {
            st=conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();


        }
        finally {
            try {
                conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }



    }

    public void updateUsedQtyInItem(){

        DBcon();



        qry=" update item set item.usedQty = " +
                " (select  sum( sales.qty) as qtySum from sales where " +
                " sales.sName=item.sName group by item.sName )";

        try {
            st=conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();


        }
        finally {
            try {
                conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }



    }



    public void updateQtyInItem(){

        DBcon();



        qry=" update item set item.qty =(select  sum( item1.qty) as qtySum " +
                "from item1 where item1.sName=item.sName group by item.sName)";

        try {
            st=conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();


        }
        finally {
            try {
                conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }



    }






    public static void createExcelLoadItemsFile(){
        ShareData shareData = ShareData.getInstance();
        DBconnect dBconnect = new DBconnect();
        File file = new File(shareData.file.toString() + "\\loadItems.xlsx");
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("items");

            Map<String,Object[]> list=new HashMap<String,Object[]>();

            list=  dBconnect.getItemsInShortage();

            spreadsheet.addMergedRegion(new CellRangeAddress(
                    0, // first row (0-based)
                    1, // last row (0-based)
                    0,
                    // first column (0-based)
                    3 // last column (0-based)
            ));


            spreadsheet.addMergedRegion(new CellRangeAddress(
                    0, // first row (0-based)
                    1, // last row (0-based)
                    6,
                    // first column (0-based)
                    9 // last column (0-based)
            ));


            CellStyle style = spreadsheet.getWorkbook().createCellStyle();

            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle style1 = spreadsheet.getWorkbook().createCellStyle();
            spreadsheet.setColumnWidth(0, 8000);
            spreadsheet.setColumnWidth(6, 8000);
            style1.setAlignment(HorizontalAlignment.CENTER);
            style1.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font=workbook.createFont();
            Font font1=workbook.createFont();
            font.setColor(IndexedColors.WHITE.index);
            font1.setColor(IndexedColors.WHITE.index);
            font.setBold(true);
            font1.setBold(true);



            // style1.setFillBackgroundColor(IndexedColors.BLACK.index);

            style.setFillForegroundColor(IndexedColors.RED.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFont(font);

            style1.setFillForegroundColor(IndexedColors.BLUE.index);
            style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style1.setFont(font1);




            Set<String> keys=list.keySet();
            int rowNum=0;

            for (String i:keys){
                Row row = spreadsheet.createRow(rowNum++);
                Object [] array=list.get(i);
                int cellNum=0;
                for (Object ii:array){
                    Cell cell = row.createCell(cellNum++);
                    int c=cell.getColumnIndex();




                    if (c<4) {

                        cell.setCellStyle(style);
                    }

                    if ((cellNum>6)) {
                        cell.setCellStyle(style1);
                    }


                    if (ii instanceof Double){
                        cell.setCellValue((Double)ii );
                    }
                    else   if (ii instanceof Integer){
                        cell.setCellValue((Integer)ii);
                    }
                    else    if (ii instanceof String){
                        cell.setCellValue((String) ii);
                    }
                }
            }

            FileOutputStream out = new FileOutputStream(new File(file.toString()));
            workbook.write(out);

            out.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


 int p;
    public void getPercentageItemFinishing() {
        qry = "SELECT declareFinishing as p from item limit 1";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {

                String v  = rs.getString("p").trim();

                  p=Integer.parseInt(v);


                 conn.close();


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public void updateFinishingPercentage(){
        getPercentageItemFinishing();
        DBcon();
        qry="UPDATE item SET declareFinishing ="+p;

        try {
            st=conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();


        }
        finally {
            try {
                conn.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


    }


    void getFocus1(){

        if (ptf1.getText().trim().isEmpty()){
            ptf1.requestFocus();
        }

        else   if (ptf2.getText().trim().isEmpty()){
            ptf2.requestFocus();
        }





    }


}