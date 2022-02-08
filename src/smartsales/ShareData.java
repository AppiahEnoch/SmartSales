package smartsales;

import javafx.concurrent.Task;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import static java.lang.System.getenv;
import static java.lang.System.setOut;

public class ShareData  {

    static   String  pathToPassword="";
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

        pathToPassword=getenv("ProgramData")+"\\";

      createFolderOnDeskTop();
    }

    static synchronized public ShareData getInstance(){
        if (instance==null){
            instance=new ShareData();
          
            
        }
        return instance;
    }


    static Path pathToDesktop=null;

    private void createFolderOnDeskTop(){
        String folderName="New_item_Image";

        try{

            String homeFolder=System.getProperty("user.home"); //path for folder
            java.nio.file.Path  path= Paths.get(homeFolder,"Desktop",folderName);
            if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
                // Folder is already created

                file=new File(String.valueOf(path));
                pathToDesktop=path;
            }
            else {
                // folder is being created
                Paths.get(homeFolder,"Desktop",folderName).toFile().mkdir();
                file=new File(String.valueOf(path));
                pathToDesktop=path;
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
                conn = DriverManager.getConnection(url, DBUserName, DBPassword);
                st = conn.createStatement();
                st.execute(qrydb);

                conn = DriverManager.getConnection(url2,DBUserName, DBPassword);
                st.execute(qrydb);
                ShareData.directConnection=conn;
                return true;
            } catch (Exception e) {
               // Alert alert = new Alert(Alert.AlertType.WARNING);
              //  alert.setHeaderText("DATABASE CONNECTION ERROR: " + e.getMessage());
              //  alert.showAndWait();
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

                File file = new File(ShareData.pathToPassword+  fileName);
                BufferedReader br = new BufferedReader(new FileReader(file));
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



static  JasperReport jasperDocument=null;
static  JasperReport jasperDocumentAccounts=null;

    static   String  s1=   "customerReport.jrxml";
    static   String  s2=   "salesAccountsPdf.jrxml";

    JasperDesign jd1=null;
    JasperDesign jd2=null;

    public   void   jasperCompile(){
        try{
            jd1= JRXmlLoader.load(s1);
            jd2= JRXmlLoader.load(s2);

        }
        catch(Exception e){
          //  System.out.println(" gggggggggggggggggggg");
            e.printStackTrace();
        }


        Task task=new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    jasperDocument = JasperCompileManager
                            .compileReport(jd1);

                    jasperDocumentAccounts= JasperCompileManager
                            .compileReport(jd2);



                }
                catch(Exception e){
                    e.printStackTrace();
                }

                return null;
            }

        };

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(task);
        executorService.shutdown();
    }
    


    public   JasperReport  jasperCompileAC(){

        Task task=new Task() {
            @Override
            protected Object call() throws Exception {


                   try{
                       openConn(conn);
                  
                   jasperDocumentAccounts = JasperCompileManager
                        .compileReport( "salesAccountsPdf.jrxml");
                }
                catch(Exception e){
                   e.printStackTrace();
                }

                
              return null;
            }

        };

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(task);
        executorService.shutdown();




return  jasperDocumentAccounts;
}
















public void backupDatabase(){
    Task task=new Task() {
        @Override
        protected Object call() throws Exception {

            try {

              backUp();
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






 private     void backUp() {
        String pathToMYSQLBinFolder="C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin";
        String databaseName="Smart";
        String DBackupName = "backup.sql";
        String savePath = ShareData.pathToDesktop+"\\";


        InputStreamReader inputStreamReader;
        String executeCmd;
        String userName = ShareData.DBUserName;
        String password = ShareData.DBPassword;





        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// If the directory does not exist
            saveFile.mkdirs();// create folder

        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        executeCmd = pathToMYSQLBinFolder + "\\mysqldump -u "
                + userName + " -p" + password+ " --add-drop-database -B " + databaseName + " -r " + savePath+DBackupName;
        try {

            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + DBackupName), "utf8"));
            Process process = Runtime.getRuntime().exec
                    (executeCmd);

            try {
                inputStreamReader
                        = new InputStreamReader(process.getInputStream(), "utf8");
                bufferedReader = new BufferedReader(inputStreamReader);
            }
            catch (Exception e){
                e.printStackTrace();
            }


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
            printWriter.flush();
            if(process.waitFor() == 0){//0 Indicates that the thread is terminated normally.
                System.out.println("correct");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        //return false;
    }








    public void restoreDatabase(){
        Task task=new Task() {
            @Override
            protected Object call() throws Exception {

                try {
                    restore();
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










    private   boolean restore() {

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
