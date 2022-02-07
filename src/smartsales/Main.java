package smartsales;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javafx.stage.WindowEvent;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;


import java.util.HashMap;
import java.util.Map;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        ShareData m = ShareData.getInstance();

        restoreDB();

        Parent root;
        String css;

        boolean proceed = false;


        String fileName = ShareData.fileName;
        File userFile = new File(ShareData.pathToAllInternalFiles + fileName);


        if (userFile.exists()) {

            proceed = true;
        }


        if (proceed) {


            DBconnect dBconnect = new DBconnect();

            boolean DBExists = dBconnect.DBExists();


            if (DBExists) {


                loadItemFromExcel();
                createExcelLoadItemsFile();

                //basic routines

                try {

                    loadItemManual obj = new loadItemManual();

                    obj.isItemInDatabase(obj.checkEmptyFolder());

                    try {

                    } catch (Exception ee) {

                    }
                    obj.writeAllNoImageItems();
                    obj.getImagesFromFolder();
                } catch (Exception ee) {

                }
            }


            if (DBExists) {
                root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
                //    root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("recentItem.fxml"));

                //  root = FXMLLoader.load(getClass().getResource("showImagesInFolder.fxml"));
                //   root = FXMLLoader.load(getClass().getResource("emptySystemWindow.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("recentItem.fxml"));
                //   root = FXMLLoader.load(getClass().getResource("editItem.fxml"));
                //   root = FXMLLoader.load(getClass().getResource("salesWindow.fxml"));
                //      root = FXMLLoader.load(getClass().getResource("print.fxml"));
                //    root = FXMLLoader.load(getClass().getResource("printPreviewWindow.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("msc.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("sellerOtherWindow.fxml"));
                //     root = FXMLLoader.load(getClass().getResource("sellerTotalSales.fxml"));
                //     root = FXMLLoader.load(getClass().getResource("log.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("viewCost.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("viewSales.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("reportWindow.fxml"));
                //     root = FXMLLoader.load(getClass().getResource("receiptT.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
                //  root = FXMLLoader.load(getClass().getResource("databasePassword.fxml"));


                //  css = this.getClass().getResource("mainLock.css").toExternalForm();
                // css = this.getClass().getResource("loadItemManual.css").toExternalForm();
                //   css = this.getClass().getResource("recentItem.css").toExternalForm();
                // css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
                // css = this.getClass().getResource("recentItem.css").toExternalForm(); \


            } else {
                root = FXMLLoader.load(getClass().getResource("logAdmin.fxml"));
                //    root = FXMLLoader.load(getClass().getResource("databasePassword.fxml"));
                //     css = this.getClass().getResource("logProgram.css").toExternalForm();
                logAdmin logAdmin = new logAdmin();
                logAdmin.createTables();
            }


            //   root.getStylesheets().add(css);
            primaryStage.setTitle("Smart Sales - AECleanCodes");
            primaryStage.setScene(new Scene(root));
            // root.requestFocus();


            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);


            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                public void handle(WindowEvent event) {
                    m.continueItemSuggestion = false;
                }
            });


        } else {


            root = FXMLLoader.load(getClass().getResource("databasePassword.fxml"));

            primaryStage.setTitle("Smart Sales - AECleanCodes");
            primaryStage.setScene(new Scene(root));
            // root.requestFocus();


            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);


        }


        m.jasperCompile();
        //  m.jasperCompileAC();


    }


    public static void main(String[] args) {

        launch(args);

    }


    public static void createExcelLoadItemsFile() {
        ShareData shareData = ShareData.getInstance();
        DBconnect dBconnect = new DBconnect();
        File file = new File(shareData.file.toString() + "\\loadItems.xlsx");
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("items");

            Map<String, Object[]> list = new HashMap<String, Object[]>();

            list = dBconnect.getItemsInShortage();
            System.out.println(" list size:" + list.size());

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

            Font font = workbook.createFont();
            Font font1 = workbook.createFont();
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


            Set<String> keys = list.keySet();
            int rowNum = 0;

            for (String i : keys) {
                Row row = spreadsheet.createRow(rowNum++);
                Object[] array = list.get(i);
                int cellNum = 0;
                for (Object ii : array) {
                    Cell cell = row.createCell(cellNum++);
                    int c = cell.getColumnIndex();


                    if (c < 4) {

                        cell.setCellStyle(style);
                    }

                    if ((cellNum > 6)) {
                        cell.setCellStyle(style1);
                    }


                    if (ii instanceof Double) {
                        cell.setCellValue((Double) ii);
                    } else if (ii instanceof Integer) {
                        cell.setCellValue((Integer) ii);
                    } else if (ii instanceof String) {
                        cell.setCellValue((String) ii);
                    }
                }
            }

            FileOutputStream out = new FileOutputStream(new File(file.toString()));
            workbook.write(out);

            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void loadItemFromExcel() {
        ShareData shareData = ShareData.getInstance();

        ArrayList<String> item = new ArrayList();
        ArrayList<String> qty = new ArrayList();
        ArrayList<String> cost = new ArrayList();
        ArrayList<String> price = new ArrayList();

        try {
            File file = new File(shareData.file.toString() + "\\loadItems.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            XSSFCell cell2;


            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    DataFormatter dataFormatter = new DataFormatter();
                    int col = cell.getColumnIndex();


                    if (col == 6) {

                        String value = dataFormatter.formatCellValue(cell);
                        value = value.toLowerCase();
                        item.add(value);

                    }
                    if (col == 7) {

                        String value = dataFormatter.formatCellValue(cell);
                        qty.add(value);

                    }
                    if (col == 8) {

                        String value = dataFormatter.formatCellValue(cell);
                        cost.add(value);

                    }
                    if (col == 9) {

                        String value = dataFormatter.formatCellValue(cell);
                        price.add(value);
                    }


                }

            }


            fis.close();
        } catch (Exception e) {

        }

        int ctr = item.size();

        for (int i = 0; i < ctr; i++) {


            try {
                boolean iswrong = false;
                String name = item.get(i).toString().trim();
                String qq = qty.get(i).toString().trim();
                String cc = cost.get(i).toString().trim();
                String pp = price.get(i).toString().trim();


                int q = 0;
                Double c = 0.0;
                Double p = 0.0;

                if (name.isEmpty()) {
                    iswrong = true;
                } else if (qq.isEmpty()) {
                    iswrong = true;

                } else if (cc.isEmpty()) {
                    iswrong = true;

                } else if (pp.isEmpty()) {
                    iswrong = true;

                }


                try {
                    q = Integer.parseInt(qty.get(i));

                } catch (Exception e) {
                    iswrong = true;
                }
                try {
                    c = Double.parseDouble(cc);
                } catch (Exception e) {
                    iswrong = true;
                }
                try {
                    p = Double.parseDouble(pp);
                } catch (Exception e) {
                    iswrong = true;
                }


                if (!iswrong) {

                    try {


                        if ((q > 0) && (c > 0)) {
                            updateItem1(name, q, c, p);
                            updateItem(name, q, c, p);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    public static void updateItem1(String sName, int qty2, double cost2, double price2) {
        DBconnect dBconnect = new DBconnect();
        Connection conn;
        dBconnect.DBcon();
        conn = dBconnect.conn;


        Statement st;
        ResultSet rs;
        String qry = "";

        try {
            qry = "INSERT IGNORE INTO " +
                    "item1(sName,qty,cost,price)" +
                    " values('" + sName + "'," + qty2 + "," + cost2 + "," + price2 + ")";

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


    public static void updateItem(String sName, int qty2, double cost2, double price2) {
        DBconnect dBconnect = new DBconnect();
        Connection conn;
        dBconnect.DBcon();
        conn = dBconnect.conn;


        Statement st;
        ResultSet rs;
        String qry = "";

        try {
            qry = "INSERT IGNORE INTO " +
                    "item(sName,qty,cost,price)" +
                    " values('" + sName + "'," + qty2 + "," + cost2 + "," + price2 + ")";

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





    public  boolean restoreDB() {

        String databaseName="Smart";
        String DBackupName = "backup.sql";
        String savePath = ShareData.pathToDesktop+"\\";
        String userName = ShareData.DBUserName;
        String password = ShareData.DBPassword;


        String[] executeCmd = new String[]{"mysql", "--user=" + userName, "--password=" + password, databaseName,"-e", " source "+savePath+DBackupName};
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup restored successfully");
                return true;
            }
            else {
                System.out.println("Could not restore the backup");
            }
        }
    catch (Exception ex) {
        ex.printStackTrace();
    }
        return false;
}


}
   




