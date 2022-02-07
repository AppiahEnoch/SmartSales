package smartsales;

import javafx.concurrent.Task;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class printTask extends Task {
    ShareData m=ShareData.getInstance();

    @Override
    protected Object call() throws Exception {

        Connection connection=ShareData.directConnection;
          connection= m.openConn(connection);

        if (connection.isClosed()){
            System.out.println("closed!!!!");
        }

        try {
            String jp;
            Map param = new HashMap();
            try {


                jp = JasperFillManager.fillReportToFile("customerReport.jasper"
                        , param, ShareData.directConnection);

                updateProgress(3,5);

                if (jp!=null){
                    updateProgress(4,5);
                    JasperPrintManager.printReport(jp,false);



                }
                else {

                }



            } catch (JRException e) {
                e.printStackTrace();
            }




            updateProgress(5,5);




        }
        catch (Exception e){

        }


        return null;
    }
}
