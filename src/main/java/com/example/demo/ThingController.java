package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.chart.LineChart;
import java.security.Key;

import javafx.util.Duration;

public class ThingController {
    @FXML
    private TextField velocity_init;
    @FXML
    private Button submitButton;
    private Timeline timeline;
    @FXML
    private TextField angle_init;
    @FXML
    private LineChart<Number, Number> trajectoryChart;
    @FXML
    private Label Height;
    @FXML
    private Label Length;
    @FXML
    private Label Time;

    @FXML
    private void onSubmit(ActionEvent event) {
        try {
            double velocity = Double.parseDouble(velocity_init.getText().trim().strip());
            double angle = Double.parseDouble(angle_init.getText().trim().strip());
            ProjectileMethods projectile = new ProjectileMethods(velocity, angle);
            double height = projectile.getHeight();
            double length = projectile.getLength();
            double time = projectile.getFlight_time();
            Height.setText(String.format("%.2f m", height));
            Length.setText(String.format("%.2f m", length));
            Time.setText(String.format("%.2f s", time));
            animateTrajectory(projectile);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error: Wrong info" + e);
            alert.showAndWait();
        }
    }
    private void animateTrajectory(ProjectileMethods projectile) {
        if (timeline != null) { // yes i used ai for this because i have no clue how to make these graphs
            timeline.stop();
        } // okay this just clears all previous trajectory data
        trajectoryChart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        trajectoryChart.getData().add(series); // add the line
        double maxDistance = projectile.getLength(); // self-explanatory
        int steps = 100;
        double dx = maxDistance / steps; // basically, dx is how much the line moves per step of the projectile because math
        final int[] currentStep = {0}; // lambda expressions like the one below only get a copy of the variable, not access the actual variable, which would fuck up memory so i needa do this weird ass trick
        timeline = new Timeline(new KeyFrame(Duration.millis(15), e-> {
            double x =  currentStep[0] * dx;
            double y = projectile.trajectoryFunction(x); // my trajectory function is finally used
            series.getData().add(new XYChart.Data<>(Double.valueOf(x), Double.valueOf(y)));
            currentStep[0]++; // understandable
            if (currentStep[0] > steps) {
                timeline.stop(); // can't have too much
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //private static int count = 0;
}
