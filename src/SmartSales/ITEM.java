package SmartSales;

public class ITEM {


    public String getQty() {
        return qty;
    }

    public String getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }

    public String getSn() {
        return sn;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public ITEM(String sn,String item, String qty, String cost, String price) {
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



    String qty;
    String item=null;
    String price=null;
    String sn;
    String cost;









}
