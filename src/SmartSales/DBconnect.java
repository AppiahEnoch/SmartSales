package SmartSales;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DBconnect {
    ArrayList<String> arraylistItems = new ArrayList<>();

    Connection conn;
    Statement st;
    ResultSet rs;
    String qry = "";

    int c1 = 1;
    int c2 = 1;
    int c3 = 1;
    int c4 = 1;


    String DBitem;
    String DBprice;
    String DBqty;
    String DBsn;
    String DBCost;
    String DBtime;

    public DecimalFormat dp00 = new DecimalFormat("0.00");


    int windowSize0 = 20;


    public boolean DBcon() {

        Statement st;
        String smartSales = "Smart";
        String url2 = "jdbc:mysql://localhost:3306/" + smartSales;
        String qrydb = "CREATE DATABASE IF NOT EXISTS " + smartSales;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //server URL
            String url = "jdbc:mysql://localhost/";
            conn = DriverManager.getConnection(url, "root", "root");
            st = conn.createStatement();
            st.execute(qrydb);

            conn = DriverManager.getConnection(url2, "root", "root");
            st.execute(qrydb);
            ShareData.directConnection=conn;
            return true;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("DATABASE CONNECTION ERROR: " + e.getMessage());
            alert.showAndWait();
        }
        return false;
    }

    public boolean DBExists() {
        DBcon();
        String qryt = "SELECT COUNT(name) as totalNames FROM businessName";


        try {
            st = conn.createStatement();
            rs = st.executeQuery(qryt);
            if (rs.next()) {

                String i = rs.getString("totalNames").trim();

                if (i.isEmpty()) {

                    return false;
                }


                int ii = Integer.parseInt(i);

                if (ii == 0) {

                    return false;

                }

            } else {


                return false;
            }



        } catch (Exception e) {

            e.printStackTrace();


            try {
                conn.close();
            } catch (Exception ee) {

            }
            return false;
        }

return true;
    }



    public String getS1() {
        String ID = null;
        if (DBcon()) {

            try {

                qry = "SELECT s1 from data as s1";
                st = conn.createStatement();
                rs = st.executeQuery(qry);

                if (rs.next()) {
                    String d = rs.getString("s1");
                    ID = d.trim();

                    return ID;
                }

            } catch (Exception e) {
                try {
                    conn.close();
                } catch (Exception ee) {

                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA: " + e.getMessage());
                alert.showAndWait();
            }

        }

        return null;
    }

    public void setN1(String n1, String n2, String s1, String s2) {
        deleteRecord("data");
        if (DBcon()) {

            try {

                qry = "INSERT INTO data(n1,n2,s1,s2) values('" + n1 + "','" + n2 + "','" + s1 + "' ,'" + s2 + "')";
                st = conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA:" + e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                } catch (Exception ee) {

                }
            }
        }


    }

    public String getN1() {
        String ID = null;
        if (DBcon()) {

            try {

                qry = "SELECT     n1 from data as n1";
                st = conn.createStatement();
                rs = st.executeQuery(qry);

                if (rs.next()) {
                    String d = rs.getString("n1");
                    ID = d.trim();

                }

            } catch (Exception e) {
                try {
                    conn.close();
                } catch (Exception ee) {

                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA getN1: " + e.getMessage());
                alert.showAndWait();
            }

        }

        return ID;
    }


    public void deleteRecord(String tb) {
        DBcon();
        try {
            qry = "DELETE FROM  " + tb;
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }
        }

    }

    public void deleteRecord(String tb, String where) {
        DBcon();
        try {
            qry = "DELETE  FROM " + tb + " " + where;
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }
    }


    public int countRecord(String tb, String column) {
        openConn(conn);
        int i = 0;
        try {
            qry = "SELECT COUNT('" + column + "')  as  '" + column + "' FROM " + tb;
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                i = Integer.parseInt(rs.getString(column).trim());
            }

            conn.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }
        }

        return 0;
    }


    public boolean isNumeric(String value) {
        double v;
        try {
            v = Double.parseDouble(value);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Connection openConn(Connection con) {

        try {
            if (con.isClosed()) {
                DBcon();
                con = conn;
                return con;
            } else {
                return conn;
            }

        } catch (Exception e) {

        }

        return con;
    }

    public boolean doesThisExist(String table, String columnName, String value) {
        DBcon();
        openConn(conn);
        qry = "SELECT " + columnName + " from " + table + "  where " + columnName + "= '" + value + "'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                conn.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

            try {
                conn.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }

        }
        return false;
    }

    public boolean doesThisExist(String table, String firstColumn, String secondColumn, String firstValue) {
        DBcon();
        openConn(conn);
        qry = "SELECT " + firstColumn + " from " + table + "  where " + firstColumn + "= '" + firstValue + "'";

        System.out.println("fC:" + firstColumn + " v:" + firstValue);

        try {
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                conn.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

            try {
                conn.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }

        }
        return false;
    }


    public void getItems4(String table) {

        qry = "SELECT ID,item,qty,cost,price from " + table + " order by ID ASC";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                DBsn = rs.getString("ID").trim();
                DBitem = rs.getString("item").trim();
                DBqty = rs.getString("qty").trim();
                DBprice = rs.getString("price").trim();
                DBCost = rs.getString("cost").trim();


            } else {
                System.out.println(" no match in two tables: ");
            }
        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
        }


    }


    public void insertItem4(String tb, int sn, String item, int qty, double cost, double price) {
        DBcon();
        try {
            qry = "INSERT IGNORE INTO " + tb + " values('" + sn + "','" + item + "','" + qty + "','" + cost + "','" + price + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }
    }

    public void insertItem2(String item, int qty, double cost, double price) {
        DBcon();
        try {
            qry = "INSERT  INTO item1 (sName,qty,cost,price) values('" + item + "','" + qty + "','" + cost + "','" + price + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }
    }


    public void insertItem1(String tb, String thisData) {
        DBcon();
        try {
            qry = "INSERT IGNORE INTO " + tb + " values('" + thisData + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            } catch (Exception ee) {

            }

        }
    }

    public void loadImage(File file) {
        System.out.println("file fromload2 " + file);
        try {
            PreparedStatement pst = null;
            openConn(conn);
            ShareData shareData = ShareData.getInstance();
            // File file=new File("C:/Users/AECleanCodes/Downloads/biscuits/digestive.jpg");  //correct


            qry = "update item set img= ? ";
            pst = conn.prepareStatement(qry);
            FileInputStream fileInputStream = new FileInputStream(file);
            pst.setBinaryStream(1, fileInputStream);
            pst.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public String getItemName(String table, String column, String searchThis) {

        qry = "SELECT " + column + " from " + table + " where " + column + " like '" + searchThis + "'";

        try {
            DBcon();
            st = conn.createStatement();
            rs = st.executeQuery(qry);

            if (rs.next()) {
                String item = rs.getString(column).trim();
                return item;

            }

        } catch (Exception e) {
            System.out.println("here");
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }


    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static String getExtension(String fileName) {
        String ext = null;
        String s = fileName;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public String removeFileExtension(String fileName) {
        try {
            String rmx = getExtension(fileName).trim();

            int rx = rmx.length();
            StringBuilder name = new StringBuilder();
            name.append(fileName);
            int l = name.length();
            int endOfString = l - (rx + 1);
            String validName = name.substring(0, endOfString);
            return validName;
        } catch (Exception e) {

        }

        return null;
    }


    public boolean hasNoImage(String sName) {
        Blob img = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        File file = null;


        try {
            openConn(conn);
            st = conn.createStatement();
            qry = "SELECT img from item where sName='" + sName + "'";
            rs = st.executeQuery(qry);

            if (rs.next()) {
                inputStream = rs.getBinaryStream("img");

            }
            try {
                inputStreamReader = new InputStreamReader(inputStream);
                return false;
            } catch (Exception ee) {

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Map<String, Object[]> getItemsInShortage() {
        Map<String, Object[]> list = new HashMap<String, Object[]>();
        list.put("0", new Object[]{"OLD DATA - ITEMS IN SHORTAGE", "", "", "", "", "", "NEW DATA- TO BE LOADED", "", "", ""});
        list.put("1", new Object[]{"", "", "", "", "", "", "", "", "", ""});
        list.put("2", new Object[]{"", "", "", "", "", "", "", "", "", ""});
        list.put("3", new Object[]{"ITEM", "QTY", "COST", "PRICE", "", "", "ITEM", "QTY", "COST", "PRICE"});


        if (DBcon()) {

            qry = "SELECT sName,qty,cost,price from item1";

            try {

                st = conn.createStatement();
                rs = st.executeQuery(qry);

                while (rs.next()) {
                    DBitem = rs.getString("sName").trim();
                    DBqty = rs.getString("qty").trim();
                    DBCost = rs.getString("cost").trim();
                    DBprice = rs.getString("price").trim();

                    list.put(DBitem, new Object[]{DBitem, Integer.parseInt(DBqty), Double.parseDouble(DBCost), Double.parseDouble(DBprice), "", "", DBitem, 0, 0, 0});
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return list;
    }





}