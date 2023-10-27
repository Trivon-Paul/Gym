package com.mycompany.databaseexample;



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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class DatabaseSQLiteController implements Initializable {

    @FXML
    private TableView tableView = new TableView();
  
    @FXML
    private VBox vBox;
    
    @FXML
    VBox contentPane = new VBox();

    @FXML
    private TextField employeeIDTextField, first_NameTextField, last_NameTextField, hire_DateTextField, membersTextField, supervisorTextField;
    private TextField equipment_IDTextField, costTextField, daily_UsageTextField, employee_OwnerTextField;
    private TextField member_IDTextField, member_first_nameTextField, member_last_nameTextField,join_dateTextField,
                      last_visitTextField, membershipTextfield, employee_SponsorTextField;
    private TextField membership_TypeIDTextField, membership_CostTextField, duration_TextField, typeTextField;
    private TextField supervisor_ID, supervisor_first_nameTextField, supervisor_last_nameTextField;
    
    @FXML
    Label footerLabel;
    
    @FXML
    TableColumn EmployeeID = new TableColumn("ID");
    TableColumn EquipmentID = new TableColumn("ID");
    TableColumn MemberID = new TableColumn("ID");
    TableColumn MembershipID = new TableColumn("ID");
    TableColumn SupervisorID = new TableColumn("ID");

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try {
            loadEmployeeData();
            loadEquipmentData();
            loadMembersData();
            loadMembershipTypesData();
            loadSupervisorData();
        } catch (SQLException ex) {
            
            System.out.println(ex.toString());
        }
        intializeEmployeeColumns();
        intializeEquipmentColumns();
        intializeMemberColumns();
        intializeMembershipColumns();
        intializeSupervisorColumns();
        
    }

    String databaseURL = "jdbc:sqlite:src/main/resources/com/mycompany/databaseexample/Gym_Database1.db";

    /* Connect to a sample database
     */
    private ObservableList<Employee> employeeData;
    private ObservableList<Equipment> equipmentData;
    private ObservableList<Members> membersData;
    private ObservableList<MembershipTypes> membershipTypesData;
    private ObservableList<Supervisor> supervisorData;

    /*
       ArrayList: Resizable-array implementation of the List interface. 
       Implements all optional list operations, and permits all elements, including null.
       ObservableList: A list that allows listeners to track changes when they occur
    */
    
    
    public DatabaseSQLiteController() throws SQLException {
        this.employeeData = FXCollections.observableArrayList();
        this.equipmentData = FXCollections.observableArrayList();
        this.membersData = FXCollections.observableArrayList();
        this.membershipTypesData = FXCollections.observableArrayList();
        this.supervisorData = FXCollections.observableArrayList();
    }

    //initialize columns for employee data
    private void intializeEmployeeColumns() {
        
        EmployeeID = new TableColumn("Employee ID");
        EmployeeID.setMinWidth(50);
        EmployeeID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employee_ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(100);
        first_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("first_name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("last_name"));

        TableColumn hire_date = new TableColumn("Hire Date");
        hire_date.setMinWidth(100);
        hire_date.setCellValueFactory(new PropertyValueFactory<Employee, String>("hire_date"));
        
        TableColumn members_representing = new TableColumn("Members Representing");
        members_representing.setMinWidth(150);
        members_representing.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("members_representing"));
        
        TableColumn supervisor = new TableColumn("Supervisor");
        supervisor.setMinWidth(100);
        supervisor.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("supervisor"));
        
        tableView.setItems(employeeData);
        tableView.getColumns().addAll(EmployeeID, first_name, last_name, hire_date, members_representing, supervisor);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    //initialize columns for equipment table
    private void intializeEquipmentColumns() {
        EquipmentID = new TableColumn("Equipment ID");
        EquipmentID.setMinWidth(50);
        EquipmentID.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("equipment_ID"));

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(100);
        cost.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("cost"));

        TableColumn daily_usage = new TableColumn("Daily Usage");
        daily_usage.setMinWidth(100);
        daily_usage.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("daily_usage"));

        TableColumn employee_owner = new TableColumn("Employee Owner");
        employee_owner.setMinWidth(100);
        employee_owner.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("employee_owner"));
        
        tableView.setItems(equipmentData);
        tableView.getColumns().addAll(EquipmentID, cost, daily_usage, employee_owner);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    //initialiize columns for member table
    private void intializeMemberColumns() {
        MemberID = new TableColumn("Member ID");
        MemberID.setMinWidth(50);
        MemberID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("member_ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(100);
        first_name.setCellValueFactory(new PropertyValueFactory<Members, String>("first_name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Members, String>("last_name"));

        TableColumn join_date = new TableColumn("Join Date");
        join_date.setMinWidth(100);
        join_date.setCellValueFactory(new PropertyValueFactory<Members, String>("join_date"));
        
        TableColumn last_visit = new TableColumn("Last Visit");
        last_visit.setMinWidth(100);
        last_visit.setCellValueFactory(new PropertyValueFactory<Members, String>("last_visit"));
        
        TableColumn total_visits = new TableColumn("Total Visits");
        total_visits.setMinWidth(100);
        total_visits.setCellValueFactory(new PropertyValueFactory<Members, Integer>("total_visits"));
        
        TableColumn membership_types = new TableColumn("Membership Type");
        membership_types.setMinWidth(150);
        membership_types.setCellValueFactory(new PropertyValueFactory<Members, Integer>("membership_types"));
        
        TableColumn employee_sponsor = new TableColumn("Employee Sponsor");
        employee_sponsor.setMinWidth(150);
        employee_sponsor.setCellValueFactory(new PropertyValueFactory<Members, Integer>("employee_sponsor"));
        
        tableView.setItems(membersData);
        tableView.getColumns().addAll(MemberID, first_name,last_name , join_date, last_visit,total_visits, membership_types, employee_sponsor);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
        
    //initialize membership columns
    private void intializeMembershipColumns() {
        MembershipID = new TableColumn("Membership ID");
        MembershipID.setMinWidth(50);
        MembershipID.setCellValueFactory(new PropertyValueFactory<MembershipTypes, Integer>("membership_type_ID"));

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(100);
        cost.setCellValueFactory(new PropertyValueFactory<MembershipTypes, Integer>("cost"));

        TableColumn duration = new TableColumn("Duration");
        duration.setMinWidth(100);
        duration.setCellValueFactory(new PropertyValueFactory<MembershipTypes, Integer>("duration"));

        TableColumn type = new TableColumn("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(new PropertyValueFactory<MembershipTypes, String>("type"));
        
        tableView.setItems(membershipTypesData);
        tableView.getColumns().addAll(MembershipID, type, cost, duration);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    //initialize supervisor columns
    private void intializeSupervisorColumns() {
        SupervisorID = new TableColumn("Supervisor ID");
        SupervisorID.setMinWidth(50);
        SupervisorID.setCellValueFactory(new PropertyValueFactory<Supervisor, Integer>("supervisor_ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(100);
        first_name.setCellValueFactory(new PropertyValueFactory<Supervisor, String>("first_name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Supervisor, String>("last_name"));
        
        tableView.setItems(supervisorData);
        tableView.getColumns().addAll(SupervisorID, first_name, last_name);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    
    public void loadEmployeeData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            String sql = "SELECT * FROM Employees;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee employee;
                employee = new Employee(rs.getInt("Employee_ID"), rs.getString("First_Name"), 
                        rs.getString("Last_Name"),rs.getString("Hire_Date"), rs.getInt("Members_Representing"), 
                        rs.getInt("Supervisor"));
                System.out.println(employee.getEmployee_ID() + " - " + employee.getFirst_name() + " - " + 
                        employee.getLast_name() + " - " + employee.getHire_date()+ " - " + 
                        employee.getMembersRepresenting()+ " - " + employee.getSupervisor());
                employeeData.add(employee);
            }

            rs.close();
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
    }
    
    public void loadEquipmentData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            String sql = "SELECT * FROM Equipment;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Equipment equipment;
                equipment = new Equipment(rs.getInt("Equipment_ID"), rs.getInt("Cost"), 
                        rs.getInt("Daily_Usage"),rs.getInt("Employee_Owner"));
                System.out.println(equipment.getEquipment_ID()+ " - " + equipment.getCost() + " - " + 
                        equipment.getDaily_usage() + " - " + equipment.getEmployee_owner());
                equipmentData.add(equipment);
            }

            rs.close();
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
    }
    
    public void loadMembersData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            String sql = "SELECT * FROM Members;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Members member;
                member = new Members(rs.getInt("Member_ID"), rs.getString("First Name"), 
                        rs.getString("Last Name"),rs.getString("Join_Date"), rs.getString("Last_Visit"), 
                        rs.getInt("Total_Visits"), rs.getInt("Employee_Sponsor"), rs.getInt("Membership"));
                
                System.out.println(member.getMember_ID() + " - " + member.getFirst_name() + " - " + 
                        member.getLast_name() + " - " + member.getJoin_date()+ " - " + 
                        member.getLast_visit()+ " - " + member.getTotal_visits() +" - "+ 
                        member.getEmployee_sponsor()+" - "+ member.getMembership());
                membersData.add(member);
            }

            rs.close();
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
    }
    
    public void loadMembershipTypesData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            String sql = "SELECT * FROM Membership_Types;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                MembershipTypes membership;
                membership = new MembershipTypes(rs.getInt("Membership_Type_ID"), rs.getString("Type"), 
                        rs.getInt("Cost"),rs.getInt("Duration"));
                System.out.println(membership.getMembership_type_ID()+ " - " + membership.getType() + " - " + 
                        membership.getCost() + " - " + membership.getDuration());
                membershipTypesData.add(membership);
            }

            rs.close();
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
    }
    
    public void loadSupervisorData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            String sql = "SELECT * FROM Supervisor;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Supervisor supervisor;
                supervisor = new Supervisor(rs.getInt("Supervisor_ID"), rs.getString("First_Name"), 
                        rs.getString("Last_Name"));
                System.out.println(supervisor.getSupervisor_ID()+ " - " + supervisor.getFirst_name() + " - " + 
                        supervisor.getLast_name());
                supervisorData.add(supervisor);
            }

            rs.close();
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
    }
    
    

    public void drawText() {
        //Drawing a text 
        Text text = new Text("The GYM Database");

        //Setting the font of the text 
        text.setFont(Font.font("Edwardian Script ITC", 55));

        //Setting the position of the text 
//        text.setX(600);
//        text.setY(100);
        //Setting the linear gradient 
        Stop[] stops = new Stop[]{
            new Stop(0, Color.DARKSLATEBLUE),
            new Stop(1, Color.RED)
        };
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        //Setting the linear gradient to the text 
        text.setFill(linearGradient);
        // Add the child to the grid
        vBox.getChildren().add(text);

    }

    /**
     * Insert a new row into the Employee table
     * @param employee_ID
     * @param first_name     
     * @param last_name     
     * @param hire_date     
     * @param members_representing     
     * @param supervisor     
     * @throws java.sql.SQLException
     */
    
    public void insertEmployee(int employee_ID,String first_name, String last_name, 
            String hire_date, int members_representing, int supervisor) throws SQLException {
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Employee(employee_ID,first_name,last_name,hire_date,"
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
    
    public void insertEquipment(int equipment_ID, int cost, int daily_usage, int employee_owner) throws SQLException{
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Equipment(equipment_ID,cost,daily_usage,employee_owner,) VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, equipment_ID);
            pstmt.setInt(2, cost);
            pstmt.setInt(3, daily_usage);
            pstmt.setInt(4, employee_owner);
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
        System.out.println("equipment_ID " + equipment_ID);
  
        equipmentData.add(new Equipment(equipment_ID, cost,daily_usage,employee_owner));
        }
    }
    
    public void insertMember(int member_ID, String first_name, String last_name, String join_date, String last_visit, 
            int total_visits, int employee_sponsor, int membership) throws SQLException{
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Members(member_ID,first_name,last_name,join_date,last_visit,"
                    + "total_visits,employee_sponsor, membership) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, member_ID);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, join_date);
            pstmt.setString(5, last_visit);
            pstmt.setInt(6, total_visits);
            pstmt.setInt(7, employee_sponsor);
            pstmt.setInt(8, membership);
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
        System.out.println("member_ID " + member_ID);
        membersData.add(new Members(member_ID, first_name,last_name,join_date, last_visit, total_visits, 
                employee_sponsor, membership));
    }
    }
        
    public void insertMembershipType(int membership_type_ID, String type, int cost, int duration)throws SQLException{
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Membership_Types(memberhip_type_ID,type,cost,duration,) VALUES(?,?,?,?,)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, membership_type_ID);
            pstmt.setString(2, type);
            pstmt.setInt(3, cost);
            pstmt.setInt(4, duration);
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
        System.out.println("membership_type_ID " + membership_type_ID);
        membershipTypesData.add(new MembershipTypes(membership_type_ID, type,cost,duration));
    }
    }
    
    public void insertSupervisor(int supervisor_ID, String first_name, String last_name)throws SQLException{
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Supervisor(supervsor_ID,first_name,last_name) VALUES(?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, supervisor_ID);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
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
        System.out.println("supervisor_ID " + supervisor_ID);
        supervisorData.add(new Supervisor(supervisor_ID, first_name,last_name));
    }
    }

    @FXML
    public void handleAddEmployee(ActionEvent actionEvent) {

        System.out.println("First Name: " + first_NameTextField.getText() + "\nLast Name: " + last_NameTextField.getText() + "\nHire Date: " + hire_DateTextField.getText() + "\nMembers: " + membersTextField.getText());

        try {
            // insert a new rows
            insertEmployee(Integer.parseInt(employeeIDTextField.getText()), first_NameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()),Integer.parseInt(supervisorTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        first_NameTextField.setText("");
        last_NameTextField.setText("");
        hire_DateTextField.setText("");
        membersTextField.setText("");

        footerLabel.setText("Record inserted into table successfully!");
    }

    

    public void deleteRecord(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM employee WHERE id=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void deleteEmployee(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM employees WHERE employee_id=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void deleteMember(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM Members WHERE member_id=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void deleteEquipment(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM equipment WHERE equipment_id=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void deleteSupervisor(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM supervisors WHERE supervisor_id=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void deleteMembershipType(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM membership_type WHERE membership_type_id=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    private void handleDeleteAction(ActionEvent event) throws IOException {
        System.out.println("Delete Employee");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                Employee employee = (Employee) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + employee.getEmployee_ID());
                System.out.println("First Name: " + employee.getFirst_name());
                System.out.println("Last Name: " + employee.getLast_name());
                System.out.println("Hire Date: " + employee.getEmployee_ID());
                System.out.println("Members: " + employee.getEmployee_ID());
                deleteRecord(employee.getEmployee_ID(), selectedIndex);
            }

        }
    }

    Integer index = -1;

    @FXML
    private void showRowData() {

        index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        System.out.println("showRowData");
        System.out.println(index);
        Employee employee = (Employee) tableView.getSelectionModel().getSelectedItem();
        System.out.println("ID: " + employee.getEmployee_ID());
        System.out.println("First Name: " + employee.getFirst_name());
        System.out.println("Last Name: " + employee.getLast_name());
        System.out.println("Hire Date: " + employee.getEmployee_ID());
        System.out.println("Members: " + employee.getMembersRepresenting());

        first_NameTextField.setText(employee.getFirst_name());
        last_NameTextField.setText(employee.getLast_name());
        hire_DateTextField.setText(employee.getHire_date());
        membersTextField.setText(Integer.toString(employee.getMembersRepresenting()));
        supervisorTextField.setText(Integer.toString(employee.getSupervisor()));

        String content = "Id= " + employee.getEmployee_ID() + "\nfirst_name= " + employee.getFirst_name() + "\nlast_name= " + employee.getLast_name() + "\nHire Date= " + employee.getHire_date() + "\nmembers= " + employee.getMembersRepresenting()+"\nSupervisor = "+employee.getSupervisor();

    }


    @SuppressWarnings("empty-statement")
    public ObservableList<Employee> search(String _first_name, String _last_name, String _hire_date, int _members) throws SQLException {
        ObservableList<Employee> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        //CreateSQLiteTable();
        String sql = "Select * from employee where true";

        if (!_first_name.isEmpty()) {
            sql += " and first_name like '%" + _first_name + "%'";
        }
        if (!_last_name.isEmpty()) {
            sql += " and last_name ='" + _last_name + "'";
        }

        if (!_hire_date.isEmpty()) {
            sql += " and hire_date like '%" + _hire_date + "%'";
        }
        
        if (!Integer.toString(_members).isEmpty()) {
            sql += " and members like '%" + _members + "%'";
        }

        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(databaseURL);
                Statement stmt = conn.createStatement()) {
            // create a ResultSet

            ResultSet rs = stmt.executeQuery(sql);
            // checking if ResultSet is empty
            if (rs.next() == false) {
                System.out.println("ResultSet in empty");
            } else {
                // loop through the result set
                do {

                    int recordId = rs.getInt("id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");
                    String hire_date = rs.getString("hire_date");
                    int members = Integer.parseInt(rs.getString("members_representing"));
                    int supervisor = Integer.parseInt(rs.getString("supervisor"));

                    Employee employee = new Employee(recordId, first_name,last_name, hire_date, members,supervisor);
                    searchResult.add(employee);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchResult;
    }

    @FXML
    private void handleSearchAction(ActionEvent event) throws IOException, SQLException {
        String _first_name = first_NameTextField.getText().trim();
        String _last_name = last_NameTextField.getText().trim();
        String _hire_date = hire_DateTextField.getText().trim();
        String _members = membersTextField.getText().trim();
        ObservableList<Employee> tableItems = search(_first_name, _last_name, _hire_date, Integer.parseInt(_members));
        tableView.setItems(tableItems);

    }

    @FXML
    private void handleShowAllEmployee(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(employeeData);
    }
    
    @FXML
    private void handleShowAllEquipment(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(equipmentData);
    }
    
    @FXML
    private void handleShowAllMembers(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(membersData);
    }
    
    @FXML
    private void handleShowAllMemberships(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(membershipTypesData);
    }
    
    @FXML
    private void handleShowAllSupervisors(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(supervisorData);
    }

    /**
     * Update a record in the movies table
     *
     * @param employee_ID
     * @param first_name
     * @param last_name
     * @param hire_date
     * @param members_representing
     * @param supervisor
     * @throws java.sql.SQLException
     */
    public void updateEmployee(int employee_ID,String first_name, String last_name, String hire_date,int members_representing, int supervisor) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE employee SET employee_ID =?, first_name = ?, last_name =?, hire_date =?,members_representing =? ,supervisor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employee_ID);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, hire_date);
            pstmt.setString(5, Integer.toString(members_representing));
            pstmt.setString(6, Integer.toString(supervisor));

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
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

    }

    @FXML
    private void handleUpdateEmployee(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                Employee employee = (Employee) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + employee.getEmployee_ID());

                try {
                    // insert a new rows
                    updateEmployee(Integer.parseInt(employeeIDTextField.getText()),first_NameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()), Integer.parseInt(supervisorTextField.getText()));

                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                first_NameTextField.setText("");
                last_NameTextField.setText("");
                hire_DateTextField.setText("");
                membersTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                employeeData.clear();
                loadEmployeeData();
                tableView.refresh();
            }

        }

    }

    @FXML
    private void sidebarShowAllEmployees() {
        tableView.setItems(employeeData);
    }

    @FXML
    private void sidebarAddNewRecord() {
        System.out.println("First Name: " + first_NameTextField.getText() + "\nLast Name: " + last_NameTextField.getText() + "\nHire Date: " + hire_DateTextField.getText() + "\nMembers: " + membersTextField.getText());

        try {
            // insert a new rows
            insertEmployee(Integer.parseInt(employeeIDTextField.getText()),first_NameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()),Integer.parseInt(supervisorTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

            first_NameTextField.setText("");
            last_NameTextField.setText("");
            hire_DateTextField.setText("");
            membersTextField.setText("");

        footerLabel.setText("Record inserted into table successfully!");

    }

    @FXML
    private void sidebarDeleteRecord() {
        System.out.println("Delete Employee");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                Employee employee = (Employee) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + employee.getEmployee_ID());
                System.out.println("First Name: " + employee.getFirst_name());
                System.out.println("Last Name: " + employee.getLast_name());
                System.out.println("Hire Date: " + employee.getHire_date());
                System.out.println("Members: " + employee.getMembersRepresenting());
                deleteRecord(employee.getEmployee_ID(), selectedIndex);
            }

        }
    }

    @FXML
    private void sidebarSearch() throws SQLException {
        String _first_name = first_NameTextField.getText().trim();
        String _last_name = last_NameTextField.getText().trim();
        String _hire_date = hire_DateTextField.getText().trim();
        String _members = membersTextField.getText().trim();
        ObservableList<Employee> tableItems = search(_first_name, _last_name, _hire_date, Integer.parseInt(_members));
        tableView.setItems(tableItems);
    }

    
     @FXML
    private void sidebarUpdateRecord() throws SQLException {
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                Employee employee = (Employee) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + employee.getEmployee_ID());

                try {
                    // insert a new rows
                    updateEmployee(Integer.parseInt(employeeIDTextField.getText()),first_NameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()), Integer.parseInt(supervisorTextField.getText()));

                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                first_NameTextField.setText("");
                last_NameTextField.setText("");
                hire_DateTextField.setText("");
                membersTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                employeeData.clear();
                loadEmployeeData();
                tableView.refresh();
            }
        }
    }   
        
}
