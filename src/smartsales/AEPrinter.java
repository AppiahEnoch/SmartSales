package smartsales;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Set;


public class AEPrinter {

    boolean showingPrinters=false;
    @FXML
    Text txtStatus;

    @FXML
    Label lbAvail;

    @FXML
    private Text txt1;

    @FXML
    private HBox h1;

    @FXML
    private Button btPrev;

    @FXML
    private Button btPrint;

    @FXML
    private TextArea taPrinters;


    @FXML
    private HBox hDown;

    @FXML
    void previewReport(ActionEvent event) {

    }

    @FXML
    void printReport(ActionEvent event) {

        print(taPrinters);
    }

    @FXML
    void getSettings(ActionEvent event) {
        switchTextArea();
    }

    @FXML
    public   void initialize(){
        // get all printers
        ObservableSet p=getPrinters();
        for (Object i:p){
            taPrinters.appendText(i.toString()+"\n");
        }

        //get default printer
        txt1.setText(getDefaultPrinter());




    }





    void switchTextArea(){
        if (showingPrinters){
            printAttributes();
        }
        else {
            // get all printers
            ObservableSet p=getPrinters();
            for (Object i:p){
                taPrinters.appendText(i.toString()+"\n");
            }

            //get default printer
            txt1.setText(getDefaultPrinter());
        }

    }



    public String getDefaultPrinter(){
        Object defaultP=null;

        try {

            defaultP=Printer.getDefaultPrinter();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return defaultP.toString().trim();
    }





    public ObservableSet<Printer> getPrinters(){
        lbAvail.setText("Printers Available:");
        showingPrinters=true;
        taPrinters.clear();
        ObservableSet<Printer>printer=null;
        try{
            printer=Printer.getAllPrinters();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return   printer;
    }



    public void print(Node node)
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


    private void printAttributes()
    {
        showingPrinters=false;
        lbAvail.setText("Default printer Settings:");
        taPrinters.clear();
// Get the Default Printer
        Printer printer = Printer.getDefaultPrinter();
// Get the Printer Attributes
        PrinterAttributes attribs = printer.getPrinterAttributes();
// Read some printer attributes
        int maxCopies = attribs.getMaxCopies();
        PrintSides printSides = attribs.getDefaultPrintSides();
        Set<PageOrientation> orientations = attribs.getSupportedPageOrientations();
        Set<Collation> collations = attribs.getSupportedCollations();
// Print the printer attributes
        taPrinters.appendText("Max. Copies: " + maxCopies + "\n");
        taPrinters.appendText("Print Sides: " + printSides + "\n");
        taPrinters.appendText("Supported Orientation: " + orientations + "\n");
        taPrinters.appendText("Supported Collations: " + collations + "\n");
// Create a printer job for the default printer
        PrinterJob job = PrinterJob.createPrinterJob();
// Get the JobSettings for the print job
        JobSettings jobSettings = job.getJobSettings();
// Print the printer attributes
        taPrinters.appendText("Print Sides: " + jobSettings.getPrintSides() + "\n");
// Set the printSides to DUPLEX
        jobSettings.setPrintSides(PrintSides.DUPLEX);
// Print the printer attributes
        taPrinters.appendText("Print Sides: " + jobSettings.getPrintSides() + "\n");
    }



}
