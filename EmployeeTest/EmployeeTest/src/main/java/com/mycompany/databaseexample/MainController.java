package com.mycompany.databaseexample;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainController {

    @FXML
    VBox root;
    @FXML
    VBox contentPane;

    @FXML
    private void about() throws IOException {

        try {
            //contentScene.setRoot(fxmlLoader1.load());
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("about.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newLoadedPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void openFile() throws IOException {

        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("openFile.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newLoadedPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    
    
     @FXML
    private void Employee() throws IOException {

        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("DatabaseSQLite.fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newLoadedPane);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }   
    
    
    @FXML
    private void close() throws IOException {

        System.out.println("Quetting!!");
        Platform.exit();

    }
}
