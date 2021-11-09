package SmartSales;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {



        DBconnect dBconnect = new DBconnect();
        boolean DBExists = dBconnect.DBExists();

        createExcelLoadItemsFile();
        loadItemFromExcel();
        //basic routines

        try {
            ShareData m = ShareData.getInstance();
            loadItemManual obj = new loadItemManual();

            obj.isItemInDatabase(obj.checkEmptyFolder());

            try {

            } catch (Exception ee) {

            }
            obj.writeAllNoImageItems();
            obj.getImagesFromFolder();
        } catch (Exception ee) {

        }
        Parent root;
        String css;
        if (DBExists) {
            //  root = FXMLLoader.load(getClass().getResource("mainLock.fxml"));
            //  root = FXMLLoader.load(getClass().getResource("adminMainWindow.fxml"));
              //  root = FXMLLoader.load(getClass().getResource("recentItem.fxml"));

            //  root = FXMLLoader.load(getClass().getResource("showImagesInFolder.fxml"));
           //   root = FXMLLoader.load(getClass().getResource("emptySystemWindow.fxml"));
            root = FXMLLoader.load(getClass().getResource("recentItem.fxml"));


            //    css = this.getClass().getResource("mainLock.css").toExternalForm();
            // css = this.getClass().getResource("loadItemManual.css").toExternalForm();
              css = this.getClass().getResource("recentItem.css").toExternalForm();
            // css = this.getClass().getResource("showImagesInFolder.css").toExternalForm();
           // css = this.getClass().getResource("recentItem.css").toExternalForm();

        } else {
            root = FXMLLoader.load(getClass().getResource("logAdmin.fxml"));
            css = this.getClass().getResource("logProgram.css").toExternalForm();
            logAdmin logAdmin = new logAdmin();
            logAdmin.createTables();
        }

        root.getStylesheets().add(css);
        primaryStage.setTitle("Smart Sales - AECleanCodes");
        primaryStage.setScene(new Scene(root));
        root.requestFocus();


        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);





    }


    public static void main(String[] args) {

        launch(args);

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



                    if (col == 0) {

                        String value = dataFormatter.formatCellValue(cell);
                        item.add(value);

                    }
                    if (col == 1) {

                        String value = dataFormatter.formatCellValue(cell);
                        qty.add(value);
                    }
                    if (col == 2) {

                        String value = dataFormatter.formatCellValue(cell);
                        cost.add(value);
                    }
                    if (col == 3) {

                        String value = dataFormatter.formatCellValue(cell);
                        price.add(value);
                    }



                }

            }


         fis.close();
        }
        catch(Exception e){

        }

        for (String i:item){
            System.out.println("i: "+i);
        }
    }





    public static void createExcelLoadItemsFile(){
        ShareData shareData = ShareData.getInstance();
        File file = new File(shareData.file.toString() + "\\loadItems.xlsx");
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("items");
            Map<String,Object[]>list=new HashMap<String,Object[]>();
            list.put("1",new Object[]{"ITEM","QTY","COST","PRICE"});
            list.put("2",new Object[]{"toffee",56,699,90});

            Set <String>keys=list.keySet();
            int rowNum=0;
            for (String i:keys){
                Row row = spreadsheet.createRow(rowNum++);
                Object [] array=list.get(i);
                int cellNum=0;
                for (Object ii:array){
                    Cell cell = row.createCell(cellNum++);
                    if (ii instanceof String){
                       cell.setCellValue((String) ii);
                    }
                    if (ii instanceof Integer){
                        cell.setCellValue((Integer)ii);
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


}


