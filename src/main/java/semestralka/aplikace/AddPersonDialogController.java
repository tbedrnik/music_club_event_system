package semestralka.aplikace;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tomas
 */
public class AddPersonDialogController implements Initializable {
    
    @FXML
    private TextField id;

    @FXML
    private TextField fn;
    
    @FXML
    private TextField ln;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField phone;
    
    @FXML
    private Button submit;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void addPersonToDB() {
//        try {
//            Connection conn = DBConnector.getConnection();
//            PreparedStatement ps = conn.prepareStatement("insert into person(id, firstName, lastName, email, phone) values(?, ?, ?, ?, ?)");
//            ps.setInt(1, Integer.valueOf(id.getText()));
//            ps.setString(2, fn.getText());
//            ps.setString(3, ln.getText());
//            ps.setString(4, email.getText());
//            ps.setString(5, phone.getText());
//            
//            
//            if(ps.executeUpdate()==1) {
//                Alert alert = new Alert(AlertType.INFORMATION);
//                alert.setTitle("Data saved");
//                alert.setHeaderText(null);
//                alert.setContentText("Person has been added to database.");
//                alert.showAndWait();
//                
//                
//            } else {
//                Alert alert = new Alert(AlertType.ERROR);
//                alert.setTitle("Data saved");
//                alert.setHeaderText(null);
//                alert.setContentText("Person couldn't be added to database.");
//
//                alert.showAndWait();
//            }
//            
//            
//        } catch (ClassNotFoundException | SQLException | NullPointerException ex) {
//            Logger.getLogger(AddPersonDialogController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
}
