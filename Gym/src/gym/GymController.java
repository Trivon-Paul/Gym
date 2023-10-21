/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gym;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author trivv.pault
 */
public class GymController implements Initializable {
    @FXML
    private TableView tableView;

    @FXML
    private VBox vBox;

    @FXML
    private TextField first_NameTextField, last_NameTextField, hire_DateTextField, membersTextField;
    private TextField equipment_IDTextField, costTextField, daily_UsageTextField, employee_OwnerTextField;
    private TextField member_IDTextField, member_first_nameTextField, member_last_nameTextField,join_dateTextField,
                      last_visitTextField, membershipTextfield, employee_SponsorTextField;
    private TextField membership_TypeIDTextField, membership_CostTextField, duration_TextField, typeTextField;
    private TextField supervisor_ID, supervisor_first_nameTextField, supervisor_last_nameTextField;

    @FXML
    TableColumn id = new TableColumn("ID");

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException ex) {
            
            System.out.println(ex.toString());
        }
        intializeColumns();
        CreateSQLiteTable();
    }
    
    String databaseURL = "jdbc:sqlite:Gym_Database.db";
    
    private ObservableList<Employee> employeeData;
    private ObservableList<Equipment> equipmentData;
    private ObservableList<Members> membersData;
    private ObservableList<MembershipTypes> membershipTypesData;
    private ObservableList<Supervisor> supervisorData;
    
    public GymController() throws SQLException {
        this.employeeData = FXCollections.observableArrayList();
        this.equipmentData = FXCollections.observableArrayList();
        this.membersData = FXCollections.observableArrayList();
        this.membershipTypesData = FXCollections.observableArrayList();
        this.supervisorData = FXCollections.observableArrayList();
    }
    
    
}
