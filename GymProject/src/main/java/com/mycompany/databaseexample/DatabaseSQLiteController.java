package com.mycompany.databaseexample;


import java.text.NumberFormat;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //These Tableviews represent the differnt tables in the FXML
    //This is for Employees
    @FXML
    private TableView tableView = new TableView();

    //This is for Equipment
    @FXML
    private TableView tableView1 = new TableView();

    //This is for Members
    @FXML
    private TableView tableView2 = new TableView();

    //This is for Memberships
    @FXML
    private TableView tableView3 = new TableView();

    //This is for Supervisors
    @FXML
    private TableView tableView4 = new TableView();

    //Vbox in FXML
    @FXML
    private VBox vBox;

    //This is where the content will be displayed in the FXML
    @FXML
    VBox contentPane = new VBox();

    //These are the textfields for the different attributes these, basically set the values in the table
    @FXML
    private TextField employeeIDTextField, first_NameTextField, last_NameTextField, hire_DateTextField, membersTextField, supervisorTextField;
    @FXML
    private TextField equipment_IDTextField, costTextField, daily_UsageTextField, employee_OwnerTextField;
    @FXML
    private TextField member_IDTextField, member_first_nameTextField, member_last_nameTextField,join_dateTextField,
                      last_visitTextField, total_visitsTextField, membershipTextfield, employee_SponsorTextField;
    @FXML
    private TextField membership_TypeIDTextField, membership_CostTextField, duration_TextField, typeTextField;
    @FXML
    private TextField supervisor_ID, supervisor_first_nameTextField, supervisor_last_nameTextField;
    
    @FXML
    Label footerLabel;
    
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

    String databaseURL = "jdbc:mysql://localhost:3306/Gym_Database?" +
                                   "user=client&password=password";
    //Connect to a sample database
     
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
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSQLiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseSQLiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseSQLiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    //These initialize methods create the columns in the appropriate tables
    //initialize columns for employee data
    private void intializeEmployeeColumns() { 
        TableColumn<Employee, Integer> EmployeeID = new TableColumn("Employee ID");
        EmployeeID.setMinWidth(50);
        EmployeeID.setCellValueFactory(new PropertyValueFactory<>("employee_ID"));

        TableColumn<Employee, String> first_name = new TableColumn("First Name");
        first_name.setMinWidth(100);
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));

        TableColumn<Employee, String> last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));

        TableColumn<Employee, String> hire_date = new TableColumn("Hire Date");
        hire_date.setMinWidth(100);
        hire_date.setCellValueFactory(new PropertyValueFactory<>("hire_date"));
        
        TableColumn<Employee, Integer> members = new TableColumn("Members Representing");
        members.setMinWidth(150);
        members.setCellValueFactory(new PropertyValueFactory<>("members"));
        
        TableColumn<Employee, Integer> supervisor = new TableColumn("Supervisor");
        supervisor.setMinWidth(100);
        supervisor.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        
        tableView.setItems(employeeData);
        tableView.getColumns().addAll(EmployeeID, first_name, last_name, hire_date, members, supervisor);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    @FXML
    //initialize columns for equipment table
    private void intializeEquipmentColumns() {
        TableColumn EquipmentID = new TableColumn("Equipment ID");
        EquipmentID.setMinWidth(50);
         EquipmentID.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("equipment_ID"));

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(100);
        cost.setCellValueFactory(new PropertyValueFactory<Equipment, String>("cost"));

        TableColumn daily_usage = new TableColumn("Daily Usage");
        daily_usage.setMinWidth(100);
        daily_usage.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("daily_usage"));

        TableColumn employee_owner = new TableColumn("Employee Owner");
        employee_owner.setMinWidth(100);
        employee_owner.setCellValueFactory(new PropertyValueFactory<Equipment, Integer>("employee_owner"));
        
        tableView1.setItems(equipmentData);
        tableView1.getColumns().addAll(EquipmentID, cost, daily_usage, employee_owner);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    @FXML
    //initialiize columns for member table
    private void intializeMemberColumns() {
        TableColumn MemberID = new TableColumn("Member ID");
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
        membership_types.setCellValueFactory(new PropertyValueFactory<Members, Integer>("membership"));
        
        TableColumn employee_sponsor = new TableColumn("Employee Sponsor");
        employee_sponsor.setMinWidth(150);
        employee_sponsor.setCellValueFactory(new PropertyValueFactory<Members, Integer>("employee_sponsor"));
        
        tableView2.setItems(membersData);
        tableView2.getColumns().addAll(MemberID, first_name,last_name , join_date, last_visit,total_visits, membership_types, employee_sponsor);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
        
    @FXML
    //initialize membership columns
    private void intializeMembershipColumns() {
        TableColumn MembershipID = new TableColumn("Membership ID");
        MembershipID.setMinWidth(50);
        MembershipID.setCellValueFactory(new PropertyValueFactory<MembershipTypes, Integer>("membership_type_ID"));

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(100);
        cost.setCellValueFactory(new PropertyValueFactory<MembershipTypes, String>("cost"));

        TableColumn duration = new TableColumn("Duration");
        duration.setMinWidth(100);
        duration.setCellValueFactory(new PropertyValueFactory<MembershipTypes, Integer>("duration"));

        TableColumn type = new TableColumn("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(new PropertyValueFactory<MembershipTypes, String>("type"));
        
        tableView3.setItems(membershipTypesData);
        tableView3.getColumns().addAll(MembershipID, type, cost, duration);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }
    
    @FXML
    //initialize supervisor columns
    private void intializeSupervisorColumns() {
        TableColumn SupervisorID = new TableColumn("Supervisor ID");
        SupervisorID.setMinWidth(50);
        SupervisorID.setCellValueFactory(new PropertyValueFactory<Supervisor, Integer>("supervisor_ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(100);
        first_name.setCellValueFactory(new PropertyValueFactory<Supervisor, String>("first_name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Supervisor, String>("last_name"));
        
        tableView4.setItems(supervisorData);
        tableView4.getColumns().addAll(SupervisorID, first_name, last_name);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    //These load methods read data from the tables and loads it to the observable lists
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
            stmt.execute("SET TRANSACTION ISOLATION LEVEL READ COMMITTED;");
            stmt.execute("START TRANSACTION;");
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee employee;
                employee = new Employee(rs.getInt("Employee_ID"), rs.getString("First_Name"), 
                        rs.getString("Last_Name"),rs.getString("Hire_Date"), rs.getInt("Members_Representing"), 
                        rs.getInt("Supervisor"));
                System.out.println(employee.getEmployee_ID() + " - " + employee.getFirst_name() + " - " + 
                        employee.getLast_name() + " - " + employee.getHire_date()+ " - " + 
                        employee.getMembers()+ " - " + employee.getSupervisor());
                employeeData.add(employee);
            }

            stmt.execute("COMMIT;");
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
            stmt.execute("SET TRANSACTION ISOLATION LEVEL READ COMMITTED;");
            String sql = "SELECT * FROM Equipment;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            stmt.execute("START TRANSACTION;");
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Equipment equipment;
                equipment = new Equipment(rs.getInt("Equipment_ID"), rs.getString("Cost"), 
                        rs.getString("Daily_Usage"),rs.getInt("Employee_Owner"));
                System.out.println(equipment.getEquipment_ID()+ " - " + equipment.getCost() + " - " + 
                        equipment.getDaily_usage() + " - " + equipment.getEmployee_owner());
                equipmentData.add(equipment);
            }

            stmt.execute("COMMIT;");
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

            System.out.println("Connection to SQLite has been established. Members");
            stmt.execute("SET TRANSACTION ISOLATION LEVEL READ COMMITTED;");
            String sql = "SELECT * FROM Members;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            stmt.execute("START TRANSACTION;");
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Members member;
                member = new Members(rs.getInt("Member_ID"), rs.getString("First_Name"), 
                        rs.getString("Last_Name"),rs.getString("Join_Date"), rs.getString("Last_Visit"), 
                        rs.getInt("Total_Visits"), rs.getInt("Employee_Sponsor"), rs.getInt("Membership"));
                System.out.println(member.getMember_ID() + " - " + member.getFirst_name() + " - " + 
                        member.getLast_name() + " - " + member.getJoin_date()+ " - " + 
                        member.getLast_visit()+ " - " + member.getTotal_visits() +" - "+ 
                        member.getEmployee_sponsor()+" - "+ member.getMembership());
                membersData.add(member);
            }
            stmt.execute("COMMIT;");
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
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            stmt.execute("SET TRANSACTION ISOLATION LEVEL READ COMMITTED;");
            stmt.execute("START TRANSACTION;");
            String sql = "SELECT * FROM Membership_Types;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                MembershipTypes membership;
                membership = new MembershipTypes(rs.getInt("Membership_Type_ID"), rs.getString("Type"), 
                        rs.getString("Cost"),rs.getInt("Duration"));
                System.out.println(membership.getMembership_type_ID()+ " - " + membership.getType() + " - " + 
                        membership.getCost() + " - " + membership.getDuration());
                membershipTypesData.add(membership);
            }
            
            stmt.execute("COMMIT;");
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
            stmt.execute("SET TRANSACTION ISOLATION LEVEL READ COMMITTED;");
            String sql = "SELECT * FROM Supervisor;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            stmt.execute("START TRANSACTION;");
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Supervisor supervisor;
                supervisor = new Supervisor(rs.getInt("Supervisor_ID"), rs.getString("First_Name"), 
                        rs.getString("Last_Name"));
                System.out.println(supervisor.getSupervisor_ID()+ " - " + supervisor.getFirst_name() + " - " + 
                        supervisor.getLast_name());
                supervisorData.add(supervisor);
            }

            
            stmt.execute("COMMIT;");
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
    //These are the insertion methods for inserting into the tables
    public void insertEmployee(int employee_ID,String first_name, String last_name, 
            String hire_date, int members_representing, int supervisor) throws SQLException {
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Employees(employee_ID,first_name,last_name,hire_date,"
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
        System.out.println("Employee ID: " + employee_ID);
  
        employeeData.add(new Employee(employee_ID, first_name,last_name,hire_date, 
                members_representing, supervisor));
    }
    
    public void insertEquipment(int equipment_ID, String cost, String daily_usage, int employee_owner) throws SQLException{
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Equipment(equipment_ID,cost,daily_usage,employee_owner) VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, equipment_ID);
            pstmt.setString(2, cost);
            pstmt.setString(3, daily_usage);
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
        System.out.println("Equipment ID: " + equipment_ID);
  
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
        
    public void insertMembershipType(int membership_type_ID, String type, String cost, int duration)throws SQLException{
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
            pstmt.setString(3, cost);
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

            String sql = "INSERT INTO Supervisor(supervisor_ID,first_name,last_name) VALUES(?,?,?)";

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

    //Action methods to insert the tables 
    @FXML
    public void handleAddEmployee(ActionEvent actionEvent) {

        System.out.println("ID: "+ employeeIDTextField.getText()+"\nFirst Name: " + first_NameTextField.getText() + "\nLast Name: " + last_NameTextField.getText() + "\nHire Date: " + hire_DateTextField.getText() + "\nMembers: " + membersTextField.getText()+"\nSupervisor: "+supervisorTextField.getText());

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
        supervisorTextField.setText("");

        //footerLabel.setText("Record inserted into table successfully!");
    }
    
    @FXML
    public void handleAddEquipment(ActionEvent actionEvent) {

        System.out.println("ID: " + equipment_IDTextField.getText() + "\nCost: " + costTextField.getText() + "\nDaily Usage: " + daily_UsageTextField.getText() + "\nEmployee Owner: " + employee_OwnerTextField.getText());

        try {
            // insert a new rows
            insertEquipment(Integer.parseInt(equipment_IDTextField.getText()), costTextField.getText(), daily_UsageTextField.getText(), Integer.parseInt(employee_OwnerTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        equipment_IDTextField.setText("");
        costTextField.setText("");
        daily_UsageTextField.setText("");
        employee_OwnerTextField.setText("");

        //footerLabel.setText("Record inserted into table successfully!");
    }
    
    @FXML
    public void handleAddMember(ActionEvent actionEvent) {

        System.out.println("ID"+ member_IDTextField.getText()+"First Name: " + member_first_nameTextField.getText() + "\nLast Name: " + member_last_nameTextField.getText() + "\nJoin Date: " + join_dateTextField.getText() + "\nLast Visit: " + last_visitTextField.getText()+"Total Visits: "+total_visitsTextField.getText()+"Membership: "+membershipTextfield.getText()+"Employee Sponsors: "+employee_SponsorTextField.getText());
        
        
        try {
            // insert a new rows
            insertMember(Integer.parseInt(member_IDTextField.getText()), member_first_nameTextField.getText(), member_last_nameTextField.getText(), join_dateTextField.getText(), last_visitTextField.getText(),Integer.parseInt(total_visitsTextField.getText()),Integer.parseInt(employee_SponsorTextField.getText()),Integer.parseInt(membershipTextfield.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
        member_IDTextField.setText("");
        member_first_nameTextField.setText("");
        member_last_nameTextField.setText("");
        join_dateTextField.setText("");
        last_visitTextField.setText("");
        total_visitsTextField.setText("");
        membershipTextfield.setText("");
        employee_SponsorTextField.setText("");
        

        //footerLabel.setText("Record inserted into table successfully!");
    }
    
    @FXML
    public void handleAddMembershipType(ActionEvent actionEvent) {

        System.out.println("ID"+ membership_TypeIDTextField.getText()+"Cost: " + membership_CostTextField.getText() + "\nDaily Usage: " + duration_TextField.getText() + "EMployee Owner: " + typeTextField.getText());


        try {
            // insert a new rows
            insertMembershipType(Integer.parseInt(membership_TypeIDTextField.getText()), typeTextField.getText(), membership_CostTextField.getText(), Integer.parseInt(duration_TextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        membership_TypeIDTextField.setText("");
        typeTextField.setText("");
        membership_CostTextField.setText("");
        duration_TextField.setText("");

        //footerLabel.setText("Record inserted into table successfully!");
    }
    
    public void handleAddSupervisor(ActionEvent actionEvent) {

        System.out.println("ID"+ supervisor_ID.getText()+"First Name : " + supervisor_first_nameTextField.getText() + "\nLast Name: " + supervisor_last_nameTextField.getText());


        try {
            // insert a new rows
            insertSupervisor(Integer.parseInt(supervisor_ID.getText()), supervisor_first_nameTextField.getText(), supervisor_last_nameTextField.getText());

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        supervisor_ID.setText("");
        supervisor_first_nameTextField.setText("");
        supervisor_last_nameTextField.setText("");
        //footerLabel.setText("Record inserted into table successfully!");
    }

    
    public void deleteEmployee(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            String sql = "DELETE FROM Employees WHERE Employee_ID = " + Integer.toString(id);
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

            String sql = "DELETE FROM Members WHERE Member_ID=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView2.getItems().remove(selectedIndex);
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

            String sql = "DELETE FROM Equipment WHERE Equipment_ID=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView1.getItems().remove(selectedIndex);
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

            String sql = "DELETE FROM Supervisors WHERE Supervisor_ID=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView4.getItems().remove(selectedIndex);
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

            String sql = "DELETE FROM Membership_Types WHERE Membership_Type_ID=" + Integer.toString(id);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            tableView3.getItems().remove(selectedIndex);
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
    private void handleDeleteEmployee(ActionEvent event) throws IOException {
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
                System.out.println("Members: " + employee.getMembers());
                System.out.println("Supervisor: "+ employee.getSupervisor());
                deleteEmployee(employee.getEmployee_ID(), selectedIndex);
            }

        }
    }
    
    @FXML
    private void handleDeleteEquipment(ActionEvent event) throws IOException {
        System.out.println("Delete Equipment");
        //Check whether item is selected and set value of selected item to Label
        if (tableView1.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView1.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                Equipment equipment = (Equipment) tableView1.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + equipment.getEquipment_ID());
                System.out.println("Cost: " + equipment.getCost());
                System.out.println("Daily Usage: " + equipment.getDaily_usage());
                System.out.println("Employee Owner: " + equipment.getEmployee_owner());
                deleteEquipment(equipment.getEquipment_ID(), selectedIndex);
            }

        }
    }
    
    @FXML
    private void handleDeleteMember(ActionEvent event) throws IOException {
        System.out.println("Delete Member");
        //Check whether item is selected and set value of selected item to Label
        if (tableView2.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView2.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                Members member = (Members) tableView2.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + member.getMember_ID());
                System.out.println("First Name: " + member.getFirst_name());
                System.out.println("Last Name: " + member.getLast_name());
                System.out.println("Join Date: " + member.getJoin_date());
                System.out.println("Last Visit: " + member.getLast_visit());
                System.out.println("Total Visits: " + member.getTotal_visits());
                System.out.println("Membership : " + member.getMembership());
                System.out.println("Employee Sponsor: " + member.getEmployee_sponsor());
                deleteMember(member.getMember_ID(), selectedIndex);
            }

        }
    }
    
    @FXML
    private void handleDeleteMemberbershipTypes(ActionEvent event) throws IOException {
        System.out.println("Delete Membership");
        //Check whether item is selected and set value of selected item to Label
        if (tableView3.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView3.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                MembershipTypes membership = (MembershipTypes) tableView3.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + membership.getMembership_type_ID());
                System.out.println("First Name: " + membership.getType());
                System.out.println("Last Name: " + membership.getCost());
                System.out.println("Join Date: " + membership.getDuration());
                deleteMembershipType(membership.getMembership_type_ID(), selectedIndex);
            }

        }
    }
    
    @FXML
    private void handleDeleteSupervisor(ActionEvent event) throws IOException {
        System.out.println("Delete Membership");
        //Check whether item is selected and set value of selected item to Label
        if (tableView4.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView4.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                Supervisor supervisor = (Supervisor) tableView4.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + supervisor.getSupervisor_ID());
                System.out.println("First Name: " + supervisor.getFirst_name());
                System.out.println("Last Name: " + supervisor.getLast_name());
                deleteSupervisor(supervisor.getSupervisor_ID(), selectedIndex);
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
        System.out.println("Members: " + employee.getMembers());

        first_NameTextField.setText(employee.getFirst_name());
        last_NameTextField.setText(employee.getLast_name());
        hire_DateTextField.setText(employee.getHire_date());
        membersTextField.setText(Integer.toString(employee.getMembers()));
        supervisorTextField.setText(Integer.toString(employee.getSupervisor()));

        String content = "Id= " + employee.getEmployee_ID() + "\nfirst_name= " + employee.getFirst_name() + "\nlast_name= " + employee.getLast_name() + "\nHire Date= " + employee.getHire_date() + "\nmembers= " + employee.getMembers()+"\nSupervisor = "+employee.getSupervisor();

    }


    @SuppressWarnings("empty-statement")
    public ObservableList<Employee> search(int _employee_ID, String _first_name, String _last_name, String _hire_date, int _members, int _supervisor) throws SQLException {
        ObservableList<Employee> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        //CreateSQLiteTable();
        String sql = "Select * FROM Employees WHERE TRUE";
        if (!Integer.toString(_employee_ID).isEmpty()) {
            sql += " and Employee_ID like '%" + _employee_ID + "%'";
        }
        if (!_first_name.isEmpty()) {
            sql += " and First_Name like '%" + _first_name + "%'";
        }
        if (!_last_name.isEmpty()) {
            sql += " and Last_Name like '%" + _last_name + "%'";
        }

        if (!_hire_date.isEmpty()) {
            sql += " and Hire_Date like '%" + _hire_date + "%'";
        }
        
        if (!Integer.toString(_members).isEmpty()) {
            sql += " and Members_Representing like '%" + _members + "%'";
        }
        if (!Integer.toString(_supervisor).isEmpty()) {
            sql += " and Supervisor like '%" + _supervisor + "%'";
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

                    int recordId = rs.getInt("Employee_ID");
                    String first_name = rs.getString("First_Name");
                    String last_name = rs.getString("Last_Name");
                    String hire_date = rs.getString("Hire_Date");
                    int members = Integer.parseInt(rs.getString("Members_Representing"));
                    int supervisor = Integer.parseInt(rs.getString("Supervisor"));

                    Employee employee = new Employee(recordId, first_name,last_name, hire_date, members,supervisor);
                    searchResult.add(employee);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        

        return searchResult;
    }
    
    @SuppressWarnings("empty-statement")
    public ObservableList<Members> search(int _member_ID, String _first_name, String _last_name, String _join_date, String _last_visit, int _total_visits, int _membership_type, int _employee_sponsor ) throws SQLException {
        ObservableList<Members> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        //CreateSQLiteTable();
        String sql = "Select * FROM Members WHERE TRUE";
        if (!Integer.toString(_member_ID).isEmpty()) {
            sql += " and Member_ID like '%" + _member_ID + "%'";
        }
        if (!_first_name.isEmpty()) {
            sql += " and First_Name like '%" + _first_name + "%'";
        }
        if (!_last_name.isEmpty()) {
            sql += " and Last_Name like '%" + _last_name + "%'";
        }

        if (!_join_date.isEmpty()) {
            sql += " and Join_Date like '%" + _join_date + "%'";
        }
         if (!_last_visit.isEmpty()) {
            sql += " and Last_Visit like '%" + _last_visit + "%'";
        }
          if (!Integer.toString(_total_visits).isEmpty()) {
            sql += " and Total_Visits like '%" + _total_visits + "%'";
        }
          if (Integer.toString(_employee_sponsor).isEmpty()) {
            sql += " and Employee Sponsor like '%" + _employee_sponsor + "%'";
        }
         if (Integer.toString(_membership_type).isEmpty()) {
            sql += " and Membership like '%" + _membership_type + "%'";
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

                    int member_ID = rs.getInt("Member_ID");
                    String first_name = rs.getString("First_Name");
                    String last_name = rs.getString("Last_Name");
                    String join_date = rs.getString("Join_Date");
                    String last_visit = rs.getString("Last_Visit");
                    int total_visits = rs.getInt("Total_Visits");
                    int employee_sponsor = rs.getInt("Employee_Sponsor");
                    int supervisor = rs.getInt("Membership");

                    Members member = new Members(member_ID,first_name,last_name,join_date,last_visit,total_visits,employee_sponsor,supervisor);
                    searchResult.add(member);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        

        return searchResult;
    }
    
    @SuppressWarnings("empty-statement")
    public ObservableList<Equipment> search(int _equipment_ID, String _cost, String _daily_usage, int _employee_owner) throws SQLException {
        ObservableList<Equipment> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        //CreateSQLiteTable();
        String sql = "Select * FROM Equipment WHERE TRUE";
        if (!Integer.toString(_equipment_ID).isEmpty()) {
            sql += " and Equipment_ID like '%" + _equipment_ID + "%'";
        }
        if (!_cost.isEmpty()) {
            sql += " and Cost like '%" + _cost + "%'";
        }
        if (!_daily_usage.isEmpty()) {
            sql += " and Daily_Usage like '%" + _daily_usage + "%'";
        }

        if (!Integer.toString(_employee_owner).isEmpty()) {
            sql += " and Employee_Owner like '%" + _employee_owner + "%'";
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

                    int equipment_ID = rs.getInt("Equipment_ID");
                    String cost = rs.getString("Cost");
                    String daily_usage = rs.getString("Daily_Usage");
                    int employee_owner = rs.getInt("Employee_Owner");

                    Equipment equipment = new Equipment(equipment_ID, cost,daily_usage, employee_owner);
                    searchResult.add(equipment);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        

        return searchResult;
    }
    
    @SuppressWarnings("empty-statement")
    public ObservableList<MembershipTypes> search(int _membership_ID, String _type, int _duration, String _cost) throws SQLException {
        ObservableList<MembershipTypes> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        //CreateSQLiteTable();
        String sql = "Select * FROM Membership_Types WHERE TRUE";
        if (!Integer.toString(_membership_ID).isEmpty()) {
            sql += " and Membership_Type_ID like '%" + _membership_ID + "%'";
        }
        if (!_type.isEmpty()) {
            sql += " and Type like '%" + _type + "%'";
        }
        if (!_cost.isEmpty()) {
            sql += " and Cost like '%" + _cost + "%'";
        }

        if (!Integer.toString(_duration).isEmpty()) {
            sql += " and Duration like '%" + _duration + "%'";
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

                    int membership_ID = rs.getInt("Membership_Type_ID");
                    String type = rs.getString("Type");
                    String cost = rs.getString("Cost");
                    int duration = rs.getInt("Duration");

                    MembershipTypes membership = new MembershipTypes(membership_ID, type,cost, duration);
                    searchResult.add(membership);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        

        return searchResult;
    }
    
    public ObservableList<Supervisor> search(int _supervisor_ID, String _first_name, String _last_name) throws SQLException {
        ObservableList<Supervisor> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        //CreateSQLiteTable();
        String sql = "Select * FROM Supervisor WHERE TRUE";
        if (!Integer.toString(_supervisor_ID).isEmpty()) {
            sql += " and Supervisor_ID like '%" + _supervisor_ID + "%'";
        }
        if (!_first_name.isEmpty()) {
            sql += " and First_Name like '%" + _first_name + "%'";
        }
        if (!_last_name.isEmpty()) {
            sql += " and Last_Name like '%" + _last_name + "%'";
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

                    int supervisor_ID = rs.getInt("Supervisor_ID");
                    String first_name = rs.getString("First_Name");
                    String last_name = rs.getString("Last_Name");

                    Supervisor supervisor = new Supervisor(supervisor_ID, first_name,last_name);
                    searchResult.add(supervisor);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        

        return searchResult;
    }
    
    

    @FXML
    private void handleSearchEmployee(ActionEvent event) throws IOException, SQLException {
        int _employee_ID = Integer.parseInt(employeeIDTextField.getText().trim());
        String _first_name = first_NameTextField.getText().trim();
        String _last_name = last_NameTextField.getText().trim();
        String _hire_date = hire_DateTextField.getText().trim();
        int _members = Integer.parseInt(membersTextField.getText().trim());
        int _supervisor = Integer.parseInt(supervisorTextField.getText().trim());
        ObservableList<Employee> tableItems = search(_employee_ID, _first_name, _last_name, _hire_date, _members,_supervisor);
        tableView.setItems(tableItems);
    }
    
    @FXML
    private void handleSearchEquipment(ActionEvent event) throws IOException, SQLException {
        int _equipment_ID = Integer.parseInt(equipment_IDTextField.getText().trim());
        String _cost = costTextField.getText().trim();
        String _daily_usage = daily_UsageTextField.getText().trim();
        int _employee_owner = Integer.parseInt(employee_OwnerTextField.getText().trim());
        ObservableList<Equipment> tableItems = search(_equipment_ID, _cost, _daily_usage, _employee_owner);
        tableView1.setItems(tableItems);
    }
    
    @FXML
    private void handleSearchMember(ActionEvent event) throws IOException, SQLException {
        int _member_ID = Integer.parseInt(member_IDTextField.getText().trim());
        String _first_name = member_first_nameTextField.getText().trim();
        String _last_name = member_last_nameTextField.getText().trim();
        String _join_date = join_dateTextField.getText().trim();
        String _last_visit = last_visitTextField.getText().trim();
        int _total_visits = Integer.parseInt(total_visitsTextField.getText().trim());
        int _employee_sponsor = Integer.parseInt(employee_SponsorTextField.getText().trim());
        int _membership = Integer.parseInt(membershipTextfield.getText().trim());
        
        ObservableList<Members> tableItems = search(_member_ID, _first_name, _last_name, _join_date,_last_visit,_total_visits,_employee_sponsor,_membership);
        tableView2.setItems(tableItems);
    }
        
    @FXML
    private void handleSearchMembership(ActionEvent event) throws IOException, SQLException {
        int _membership_ID = Integer.parseInt(membership_TypeIDTextField.getText().trim());
        String _type = typeTextField.getText().trim();
        String _cost= membership_CostTextField.getText().trim();
        int _duration = Integer.parseInt(duration_TextField.getText().trim());
        ObservableList<MembershipTypes> tableItems = search(_membership_ID, _type, _duration, _cost);
        tableView3.setItems(tableItems);
    }
    @FXML
    private void handleSearchSupervisor(ActionEvent event) throws IOException, SQLException {
        int _supervisor_ID = Integer.parseInt(supervisor_ID.getText().trim());
        String _first_name = supervisor_first_nameTextField.getText().trim();
        String _last_name= supervisor_last_nameTextField.getText().trim();
        ObservableList<Supervisor> tableItems = search(_supervisor_ID, _first_name, _last_name);
        tableView4.setItems(tableItems);
    }

    @FXML
    private void handleShowAllEmployees(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(employeeData);
    }
    
    @FXML
    private void handleShowAllEquipment(ActionEvent event) throws IOException, SQLException {
        tableView1.setItems(equipmentData);
    }
    
    @FXML
    private void handleShowAllMembers(ActionEvent event) throws IOException, SQLException {
        tableView2.setItems(membersData);
    }
    
    @FXML
    private void handleShowAllMemberships(ActionEvent event) throws IOException, SQLException {
        tableView3.setItems(membershipTypesData);
    }
    
    @FXML
    private void handleShowAllSupervisors(ActionEvent event) throws IOException, SQLException {
        tableView4.setItems(supervisorData);
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
            String sql = "UPDATE Employees SET Employee_ID =?, First_Name = ?, Last_Name = ?, Hire_Date = ?,Members_Representing = ? ,Supervisor = ?";
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
    
    public void updateEquipment(int equipment_ID, String cost, String daily_usage, int employee_owner) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE Equipment SET Equipment_ID = ?, Cost = ?, Daily_Usage = ?, Employee_Owner = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, equipment_ID);
            pstmt.setString(2, cost);
            pstmt.setString(3, daily_usage);
            pstmt.setString(4, Integer.toString(employee_owner));


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
    
    public void updateMember(int member_ID,String first_name, String last_name, String join_date,String last_visit, int total_visits, int employee_sponsor, int membership) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE Members SET Member_ID = ?, First_Name = ?, Last_Name = ?, Join_Date = ?,Last_Visit = ? ,Total_Visits = ?, Employee_Sponsor = ?, Membership = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, member_ID);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, join_date);
            pstmt.setString(5, last_visit);
            pstmt.setString(6, Integer.toString(total_visits));
            pstmt.setString(7, Integer.toString(employee_sponsor));
            pstmt.setString(8, Integer.toString(membership));

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
    
    public void updateMembershipType(int membership_ID, String type, String cost, int duration) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE Membership_Types SET Membership_Type_ID = ?, Type = ?, Cost = ?, Duration = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, membership_ID);
            pstmt.setString(2, type);
            pstmt.setString(3, cost);
            pstmt.setString(4, Integer.toString(duration));


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
    
    public void updateSupervisor(int supervisor_ID, String first_name, String last_name) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE Supervisor SET Supervisor_ID = ?, First_Name = ?, Last_Name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, supervisor_ID);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);



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
                supervisorTextField.setText("");

                //footerLabel.setText("Record updated successfully!");
                employeeData.clear();
                loadEmployeeData();
                tableView.refresh();
            }

        }

    }
    
    @FXML
    private void handleUpdateEquipment(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView1.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView1.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                Equipment equipment = (Equipment) tableView1.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + equipment.getEquipment_ID());

                try {
                    // insert a new rows
                    updateEquipment(Integer.parseInt(equipment_IDTextField.getText()),costTextField.getText(), daily_UsageTextField.getText(), Integer.parseInt(employee_OwnerTextField.getText()));
                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                equipment_IDTextField.setText("");
                costTextField.setText("");
                daily_UsageTextField.setText("");
                employee_OwnerTextField.setText("");

                //footerLabel.setText("Record updated successfully!");
                equipmentData.clear();
                loadEquipmentData();
                tableView1.refresh();
            }

        }

    }
    
    @FXML
    private void handleUpdateMember(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView2.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView2.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                Members member = (Members) tableView2.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + member.getMember_ID());

                try {
                    // insert a new rows
                    updateMember(Integer.parseInt(member_IDTextField.getText()),member_first_nameTextField.getText(), member_last_nameTextField.getText(), join_dateTextField.getText(),last_visitTextField.getText(), Integer.parseInt(total_visitsTextField.getText()),Integer.parseInt(employee_SponsorTextField.getText()),Integer.parseInt(membershipTextfield.getText()));

                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                member_IDTextField.setText("");
                member_first_nameTextField.setText("");
                member_last_nameTextField.setText("");
                join_dateTextField.setText("");
                last_visitTextField.setText("");
                total_visitsTextField.setText("");
                membershipTextfield.setText("");
                employee_SponsorTextField.setText("");

                //footerLabel.setText("Record updated successfully!");
                membersData.clear();
                loadMembersData();
                tableView2.refresh();
            }

        }

    }
    
    @FXML
    private void handleUpdateMembership(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView3.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView3.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                MembershipTypes membership = (MembershipTypes) tableView3.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + membership.getMembership_type_ID());

                try {
                    // insert a new rows
                    updateMembershipType(Integer.parseInt(membership_TypeIDTextField.getText()),typeTextField.getText(), membership_CostTextField.getText(), Integer.parseInt(duration_TextField.getText()));
                    System.out.println("Record updated successfully!");
                    
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                membership_TypeIDTextField.setText("");
                membership_CostTextField.setText("");
                duration_TextField.setText("");
                typeTextField.setText("");

                //footerLabel.setText("Record updated successfully!");
                membershipTypesData.clear();
                loadMembershipTypesData();
                tableView3.refresh();
            }

        }

    }
    
    @FXML
    private void handleUpdateSupervisor(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView4.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView4.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                Supervisor supervisor = (Supervisor) tableView4.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + supervisor.getSupervisor_ID());

                try {
                    // insert a new rows
                    updateSupervisor(Integer.parseInt(supervisor_ID.getText()),supervisor_first_nameTextField.getText(), supervisor_last_nameTextField.getText());
                    System.out.println("Record updated successfully!");
                    
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                supervisor_ID.setText("");
                supervisor_first_nameTextField.setText("");
                supervisor_last_nameTextField.setText("");

                //footerLabel.setText("Record updated successfully!");
                supervisorData.clear();
                loadSupervisorData();
                tableView4.refresh();
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
                System.out.println("Members: " + employee.getMembers());
                deleteEmployee(employee.getEmployee_ID(), selectedIndex);
            }

        }
    }

   /* @FXML
    private void sidebarSearch() throws SQLException {
        String _first_name = first_NameTextField.getText().trim();
        String _last_name = last_NameTextField.getText().trim();
        String _hire_date = hire_DateTextField.getText().trim();
        String _members = membersTextField.getText().trim();
        ObservableList<Employee> tableItems = search(_first_name, _last_name, _hire_date, Integer.parseInt(_members));
        tableView.setItems(tableItems);
    }*/

    
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
