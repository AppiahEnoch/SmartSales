package SmartSales;

public class ITEM2 {



    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public ITEM2 setName(Object name) {
        this.name = name;
        return null;
    }

    public Object getQty() {
        return qty;
    }

    public void setQty(Object qty) {
        this.qty = qty;
    }

    public Object getCost() {
        return cost;
    }

    public void setCost(Object cost) {
        this.cost = cost;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public ITEM2(Object id, Object name, Object qty, Object cost, Object price) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.cost = cost;
        this.price = price;
    }

    public ITEM2( Object name, Object qty, Object cost, Object price) {
        this.name = name;
        this.qty = qty;
        this.cost = cost;
        this.price = price;
    }

    Object id;
    Object name;
    Object qty;
    Object cost;
    Object price;
}
