package SmartSales;

public class tbEditSellerDetails {
    public tbEditSellerDetails(String ID, String name, String mobile, String location) {
        this.ID = ID;
        this.name = name;
        this.mobile = mobile;
        this.location = location;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Object getMobile() {
        return mobile;
    }

    public Object getLocation() {
        return location;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public tbEditSellerDetails setName(Object name) {
        this.name = (String) name;
        return null;
    }

    public tbEditSellerDetails setMobile(Object mobile) {
        this.mobile = mobile;
        return null;
    }

    public tbEditSellerDetails setLocation(Object location) {
        this.location = location;
        return null;
    }
    String ID;
    String name;
    Object mobile;
    Object location;
}
