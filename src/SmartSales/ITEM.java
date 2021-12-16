package SmartSales;

public class ITEM {


    public ITEM(Object item, String qty, String price) {
        this.qty = qty;
        this.item = (String) item;
        this.price = price;

    }

    public String getQty() {
        return qty.toString();
    }

    public String getItem() {
        return (String) item;
    }

    public String getPrice() {
        return price;
    }

    public String getSn() {
        return sn;
    }

    public ITEM setQty(Object qty) {
        this.qty = (String) qty;
        return null;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public ITEM(String sn, String item, String qty, String cost, String price) {
        this.qty = qty;
        this.item = item;
        this.price = price;
        this.sn = sn;
        this.cost = cost;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


    Object qty;
    Object item = null;
    String price = null;
    String sn;
    String cost;

}
