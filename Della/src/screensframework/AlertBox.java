/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensframework;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author SreePurna
 */
public class AlertBox {
    
    /**
     *
     * @param title
     * @param message
     */
    public static void alert(String Header,String content)   {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(Header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void alert(String Title,String Header,String Content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(1);
        }
        
    }
    public static boolean deleteAlert()    {
        boolean decision=true;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Current Action Item");
        alert.setHeaderText("Do you want to delete the current action item ");
        alert.setContentText("Click \"OK\" if you are sure \nClick \"Cancel\" to retain it");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            decision=true;
        } else {
            decision=false;
        }
        return decision;
    }
    public static String nameAlert(String header,String content) {
        String flag="";
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Error");
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your Action Item name: " + result.get());
            flag=result.get();
        }
        return flag;
    }

    
}
