package com.example.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import dao.EvenementDAO;
import dao.RéservationsDAO;
import dao.SalleDAO;
import dao.TerrainsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Réservations;
import model.Terrains;
import model.evenements;
import model.salle;

public class reservationController {

    @FXML
    private VBox mainVBox;

    @FXML
    private TextField eventNameField;

    @FXML
    private TextArea eventDescriptionArea;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private ComboBox<String> salleComboBox;

    @FXML
    private ComboBox<String> terrainComboBox;



    @FXML
    private Label userNameLabel;

    private SalleDAO salleDAO = new SalleDAO();
    private TerrainsDAO terrainDAO = new TerrainsDAO();
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
    public void initialize() {
        String utilisateur = loginControllerytb.getUtilisateurConnecte();
        userNameLabel.setText(utilisateur);
        try {
            List<salle> salles = salleDAO.getAll();
            List<Terrains> terrains = terrainDAO.getAll();

            if (salleComboBox != null) {
                for (salle s : salles) {
                    salleComboBox.getItems().add(s.getId_salle() + " - " + s.getNom_salle() + " (Capacité: " + s.getCapacite() + ")");
                }
            }

            if (terrainComboBox != null) {
                for (Terrains t : terrains) {
                    terrainComboBox.getItems().add(t.getId_terrain() + " - " + t.getNom() + " (Type: " + t.getType() + ")");
                }
            }

            
        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }
    
    @FXML
    public void handleReservation() {
        try {
            String eventName = eventNameField.getText();
            String eventDescription = eventDescriptionArea.getText();
            String eventDate = (eventDatePicker.getValue() != null) ? eventDatePicker.getValue().toString() : null;
            String salle = salleComboBox.getValue();
            String terrain = terrainComboBox.getValue();
    
            // Validation des champs
            if (eventName.isEmpty() || eventDescription.isEmpty() || eventDate == null) {
                showError("Veuillez remplir tous les champs obligatoires !");
                return;
            }
    
            if (salle == null && terrain == null) {
                showError("Sélectionnez au moins une salle ou un terrain !");
                return;
            }
    
            int userId =  loginControllerytb.getUtilisateurId(); 
           
            // Création de l'événement
            evenements event = new evenements();
            event.setNom_event(eventName);
            event.setDescription(eventDescription);
            event.setDate_evnt(Date.valueOf(eventDate));
            event.setId_user(userId);
    
            EvenementDAO evenementDAO = new EvenementDAO();
            evenementDAO.add(event);
            int eventId = event.getId_event();
            if (eventId <= 0) {
                throw new IllegalStateException("L'ID de l'événement n'a pas été généré correctement.");
            }
    
            Réservations reservation = new Réservations();
            reservation.setId_user(userId);
            reservation.setId_event(eventId);
    
            if (salle != null) {
                reservation.setId_salle(extractIdFromString(salle));
            }
    
            if (terrain != null) {
                reservation.setId_terrain(extractIdFromString(terrain));
            }
    
            reservation.setReservatiion(new Date(System.currentTimeMillis()));
    
            RéservationsDAO reservationsDAO = new RéservationsDAO();
            reservationsDAO.add(reservation);
    
            showConfirmation("Votre réservation a été enregistrée avec succès !");
    
            resetFields();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Une erreur s'est produite lors de la réservation. Veuillez réessayer.");
        }
    }
    
    private void showConfirmation(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    private void resetFields() {
        eventNameField.clear();
        eventDescriptionArea.clear();
        eventDatePicker.setValue(null);
        salleComboBox.setValue(null);
        terrainComboBox.setValue(null);
    }

    private int extractIdFromString(String value) {
        try {
            return Integer.parseInt(value.split(" ")[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format d'ID invalide : " + value);
        }
    }

   
}
