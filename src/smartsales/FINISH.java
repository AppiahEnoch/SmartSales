package smartsales;

public class FINISH {
    public FINISH(String name, String totalQty, String usedQty, String availableQty, String percentage) {
        this.name = name;
        this.totalQty = totalQty;
        this.usedQty = usedQty;
        this.availableQty = availableQty;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public String getUsedQty() {
        return usedQty;
    }

    public String getAvailableQty() {
        return availableQty;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public void setUsedQty(String usedQty) {
        this.usedQty = usedQty;
    }

    public void setAvailableQty(String availableQty) {
        this.availableQty = availableQty;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    String name="";
    String totalQty="";
    String  usedQty ="";
    String  availableQty="";
    String percentage="";


}
