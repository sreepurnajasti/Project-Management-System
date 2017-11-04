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

/**
 *
 * @author SreePurna
 */
public class TeamsDb {
    private Statement st;
    private Connection con;
    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private String MYSQL_URL = "jdbc:mysql://10.10.10.157:3306/delladb";
    private String MYSQL_TEMP = "jdbc:mysql://localhost:3306/delladb";
    InternetConnectivity ic = new InternetConnectivity();
    public TeamsDb(){
        try{
       Connection conTwo =DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
     if(ic.getInternetStatus())
        con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
        Statement stmt = con.createStatement();//central db
        Statement stmtTwo = conTwo.createStatement();//local
        stmtTwo.execute("delete from teams");
        ResultSet rs2 = stmt.executeQuery("SELECT * FROM teams");
        System.out.println("Connected to the database....");
        while(rs2.next()){
        String name = rs2.getNString(1);
        stmtTwo.executeUpdate("insert into teams(Tname) values("+
                       "\""+name+"\");");
         
     }
     rs2.close();
     conTwo.close();
     con.close();
     }catch(Exception e){}
    }
    /**
     * This is used to get all team names from database.
     * @return 
     */
    public ArrayList<String> retrieve(){
        ArrayList<String> teams=new ArrayList();
        String team = "SELECT * FROM Teams;";
        ResultSet rs;
        try{
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            System.out.println("Connected to the database....");
            st = con.createStatement();
            rs = st.executeQuery(team);
            while(rs.next()){   
                teams.add(rs.getString("Tname"));
            }
            System.out.println(teams);
            con.close();
            rs.close();return teams;
        }
        catch(Exception ex){ex.printStackTrace();}
        return null;
    }
    /**
     * This method is used to get all the member names.
     * @return 
     */
    public ArrayList<String> getAllMembers(){
        ArrayList<String> members=new ArrayList();
        String member = "SELECT * FROM members order by mname;";
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
            System.out.println("all members are \n"+members);
            rs.close();
            con.close();
        }
        catch(Exception ex){ex.printStackTrace();}
        return members;
    }
    public ArrayList<String> getCurrentMembers(String temp){
        ArrayList<String> current=new ArrayList();
        String sql = "SELECT * FROM TM where Tname = '"+ temp+"';";
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
                current.add(rs.getString("Mname"));
            }
            System.out.println("Current members are \n"+current);
            rs.close();
            con.close();
        }
        catch(Exception ex){ex.printStackTrace();}
        return current;
    }
    /**
     * This method is used to insert the new team in the database.
     * @param temp 
     */
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
            int noRows = st.executeUpdate("INSERT INTO Teams VALUES ("+"\""+temp+"\""+");");
            System.out.println("done adding! no. of rows effected:"+noRows);
            con.close();
        }catch(Exception e) {e.printStackTrace();}
    }
    /**
     * To remove a team from database by taking team name.
     * @param name 
     */
    public void removeT(String name){
        try {
            Class.forName(MYSQL_DRIVER);
            System.out.println("name of team"+name);
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
             System.out.println("deleting from Teams");
            System.out.println("DELETE FROM Teams " +
                   "WHERE Tname ="+
                    "\""+name+"\""+";");
            String sql = "DELETE FROM Teams " +
                   "WHERE Tname ="+
                    "\""+name+"\""+";"
                    ;
               int no= st.executeUpdate(sql);
               sql = "DELETE FROM TM " +
                   "WHERE Tname ="+
                    "\""+name+"\""+";"
                    ;
               st.executeUpdate(sql);
               System.out.println("no. of rows affected"+no);
               con.close();
          //  }
        }catch(Exception ex){ex.printStackTrace();}
    }
    
    public void removeM(String name,String member){
        try {
            Class.forName(MYSQL_DRIVER);
            System.out.println("name of team"+name+"\nadd to member"+member);
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
                   "WHERE tname =" + 
                    "\""+name+"\"" +
                    "AND mname =" +
                    "\""+member+"\" "+";");
            
            String sql = "DELETE FROM TM " +
                   "WHERE tname =" + 
                    "\""+name+"\"" +
                    "AND mname =" +
                    "\""+member+"\" "+";";
                    
            boolean no= st.execute(sql);
            System.out.println("no. of rows affected"+no);
            con.close();
        }catch(Exception e) {e.printStackTrace();}
        
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
            st = con.createStatement();con.close();
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
        
        String team = "SELECT * FROM Teams where tname = \""+name+"\""+";";
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
            rs = st.executeQuery(team);
            boolean value = rs.next();
            rs.close();con.close();
            return value;
        }
        catch(Exception ex){ex.printStackTrace();}
        return false;
    }
}
