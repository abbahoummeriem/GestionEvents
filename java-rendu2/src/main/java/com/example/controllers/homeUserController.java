package com.example.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class homeUserController{
    @FXML
    private Label bienvenueLabel;
    @FXML
    private void hostorique(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/historique.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("historique");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
        @FXML
    private void onReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/reservation.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Gestion des terrains");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    @FXML
    private void onlogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/signinForm.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/homeUser.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    @FXML
    private Label userNameLabel;

    private String userName;

    public void initialize() {
        String utilisateur = loginControllerytb.getUtilisateurConnecte();
       
        // Afficher un message de bienvenue
        if (utilisateur != null) {
            bienvenueLabel.setText("Bienvenue, " + utilisateur + " !");
        } else {
            bienvenueLabel.setText("Bienvenue !");
        }
    
       
        userNameLabel.setText(utilisateur);
    }

    @FXML
    private void onHomeClicked() {
        // Charger la scène Home
        loadScene("Home.fxml");
    }

    @FXML
    private void onReservationClicked() {
        // Charger la scène Réservation
        loadScene("Reservation.fxml");
    }

    @FXML
    private void onLogoutClicked() {
        // Déconnecter l'utilisateur
        System.out.println("Utilisateur déconnecté");
        loadScene("Login.fxml");
    }

    private void loadScene(String fxmlFile) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/" + fxmlFile));
            Parent root = loader.load();

            // Obtenir la fenêtre actuelle
            Stage stage = (Stage) userNameLabel.getScene().getWindow();

            // Remplacer la scène
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la scène : " + fxmlFile);
        }
    }
}
