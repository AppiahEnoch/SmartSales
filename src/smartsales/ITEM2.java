package smartsales;

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

    public ITEM2 setQty(Object qty) {
        this.qty = qty;
        return null;
    }

    public Object getCost() {
        return cost;
    }

    public ITEM2 setCost(Object cost) {
        this.cost = cost;
        return null;
    }

    public Object getPrice() {
        return price;
    }

    public ITEM2 setPrice(Object price) {
        this.price = price;
        return null;
    }

    public ITEM2( Object name, Object qty, Object cost, Object price,Object time) {
        this.name = name;
        this.qty = qty;
        this.cost = cost;
        this.price = price;
        this.time = time;
    }

    public ITEM2(Object id, Object name, Object qty, Object cost, Object price,Object time) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.cost = cost;
        this.price = price;
        this.time = time;

    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
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
    Object time;
}
