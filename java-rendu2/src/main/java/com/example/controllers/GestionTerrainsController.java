package com.example.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.TerrainsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Terrains;

public class GestionTerrainsController {

    @FXML
    private TextField nomTerrainField;

    @FXML
    private TextField typeTerrainField;

    @FXML
    private Button ajouterTerrainButton;

    @FXML
    private TableView<Terrains> tableTerrains;

    @FXML
    private TableColumn<Terrains, String> nomTerrainColumn;

    @FXML
    private TableColumn<Terrains, String> typeTerrainColumn;

    @FXML
    private TableColumn<Terrains, String> modifierColumn;

    private ObservableList<Terrains> terrainsList;
    private TerrainsDAO terrainsDAO;
    @FXML
    private TableColumn<Terrains, String> supprimerColumn;
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
    private void supprimerTerrain(Terrains terrainSelectionne) {
    if (terrainSelectionne == null) {
        System.out.println("Aucun terrain sélectionné.");
        return;
    }

    // Demander confirmation avant de supprimer
    boolean confirmation = afficherDialogueConfirmation("Êtes-vous sûr de vouloir supprimer ce terrain ?");
    if (confirmation) {
        // Supprimer le terrain de la base de données
        TerrainsDAO terrain= new TerrainsDAO();
        terrain.delete(terrainSelectionne.getId_terrain());

        // Recharger la liste des terrains
        chargerTerrains();
    }
}

private boolean afficherDialogueConfirmation(String message) {
  
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(null);
    alert.setContentText(message);
    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}

    @FXML
    public void initialize() {
       
        terrainsDAO = new TerrainsDAO();
        supprimerColumn.setCellFactory(column -> new TableCell<Terrains, String>() {
            private final Button btnSupprimer = new Button("Supprimer");
        
            {  btnSupprimer.setStyle("-fx-background-color:rgb(247, 245, 243); -fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-border-color:red");
                btnSupprimer.setOnAction(event -> {
                    Terrains terrainSelectionne = getTableView().getItems().get(getIndex());
                    supprimerTerrain(terrainSelectionne);
                });
            }
        
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnSupprimer);
                }
            }
        });
       
        terrainsList = FXCollections.observableArrayList();

      
        nomTerrainColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeTerrainColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        modifierColumn.setCellFactory(column -> new TableCell<Terrains, String>() {
            private final Button btnModifier = new Button("Modifier");
        
            {  btnModifier.setStyle("-fx-background-color:rgb(247, 245, 243); -fx-text-fill: green; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-border-color:green");
                btnModifier.setOnAction(event -> {
                    Terrains terrainSelectionne = getTableView().getItems().get(getIndex());
                    ouvrirFenetreModification(terrainSelectionne);
                });
            }
        
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnModifier);
                }
            }
        });

      
        tableTerrains.setItems(terrainsList);

        
        chargerTerrains();

        
        ajouterTerrainButton.setOnAction(event -> ajouterTerrain());
    }

    private void chargerTerrains() {
        List<Terrains> listeTerrains = terrainsDAO.getAll();
        terrainsList.clear();
        terrainsList.addAll(listeTerrains);
    }

    private void ajouterTerrain() {
        String nomTerrain = nomTerrainField.getText();
        String typeTerrain = typeTerrainField.getText();

        if (nomTerrain.isEmpty() || typeTerrain.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

    
        Terrains nouveauTerrain = new Terrains();
        nouveauTerrain.setNom(nomTerrain);
        nouveauTerrain.setType(typeTerrain);

       
        terrainsDAO.add(nouveauTerrain);

    
        chargerTerrains();

      
        nomTerrainField.clear();
        typeTerrainField.clear();
    }

 
    private void ouvrirFenetreModification(Terrains terrainSelectionne) {
        if (terrainSelectionne == null) {
            System.out.println("Aucun terrain sélectionné.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/ModifierTerrains.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            ModifierTerrainController controller = loader.getController();
            controller.setTerrain(terrainSelectionne);

            stage.setTitle("Modifier Terrain");
            stage.showAndWait();


            terrainsDAO.update(terrainSelectionne, terrainSelectionne.getId_terrain());

    
            tableTerrains.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
