/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbpackage;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SreePurna
 */
public class ConnectionDb {
    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private String MYSQL_URL = "jdbc:mysql://10.10.10.157:3306/delladb";
    private Connection con;
    private Statement st;
    
    
    /**
     * This is a method to connect to database and to create tables.
     */
    public ConnectionDb() {
        try {
            Class.forName(MYSQL_DRIVER);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Class Loaded....");
            con = (Connection) DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            System.out.println("Connected to the database....");
            st = con.createStatement();
            int a =st.executeUpdate("CREATE TABLE IF NOT EXISTS ActionItems ("
                    + "Name VARCHAR(30)"
                    + ",Description VARCHAR(150)"
                    + ",Resolution VARCHAR(150)"
                    + ",Creation Varchar(150)"
                    + ",Due Varchar(150)"
                    + ",Status Varchar(150)"
                    + ",Team VARCHAR(200)"
                    + ",Member VARCHAR(200));");
            System.out.println("Action Items Table have been created.");
            System.out.println(a+" Row(s) have been affected");
            
            int t=st.executeUpdate("CREATE TABLE IF NOT EXISTS Teams ("
                    + "Tname VARCHAR(50));");
            System.out.println("Teams Table have been created.");
            System.out.println(t+" Row(s) have been affected");
            
            int m=st.executeUpdate("CREATE TABLE IF NOT EXISTS Members ("
                    + "Mname VARCHAR(50));");
            System.out.println("Members Table have been created.");
            System.out.println(m+" Row(s) have been affected");

            int mt=st.executeUpdate("CREATE TABLE IF NOT EXISTS TM ("
                    + "Tname varchar(30) references Teams(Tname),"
                   + "Mname varchar(30) references  members(name));");
            System.out.println("MT Table have been created.");
            System.out.println(mt+" Row(s) have been affected");
            con.close();
        } catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException:\n"+ex.toString());
            ex.printStackTrace();
        } catch(SQLException ex) {
            System.out.println("SQLException:\n"+ex.toString());
            ex.printStackTrace();
        }
    }
    
} 
