package com.example.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dao.EvenementDAO;
import dao.RéservationsDAO;
import dao.SalleDAO;
import dao.TerrainsDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Réservations;
import model.Terrains;
import model.evenements;
import model.salle;

public class historique {

    @FXML
    private TableView<Réservations> tableReservations;

    @FXML
    private TableColumn<Réservations, String> colNomEvent;

    @FXML
    private TableColumn<Réservations, String> colNomSalle;

    @FXML
    private TableColumn<Réservations, String> colNomTerrain;
  @FXML
    private Label userNameLabel;
    @FXML
    
    private TableColumn<Réservations, Date> colDateEvent;

    private ObservableList<Réservations> reservationsList = FXCollections.observableArrayList();

    private final RéservationsDAO reservationsDAO = new RéservationsDAO();

    @FXML
    private void historique(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/historique.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Historique");
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
    // Configuration des colonnes du tableau
    colNomEvent.setCellValueFactory(cellData -> {
        int eventId = cellData.getValue().getId_event();
        EvenementDAO evenementDAO = new EvenementDAO();
        evenements event = evenementDAO.get(eventId);
        return new SimpleStringProperty(event != null ? event.getNom_event() : ""); // Retourne le nom de l'événement
    });

    colNomSalle.setCellValueFactory(cellData -> {
        int salleId = cellData.getValue().getId_salle();
        SalleDAO salleDAO = new SalleDAO();
        salle s = salleDAO.get(salleId);
        return new SimpleStringProperty(s != null ? s.getNom_salle() : ""); // Retourne le nom de la salle
    });

    colNomTerrain.setCellValueFactory(cellData -> {
        int terrainId = cellData.getValue().getId_terrain();
        TerrainsDAO terrainsDAO = new TerrainsDAO();
        Terrains terrain = terrainsDAO.get(terrainId);
        return new SimpleStringProperty(terrain != null ? terrain.getNom() : ""); // Retourne le nom du terrain
    });

    colDateEvent.setCellValueFactory(cellData -> {
        int eventId = cellData.getValue().getId_event();
        EvenementDAO evenementDAO = new EvenementDAO();
        evenements event = evenementDAO.get(eventId);
        return new SimpleObjectProperty<>(event != null ? event.getDate_evnt() : null); // Retourne la date de l'événement
    });


    loadUserReservations();
}

private void loadUserReservations() {
    int userId = loginControllerytb.getUtilisateurId(); // Remplacez par la méthode réelle pour obtenir l'ID utilisateur connecté.

   
    List<Réservations> allReservations = reservationsDAO.getAll();

    List<Réservations> userReservations = allReservations.stream()
            .filter(reservation -> reservation.getId_user() == userId)
            .collect(Collectors.toList());

    reservationsList.setAll(userReservations);


    tableReservations.setItems(reservationsList);
}

}
