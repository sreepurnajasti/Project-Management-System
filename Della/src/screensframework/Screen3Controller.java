package screensframework;

import InternetConnectivity.InternetConnectivity;
import dbpackage.MembersDb;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Sreepurna
 */
public class Screen3Controller implements Initializable, ControlledScreen {

    ScreensController myController;
    MembersDb mdb;
    InternetConnectivity ic;
    
    /**
     *
     * @param screenParent
     */
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(ScreensFramework.screen1ID);
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       ScreensFramework.mainContainer.loadScreen(ScreensFramework.screen2ID, ScreensFramework.screen2File);
       myController.setScreen(ScreensFramework.screen2ID);
    }
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(ScreensFramework.screen3ID);
    }
    @FXML
    private void goToScreen4(ActionEvent event){
        ScreensFramework.mainContainer.loadScreen(ScreensFramework.screen4ID, ScreensFramework.screen4File);
        myController.setScreen(ScreensFramework.screen4ID);
    }    
    @FXML
    TextField name;
    @FXML
    Label mName;
    @FXML
    Label memName;
    @FXML
    private ListView<String> allMembers;
    @FXML
    private ListView<String> allTeams;
    @FXML
    private ListView<String> currentTeams;
    @FXML
            Circle circle;
    @FXML
            Button addToList;
    @FXML
            Button removeFromList;
    @FXML
            Button addAffliation;
    @FXML
            Button removeAffliation;
    
            
    boolean selFlag = false;
    boolean selFlagTeam = false;
    boolean netStat = false;
    /**
     * To remove a member.
     */
    @FXML
    public void removeList(){
        if(selFlag){
            String n= allMembers.getSelectionModel().getSelectedItem();
            if(n == null) {
                AlertBox.alert("", "select a member");
                removeFromList.setDisable(true);
                addAffliation.setDisable(true);
                return;
   
            }
            mdb.removeM(n);
            clear();
            setAllMembers();
            //System.out.println("dcsdg");
            removeFromList.setDisable(true);
            addAffliation.setDisable(true);
            removeAffliation.setDisable(true);
            selFlag = false;
        }
        else {
            AlertBox.alert("", "select a member");
            removeFromList.setDisable(true);
            addAffliation.setDisable(true);
            removeAffliation.setDisable(true);
        }
        
    }
    
    /**
     * To clear the fields like labels,list view of current teams and all teams.
     */
    public void clear(){
        name.setText("");
        mName.setText("");
        memName.setText("");
        ObservableList<String> currentTeamsList = FXCollections.observableArrayList();
        allTeams.setItems(currentTeamsList);
       
        currentTeams.setItems(currentTeamsList);
        
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mdb=new MembersDb();
        ic=new InternetConnectivity();
        setConnectivity();
        addToList.setDisable(true);
        removeFromList.setDisable(true);
        addAffliation.setDisable(true);
        removeAffliation.setDisable(true);
        setAllMembers();
    } 
    
    /**
     * To set the data in list view(allMembers).
     */
    private void setAllMembers(){
        System.out.println("In the set all members");
        ArrayList<String> members=new ArrayList();
        members=mdb.retrieve();
        ObservableList<String> membersList = FXCollections.observableArrayList(members);
        allMembers.setItems(membersList);
    }
    
    @FXML
    private void setConnectivity(){
        System.out.println("in the set connectivity");
        System.out.println("in the set connectivity");
        final long timeInterval = 1000;
        Runnable runnable;
        runnable = new Runnable() {
            public void run() {
                while (true) {
                    boolean status = ic.getInternetStatus();
                    netStat = status;
                    System.out.println("status is"+status);
                    if(!status){
                        circle.setFill(Color.rgb(225, 31, 7));
                        addToList.setDisable(true);
                        removeFromList.setDisable(true);
                        addAffliation.setDisable(true);
                        removeAffliation.setDisable(true);
                    }
                    else {
                        circle.setFill(Color.rgb(70, 255, 33));
                        /*addToList.setDisable(true);
                        removeFromList.setDisable(true);
                        addAffliation.setDisable(true);
                        removeAffliation.setDisable(true);*/
                    }
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    
    /**
     * This method is called to enable the addToList button.
     * @param event 
     */
    @FXML
    private void setAddToList(MouseEvent  event) {   
        //boolean status = ic.getInternetStatus();
        if(netStat) {
            addToList.setDisable(false);            
        }
        else{
            addToList.setDisable(true);
        }
    }
    
    /**
     * To add members.
     */
    public void addList(){
    String newName = name.getText();
        if(name.getText().equals("")){
            AlertBox.alert("","enter a member name");
            addToList.setDisable(true);
            return;
        }
        else if(mdb.isPresent(newName)){
            AlertBox.alert("","enter a new member name");
            addToList.setDisable(true);
            return;
        }
        else{
            mdb.insert(newName);
            name.setText("");
            setAllMembers();
            addToList.setDisable(true);
        }
    }
    /**
     * This method is called when an member is selected from the allmembers list view. 
     * @param event 
     */
    @FXML
    private void setFields(MouseEvent  event) {    
        selFlag = true;
        String name = allMembers.getSelectionModel().getSelectedItem();
        if(name == null) return;
        mName.setText(name);
        memName.setText(name);
        setAllMembers();
        setTeams(name); 
        boolean status = ic.getInternetStatus();
        if(status) {
            addToList.setDisable(true);
            removeFromList.setDisable(false);
        }        
    }
    
    /**
     * To set the data in list views(allTeams,currentTeams) for a particular member.
     * @param name 
     */
    
    private void setTeams(String name){
        //to set list view current teams
        ArrayList<String> cTeams=new ArrayList();
        cTeams=mdb.getCurrentTeams(name);
        ObservableList<String> currentTeamsList = FXCollections.observableArrayList(cTeams);
        currentTeams.setItems(currentTeamsList);
        //to set list view of all teams except current teams
        ArrayList<String> Teams=new ArrayList();
        Teams=mdb.getAllTeams();
        Teams.removeAll(cTeams);
        ObservableList<String> allTeamsList = FXCollections.observableArrayList(Teams);
        allTeams.setItems(allTeamsList);
    }
    
    /**
     * This method is called to enable set AddAffiliations 
     * @param event 
     */
    @FXML
    private void setAddAffiliations(MouseEvent  event) {    
        boolean status = ic.getInternetStatus();
        if(status) {
            addAffliation.setDisable(false);
            //removeAffliation.setDisable(true);
        }        
    }
    /**
     * To add teams to that member.
     */
    @FXML
    public void addAffiliation(){
        String name=mName.getText();
        String team = allTeams.getSelectionModel().getSelectedItem();
        System.out.println("in add affiliation method");
        if(team == null){ AlertBox.alert("", "select a team");
            addAffliation.setDisable(true);
            return;}
        else if(!mdb.insertTm(name, team))
               return;
        setTeams(name);
        addAffliation.setDisable(true);
    }
    
    /**
     * This method is called to enable set RemoveAffiliations 
     * @param event 
     */
    @FXML
    private void setRemoveAffiliations(MouseEvent  event) {    
        boolean status = ic.getInternetStatus();
        if(status) {
            addAffliation.setDisable(true);
            removeAffliation.setDisable(false);
        }        
    }
    
    /**
     * To remove current team for that member.
     */
    @FXML
    public void removeAffiliation(){
        String name=mName.getText();
        String team=currentTeams.getSelectionModel().getSelectedItem();
        if(team == null || team.equals(null) ){
            AlertBox.alert("", "select a team");
            removeAffliation.setDisable(true);
            return;
        }
        mdb.removeTm(name,team);
        setTeams(name);
        removeAffliation.setDisable(true);
    }
    @FXML
    private void onChangeQuit(){
        String header="";
        //String content="A Quit has been requested where the content been updated \n Click "+"YES to save the changes? \n Click "+"NO to discard the changes?";
        String content="ARE YOU SURE TO QUIT THE APPLICATION \n Click "+"OK to Quit  \n Click "+"CANCEL to discard";
        AlertBox.alert("Quit",header,content);
    }
    
}
