package screensframework;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author SreePurna
 */
public class Screen2Controller implements Initializable , ControlledScreen {

    ScreensController myController;
    dbpackage.ActionItemsDb adb;
    InternetConnectivity.InternetConnectivity ic;
    
    public Screen1Controller sc1=new Screen1Controller();
    /**
     *
     * @param screenParent
     */
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){

       ScreensFramework.mainContainer.loadScreen(ScreensFramework.screen1ID, ScreensFramework.screen1File);
       myController.setScreen(ScreensFramework.screen1ID);
    }
    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(ScreensFramework.screen2ID);
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
    private TextField aName;
    @FXML
    private TextField aDue;
    @FXML
    private TextArea aDescription;
    @FXML
    private TextArea aResolution;
    @FXML
    private Button aCreate;
    @FXML
    private ComboBox sortingDirection;
    @FXML
    private ComboBox firstSortingFactor;
    @FXML
    private ComboBox secondSortingFactor;
    @FXML
    private ComboBox inclusionFactor;
    @FXML
    private ComboBox status;
    @FXML
    private ComboBox aActionItems;
    @FXML
    private ComboBox aAssignedMember;
    @FXML
    private ComboBox aAssignedTeam;
    @FXML
    private Label aCurrentDate;
    boolean setTFlag = false;
    boolean setMFlag = false;
    @FXML
    Circle circle;
    @FXML
    Button update;
    @FXML
            Button clear;
    @FXML
            Button create;
    @FXML
            Button delete;
    
    ArrayList<NodeActionItem> actionList;
    
    ArrayList<String> actionName=new ArrayList();
    List<String> listSortingDirection = Arrays.asList("Large To Small","Small To Large");
    List<String> listSortingFactor = Arrays.asList("None","Creation Date","Due Date","Assigned Member","Assigned Team");
    List<String> listInclusionFactor = Arrays.asList("All Action Items","Open Action Items","Closed Action Items");
    List<String> listStatus = Arrays.asList("Open","Close");
    List<String> listMembers;
    //add data from database here later
    List<String> listTeams;
    //add data from database here later
    
    /**
     * When "Clear the form" button is clicked this method is called
     * This function is used to clear the values in the action items screen.
     * @param event 
     */
    @FXML
    private void clearThisForm(ActionEvent event)   {
        aName.setText("");
        aDue.setText("");
        aDescription.setText("");
        aResolution.setText("");
        aCurrentDate.setText("");
        aActionItems.setValue("");
        aAssignedMember.setValue("-no member selected-");
        aAssignedTeam.setValue("-no team selected-");
    }
    
    /**
     * This method is used to display the values when an action item is selected in the Action items list.
     * @param event 
     */
    @FXML
    private void selectActionItem(ActionEvent event) {
        System.out.println("selectActionItem"+aActionItems.getValue().toString());
        NodeActionItem temp=adb.retrieveValue(aActionItems.getValue().toString());
        setValue(temp);    
    }
    
    /**
     * When an " update the action item" button is clicked this method is called.
     * It is used to update the action items.
     * @param event 
     */
    @FXML
    private void updateActionItem(ActionEvent event)    {
        //creatig node and getting values from fields
        String temp=validate();
        if(temp.equals("")) {
            NodeActionItem node= getValues();
            if(adb.check(node.name)){
                        adb.insert(node.name); //insert into  readers
                        adb.update(node);//update into action items
                        adb.delete(node.name,"readers");//deleting in readers
                        setValue(node);
                        actionItemsList();
                        clearThisForm(event);
                    }
                    else{
                        AlertBox.alert("","This action cannot be performed. \n Try again after few minutes...");
                    }
        }
        else    {
            AlertBox.alert("set the following fields",temp);
        }
    }
    
    /**
     * When an "Delete this action item" button is clicked this method is called.
     * It is used to delete the action items.
     * @param event 
     */
    @FXML
    private void deleteActionItem(ActionEvent event)    {
        boolean flag=AlertBox.deleteAlert();
        if (flag==true){
            if(ic.getInternetStatus()){
            if(adb.check(aActionItems.getValue().toString())){
                adb.insert(aActionItems.getValue().toString()); //insert into  readers
                adb.delete(aActionItems.getValue().toString());
                adb.delete(aActionItems.getValue().toString(),"readers");//deleting in readers
                actionItemsList();//to resets the action items list
                clearThisForm(event);// to clear the content of the form
            }
            else
                AlertBox.alert("","This action cannot be performed. \n Try again after few minutes...");
            }
            else
                AlertBox.alert("You are in offline mode","Action Item will be synchronized when you are onlilne");
        }
    }
    
    /**
     * When an "Create a new action item" button is clicked this method is called.
     * It is used to create a new action item.
     * @param event 
     */
    @FXML
    private void CreateActionItem(ActionEvent event)  {
        
        Boolean flag = false;
        Boolean flag2= false;
        
        String content = validate();
        flag=content.equals("")?false:true;
        System.out.println("flag value:"+flag);
        actionItemsList();
        flag2=checkName();
        if(flag==true || flag2==true) {
            if(!flag2){
                AlertBox.alert("set the following fields",content);
            }
            else{
                clearThisForm(event);
            }
        }
        else    {
            /*
            //creatig node and getting values from fields by calling getValues method.
            NodeActionItem node= getValues();
            // set values to those fields of action items by sending a NodeActionItem.
            setValue(node);
            // Inserting into data base
            adb.insert(node);
            //list of action tems should set
            actionItemsList();*/
            if(ic.getInternetStatus()){
                //creatig node and getting values from fields by calling getValues method.
                NodeActionItem node= getValues();
                    if(adb.check(node.name)){
                        adb.insert(node.name); //insert into  readers
                        adb.insert(node);//insert into action items
                        adb.delete(node.name,"readers");//deleting in readers
                        setValue(node);
                        actionItemsList();
                    }
                    else{
                        AlertBox.alert("","This action cannot be performed. \n Try again after few minutes...");
                    }
            }
            else {
                NodeActionItem node= getValues();
                if(adb.check(node.name)){
                    //adb.insert(node.name); //insert into  readers
                    //adb.insert(node,ic.getInternetStatus());//insert into action items
                    //adb.delete(node.name,"readers");//deleting in readers
                    //setValue(node);
                    //actionItemsList();
                    AlertBox.alert("You are in offline mode","Action Item will be synchronized when you are onlilne");
                }
                else{
                    AlertBox.alert("","This action cannot be performed. \n Try again after few minutes...");
                }
            }
            
        }
    }
    
    /**
     * This method is used to validate the fields.
     * @return 
     */
    private String validate(){
        String content="";
        if(aName.getText().equals(""))  {content += "Name"+"\n";}
        if(aDescription.getText().equals("")) {content += "Description"+"\n";}
        if(aResolution.getText().equals("")) {content += "Resolution"+"\n";}
        if(aDue.getText().equals("")) {content += "Due"+"\n";}
        else{
            Date current = new Date();
            Date due = new Date();
            System.out.println(current);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            try {
                due= formatter.parse(aDue.getText());//convert string to date
                System.out.println("due date:"+due);
                //check for whether due date is after the current date
                if(!current.before(due)){
                content+="Invalid Date"+"\n";
                }
            } catch(ParseException ex)   {
                //When date is not current e.g: day:31 month:nov 
                content+="Invalid Date"+"\n";  
            } 
        }
        return content;
    }
    
    /**
     * Checks whether action item name coincides.
     * If coincides tries to replace the action item name which is going to be created.
     * If it is not replaced, then returns a boolean value which is true.
     * Else the boolean value will be false. 
     * @return 
     */
    private boolean checkName(){
        boolean flag=false;
        if(actionName.contains(aName.getText())){
            String header="Action Item Name already Exit.\nDo you want to enter another Action Item Name\n";
            String content="Enter another Action Item Name here:\n";
            String name=AlertBox.nameAlert(header,content);
            if(!name.equals("")){
                aName.setText(name);
                checkName();
            }
            else{
                flag=true;
            }
        }
        return flag;
    }
    
    /**
     * It is used to set the values in the action items screen.
     * @param node 
     */
    private void setValue(NodeActionItem node){
        setTFlag = false;
        setMFlag = false;
        aActionItems.setValue(node.name);
        aActionItems.setPromptText(node.name);
        aCurrentDate.setText(node.creationDate);
        aDescription.setText(node.description);
        aResolution.setText(node.resolution);
        aName.setText(node.name);
        status.setValue(node.status);
        System.out.println("set Values "+node.assignedMember);
        membersList(true);
        aAssignedMember.setValue(node.assignedMember);
        teamsList(true);
        aAssignedTeam.setValue(node.assignedTeam);
        aCurrentDate.setText(node.creationDate);
        aDue.setText(node.dueDate);

    }
    
    /**
     * It is used to get the values from the action items screen.
     * @return 
     */
    private NodeActionItem getValues() {
        NodeActionItem node=new NodeActionItem();
        //setvalues to node
        node.description=aDescription.getText();
        node.resolution=aResolution.getText();
        node.name=aName.getText();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        node.creationDate=sdf.format(new Date());

        node.dueDate=aDue.getText();
        node.status=status.getValue().toString();
        node.assignedMember=aAssignedMember.getValue().toString();
        System.out.println("assigned member"+node.assignedMember);
        node.assignedTeam=aAssignedTeam.getValue().toString();
        System.out.println("assigned team"+node.assignedTeam);
        return node;
    }

    /**
     * It is used get all the action items from database and 
     * sets the list of action items.
     */
    public void actionItemsList(){
        System.out.println("action items list");
        aActionItems.getItems().clear();
        ArrayList<NodeActionItem> result = adb.retrieveValues();
        for(NodeActionItem temp:result) {
            System.out.println(temp.name);
            actionName.add(temp.name);
            aActionItems.getItems().add(temp.name);
        }
    }
    
    @FXML
    private void setTeamFromMembers(ActionEvent event){
        setTFlag = true;
        if(!setMFlag){
            String name = aAssignedMember.getValue().toString();
            if(name.equals("-no member selected-")) {teamsList(true);return;}
            ArrayList<String> teams = new dbpackage.MembersDb().getCurrentTeams(name);
            aAssignedTeam.getItems().clear();
            for(String x : teams) {
                aAssignedTeam.getItems().add(x);
            }
            aAssignedTeam.getItems().add(0,"-no team selected-");
            aAssignedTeam.setValue("-no team selected-");
            setMFlag = false;
        }
    }
    @FXML
    private void setMembersFromTeam(ActionEvent event){
        setMFlag = true;
        if(!setTFlag){
            String name = aAssignedTeam.getValue().toString();
            if(name.equals("-no team selected-")) {membersList(true);return;}
            ArrayList<String> members = new dbpackage.TeamsDb().getCurrentMembers(name);
            aAssignedMember.getItems().clear();
            for(String x : members) {
                aAssignedMember.getItems().add(x);
            }
            aAssignedMember.getItems().add(0,"-no member selected-");
            aAssignedMember.setValue("-no member selected-");
            setTFlag = false;
        }
    }
    public void membersList(boolean flag){
        aAssignedMember.getItems().clear();
        ArrayList<String> members = new dbpackage.MembersDb().retrieve();
        for(String x : members) {
            aAssignedMember.getItems().add(x);
        }
        aAssignedMember.getItems().add(0,"-no member selected-");
    }
    
    public void teamsList(boolean flag){
        aAssignedTeam.getItems().clear();
         ArrayList<String> teams = new dbpackage.TeamsDb().retrieve();
         for(String x : teams) {
            aAssignedTeam.getItems().add(x);
        }
        aAssignedTeam.getItems().add(0,"-no team selected-");        
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
       aActionItems.getItems().clear();
       aActionItems.getItems().addAll(actionN);
        
    }
    @FXML
    private void setConnectivity(){
        System.out.println("in the set connectivity");
        final long timeInterval = 1000;
        Runnable runnable;
        runnable = new Runnable() {
            public void run() {
                while (true) {
                    boolean status = ic.getInternetStatus();
                    System.out.println("status is"+status);
                    if(!status){
                        circle.setFill(Color.rgb(225, 31, 7));
                        update.setDisable(false);
                        clear.setDisable(false);
                        delete.setDisable(false);
                        create.setDisable(false);
                    }
                    else{
                        circle.setFill(Color.rgb(70, 255, 33));
                        update.setDisable(false);
                        clear.setDisable(false);
                        delete.setDisable(false);
                        create.setDisable(false);
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
    @FXML
    private void onChangeQuit(){
        String header="";
        //String content="A Quit has been requested where the content been updated \n Click "+"YES to save the changes? \n Click "+"NO to discard the changes?";
        String content="ARE YOU SURE TO QUIT THE APPLICATION \n Click "+"OK to Quit  \n Click "+"CANCEL to discard";
        AlertBox.alert("Quit",header,content);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setTFlag=false;
        setMFlag=false;
        sortingDirection.getItems().addAll(listSortingDirection);
        sortingDirection.setValue(listSortingDirection.get(1));
        firstSortingFactor.getItems().addAll(listSortingFactor);
        firstSortingFactor.setValue(listSortingFactor.get(0));
        secondSortingFactor.getItems().addAll(listSortingFactor);
        secondSortingFactor.setValue(listSortingFactor.get(0));
        inclusionFactor.getItems().addAll(listInclusionFactor);
        inclusionFactor.setValue(listInclusionFactor.get(0));
        status.getItems().addAll(listStatus);
        status.setValue(listStatus.get(0));
        membersList(true);
        aAssignedMember.setValue("-no member selected-");
        teamsList(true);
        aAssignedTeam.setValue("-no team selected-");
        adb=new dbpackage.ActionItemsDb();
        System.out.println("initilizer");
        actionItemsList();
        ic=new InternetConnectivity.InternetConnectivity();
        setConnectivity();
        
    }   
}
