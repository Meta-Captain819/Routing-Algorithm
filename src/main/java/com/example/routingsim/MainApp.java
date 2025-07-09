package com.example.routingsim;
// JavaFx imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point of the AODV Routing Simulator application.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the UI layout from the MainView.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));

        // Create the main scene using the loaded layout
        Scene scene = new Scene(loader.load());

        // Set the window title
        stage.setTitle("AODV Routing Simulator");

        // Attach the scene to the stage and show it
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}
