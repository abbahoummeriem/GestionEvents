package com.example.controllers;

import java.io.IOException;

import dao.RéservationsDAO;
import dao.SalleDAO;
import dao.TerrainsDAO;
import dao.UtilisateurDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Réservations;
import model.Terrains;
import model.Utilisateurs;
import model.salle;

public class homeController {

    @FXML
    private TableView<Réservations> reservationsTable;
    @FXML
    private TableColumn<Réservations, String> colUser;
    @FXML
    private TableColumn<Réservations, String> colSalle;
    @FXML
    private TableColumn<Réservations, String> colTerrain;
    @FXML
    private TableColumn<Réservations, String> colDate;
    private ImageView imageView;
    private final RéservationsDAO reservationsDAO = new RéservationsDAO();
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private final SalleDAO salleDAO = new SalleDAO();
    private final TerrainsDAO terrainDAO = new TerrainsDAO();
    
    private final ObservableList<Réservations> reservationsList = FXCollections.observableArrayList();

    @FXML
    private void onGestionTerrainsClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/gestionTerrains.fxml"));
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
    private void onDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/home.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void onGestionSallesClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/gestionSalles.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Gestion des Salles");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void initialize() {
       
        // Associer les colonnes avec les propriétés du modèle
        colUser.setCellValueFactory(cellData -> new SimpleStringProperty(getUserNameById(cellData.getValue().getId_user())));
        colSalle.setCellValueFactory(cellData -> new SimpleStringProperty(getSalleNameById(cellData.getValue().getId_salle())));
        colTerrain.setCellValueFactory(cellData -> new SimpleStringProperty(getTerrainNameById(cellData.getValue().getId_terrain())));
        colDate.setCellValueFactory(new PropertyValueFactory<>("reservatiion"));



        // Charger les données dans le tableau
        loadReservations();
    }

    private void loadReservations() {
        reservationsList.clear();
        reservationsList.addAll(reservationsDAO.getAll());
        reservationsTable.setItems(reservationsList);
    }

    private String getUserNameById(int userId) {
        Utilisateurs utilisateur = utilisateurDAO.get(userId);
        return utilisateur != null ? utilisateur.getFullname() : "Utilisateur inconnu";
    }

    private String getSalleNameById(int salleId) {
        salle salle = salleDAO.get(salleId);
        return salle != null ? salle. getNom_salle() : "Salle inconnue";
    }

    private String getTerrainNameById(int terrainId) {
        Terrains terrain = terrainDAO.get(terrainId);
        return terrain != null ? terrain.getNom() : "Terrain inconnu";
    }

    @FXML
    private void handleLogout() {
        try {
            javafx.scene.Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            javafx.stage.Stage stage = (javafx.stage.Stage) reservationsTable.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la scène de login : " + e.getMessage());
        }
    }
}
