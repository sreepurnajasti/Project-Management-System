/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbpackage;

import InternetConnectivity.InternetConnectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import screensframework.AlertBox;

/**
 *
 * @author SreePurna
 */
public class MembersDb {
    private Statement st;
    private Connection con;
    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private String MYSQL_URL = "jdbc:mysql://10.10.10.157:3306/delladb";
    private String MYSQL_TEMP = "jdbc:mysql://localhost:3306/delladb";
    InternetConnectivity ic = new InternetConnectivity();
    public MembersDb(){
        try{
       Connection conTwo =DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
     if(ic.getInternetStatus())
        con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
        Statement stmt = con.createStatement();//central db
        Statement stmtTwo = conTwo.createStatement();//local
        stmtTwo.execute("delete from members");
        stmtTwo.execute("delete from tm");
        ResultSet rs2 = stmt.executeQuery("SELECT * FROM members");
        System.out.println("Connected to the database....");
        while(rs2.next()){
        String name = rs2.getNString(1);
        stmtTwo.executeUpdate("insert into members(Mname) values("+
                       "\""+name+"\");");
         
     }
     rs2.close();
     
     
     rs2 = stmt.executeQuery("SELECT * FROM tm");
     System.out.println("Connected to the database....");
     while(rs2.next()){
         String mname = rs2.getNString(2);
         String tname = rs2.getNString(1);
         stmtTwo.executeUpdate("insert into tm(Mname,Tname) values("+
                       "\""+mname+"\" "+ ",\""+tname+"\""+");");
         
     }
     rs2.close();con.close();conTwo.close();}catch(Exception e){}
    }
    public ArrayList<String> retrieve(){
        ArrayList<String> members=new ArrayList();
        String member = "SELECT * FROM Members order by mname;";
        ResultSet rs;
        try{
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            rs = st.executeQuery(member);
            while(rs.next()){   
                members.add(rs.getString("Mname"));
            }
            System.out.println(members);
            rs.close();
            con.close();
        }
        catch(Exception ex){ex.printStackTrace();}
        return members;
    }

    public void insert(String temp)  {
        try {
            Class.forName(MYSQL_DRIVER);
            //System.out.println("Class Loaded....");
            System.out.println("name of member"+temp);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            // System.out.println("adding items.." + temp.name+"\n assigned team "+temp.assignedTeam +"\n assigned member "+temp.assignedMember);
            System.out.println("inserting");
            int noRows = st.executeUpdate("INSERT INTO Members VALUES ("+"\""+temp+"\""+");");
            System.out.println("done adding! no. of rows effected:"+noRows);
            con.close();
        }catch(Exception e) {e.printStackTrace();}
    }
 
    public ArrayList<String> getAllTeams(){
        ArrayList<String> teams=new ArrayList();
        String member = "SELECT * FROM Teams order by tname;";
        ResultSet rs;
        try{
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            rs = st.executeQuery(member);
            while(rs.next()){   
                teams.add(rs.getString("Tname"));
            }
            System.out.println("all teams are \n"+teams);
            rs.close();
            con.close();
        }
        catch(Exception ex){ex.printStackTrace();}
        return teams;
    }
    public ArrayList<String> getCurrentTeams(String temp){
        ArrayList<String> current=new ArrayList();
        String sql = "SELECT * FROM TM where Mname = '"+ temp+"' order by tname;";
                ResultSet rs;
        try{
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){   
                current.add(rs.getString("Tname"));
            }
            System.out.println("Current teams are \n"+current);
            rs.close();
            con.close();
        }
        catch(Exception ex){ex.printStackTrace();}
        return current;
    }
    public boolean insertTm(String name,String team){
        try {
            Class.forName(MYSQL_DRIVER);
            System.out.println("name of member"+name+"\nadd to team"+team);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            //con.close();
            // System.out.println("adding items.." + temp.name+"\n assigned team "+temp.assignedTeam +"\n assigned member "+temp.assignedMember);
            System.out.println("inserting to TM");
            System.out.println("INSERT INTO TM (mname,tname) VALUES ("+"\""+name+"\""+","+"\""+team+"\""+");");
            String sql="INSERT INTO TM (mname,tname) VALUES ("+"\""+name+"\""+","+"\""+team+"\""+");";
            if(sql.equals("INSERT INTO TM (mname,tname) VALUES (\"\",\"null\");")){
               // AlertBox.alert("Select following fields","1. Select member name\n2. Select team name");
                return false;
            }
            else{
                int noRows = st.executeUpdate(sql);
                System.out.println("done adding! no. of rows effected:"+noRows);
                return true;
            }
           
        }catch(Exception e) {e.printStackTrace();}
        return false;
    }
    public boolean isPresent(String name){
        
        String member = "SELECT * FROM Members where mname = \""+name+"\""+";";
        ResultSet rs;
        try{
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            //con.close();
            rs = st.executeQuery(member);
            boolean value = rs.next();
            rs.close();
            return value;
           
        }
        catch(Exception ex){ex.printStackTrace();}
        return false;
    }
    public void removeTm(String name,String team){
        try {
            Class.forName(MYSQL_DRIVER);
            System.out.println("name of member"+name+"\nadd to team"+team);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            // System.out.println("adding items.." + temp.name+"\n assigned team "+temp.assignedTeam +"\n assigned member "+temp.assignedMember);
            System.out.println("deleting from TM");
            System.out.println("DELETE FROM TM " +
                   "WHERE mname =" + 
                    "\""+name+"\"" +
                    "AND tname =" +
                    "\""+team+"\" "+";");
            
            String sql = "DELETE FROM TM " +
                   "WHERE mname =" + 
                    "\""+name+"\"" +
                    "AND tname =" +
                    "\""+team+"\" "+";";
                    
            boolean no= st.execute(sql);
            System.out.println("no. of rows affected"+no);
            con.close();
        }catch(Exception e) {e.printStackTrace();}
        
    }
        
    public void removeM(String name){
        try {
            Class.forName(MYSQL_DRIVER);
            System.out.println("name of member"+name);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            System.out.println("Connected to the database....");
            st = con.createStatement();
            // System.out.println("adding items.." + temp.name+"\n assigned team "+temp.assignedTeam +"\n assigned member "+temp.assignedMember);
            System.out.println("deleting from Members");
            System.out.println("DELETE FROM Members " +
                   "WHERE Mname ="+
                    "\""+name+"\""+";");
            String sql = "DELETE FROM Members " +
                   "WHERE Mname ="+
                    "\""+name+"\""+";"
                    ;
               int no= st.executeUpdate(sql);
               sql = "DELETE FROM TM " +
                   "WHERE Mname ="+
                    "\""+name+"\""+";"
                    ;
               st.executeUpdate(sql);
               System.out.println("no. of rows affected"+no);
               con.close();
          //  }
        }catch(Exception ex){ex.printStackTrace();}
    }
     
}
