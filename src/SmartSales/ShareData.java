package SmartSales;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class ShareData {
    private static ShareData instance;
    Paths path;
    int intData1;
    int anIntData2;
    int anIntData3;
    int anIntData4;
    String stringData1;

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
}
