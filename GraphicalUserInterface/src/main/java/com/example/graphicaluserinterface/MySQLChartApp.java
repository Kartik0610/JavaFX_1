/*
   Kartik Pawar
   200556020
   2024-03-04

 */
package com.example.graphicaluserinterface;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.Node;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLChartApp extends Application {
    //This is for the different charts, 1 is for ODI >> 2 is for T20 >> 3 is for Test
    int dataNumber = 1;

    //Initialization of data which is static
    static String DB_URL = "jdbc:mysql://localhost:3306/cricket_stats";
    static String USER = "root";
    static String PASSWORD = "1234";


    //DATA List is being created here
    ObservableList<XYChart.Data<String, Number>> odiData = FXCollections.observableArrayList();
    ObservableList<XYChart.Data<String, Number>> t20Data = FXCollections.observableArrayList();
    //    ObservableList<PieChart.Data> t20Data = FXCollections.observableArrayList();
    ObservableList<XYChart.Data<String, Number>> testData = FXCollections.observableArrayList();

    //Start method to initialize the JavaFX application
    @Override
    public void start(Stage stage) throws Exception {
        // Connecting to MySQL database
        loadDataFromDatabase();

        // Icon is being setup over here
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cricket-icon.png")));

        // Charts are being created here
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Cricket Stats");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");
        yAxis.setTickUnit(1); // PROBLEM, trying to remove decimals but not happening

        // Customize chart style
        barChart.setStyle("-fx-background-color: #FFFDD0;");
        // Create a linear gradient from left to right
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.PURPLE), // Start color (pink)
                new Stop(1, Color.LIGHTBLUE)); // End color (light blue)

// Apply the gradient to the chart plot background
        barChart.lookup(".chart-plot-background").setStyle("-fx-background-color: linear-gradient(to right, purple, lightblue);");
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                node.setStyle("-fx-bar-fill: linear-gradient(to right, pink, lightblue);");
            }
        }
        // Add initial data to chart
        XYChart.Series<String, Number> odiSeries = new XYChart.Series<>();
        odiSeries.setName("ODI");
        odiSeries.setData(odiData);
        XYChart.Series<String, Number> t20Series = new XYChart.Series<>();
        t20Series.setName("T20");
        t20Series.setData(t20Data);
        XYChart.Series<String, Number> testSeries = new XYChart.Series<>();
        testSeries.setName("Test");
        testSeries.setData(testData);

        barChart.getData().add(odiSeries);

        // Buttons to switch between data types
        Button odiButton = createButton("ODI");
        Button t20Button = createButton("T20");
        Button testButton = createButton("Test");
        Button viewButt = createButton("View Table");
        viewButt.setStyle("-fx-text-fill: white;-fx-font: normal bold 15px 'serif';-fx-background-image: url('/button_background.jpeg');-fx-background-size: cover;-fx-min-width: 120px;-fx-min-height: 50px;-fx-padding: 29px 15px 15px 15px;");

        //Button event handling to change data on the click of button, also it is changing focus of button by changing their colors
        odiButton.setOnAction(event -> {
            if (dataNumber != 1) {
                changeChartDataWithAnimation(barChart, odiSeries);
                dataNumber = 1;
            }
        });

        t20Button.setOnAction(event -> {
            if (dataNumber != 2) {
                changeChartDataWithAnimation(barChart, t20Series);
                dataNumber = 2;
            }
        });

        testButton.setOnAction(event -> {
            if (dataNumber != 3) {
                changeChartDataWithAnimation(barChart, testSeries);
                dataNumber = 3;
            }
        });


        viewButt.setOnAction(event -> {
            CricketDataTable Data = new CricketDataTable();

               Stage DataStage = new Stage();
            try {
                Data.start(DataStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        HBox buttons = new HBox(10, odiButton, t20Button, testButton);
        buttons.setStyle("-fx-alignment: BOTTOM_CENTER;");


        StackPane root = new StackPane();
        root.getChildren().addAll(barChart, buttons, viewButt);


        StackPane.setAlignment(buttons, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setAlignment(viewButt, javafx.geometry.Pos.BOTTOM_RIGHT);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Cricket Stats Chart");
        stage.show();



    }


    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-text-fill: white;-fx-font: normal bold 20px 'serif';-fx-background-image: url('/button_background.jpeg');-fx-background-size: cover;-fx-min-width: 90px;-fx-min-height: 60px;-fx-padding: 5px 20px 5px 25px;");
        return button;
    }

    // Method to update chart data with animation
    private void changeChartDataWithAnimation(BarChart<String, Number> chart, XYChart.Series<String, Number> series) {
        chart.getData().clear();
        chart.getData().add(series);
    }


    // Method to load from database
    private void loadDataFromDatabase() throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "SELECT country, odi, t20, test FROM team_stats";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String country = rs.getString("country");
            int odi = rs.getInt("odi");
            int t20 = rs.getInt("t20");
            int test = rs.getInt("test");
            odiData.add(new XYChart.Data<>(country, odi));
            t20Data.add(new XYChart.Data<>(country, t20));
            testData.add(new XYChart.Data<>(country, test));
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    // Method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    public static void changeData(String[] args) {

    }
}