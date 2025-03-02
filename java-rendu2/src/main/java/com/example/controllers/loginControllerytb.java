package com.example.controllers;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UtilisateurDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.TransactionManager;

public class loginControllerytb {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField motpasseField;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;
    
    private Connection conn = null;

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private PreparedStatement pst;
    private static int utilisateurId;

    public static void setUtilisateurId(int id) {
        utilisateurId = id;
    }
    
    public static int getUtilisateurId() {
        return utilisateurId;
    }
    
    private static String utilisateurConnecte;

    public static void setUtilisateurConnecte(String utilisateur) {
        utilisateurConnecte = utilisateur;
    }
    
   
    public static String getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    @FXML
    private void switchToRegister(ActionEvent event) {
        try {
         
            Parent loginView = FXMLLoader.load(getClass().getResource("/com/example/views/UtilisateurLogin.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(loginView));
            
            stage.setTitle("Page d'inscription");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page d'inscription.", Alert.AlertType.ERROR);
        }
    }
   
    @FXML
    private void btnLoginAction(javafx.event.ActionEvent event) {
        
        TransactionManager transactionManager = new TransactionManager();
    
        try {
            transactionManager.executeInTransaction(conn -> {
                String email = emailField.getText();
                String password = motpasseField.getText();
    
                if (email.isEmpty() || password.isEmpty()) {
                    showAlert("Erreur", "Veuillez remplir tous les champs.", AlertType.ERROR);
                    return;
                }
    
                try {
                    String sql = "SELECT email, id_user,password,nom,prenom,type FROM utilisateurs WHERE email = ?";
                    try (PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, email);
                        ResultSet rs = pst.executeQuery();
    
                        if (rs.next()) {
                            String storedPassword = rs.getString("password");
                            String name=rs.getString("nom")+" "+rs.getString("prenom");
                            String Type=rs.getString("type");
                            int id=rs.getInt("id_user");
                            String hashedPassword = passwordHash(password);
                            String typea="admin";
                            if (storedPassword.equals(hashedPassword)  ) {
                                if(Type.equals(typea)){
                                    Parent loginView = FXMLLoader.load(getClass().getResource("/com/example/views/home.fxml"));
                                    Scene currentScene = ((Node) event.getSource()).getScene();
                                    Stage stage = (Stage) currentScene.getWindow();
                                    stage.setScene(new Scene(loginView));
                                    stage.setTitle("Page de Connexion");
                                    stage.show();
                                }else{
                                    setUtilisateurId(id);
                                    setUtilisateurConnecte(name);
                            
                                    Parent loginView = FXMLLoader.load(getClass().getResource("/com/example/views/homeUser.fxml"));
                                    Scene currentScene = ((Node) event.getSource()).getScene();
                                    Stage stage = (Stage) currentScene.getWindow();
                                    stage.setScene(new Scene(loginView));
                                    stage.setTitle("Page de Connexion");
                                    stage.show();
                                }
                            }
                             else {
                                showAlert("Erreur", "Mot de passe invalide", AlertType.ERROR);
                            }
                        } else {
                            showAlert("Erreur", "Email invalide", AlertType.ERROR);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Erreur", "Erreur de connexion à la base de données", AlertType.ERROR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Une erreur inattendue est survenue", AlertType.ERROR);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de l'exécution de la transaction", AlertType.ERROR);
        }
    }
    

    
    private String passwordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    




    // Action for the "Fermer" button
    @FXML
    private void btnExitAction(ActionEvent event) {
        // Close the application when the "Fermer" button is clicked
        System.exit(0);
    }

    // Method to display alerts
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
