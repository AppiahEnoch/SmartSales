package SmartSales;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class DBconnect extends passData {
    Connection conn;
    Statement st;
    ResultSet rs;
    String qry = "";

    public boolean DBcon() {

        Statement st;
        String smartSales = "Smart";
        String url2 = "jdbc:mysql://localhost:3306/" + smartSales;
        String qrydb = "CREATE DATABASE IF NOT EXISTS " + smartSales;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //server URL
            String url = "jdbc:mysql://localhost/";
            conn = DriverManager.getConnection(url, "root", "root");
            st = conn.createStatement();
            st.execute(qrydb);

            conn = DriverManager.getConnection(url2, "root", "root");
            st.execute(qrydb);
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
        String qryt = "CREATE TABLE admin(ID int)";
        try {
            st = conn.createStatement();
            st.execute(qryt);
            String qry2 = "Drop table admin";
            st.execute(qry2);
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            try {
                conn.close();
            }
            catch (Exception ee){

            }
            return true;
        }


    }

    public String  getS1(){
        String ID=null;
        if (DBcon()){

            try {

                qry="SELECT     s1 from data as s1";
                st=conn.createStatement();
                rs=st.executeQuery(qry);

                if (rs.next()){
                    String d=rs.getString("s1");
                    ID=d.trim();

                    return ID;
                }

            }
            catch (Exception e){
                try {
                    conn.close();
                }
                catch (Exception ee){

                }
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA: "+e.getMessage());
                alert.showAndWait();
            }

        }

        return null;
    }
    public void setN1(String n1,String n2,String s1,String s2){
                    deleteRecord("data");
        if (DBcon()){

            try {

                qry="INSERT INTO data(n1,n2,s1,s2) values('"+n1+"','"+n2+"','"+s1+"' ,'"+s2+"')";
                st=conn.createStatement();
                st.executeUpdate(qry);
                conn.close();

            }
            catch (Exception e){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA:"+e.getMessage());
                alert.showAndWait();
                try {
                    conn.close();
                }
                catch (Exception ee){

                }
            }
        }


    }

    public String  getN1(){
        String ID=null;
        if (DBcon()){

            try {

                qry="SELECT     n1 from data as n1";
                st=conn.createStatement();
                rs=st.executeQuery(qry);

                if (rs.next()){
                    String d=rs.getString("n1");
                    ID=d.trim();

                }

            }
            catch (Exception e){
                try {
                    conn.close();
                }
                catch (Exception ee){

                }
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("COULD NOT SAVE DATA getN1: "+e.getMessage());
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
            }
            catch (Exception ee){

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
            }
            catch (Exception ee){

            }

        }
    }


    public int countRecord(String tb, String column) {

        try {
            qry = "SELECT COUNT('" + column + "') as '" + column + "' FROM" + tb;
            st = conn.createStatement();
            st.executeUpdate(qry);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.close();
            }
            catch (Exception ee){

            }
        }

         return 0;
    }



    public boolean isNumeric(String value){
        double v;
        try {
            v=Double.parseDouble(value);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

  public Connection openConn(Connection con){

        try {
            if (con.isClosed()){
                DBcon();
                con=conn;
                return con;
            }
            else {
              return conn;
            }

        }
        catch (Exception e){

        }

 return con;
  }

  public boolean doesThisExist(String table,String columnName, String value){
                               DBcon();
        openConn(conn);
        qry="SELECT "+columnName+" from "+table+"  where "+columnName+"= '"+value+"'";

        try {
            st=conn.createStatement();
            rs=st.executeQuery(qry);

            if (rs.next()){
               conn.close();
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();

            try {
              conn.close();
            }
            catch (Exception ee){
                ee.printStackTrace();
            }

        }
        return false;
  }



}