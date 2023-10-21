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
    TableColumn EmployeeID = new TableColumn("ID");
    TableColumn EquipmentID = new TableColumn("ID");
    TableColumn MemberID = new TableColumn("ID");
    TableColumn MembershipID = new TableColumn("ID");
    TableColumn SupervisorID = new TableColumn("ID");

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException ex) {
            
            System.out.println(ex.toString());
        }
        //initializes columns for each table
        intializeEmployeeColumns();
        intializeEquipmentColumns();
        intializeMemberColumns();
        intializeMembershipColumns();
        intializeSupervisorColumns();
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

    //initializes columns for employee table
    private void intializeEmployeeColumns() {
        EmployeeID = new TableColumn("ID");
        EmployeeID.setMinWidth(50);
        EmployeeID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(450);
        first_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("First Name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("Last Name"));

        TableColumn hire_date = new TableColumn("Hire Date");
        hire_date.setMinWidth(100);
        hire_date.setCellValueFactory(new PropertyValueFactory<Employee, String>("Hire Date"));
        
        TableColumn members_representing = new TableColumn("Members Representing");
        members_representing.setMinWidth(100);
        members_representing.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("Members Representing"));
        
        TableColumn supervisor = new TableColumn("Supervisor");
        supervisor.setMinWidth(100);
        supervisor.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("Supervisor"));
        
        tableView.setItems(employeeData);
        tableView.getColumns().addAll(EmployeeID, first_name,last_name , hire_date, members_representing, supervisor);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    //initialize columns for equipment table
    private void intializeEquipmentColumns() {
        EquipmentID = new TableColumn("ID");
        EquipmentID.setMinWidth(50);
        EquipmentID.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("ID"));

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(100);
        cost.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("Cost"));

        TableColumn daily_usage = new TableColumn("Daily Usage");
        daily_usage.setMinWidth(100);
        daily_usage.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("Daily Usage"));

        TableColumn employee_owner = new TableColumn("Employee Owner");
        employee_owner.setMinWidth(450);
        employee_owner.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("Employee Owner"));
        
        tableView.setItems(equipmentData);
        tableView.getColumns().addAll(EquipmentID, cost, daily_usage, employee_owner);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    //initialiize columns for member table
    private void intializeMemberColumns() {
        MemberID = new TableColumn("ID");
        MemberID.setMinWidth(50);
        MemberID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(450);
        first_name.setCellValueFactory(new PropertyValueFactory<Members, String>("First Name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Members, String>("Last Name"));

        TableColumn join_date = new TableColumn("Join Date");
        join_date.setMinWidth(100);
        join_date.setCellValueFactory(new PropertyValueFactory<Members, String>("Join Date"));
        
        TableColumn last_visit = new TableColumn("Last Visit");
        last_visit.setMinWidth(100);
        last_visit.setCellValueFactory(new PropertyValueFactory<Members, String>("Last Visit"));
        
        TableColumn total_visits = new TableColumn("Last Visit");
        total_visits.setMinWidth(100);
        total_visits.setCellValueFactory(new PropertyValueFactory<Members, Integer>("Total Visits"));
        
        TableColumn membership_type = new TableColumn("Membership Type");
        membership_type.setMinWidth(100);
        membership_type.setCellValueFactory(new PropertyValueFactory<Members, Integer>("Membership Type"));
        
        TableColumn employee_sponsor = new TableColumn("Employee Sponsor");
        employee_sponsor.setMinWidth(100);
        employee_sponsor.setCellValueFactory(new PropertyValueFactory<Members, Integer>("Employee Sponsor"));
        
        tableView.setItems(membersData);
        tableView.getColumns().addAll(MemberID, first_name,last_name , join_date, last_visit, membership_type, employee_sponsor);

        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    //initialize membership columns
    private void intializeMembershipColumns() {
        MembershipID = new TableColumn("ID");
        MembershipID.setMinWidth(50);
        MembershipID.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("ID"));

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(100);
        cost.setCellValueFactory(new PropertyValueFactory<MembershipTypes, String>("Cost"));

        TableColumn duration = new TableColumn("Duration");
        duration.setMinWidth(100);
        duration.setCellValueFactory(new PropertyValueFactory<MembershipTypes, Integer>("Duration"));

        TableColumn type = new TableColumn("Type");
        type.setMinWidth(450);
        type.setCellValueFactory(new PropertyValueFactory<MembershipTypes, String>("Type"));
        
        tableView.setItems(membershipTypesData);
        tableView.getColumns().addAll(MembershipID, cost, duration, type);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    //initialize supervisor columns
    private void intializeSupervisorColumns() {
        SupervisorID = new TableColumn("ID");
        SupervisorID.setMinWidth(50);
        SupervisorID.setCellValueFactory(new PropertyValueFactory<Supervisor, Integer>("ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(450);
        first_name.setCellValueFactory(new PropertyValueFactory<Supervisor, String>("First Name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Supervisor, String>("Last Name"));
        
        tableView.setItems(supervisorData);
        tableView.getColumns().addAll(SupervisorID, first_name, last_name);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    //method for inserting employees into table
    public void insertEmployee(int employee_ID,String first_name, String last_name, 
            String hire_date, int members_representing, int supervisor) throws SQLException {
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO employee(employee_ID,first_name,last_name,hire_date,"
                    + "members_representing,supervisor) VALUES(?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employee_ID);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, hire_date);
            pstmt.setInt(5, members_representing);
            pstmt.setInt(6, supervisor);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("employee_ID " + employee_ID);
  
        employeeData.add(new Employee(employee_ID, first_name,last_name,hire_date, 
                members_representing, supervisor));
    }

    //method for inserting equipment
    public void insertEquipment(){
        
    }

    //method for inserting member
    public void insertMember(){
        
    }

    //method for inserting membership
    public void insertMembershipType(){
        
    }

    //method for inserting supervisor
    public void insertSupervisor(){
        
    }
    
    
}
