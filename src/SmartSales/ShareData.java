package SmartSales;

import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class ShareData {

public static boolean isPrint=false;
    private static ShareData instance;
    Paths path;
    int intData1;
    int anIntData2;
    int anIntData3;
    int anIntData4;
    double amount;
    double change;
    double cash;
    String stringData1;
    boolean continueItemSuggestion = true;

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


    public static void print(Node node)
    {
        // Define the Job Status Message


        // Create a printer job for the default printer
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null)
        {
            // Show the printer job status


            // Print the node

            boolean printed = job.printPage(node);

            if (printed)
            {
                // End the printer job
                job.endJob();
            }
            else
            {
                // Write Error Message

            }
        }
        else
        {

        }
    }
}
