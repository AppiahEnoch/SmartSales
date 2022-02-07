package smartsales;

public class RECEIPT {
    public RECEIPT(String item, String qty, String price, String total) {
        this.item = item;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public String getItem() {
        return item;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getTotal() {
        return total;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    String item;
    String qty;
    String price;
    String total;

}
