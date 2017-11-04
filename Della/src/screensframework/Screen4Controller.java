package screensframework;

import dbpackage.TeamsDb;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class Screen4Controller implements Initializable, ControlledScreen {

    ScreensController myController;
    InternetConnectivity.InternetConnectivity ic;
    TeamsDb tdb;
    
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
       ScreensFramework.mainContainer.loadScreen(ScreensFramework.screen3ID, ScreensFramework.screen3File);
       myController.setScreen(ScreensFramework.screen3ID);
    }
    @FXML
    private void goToScreen4(ActionEvent event){
       myController.setScreen(ScreensFramework.screen4ID);
    }
    
    @FXML
            ListView teamsAll;
    @FXML
            ListView allMembers;
    @FXML
            ListView currentMembers;
    @FXML
            Label tName;
    @FXML
            Label tmName;
    
    boolean selFlag = false;
    
    @FXML
            TextField name;
    @FXML
            Circle circle;
    @FXML
            Button addToList;
    @FXML
            Button removeFromList;
    @FXML
            Button addAssociation;
    @FXML
            Button removeAssociation;

    boolean netStat = false;

    

    
    /**
     * To remove a team.
     */
    @FXML
    public void removeList(){
        if(selFlag){
            String n= teamsAll.getSelectionModel().getSelectedItem().toString();
            if(n == null) {
                AlertBox.alert("", "select a member");
                removeFromList.setDisable(true);
                addAssociation.setDisable(true);
                removeAssociation.setDisable(true);
                return;
   
            }
            tdb.removeT(n);
            clear();
            setAllTeams();
            removeFromList.setDisable(true);
            addAssociation.setDisable(true);
            removeAssociation.setDisable(true);
            selFlag = false;
        }
        else {
            AlertBox.alert("", "select a member");
            removeFromList.setDisable(true);
            addAssociation.setDisable(true);
            removeAssociation.setDisable(true);
        }
        
    }
    
    /**
     * To clear the fields like labels,list view of current members and all members.
     */
    public void clear(){
        name.setText("");
        tName.setText("");
        tmName.setText("");
        ObservableList<String> currentTeamsList = FXCollections.observableArrayList();
        allMembers.setItems(currentTeamsList);
        currentMembers.setItems(currentTeamsList);
        
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tdb=new TeamsDb();
        setAllTeams();
        ic=new InternetConnectivity.InternetConnectivity();
        setConnectivity();
        addToList.setDisable(true);
        removeFromList.setDisable(true);
        addAssociation.setDisable(true);
        removeAssociation.setDisable(true);
    }
    
    /**
     * To set the data in list view(allTeams).
     */
    private void setAllTeams(){
        System.out.println("In the set all teams");
        ArrayList<String> teams=new ArrayList();
        teams=tdb.retrieve();
        ObservableList<String> teamsList = FXCollections.observableArrayList(teams);
        teamsAll.setItems(teamsList);
    }

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
                        addAssociation.setDisable(true);
                        removeAssociation.setDisable(true);
                    }
                    else{
                        circle.setFill(Color.rgb(70, 255, 33));
                       /* addToList.setDisable(true);
                        removeFromList.setDisable(true);
                        addAssociation.setDisable(true);
                        removeAssociation.setDisable(true);*/
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
     * To add Teams.
     */
    public void addList(){
        String newName = name.getText();
        if(name.getText().equals("")){
            AlertBox.alert("","enter a team name");
            addToList.setDisable(false);
            return;
        }
        else if(tdb.isPresent(newName)){
            AlertBox.alert("","enter a new team name");
            addToList.setDisable(false);
            return;
        }
        else{
            tdb.insert(newName);
            name.setText("");
            setAllTeams();
            addToList.setDisable(true);
        }
    }
    
    /**
     * This method is called when an member is selected from the teamsAll list view. 
     * @param event 
     */
    @FXML
    private void setFields(MouseEvent  event) {    
        selFlag = true;
        String name = teamsAll.getSelectionModel().getSelectedItem().toString();
        if(name == null) return;
        tName.setText(name);
        tmName.setText(name);
        setAllTeams();
        setMembers(name);   
        boolean status = ic.getInternetStatus();
        if(status) {
            addToList.setDisable(true);
            removeFromList.setDisable(false);
        }     
    }
    
    /**
     * To set the data in list views(allMembers,currentMembers) for a particular team.
     * @param name 
     */
    private void setMembers(String name){
        //to set list view current teams
        ArrayList<String> cMember=new ArrayList();
        cMember=tdb.getCurrentMembers(name);
        ObservableList<String> currentMemberList = FXCollections.observableArrayList(cMember);
        currentMembers.setItems(currentMemberList);
        //to set list view of all teams except current teams
        ArrayList<String> Member=new ArrayList();
        Member=tdb.getAllMembers();
        Member.removeAll(cMember);
        ObservableList<String> allMemberList = FXCollections.observableArrayList(Member);
        allMembers.setItems(allMemberList);
    }
    
    /**
     * This method is called to enable set AddAffiliations 
     * @param event 
     */
    @FXML
    private void setAddAssociations(MouseEvent  event) {    
        boolean status = ic.getInternetStatus();
        if(status) {
            addAssociation.setDisable(false);
        }        
    }
    
    /**
     * To add teams to that member.
     */
    @FXML
    public void addAssociation(){
        String name=tName.getText();
        String member = allMembers.getSelectionModel().getSelectedItem().toString();
        System.out.println("in add association method");
        if(member == null){ AlertBox.alert("", "select a team");addAssociation.setDisable(true);return;}
        else if(!tdb.insertTm(member,name))return;
        setMembers(name);
        addAssociation.setDisable(true);
    }
    
    /**
     * This method is called to enable set RemoveAffiliations 
     * @param event 
     */
    @FXML
    private void setRemoveAssociation(MouseEvent  event) {    
        boolean status = ic.getInternetStatus();
        if(status) {
            addAssociation.setDisable(true);
            removeAssociation.setDisable(false);
        }        
    }
    
    /**
     * To remove current team for that member.
     */
    @FXML
    public void removeAssociation(){
        String name=tName.getText();
        String team = currentMembers.getSelectionModel().getSelectedItem().toString();
        if(team == null ){
            AlertBox.alert("", "select a team");
            removeAssociation.setDisable(true);
            return;
        }
        tdb.removeM(name,team);
        setMembers(name);
        removeAssociation.setDisable(true);
        
    }
    @FXML
    private void onChangeQuit(){
        String header="";
        //String content="A Quit has been requested where the content been updated \n Click "+"YES to save the changes? \n Click "+"NO to discard the changes?";
        String content="ARE YOU SURE TO QUIT THE APPLICATION \n Click "+"OK to Quit  \n Click "+"CANCEL to discard";
        AlertBox.alert("Quit",header,content);
    }
    
}
