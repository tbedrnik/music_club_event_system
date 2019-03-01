package semestralka.aplikace;

import semestralka.dbs.entities.Person;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.*;

public class TableController implements Initializable {
    
    @FXML
    private TableView<Person> table_person;
    
    @FXML
    private TableColumn<Person, String> col_id;
    
    @FXML
    private TableColumn<Person, String> col_fn;
    
    @FXML
    private TableColumn<Person, String> col_ln;
    
    @FXML
    private TableColumn<Person, String> col_email;
    
    @FXML
    private TableColumn<Person, String> col_phone;
    
    @FXML 
    private Button btn_addPerson;
    
    @FXML
    private Button btn_close;
    
    private ObservableList<Person> oblist = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        PropertyValueFactory pvf_id = new PropertyValueFactory<>("id");
        PropertyValueFactory pvf_fn = new PropertyValueFactory<>("firstName");
        PropertyValueFactory pvf_ln = new PropertyValueFactory<>("lastName");
        PropertyValueFactory pvf_email = new PropertyValueFactory<>("email");
        PropertyValueFactory pvf_phone = new PropertyValueFactory<>("phone");

        col_id.setCellValueFactory(pvf_id);
        col_fn.setCellValueFactory(pvf_fn);
        col_ln.setCellValueFactory(pvf_ln);
        col_email.setCellValueFactory(pvf_email);
        col_phone.setCellValueFactory(pvf_phone);        

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("semestralka_aplikace_jar_1.0PU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Person> q1 = em.createQuery(
            "SELECT p FROM Person p",
            Person.class
        );

        List<Person> list = q1.getResultList();
        
        for(Person p : list) {
            oblist.add(p);
        }

        //End the connection
        em.close();
        emf.close();

        table_person.setItems(oblist);

    }    
    
    @FXML
    private void closeWindowAction() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void showAddPersonDialog() {
        try {
            FXMLLoader fl = new FXMLLoader();
            fl.setLocation(getClass().getResource("/fxml/AddPersonDialog.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add person to database");
            stage.setScene(new Scene((Parent) fl.load()));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
