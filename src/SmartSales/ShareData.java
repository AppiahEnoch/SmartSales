package SmartSales;

public class shareData {
    private shareData instance;
    int intData1;
    int anIntData2;
    int anIntData3;
    int anIntData4;
    String stringData1;

    private shareData(){

    }

    synchronized public shareData getInstance(){
        if (instance==null){
            instance=new shareData();
        }
        return instance;
    }
}
