package SmartSales;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PrintService extends Service {

    @Override
    protected Task createTask() {
        return new  printTask();
    }
}
