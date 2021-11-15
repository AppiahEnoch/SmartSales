package SmartSales;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;


import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

import javax.script.Bindings;
import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class loadItemManual extends DBconnect {
    ShareData m = ShareData.getInstance();
    boolean canDeleteFileNoImageList = true;
    String item;
    String price;
    String qty;
    String sn;
    String cost;
    String savedSN = null;
    int ctr = 0;




    boolean isUpdateMainItem = false;

    String itemToDelete = null;

    @FXML
    private Stage currentStage;
    private Scene currentScene;
    private Parent root;


    @FXML
    private TableColumn<ITEM, String> col14;


    @FXML
    private TableView<ITEM> tbV1;

    @FXML
    private TableColumn<ITEM, String> col11;

    @FXML
    private TableColumn<ITEM, String> col12;

    @FXML
    private TableColumn<ITEM, String> col13;

    @FXML
    private TableView<ITEM2> tbV2;
    @FXML
    TableColumn<ITEM, String> colsn;

    @FXML
    private TableColumn<ITEM2, String> col21;

    @FXML
    private TableColumn<ITEM2, String> col22;

    @FXML
    private TableColumn<ITEM2, String> col23;
    @FXML
    private TableColumn<ITEM2, String> col24;

    @FXML
    ImageView imageV;

    @FXML
    private TextField tf11;

    @FXML
    private TextField tf12;

    @FXML
    private TextField tf13;

    @FXML
    private Button bt1;

    @FXML
    private TextField tf21;

    @FXML
    private TextField tf22;

    @FXML
    private TextField tf23;

    @FXML
    private Button bt2;

    @FXML
    private Button btb;

    @FXML
    private Button btclose;

    @FXML
    private Button btc;

    @FXML
    private Button btd1;

    @FXML
    private Button btd2;
    @FXML
    private Button btfc;


    @FXML
    private TextField tf221;

    @FXML
    private TextField tf121;

    TextField tfSN = new TextField("0");
    boolean regulate = true;


    @FXML
    void fullScreen(ActionEvent event) {
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (currentStage.isMaximized()) {
            minWindow();
            btfc.setText("MAX");

        } else {
            currentStage.setMaximized(true);
            maxWindow();
            btfc.setText("Min");


        }

    }


    @FXML
    private void maxWindow() {
        int windowSize = (int) tbV1.getWidth();
        int windowSize2 = (int) tbV2.getWidth();

        int windowH = (int) currentStage.getHeight();
        double h = 0.43 * windowH;
        tbV1.setPrefHeight(h);


        double s1 = 0.10 * windowSize;
        double s2 = 0.45 * windowSize;
        double s3 = 0.15 * windowSize;
        double s4 = 0.15 * windowSize;
        double s5 = 0.15 * windowSize;

        double s22 = 0.40 * windowSize2;
        double s23 = 0.20 * windowSize2;
        double s24 = 0.20 * windowSize2;
        double s25 = 0.20 * windowSize2;

        System.out.println("windowsize:" + windowSize);

        colsn.setPrefWidth(s1);
        col11.setPrefWidth(s2);
        col12.setPrefWidth(s3);
        col13.setPrefWidth(s4);
        col14.setPrefWidth(s5);

        col21.setPrefWidth(s22);
        col22.setPrefWidth(s23);
        col23.setPrefWidth(s24);
        col24.setPrefWidth(s25);

        tf21.requestFocus();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
        currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);


    }

    @FXML
    private void minWindow() {
        currentStage.setMaximized(false);
        tbV1.setPrefWidth(400);
        colsn.setPrefWidth(60);
        col11.setPrefWidth(379);
        col12.setPrefWidth(140);
        col13.setPrefWidth(150);
        col14.setPrefWidth(150);

        tbV1.setPrefHeight(282);
        tbV2.setPrefWidth(350);
        col21.setPrefWidth(266);
        col22.setPrefWidth(125);
        col23.setPrefWidth(134);
        col24.setPrefWidth(134);
        currentStage.sizeToScene();


        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        currentStage.setX((primScreenBounds.getWidth() - currentStage.getWidth()) / 2);
        currentStage.setY((primScreenBounds.getHeight() - currentStage.getHeight()) / 2);
        currentStage.setResizable(false);
    }


    @FXML
    void addItem1(ActionEvent event) {

        //  isItemInDatabase(checkEmptyFolder());

        boolean isWrong = false;
        item = tf11.getText().trim();
        qty = tf12.getText().trim();
        price = tf13.getText().trim();
        cost = tf121.getText().trim();
        int s;


        if (item.isEmpty()) {
            tf11.clear();
            isWrong = true;
        } else if (qty.isEmpty()) {
            tf12.clear();
            isWrong = true;
        } else if (cost.isEmpty()) {
            tf121.clear();
            isWrong = true;
        } else if (price.isEmpty()) {
            tf13.clear();
            isWrong = true;
        } else if (!(isNumeric(qty))) {
            tf12.clear();
            tf12.requestFocus();
        } else if (!(isNumeric(price))) {
            tf13.clear();
            tf13.requestFocus();
        } else if (!(isNumeric(cost))) {
            tf121.clear();
            tf121.requestFocus();
        } else {


            itemToDelete = savedSN;
            if (!(savedSN == null)) {
                tfSN.setText(savedSN);
                s = Integer.parseInt(tfSN.getText().trim());
                savedSN = null;
            } else {
                s = ctr;
                s++;
                ctr = s;

            }


            sn = String.valueOf(s);

            int i = 0;
            if (!(itemToDelete == null)) {
                i = Integer.parseInt(itemToDelete);
            }


            deleteRecord("tmpl1", "where id=" + i);

            insertItem4("tmpl1", Integer.parseInt(sn), item, Integer.parseInt(qty),

                    Double.parseDouble(cost), Double.parseDouble(price));


            col11.setCellValueFactory(new PropertyValueFactory<ITEM, String>("item"));
            col12.setCellValueFactory(new PropertyValueFactory<ITEM, String>("qty"));
            col14.setCellValueFactory(new PropertyValueFactory<ITEM, String>("cost"));
            col13.setCellValueFactory(new PropertyValueFactory<ITEM, String>("price"));
            colsn.setCellValueFactory(new PropertyValueFactory<ITEM, String>("sn"));

            getItems4("tmpl1");

            tf11.clear();
            tf12.clear();
            tf13.clear();
            tf121.clear();
            tf21.requestFocus();

            isTableviewEmpty();

        }


    }


    public void initialize() {
        deleteRecord("tmpl1");
        //    loadImage();

        col11.setCellValueFactory(new PropertyValueFactory<ITEM, String>("item"));
        col12.setCellValueFactory(new PropertyValueFactory<ITEM, String>("qty"));
        col14.setCellValueFactory(new PropertyValueFactory<ITEM, String>("cost"));
        col13.setCellValueFactory(new PropertyValueFactory<ITEM, String>("price"));
        colsn.setCellValueFactory(new PropertyValueFactory<ITEM, String>("sn"));

        col21.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("name"));
        col22.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("qty"));
        col23.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("cost"));
        col24.setCellValueFactory(new PropertyValueFactory<ITEM2, String>("price"));

        //  getImage();
        startThread();

        if (isTableviewEmpty()) {
            imageV.setImage(null);
        }

    }


    @FXML
    void out() {
        System.out.println("got it!");
    }

    @FXML
    void addItem2(ActionEvent event) {

        boolean isWrong = false;
        item = tf21.getText().trim();
        qty = tf22.getText().trim();
        price = tf23.getText().trim();
        cost = tf221.getText().trim();
        int s;
        setFocus();
        if (item.isEmpty()) {
            tf21.clear();
            isWrong = true;
        } else if (qty.isEmpty()) {
            tf22.clear();

            isWrong = true;
        } else if (cost.isEmpty()) {
            tf221.clear();
            isWrong = true;
        } else if (price.isEmpty()) {
            tf23.clear();
            isWrong = true;
        } else if (!(isNumeric(qty))) {
            tf22.clear();
            tf22.requestFocus();
        } else if (!(isNumeric(price))) {
            tf23.clear();
            tf23.requestFocus();
        } else if (!(isNumeric(cost))) {
            tf221.clear();
            tf221.requestFocus();
        } else {

            itemToDelete = savedSN;
            if (!(savedSN == null)) {
                tfSN.setText(savedSN);
                s = Integer.parseInt(tfSN.getText().trim());
                savedSN = null;
            } else {
                s = ctr;
                s++;
                ctr = s;

            }


            sn = String.valueOf(s);

            int i = 0;
            if (!(itemToDelete == null)) {
                i = Integer.parseInt(itemToDelete);
            }


            deleteRecord("tmpl1", "where id=" + i);

            insertItem4("tmpl1", Integer.parseInt(sn), item, Integer.parseInt(qty),
                    Double.parseDouble(cost), Double.parseDouble(price));


            getItems4("tmpl1");

            tf21.clear();
            tf22.clear();
            tf23.clear();
            tf221.clear();
            tf21.requestFocus();
            startThread();
            isTableviewEmpty();

        }

    }

    @FXML
    void clearF(ActionEvent event) {


        tf21.clear();
        tf22.clear();
        tf23.clear();
        tf221.clear();
        tf121.clear();
        tf11.clear();
        tf12.clear();
        tf13.clear();

        itemToDelete = null;
        savedSN = null;


    }

    @FXML
    void deleteAll(ActionEvent event) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "DO YOU REALLY WANT TO DELETE ALL RECORDS?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                " DELETION.",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("DELETE SELECTED WARNING!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            tbV1.getItems().clear();
            tbV2.getItems().clear();
            tfSN.setText("0");
            deleteRecord("tmpl1");
            tf21.clear();
            tf22.clear();
            tf23.clear();
            tf221.clear();

            tf11.clear();
            tf12.clear();
            tf13.clear();
            tf121.clear();

            imageV.setImage(null);


            ctr = 0;
        }
    }


    @FXML
    void deleteSelected(ActionEvent event) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "DO YOU REALLY WANT TO DELETE THE SELECTED RECORD?\n\n\t NB: \n YOU CANNOT UNDO THIS CHANGES\n AFTER" +
                                " DELETION.",
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("DELETE SELECTED WARNING!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {


            try {
                ITEM colSelected1 = tbV1.getSelectionModel().getSelectedItem();
                Object n = colSelected1.getItem();
                Object ID = colSelected1.getSn();
                System.out.println(n);
                System.out.println(ID);
                String ID2 = ID.toString();
                deleteSelectedFromTmpl1(ID2);
                getItems4("tmpl1");

                imageV.setImage(null);

            } catch (Exception e) {

            }


        } else {

        }


    }


    @FXML
    void submit(ActionEvent event) {

        if ((countRecord("tmpl1", "ID") > 0)) {
            if (isGoOn("DO YOU REALLY WANT TO SAVE THE RECORD?")) {
                isUpdateMainItem = true;
                getItems4("tmpl1");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("CONGRATS!\n\nITEMS  SUBMITTED SUCCESSFULLY!");
                alert.showAndWait();


                tbV1.getItems().clear();
                deleteRecord("tmpl1");

            }
        }

        isItemInDatabase(checkEmptyFolder());

        writeAllNoImageItems();
    }

    @FXML
    void goBackR(ActionEvent event) {
        if ((countRecord("tmpl1", "ID") > 0)) {

            if (isGoOn("DO YOU WANT TO SAVE THE RECORD?")) {

                isUpdateMainItem = true;
                getItems4("tmpl1");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("CONGRATS!\n\nITEMS  SUBMITTED SUCCESSFULLY!");
                alert.showAndWait();


                tbV1.getItems().clear();
                deleteRecord("tmpl1");
            }

        }


        try {
            root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setResizable(true);
            currentScene = new Scene(root);
            String css = this.getClass().getResource("adminMainWindow.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
        } catch (Exception e) {


        }
    }

    @FXML
    void goMainWindow(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
            currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentScene = new Scene(root);
            String css = this.getClass().getResource("mainLock.css").toExternalForm();
            root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
        } catch (Exception e) {


        }
    }


    // loads tbV1
    @FXML
    public void getItems4(String table) {
        tbV1.getItems().clear();
        qry = "SELECT ID,item,qty,cost,price from " + table + " order by ID ASC";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {
                sn = rs.getString("ID").trim();
                item = rs.getString("item").trim();
                qty = rs.getString("qty").trim();
                cost = rs.getString("cost").trim();
                price = rs.getString("price").trim();


                if (isUpdateMainItem) {

                    updateMainItem(item, "noFName", Integer.parseInt(qty), Double.parseDouble(cost), Double.parseDouble(price));
                    insertItem2(item, Integer.parseInt(qty), Double.parseDouble(cost), Double.parseDouble(price));

                } else {
                    tbV1.getItems().add(new ITEM(sn, item, qty,
                            price, cost));
                }


            }
            isUpdateMainItem = false;
            conn.close();

        } catch (Exception e) {
            System.out.println("hh");
            e.printStackTrace();
        }
    }


    public void deleteSelectedFromTmpl1(String ID) {
        DBcon();
        qry = "DELETE FROM tmpl1 where ID=" + ID;
        try {
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();

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


    @FXML
    void getSelected() {
        try {
            ITEM colSelected1 = tbV1.getSelectionModel().getSelectedItem();
            Object ns = colSelected1.getSn();
            Object item = colSelected1.getItem();
            Object qty = colSelected1.getQty();
            Object price = colSelected1.getPrice();
            Object cost = colSelected1.getCost();

            itemToDelete = item.toString();
            savedSN = ns.toString();

            tf21.setText(item.toString());
            tf22.setText(qty.toString());
            tf23.setText(price.toString());
            tf221.setText(cost.toString());


        } catch (Exception e) {

        }
    }


    @FXML
    void getSelected2() {
        try {
            ITEM2 colSelected1 = tbV2.getSelectionModel().getSelectedItem();

            Object item = colSelected1.getName();
            Object qty = colSelected1.getQty();
            Object price = colSelected1.getPrice();
            Object cost = colSelected1.getCost();


            tf11.setText(item.toString());
            tf12.setText(qty.toString());
            tf121.setText(cost.toString());
            tf13.setText(price.toString());
            getImage(tf11.getText().trim());


        } catch (Exception e) {

        }
    }


    private void setFocus() {

        System.out.println(99999);
        if ((item.isEmpty())) {
            tf21.requestFocus();
        } else if (qty.isEmpty()) {
            tf22.requestFocus();
        } else if (cost.isEmpty()) {
            tf221.requestFocus();
        } else if (price.isEmpty()) {
            tf23.requestFocus();
        } else {

            stopThread();

        }


    }


    public void updateMainItem(String sName, String fName, int qty2, double cost2, double price2) {
        openConn(conn);
        try {
            qry = "INSERT IGNORE INTO " +
                    "item(fName,sName,qty,cost,price,img)" +
                    " values('" + fName + "','" + sName + "','" + qty2 + "','" + cost2 + "','" + price2 + "',null)";

            st = conn.createStatement();
            st.executeUpdate(qry);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }

    }

    private boolean isGoOn(String message) {
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        message,
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("CONFIRM ACTION!");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {

            return true;
        }
        return false;
    }

    @FXML
    synchronized private boolean fillTbV2() {

        openConn(conn);
        String i = "";

        try {


            while (m.continueItemSuggestion) {


                String keyword = tf21.getText().trim();
                if (!(i.equals(keyword))) {
                    tbV2.getItems().clear();
                }
                if (!(keyword.isEmpty())) {

                    qry = "SELECT distinct sName,qty,cost,price" +
                            " from item " +
                            "where sName like '%" + keyword + "%' " +
                            "order by sName ASC";
                    try {
                        openConn(conn);
                        st = conn.createStatement();
                        rs = st.executeQuery(qry);

                        while (rs.next()) {
                            String s = rs.getString("sName").trim();
                            String q = rs.getString("qty").trim();
                            String c = rs.getString("cost").trim();
                            String p = rs.getString("price").trim();


                            if (!(i.equals(keyword))) {
                                tbV2.refresh();
                                tbV2.getItems().addAll(new ITEM2(s, q, c, p));

                            }

                        }
                        i = keyword;


                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    @FXML
    void startThread() {
        stopThread();

        m.continueItemSuggestion = true;
        new Thread(() -> fillTbV2()).start();
    }

    @FXML
    void stopThread() {
        m.continueItemSuggestion = false;
    }

    private void loadImage(File myfile, String name) {

        System.out.println("myfff:" + myfile);
        try {
            PreparedStatement pst = null;
            openConn(conn);
            ShareData shareData = ShareData.getInstance();
            //  File file=new File("C:/Users/AECleanCodes/Downloads/biscuits/digestive.jpg");  //correct


            qry = "update item set img= ? where sName= ? ";
            pst = conn.prepareStatement(qry);
            FileInputStream fileInputStream = new FileInputStream(myfile);
            pst.setBinaryStream(1, fileInputStream);
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void getImage() {
        Blob img = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        File file = null;

        try {
            openConn(conn);
            st = conn.createStatement();
            qry = "SELECT img from item";
            rs = st.executeQuery(qry);

            if (rs.next()) {
                inputStream = rs.getBinaryStream("img");

            }


            try {

                inputStreamReader = new InputStreamReader(inputStream);
            } catch (Exception ee) {

            }

            if (inputStreamReader.ready()) {
                file = new File("item.png");
            }

            OutputStream os = new FileOutputStream(file);
            byte[] content = new byte[1024];

            while (inputStream.read(content) > 0) {
                os.write(content);
            }

            Image image1 = new Image(file.toURI().toString());
            imageV.setImage(image1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private int getImage(String sName) {
        Blob img = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        File file = null;

        try {
            openConn(conn);
            st = conn.createStatement();
            qry = "SELECT img from item where sName= '" + sName + "'";
            rs = st.executeQuery(qry);

            if (rs.next()) {
                inputStream = rs.getBinaryStream("img");


            }

            if (inputStream == null) {

                imageV.setImage(null);
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
            imageV.setImage(image1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    public String[] checkEmptyFolder() {


        ShareData shareData = ShareData.getInstance();
        File file = new File(shareData.file.toString());
        System.out.println("CHECK FOLDER " + file);
        try {

            if (file.isDirectory()) {
                String[] avail = file.list();

                if (avail.length > 0) {

                    System.out.println("avail:" + avail.length);
                    return avail;
                } else {
                    System.out.println("folder empty");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


    public boolean isItemInDatabase(String[] avail) {


        boolean isCorrect = false;
        ShareData shareData = ShareData.getInstance();

        StringBuilder sb = new StringBuilder();


        try {


            for (String i : avail) {

                sb.delete(0, sb.length());
                sb.append(shareData.file.toString() + "\\");

                String name = removeFileExtension(i);
                sb.append(i);
                if (getItemName("item", "sName", name) != null) {

                    if (hasNoImage(name)) {
                        File myFile = new File(sb.toString());



                        loadImage(myFile, name);

                        isCorrect = true;
                    }

                    //  loadImage(myFile);


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return isCorrect;
    }


    public String getAllItemNames() {
        arraylistItems.clear();

        qry = "SELECT sName as name FROM item";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            while (rs.next()) {

                String item = rs.getString("name").trim();
                arraylistItems.add(item);


            }

        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }


    public void createFile(String url, int ctr, String item, String lastItem) {
        String fileName = "noImageList.txt";
        File noImageFile = new File(url, fileName);

        try {

            if (canDeleteFileNoImageList) {
                if (noImageFile.exists()) {
                    noImageFile.delete();

                }
            }

            if (!(noImageFile.exists())) {
                noImageFile.createNewFile();
                canDeleteFileNoImageList = false;
            }

            PrintWriter writeToFile = new PrintWriter(new BufferedWriter(new FileWriter(noImageFile, true)));
            writeToFile.append("\n");
            if (ctr == 1) {
                writeToFile.append("\n");
                writeToFile.append(" POWERED BY APPIAH ENOCH-Clean Codes");
                writeToFile.append(" Call 0549822202 for any Enquiries!! ");
                writeToFile.append("\n***************************************************************");
                writeToFile.append("\n\n\n ALL THESE ITEMS DO NOT HAVE IMAGES PASTE THEIR IMAGES INSIDE THIS FOLDER AND\n" +
                        " COPY THEIR NAMES HERE TO RENAME THE IMAGES.\n THEIR IMAGES WILL BE AUTOMATICALLY ADDED TO DATABASE.");
            }

            // writeToFile.append("\n\n\t"+ctr);

            writeToFile.append("\n\n");
            writeToFile.append("\t" + item);
            writeToFile.append("\n");

            writeToFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // check if file does not exist then create it

    }

    public void writeAllNoImageItems() {

        canDeleteFileNoImageList = true;
        ShareData shareData = ShareData.getInstance();
        String url = shareData.file.toString();
        int ctr = 0;


        try {


            getAllItemNames();
            String lastItem = arraylistItems.get(arraylistItems.size() - 1);

            for (String i : arraylistItems) {
                if (hasNoImage(i)) {
                    System.out.println("has no img");
                    ctr++;
                    createFile(url, ctr, i, lastItem);
                } else {
                    deleteFile(i);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteFile(String fileName) {
        try {

            ShareData shareData = ShareData.getInstance();
            String url = shareData.file.toString();
            File file = new File(url, fileName);

            if (file.exists()) {
                System.out.println("EXITS: " + fileName);
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void getImagesFromFolder() {
        try {
            ArrayList<String> availableImages = new ArrayList<>();
            ShareData shareData = ShareData.getInstance();
            File file = new File(shareData.file.toString());
            if (file.isDirectory()) {
                String[] avail = file.list();
                for (String i : avail) {
                    String name = removeFileExtension(i);
                    if (hasNoImage(name)) {
                    } else {
                        deleteFile(i);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    Stage sst = new Stage();
    Scene scene;
    Parent r;
    @FXML
    void showImages(ActionEvent event) {

        if (sst.isShowing()){
            sst.close();

        }

        try {
            r = FXMLLoader.load(getClass().getResource("showImagesInFolder.fxml"));
            String css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
            r.getStylesheets().add(css);
            sst.setTitle("Smart Sales - AECleanCodes");
            sst.setScene(new Scene(r));
            r.requestFocus();
           // sst.initStyle(StageStyle.UTILITY);
            sst.show();
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    public boolean isTableviewEmpty() {
        try {
            ObservableList<ITEM> list = tbV1.getItems();
            if (list.isEmpty()) {
                imageV.setImage(null);
                return true;
            }
        } catch (Exception e) {

        }

        return false;


    }

    @FXML
    void showLoadedItems(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("viewItems.fxml"));
            currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
            currentScene=new Scene(root);
            String css=this.getClass().getResource("viewItems.css").toExternalForm();
           root.getStylesheets().add(css);
            currentStage.setScene(currentScene);

            currentStage.show();
            root.requestFocus();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            currentStage.setX((primScreenBounds.getWidth() -  currentStage.getWidth()) / 2);
            currentStage.setY((primScreenBounds.getHeight() -  currentStage.getHeight()) / 2);
            currentStage.setResizable(false);
        }
        catch (Exception e){
          e.printStackTrace();
        }
    }



}

