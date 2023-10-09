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


public class DatabaseSQLiteController implements Initializable {

    @FXML
    private TableView tableView;

    @FXML
    private VBox vBox;

    @FXML
    private TextField first_nameTextField, last_NameTextField, hire_DateTextField, membersTextField;

    @FXML
    Label footerLabel;
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

    String databaseURL = "jdbc:sqlite:src/main/resources/com/mycompany/databaseexample/employee.db";

    /* Connect to a sample database
     */
    private ObservableList<Employee> data;

    /*
       ArrayList: Resizable-array implementation of the List interface. 
       Implements all optional list operations, and permits all elements, including null.
       ObservableList: A list that allows listeners to track changes when they occur
    */
    
    
    public DatabaseSQLiteController() throws SQLException {
        this.data = FXCollections.observableArrayList();
    }

    private void intializeColumns() {
        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("ID"));

        TableColumn first_name = new TableColumn("First Name");
        first_name.setMinWidth(450);
        first_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("First Name"));

        TableColumn last_name = new TableColumn("Last Name");
        last_name.setMinWidth(100);
        last_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("Last Name"));

        TableColumn hire_date = new TableColumn("Hire Date");
        hire_date.setMinWidth(100);
        hire_date.setCellValueFactory(new PropertyValueFactory<Employee, String>("Hire Date"));
        
        TableColumn members = new TableColumn("Members");
        members.setMinWidth(100);
        members.setCellValueFactory(new PropertyValueFactory<Employee, String>("Members"));
        
        tableView.setItems(data);
        tableView.getColumns().addAll(id, first_name,last_name , hire_date);

        
        //tableView.setOpacity(0.3);
        /* Allow for the values in each cell to be changable */
    }

    public void loadData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");
            String sql = "SELECT * FROM employee;";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee employee;
                employee = new Employee(rs.getInt("employee_ID"), rs.getString("first_name"), rs.getString("last_name"),rs.getString("hireDate"), rs.getInt("members"));
                System.out.println(employee.getEmployee_ID() + " - " + employee.getFirst_name() + " - " + employee.getLast_name() + " - " + employee.getHire_date());
                data.add(employee);
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
        Text text = new Text("The Employee Database");

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
     * Insert a new row into the movies table
     * @param first_name     
     * @param last_name     
     * @param hire_date     
     * @param members     
     * @throws java.sql.SQLException
     */
    public void insert(String first_name, String last_name, String hire_date, int members) throws SQLException {
        int last_inserted_id = 0;
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO employee(first_name,last_name,hire_date,members) VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, hire_date);
            pstmt.setInt(4, members);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
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
        System.out.println("last_inserted_id " + last_inserted_id);
  
        data.add(new Employee(last_inserted_id, first_name,last_name,hire_date, members));
    }

    @FXML
    public void handleAddMovie(ActionEvent actionEvent) {

        System.out.println("First Name: " + first_nameTextField.getText() + "\nLast Name: " + last_NameTextField.getText() + "\nHire Date: " + hire_DateTextField.getText() + "\nMembers: " + membersTextField.getText());

        try {
            // insert a new rows
            insert(first_nameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        first_nameTextField.setText("");
        last_NameTextField.setText("");
        hire_DateTextField.setText("");
        membersTextField.setText("");

        footerLabel.setText("Record inserted into table successfully!");
    }

    private void CreateSQLiteTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS employee (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	first_name text NOT NULL,\n"
                + "	last_name text NOT NULL,\n"
                + "	hire_date text NOT NULL\n"
                + "     members integer NOT NULL, \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(databaseURL);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            /*==================================================================================
            inset("Allison", "Brown", "08/29/2023", 5);

==================================================================================*/
            System.out.println("Table Created Successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        System.out.println("Members: " + employee.getEmployee_ID());

        first_nameTextField.setText(employee.getFirst_name());
        last_NameTextField.setText(employee.getLast_name());
        hire_DateTextField.setText(employee.getHire_date());
        membersTextField.setText(Integer.toString(employee.getMembers()));
        membersTextField.setText(Integer.toString(employee.getMembers()));

        String content = "Id= " + employee.getEmployee_ID() + "\nfirst_name= " + employee.getFirst_name() + "\nlast_name= " + employee.getLast_name() + "\nHire Date= " + employee.getHire_date() + "\nmembers= " + employee.getMembers();

    }


    @SuppressWarnings("empty-statement")
    public ObservableList<Employee> search(String _first_name, String _last_name, String _hire_date, int _members) throws SQLException {
        ObservableList<Employee> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        CreateSQLiteTable();
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
                    int members = Integer.parseInt(rs.getString("members"));

                    Employee employee = new Employee(recordId, first_name,last_name, hire_date, members);
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
        String _first_name = first_nameTextField.getText().trim();
        String _last_name = last_NameTextField.getText().trim();
        String _hire_date = hire_DateTextField.getText().trim();
        String _members = membersTextField.getText().trim();
        ObservableList<Employee> tableItems = search(_first_name, _last_name, _hire_date, Integer.parseInt(_members));
        tableView.setItems(tableItems);

    }

    @FXML
    private void handleShowAllRecords(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(data);

    }

    /**
     * Update a record in the movies table
     *
     * @param first_name
     * @param last_name
     * @param hire_date
     * @param members
     * @param selectedIndex
     * @param id
     * @throws java.sql.SQLException
     */
    public void update(String first_name, String last_name, String hire_date,int members, int selectedIndex, int id) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL);
            String sql = "UPDATE employee SET first_name = ?, last_name =?, hire_date =?,members =? , Where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, hire_date);;
            pstmt.setString(4, Integer.toString(members));
            pstmt.setString(4, Integer.toString(id));

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
    private void handleUpdateRecord(ActionEvent event) throws IOException, SQLException {

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
                    update(first_nameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()), selectedIndex, employee.getEmployee_ID());

                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                first_nameTextField.setText("");
                last_NameTextField.setText("");
                hire_DateTextField.setText("");
                membersTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                data.clear();
                loadData();
                tableView.refresh();
            }

        }

    }

    @FXML
    private void sidebarShowAllRecords() {
        tableView.setItems(data);
    }

    @FXML
    private void sidebarAddNewRecord() {
        System.out.println("First Name: " + first_nameTextField.getText() + "\nLast Name: " + last_NameTextField.getText() + "\nHire Date: " + hire_DateTextField.getText() + "\nMembers: " + membersTextField.getText());

        try {
            // insert a new rows
            insert(first_nameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

            first_nameTextField.setText("");
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
                deleteRecord(employee.getEmployee_ID(), selectedIndex);
            }

        }
    }

    @FXML
    private void sidebarSearch() throws SQLException {
        String _first_name = first_nameTextField.getText().trim();
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
                    update(first_nameTextField.getText(), last_NameTextField.getText(), hire_DateTextField.getText(), Integer.parseInt(membersTextField.getText()) ,selectedIndex, employee.getEmployee_ID());

                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                first_nameTextField.setText("");
                last_NameTextField.setText("");
                hire_DateTextField.setText("");
                membersTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                data.clear();
                loadData();
                tableView.refresh();
            }
        }
    }   
    
}
