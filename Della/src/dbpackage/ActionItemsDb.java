/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbpackage;
import screensframework.*;
import InternetConnectivity.InternetConnectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import screensframework.Screen1Controller;


/**
 *
 * @author SreePurna
 */
public class ActionItemsDb {
    private Statement st;
    private Connection con;
    private String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private String MYSQL_URL = "jdbc:mysql://10.10.10.157:3306/delladb";
    private String MYSQL_TEMP = "jdbc:mysql://localhost:3306/delladb";
    private InternetConnectivity ic = new InternetConnectivity();
    private Connection two;
    /**
     * This method is used to add the action items to the database
     * @param temp
     */
    public ActionItemsDb(){
        try{
            Class.forName(MYSQL_DRIVER);
            //System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        two = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");//local
        con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");//central
        if(ic.getInternetStatus()){
            Statement stmt = con.createStatement();//central db
         Statement stmtTwo = two.createStatement();//local
         stmtTwo.execute("delete from actionItems");
          ResultSet rs2 = stmt.executeQuery("SELECT * FROM ActionItems");
          NodeActionItem temp = new NodeActionItem();
         System.out.println("done retrieving and storing to collection....");
     //STEP 5: Extract data from result set
        while(rs2.next()){
        //Retrieve by column name
        System.out.println("waiting for next record..");
        
        temp.name = rs2.getNString(1);
        temp.description = rs2.getNString(2);
        temp.resolution = rs2.getNString(3);
        temp.assignedTeam = rs2.getNString(7);
        temp.assignedMember = rs2.getNString(8);
        temp.status = rs2.getNString(6);
        temp.creationDate = rs2.getNString(4);
        temp.dueDate = rs2.getNString(5);
        System.out.println(temp);
        stmtTwo.executeUpdate("INSERT INTO ActionItems (Name,Description,Resolution,Team,Member,Status,Creation,Due) VALUES ("+"\""+temp.name+"\""+","+
                                       "\""+temp.description+"\""+","+"\""+temp.resolution+"\""+","+
                                       "\""+temp.assignedTeam+"\""+","+
                                       "\""+temp.assignedMember+"\""+","+
                                       "\""+temp.status+"\""+","+
                                       "\""+temp.creationDate+"\""+","+
                                       "\""+temp.dueDate+"\""+");");
        
     }
     rs2.close();
     two.close();
     con.close();
            
        }
        }
        catch(Exception e){
            
        }
    
        
    }
    public void insert(screensframework.NodeActionItem temp)  {
        try {
            Class.forName(MYSQL_DRIVER);
            //System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            if(ic.getInternetStatus()){
                con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            }
            else{
                con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
            }
            
            System.out.println("Connected to the database....");
            st = con.createStatement();
            System.out.println("adding items.." + temp.name+"\n assigned team "+temp.assignedTeam +"\n assigned member "+temp.assignedMember);
            System.out.println("inserting");
            int noRows = st.executeUpdate("INSERT INTO ActionItems (Name,Description,Resolution,Creation,Due,Status,Team,Member) VALUES ("+"\""+temp.name+"\""+","+
                                        "\""+temp.description+"\""+","+"\""+temp.resolution+"\""+","+"\""+temp.creationDate+"\""+","+"\""+temp.dueDate+"\""+","+
                                        "\""+temp.status+"\""+","+"\""+temp.assignedTeam+"\""+","+"\""+temp.assignedMember+"\""+");");
            System.out.println("done adding! no. of rows effected:"+noRows);
            
        }catch(Exception e) {e.printStackTrace();}
    }

    /**
     * This method is used to get the data from the database of the action items
     * @return an array list of NodeActionItem 
     */
    public ArrayList<screensframework.NodeActionItem> retrieveValues() {
        ArrayList nodeAction=new ArrayList();
        screensframework.NodeActionItem temp;
        String sql = "SELECT * FROM ActionItems order by name;";
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
                temp=new screensframework.NodeActionItem();
                temp.name = rs.getString("name");
                temp.description = rs.getString("description");
                temp.resolution  = rs.getString("resolution");
                temp.creationDate = rs.getString("creation");
                temp.dueDate = rs.getString("due");
                temp.status = rs.getString("status");
                temp.assignedTeam = rs.getString("team");
                temp.assignedMember = rs.getString("member");
                nodeAction.add(temp);
                //System.out.println(temp.name+" "+temp.description+" "+temp.resolution);
            }
            rs.close();
            return nodeAction;
        }
        catch(Exception ex){ex.printStackTrace();}
        
        return nodeAction;
    }

    /**
     * This method is used to get the data from database(ACtionItems) 
     * @param tempname is the value of the field 'name' in the database(ACtionItems) 
     * @return an NodeActionItem
     */
    public screensframework.NodeActionItem retrieveValue(String tempname) {
        screensframework.NodeActionItem temp=new screensframework.NodeActionItem();;
        String sql = "SELECT * FROM ActionItems where name = '"+ tempname+"';";
        System.out.println(sql);
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
                if(tempname.equals(rs.getString("name"))){
                    temp.name = rs.getString("name");
                    temp.description = rs.getString("description");
                    temp.resolution  = rs.getString("resolution");
                    temp.creationDate = rs.getString("creation");
                    temp.dueDate = rs.getString("due");
                    temp.status = rs.getString("status");
                    temp.assignedTeam = rs.getString("team");
                    temp.assignedMember = rs.getString("member");
                    break;
                }
           
            }
            rs.close();
        }
        catch(Exception ex){ex.printStackTrace();}
        return temp;
    }

    /**
     * This method is used to update the value of action item in the database(ActionItems).
     * @param node 
     */
    public void update(screensframework.NodeActionItem node)    {
        try{
            String sql;
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            if(ic.getInternetStatus()){
                    con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
                }
                else{
                    con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
                }
            sql = "update actionitems set description='"+node.description+
                    "',resolution='"+node.resolution+
                    "',status='"+node.status+
                    "',team='"+node.assignedTeam+
                    "',member='"+node.assignedMember+
                    "',name='"+node.name+
                    "',due='"+node.dueDate+
                    "',creation='"+node.creationDate+"'where name='"+node.name+"'";
            System.out.println(sql);
            st.executeUpdate(sql);
        }
        catch(Exception ex){ex.printStackTrace();}
    }

    /**
     * This method is used to delete the action item in the database(ActionItems) 
     * @param tempName is the value of the field 'name' in the database(ACtionItems) 
     */
    public void delete(String tempName) {
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
        String sql = "DELETE FROM actionitems " +
                   "WHERE Name = '"+tempName+"'";
        st.executeUpdate(sql);}
        catch(Exception ex){ex.printStackTrace();}
        
    }
    public ArrayList<screensframework.NodeActionItem> onClick(String status,String direction,String first,String second) {
        ArrayList nodeAction=new ArrayList();
        screensframework.NodeActionItem temp;
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
            String sql;
            if(status.equals(""))
                sql = "Select * FROM actionitems " + " order by " +first  +second + " name " +direction +";";
            else
                sql = "Select * FROM actionitems " +
                      "WHERE status = '"+status+"'"+" order by " +first  +second + " name " +direction +";";
            
            System.out.println(sql);
            
            rs = st.executeQuery(sql);
            while(rs.next()){
                temp=new screensframework.NodeActionItem();
                temp.name = rs.getString("name");
                temp.description = rs.getString("description");
                temp.resolution  = rs.getString("resolution");
                temp.creationDate = rs.getString("creation");
                temp.dueDate = rs.getString("due");
                temp.status = rs.getString("status");
                temp.assignedTeam = rs.getString("team");
                temp.assignedMember = rs.getString("member");
                nodeAction.add(temp);
                //System.out.println(temp.name+" "+temp.description+" "+temp.resolution);
            }
            rs.close();
            return nodeAction;
        }
        catch(Exception ex){ex.printStackTrace();}
        return nodeAction;
    }
    
     public boolean check(String temp){
        boolean val=false;
        try {
            
            Class.forName(MYSQL_DRIVER);
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                    con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
                }
                else{
                    con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
                }
            System.out.println("Connected to the database....");
            System.out.println("In the readders db of check methods");
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Readers where Rname = '"+ temp+"';");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            if(false == rs.next()){
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                val=true;
                return val;
            }
            else{
                val=false;
                return val;
            }
            
         
        }catch(Exception e) {e.printStackTrace();}
        return val;
    }
     
         public void insert(String temp)  {
         try {
            Class.forName(MYSQL_DRIVER);
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                    con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
                }
                else{
                    con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
                }
            System.out.println("Connected to the database....");
            System.out.println("In the readders db of insert methods");
            st = con.createStatement();
            int noRows = st.executeUpdate("INSERT INTO Readers VALUES ("+"\""+temp+"\""+");");
            System.out.println("done adding! no. of rows effected:"+noRows);
         
        }catch(Exception e) {e.printStackTrace();}
    }
         
          public void delete(String tempName,String name) {
        try{
            Class.forName(MYSQL_DRIVER);
            System.out.println("Class Loaded....");
            //con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
            if(ic.getInternetStatus()){
                    con = DriverManager.getConnection(MYSQL_URL,"root","sree1994");
                }
                else{
                    con = DriverManager.getConnection(MYSQL_TEMP,"root","sree1994");
                }
            System.out.println("Connected to the database....");
            System.out.println("Deleting in readers table");
            st = con.createStatement();
        String sql = "DELETE FROM Readers " +
                   "WHERE Rname = '"+tempName+"'";
        st.executeUpdate(sql);}
        catch(Exception ex){ex.printStackTrace();}
        
    }
}
