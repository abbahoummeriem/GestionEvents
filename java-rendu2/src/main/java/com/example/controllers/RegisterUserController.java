package com.example.controllers;

import java.io.IOException;
import java.security.MessageDigest;

import dao.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Utilisateurs;


public class RegisterUserController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField passwordField;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    public void initialize() {
        ObservableList<String> types = FXCollections.observableArrayList("etudiant", "professeur");
        typeComboBox.setItems(types);
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
    private void switchToLogin(ActionEvent event) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/com/example/views/signinForm.fxml"));

            
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();

            stage.setScene(new Scene(loginView));
          
            stage.setTitle("Page de Connexion");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page de connexion.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSubmit(javafx.event.ActionEvent evt) {
        
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String type = typeComboBox.getValue(); 
        String hashedPassword = passwordHash(passwordField.getText());
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || type == null || hashedPassword == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs correctement", Alert.AlertType.ERROR);
            return;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Erreur", "Veuillez entrer une adresse email valide.", Alert.AlertType.ERROR);
            return;
        }
        if (passwordField.getText().length() < 8) {
            showAlert("Erreur", "Le mot de passe doit contenir au moins 8 caractères.", Alert.AlertType.ERROR);
            return;
        }

        if (utilisateurDAO.existsByEmail(email)) {
            showAlert("Erreur", "Un utilisateur avec cet email existe déjà.", Alert.AlertType.ERROR);
            return;
        }

        Utilisateurs utilisateur = new Utilisateurs();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setType(type);
        utilisateur.setPassword(hashedPassword);
        utilisateurDAO.add(utilisateur);
        resetFields();

        
        showAlert("Succès", "Utilisateur ajouté avec succès", Alert.AlertType.INFORMATION);
    }

    public static String passwordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : rbt) {
                sb.append(String.format("%02x", b));

            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void resetFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        typeComboBox.getSelectionModel().clearSelection();
        passwordField.clear();
    }

    @FXML
    private void btnExitAction(ActionEvent event) {
        System.exit(0);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
