package SmartSales;

import javafx.concurrent.Task;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.print.PageFormat;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShareData  {

public static boolean isPrint=false;
    public static boolean oldReceipt=false;

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





}
