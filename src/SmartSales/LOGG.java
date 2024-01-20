package SmartSales;

public class LOGG {
    public LOGG(String name, String ID, String inTime, String outTime) {
        this.name = name;
        this.ID = ID;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getInTime() {
        return inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    String name="";
    String ID="";
    String inTime="";
    String outTime="";





}
