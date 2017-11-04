package screensframework;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Sreepurna
 */
public class Screen1Controller implements Initializable, ControlledScreen {

    ScreensController myController;
    InternetConnectivity.InternetConnectivity ic;
    dbpackage.ActionItemsDb adb;
        
    /**
     *
     * @param screenParent
     */
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(ScreensFramework.screen2ID);
    }
    
    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(ScreensFramework.screen1ID);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(ScreensFramework.screen3ID);
    }
    @FXML
    private void goToScreen4(ActionEvent event){
       myController.setScreen(ScreensFramework.screen4ID);
    }
    
    @FXML
    private Circle circle;
    @FXML
    private ComboBox sortingDirection;
    @FXML
    private ComboBox firstSortingFactor;
    @FXML
    private ComboBox secondSortingFactor;
    @FXML
    private ComboBox inclusionFactor;
    @FXML
    private Label status;
    @FXML
    private Label aAssignedMember;
    @FXML
    private Label aAssignedTeam;
    @FXML
    private ListView<String> actions;
    @FXML
    private TextField aName;
    @FXML
    private TextArea aDescription;
    @FXML
    private TextArea aResolution;
    @FXML
    private Label aCreationDate;
    @FXML
    private Label aDueDate;
    
    List<String> listSortingDirection = Arrays.asList("Large To Small","Small To Large");
    List<String> listSortingFactor = Arrays.asList("None","Creation Date","Due Date","Assigned Member","Assigned Team");
    List<String> listInclusionFactor = Arrays.asList("None","Open Action Items","Closed Action Items");
    List<String> listStatus = Arrays.asList("Open","Close");
    List<String> listMembers = Arrays.asList("-no member selected-");
    //add data from database here later
    List<String> listTeams = Arrays.asList("-no team selected-");
    
    ArrayList<String> actionName=new ArrayList();
    ObservableList<String> actionList = FXCollections.observableArrayList(actionName);
   
    /**
     * This method resets the array list of names(actionName) and resets the actions lists of console screen(actions).
     */
    public void actionItemsList(){
        ArrayList<NodeActionItem> result = adb.retrieveValues();
        actionName=new ArrayList();
        for(NodeActionItem temp:result) {
           // System.out.println("name if action"+temp.name);
            actionName.add(temp.name);
        }
        System.out.println(" empty action observable list "+actionList);
        System.out.println(" action items list "+actionName);
        actionList.clear();
        actions.setItems(actionList);
        
        actionList = FXCollections.observableArrayList(actionName);
        System.out.println("action observable list "+actionList);
        actions.setItems(actionList);
                //actionList.clear();
                //System.out.println("action observable list "+actionList);
    }
    
    /**
     * It is used to set the values in the action items screen.
     * @param node 
     */
    private void setValue(NodeActionItem node){
       aName.setText(node.name);
       aDescription.setText(node.description);
       aResolution.setText(node.resolution);
       aCreationDate.setText(node.creationDate);
       aDueDate.setText(node.dueDate);
       aAssignedTeam.setText(node.assignedTeam);
       status.setText(node.status);
       aAssignedMember.setText(node.assignedMember);
    }
    @FXML
    private void updateList(MouseEvent event) {
        actionItemsList();
    }
    @FXML
    private void setFields(MouseEvent  event) {
       String name = actions.getSelectionModel().getSelectedItem();
       System.out.println("selected name is:"+name);
       NodeActionItem node=adb.retrieveValue(name);
       setValue(node);
    }
    @FXML
    private void setConnectivity(){
        System.out.println("in the set connectivity");
        final long timeInterval = 500;
        Runnable runnable;
        runnable = new Runnable() {
            public void run() {
                while (true) {
                    boolean status = ic.getInternetStatus();
                    System.out.println("status is"+status);
                    if(!status)
                        circle.setFill(Color.rgb(225, 31, 7));
                    else
                        circle.setFill(Color.rgb(70, 255, 33));
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
    
    @FXML
    private void onChangeQuit(){
        String header="";
        //String content="A Quit has been requested where the content been updated \n Click "+"YES to save the changes? \n Click "+"NO to discard the changes?";
        String content="ARE YOU SURE TO QUIT THE APPLICATION \n Click "+"OK to Quit  \n Click "+"CANCEL to discard";
        AlertBox.alert("Quit",header,content);
    }
    @FXML
    public void sorting(ActionEvent event){
        String status = inclusionFactor.getValue().toString();
        String first = firstSortingFactor.getValue().toString();
        String second = secondSortingFactor.getValue().toString();
        String direction = sortingDirection.getValue().toString();
        
        System.out.println("status "+status+"first"+first+"second"+second+"direction"+direction);
        
        if(status.equals("All Action Items"))
            status="";
        else if(status.equals("Open Action Items"))
            status="open";
        else
            status="close";
        
        if(first.equals("None"))
            first="";
        else if(first.equals("Creation Date"))
            first="Creation ,";
        else if(first.equals("Due Date"))
            first="Due ,";
        else if(first.equals("Assigned Member"))
            first="Member ,";
        else
            first="Team ,";
                
        if(second.equals("None"))
            second="";
        else if(second.equals("Creation Date"))
            second="Creation ,";
        else if(second.equals("Due Date"))
            second="Due ,";
        else if(second.equals("Assigned Member"))
            second="Member ,";
        else
            second="Team ,";
        
        if(direction.equals("Small To Large"))
            direction="";
        else
            direction="desc";
        
        System.out.println("status "+status+"first"+first+"second"+second+"direction"+direction);
        ArrayList<NodeActionItem> result=adb.onClick(status, direction, first, second);
        ArrayList<String> actionN=new ArrayList();
        for(NodeActionItem temp:result) {
            System.out.println(temp.name);
            String s;
            if(first.equals(""))
            s="";
            else if(first.equals("Creation ,"))
            s=temp.creationDate;
            else if(first.equals("Due ,"))
            s=temp.dueDate;
            else if(first.equals("Member ,"))
            s=temp.assignedMember;
            else
            s=temp.assignedTeam;
            
            if(second.equals(""))
            s=s+"";
            else if(second.equals("Creation ,"))
            s=s+" :: "+temp.creationDate;
            else if(second.equals("Due ,"))
            s=s+" :: "+temp.dueDate;
            else if(second.equals("Member ,"))
            s=s+" :: "+temp.assignedMember;
            else
            s=s+" :: "+temp.assignedTeam;
            if(first.equals("") && second.equals(""))
            s=s+temp.name;
            else 
            s=s+" :: "+temp.name;
            actionN.add(s);
        }
       System.out.println(actionN);
       actions.getItems().clear();
       actions.getItems().addAll(actionN);
        
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sortingDirection.getItems().addAll(listSortingDirection);
        sortingDirection.setValue(listSortingDirection.get(1));
        firstSortingFactor.getItems().addAll(listSortingFactor);
        firstSortingFactor.setValue(listSortingFactor.get(0));
        secondSortingFactor.getItems().addAll(listSortingFactor);
        secondSortingFactor.setValue(listSortingFactor.get(0));
        inclusionFactor.getItems().addAll(listInclusionFactor);
        inclusionFactor.setValue(listInclusionFactor.get(0));
        adb=new dbpackage.ActionItemsDb();
        actionItemsList();
        ic=new InternetConnectivity.InternetConnectivity();
        setConnectivity();
    }

}
