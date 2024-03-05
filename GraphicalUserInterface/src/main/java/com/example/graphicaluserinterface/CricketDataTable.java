package com.example.graphicaluserinterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CricketDataTable extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create TableView to display cricket data
        TableView<CricketData> tableView = new TableView<>();

        // Create TableColumn for each property of CricketData
        TableColumn<CricketData, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());

        TableColumn<CricketData, Integer> odiColumn = new TableColumn<>("ODI");
        odiColumn.setCellValueFactory(cellData -> cellData.getValue().odiProperty().asObject());

        TableColumn<CricketData, Integer> t20Column = new TableColumn<>("T20");
        t20Column.setCellValueFactory(cellData -> cellData.getValue().t20Property().asObject());

        TableColumn<CricketData, Integer> testColumn = new TableColumn<>("Test");
        testColumn.setCellValueFactory(cellData -> cellData.getValue().testProperty().asObject());

        // Add columns to TableView
        tableView.getColumns().addAll(countryColumn, odiColumn, t20Column, testColumn);

        // Load data from the database
        loadDataFromDatabase(tableView);

        // Create scene and set it to the stage
        Scene scene1 = new Scene(new StackPane(tableView), 600, 400);
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Cricket Stats Table");
        primaryStage.show();

        Button button = new Button("Switch Scene");
        button.setOnAction(e -> primaryStage.setScene(scene1));

    }

    private void loadDataFromDatabase(TableView<CricketData> tableView) throws Exception {
        // Connect to the database
        Connection conn = DriverManager.getConnection(MySQLChartApp.DB_URL, MySQLChartApp.USER, MySQLChartApp.PASSWORD);
        Statement stmt = conn.createStatement();

        // Execute query to retrieve data
        String query = "SELECT country, odi, t20, test FROM team_stats";
        ResultSet rs = stmt.executeQuery(query);

        // Iterate through the result set and populate the TableView
        while (rs.next()) {
            String country = rs.getString("country");
            int odi = rs.getInt("odi");
            int t20 = rs.getInt("t20");
            int test = rs.getInt("test");
            CricketData cricketData = new CricketData(country, odi, t20, test);
            tableView.getItems().add(cricketData);
        }

        // Close resources
        rs.close();
        stmt.close();
        conn.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
