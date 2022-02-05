package SmartSales;

import javafx.concurrent.Task;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.print.PageFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShareData  {
Connection conn=null;
public static boolean isPrint=false;
    public static boolean oldReceipt=false;
    static   String  fileName="AECleanCodesDBP_x891kve266uus.txt";

public static Connection directConnection;
    private static ShareData instance;
    Paths path;
    int intData1;
    int anIntData2;
    int anIntData3;
    int anIntData4;
    boolean printInProgress=false;
    double amount=0.00;
   static double  discount=0.00;
    double change;
    double cash;
    String stringData1;

    static ArrayList<String> cost=new ArrayList();
    static ArrayList<String> sales=new ArrayList();
    static ArrayList<String> profit=new ArrayList();

 static   String currentUser_="";
    static   String userID_="";
 static boolean hitRun=true;
    static   String currentRandom_="";
    boolean continueItemSuggestion = true;
  static   boolean  isAdmin = false;
    static   boolean  goSales = false;
    static   String originalBill="";

    public File file;

    private ShareData(){
      createFolderOnDeskTop();
    }

    static synchronized public ShareData getInstance(){
        if (instance==null){
            instance=new ShareData();
        }
        return instance;
    }

    private void createFolderOnDeskTop(){
        String folderName="New_item_Image";

        try{

            String homeFolder=System.getProperty("user.home"); //path for folder
            java.nio.file.Path  path= Paths.get(homeFolder,"Desktop",folderName);
            if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                // Folder is already created

                file=new File(String.valueOf(path));
            }
            else {
                // folder is being created
                Paths.get(homeFolder,"Desktop",folderName).toFile().mkdir();
                file=new File(String.valueOf(path));
                System.out.println(file);
            }
        }
        catch (Exception e){

            e.printStackTrace();
        }


    }





    public static void print(String jasperDocument){

            Task task=new Task() {
                @Override
                protected Object call() throws Exception {

          try {
              JasperPrint jp;
              Map param = new HashMap();

              try {
                  jp = JasperFillManager.fillReport(jasperDocument, param, ShareData.directConnection);


              } catch (JRException e) {
                  e.printStackTrace();
              }
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

    public static void preView(String jasperDocument,String title){
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {

                JasperPrint jp;
                Map param = new HashMap();

                try {
                    jp = JasperFillManager.fillReport(jasperDocument, param, ShareData.directConnection);
                    JasperViewer jv = new JasperViewer(jp, false);
                    jv.setTitle(title);
                    jv.setVisible(true);
                } catch (JRException e) {
                    e.printStackTrace();
                }


                return null;
            }

        };
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(task);
        executorService.shutdown();


    }



    public boolean DBcon() {

        Statement st;
        String smartSales = "Smart";
        String url2 = "jdbc:mysql://localhost:3306/" + smartSales;
        String qrydb = "CREATE DATABASE IF NOT EXISTS " + smartSales;

        if(readPassword()){

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                //server URL
                String url = "jdbc:mysql://localhost/";
                conn = DriverManager.getConnection(url, "root", "root");
                st = conn.createStatement();
                st.execute(qrydb);

                conn = DriverManager.getConnection(url2, "root", "root");
                st.execute(qrydb);
                ShareData.directConnection=conn;
                return true;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("DATABASE CONNECTION ERROR: " + e.getMessage());
                alert.showAndWait();
            }




        }




        return false;
    }



    public Connection openConn(Connection con) {

        try {
            if (con.isClosed()) {
                DBcon();
                con = conn;
                directConnection=con;
                return con;
            } else {
                return conn;
            }

        } catch (Exception e) {

        }

        return con;
    }



   static String DBUserName="";
  static   String DBPassword="";

    boolean proceed = false;

    boolean readPassword() {
        if (DBUserName.length()>1){
            return true;
        }

        try {

            String fileName = ShareData.fileName;
            File userFile = new File(fileName);

            if (userFile.exists()) {
                proceed = true;
            }

        } catch (Exception e) {
            proceed = false;
        }


        if(proceed){

            try {
                String fileName = ShareData.fileName;
                String st = null;

                File file = new File(fileName);
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    String userName = data[0];
                    String password = data[1];
                    System.out.println(userName);
                    System.out.println(password);
                    DBUserName=userName;
                    DBPassword=password;

                    proceed=true;
                    return true;


                }
            }
            catch (Exception e) {

                proceed=false;
            }

        }




        return false;

    }




}
