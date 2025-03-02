package com.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class testapp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML avec le chemin correct
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/views/signinForm.fxml"));
        
        // Configurer la scène et le stage
        primaryStage.setTitle( "réservaton app");
        primaryStage.setScene(new Scene(root, 780, 500));
        primaryStage.show();
         
    }

    public static void main(String[] args) {
        launch(args);
    }
}
