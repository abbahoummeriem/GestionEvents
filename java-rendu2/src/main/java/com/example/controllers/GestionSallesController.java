package com.example.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.SalleDAO;
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
import model.salle;

public class GestionSallesController {

    
    
    @FXML
    private TextField nomSalleField;

    @FXML
    private TextField capaciteField;

    @FXML
    private Button ajouterSalleButton;

    @FXML
    private TableView<salle> tableSalles;

    @FXML
    private TableColumn<salle, String> nomSalleColumn;

    @FXML
    private TableColumn<salle, String> capaciteColumn;

    @FXML
    private TableColumn<salle, String> modifierColumn;
    @FXML
private TableColumn<salle, String> supprimerColumn;

    private ObservableList<salle> sallesList;
    private SalleDAO salleDAO;
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
    private void supprimerSalle(salle salleSelectionnee) {
    if (salleSelectionnee == null) {
        System.out.println("Aucune salle sélectionnée.");
        return;
    }

    // Demander confirmation avant de supprimer
    boolean confirmation = afficherDialogueConfirmation("Êtes-vous sûr de vouloir supprimer cette salle ?");
    if (confirmation) {

        salleDAO.delete(salleSelectionnee.getId_salle());

        chargerSalles();
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
      
        salleDAO = new SalleDAO();


supprimerColumn.setCellFactory(column -> new TableCell<salle, String>() {
    private final Button btnSupprimer = new Button("Supprimer");

    {                btnSupprimer.setStyle("-fx-background-color:rgb(247, 245, 243); -fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-border-color:red");

        btnSupprimer.setOnAction(event -> {
            salle salleSelectionnee = getTableView().getItems().get(getIndex());
            supprimerSalle(salleSelectionnee);
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

     
        sallesList = FXCollections.observableArrayList();

    
        nomSalleColumn.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
        capaciteColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));
 
   
        modifierColumn.setCellFactory(column -> new TableCell<salle, String>() {
            private final Button btnModifier = new Button("Modifier");
        
            {
                btnModifier.setStyle("-fx-background-color:rgb(247, 245, 243); -fx-text-fill: green; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-border-color:green");

                btnModifier.setOnAction(event -> {
                    salle salleSelectionnee = getTableView().getItems().get(getIndex());
                    ouvrirFenetreModification(salleSelectionnee);
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
        

        tableSalles.setItems(sallesList);

     
        chargerSalles();


        ajouterSalleButton.setOnAction(event -> ajouterSalle());
    }


    private void chargerSalles() {
        List<salle> listeSalles = salleDAO.getAll();
        sallesList.clear();
        sallesList.addAll(listeSalles);
    }


    private void ajouterSalle() {
        String nomSalle = nomSalleField.getText();
        String capacite = capaciteField.getText();

        if (nomSalle.isEmpty() || capacite.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

      
        salle nouvelleSalle = new salle();
        nouvelleSalle.setNom_salle(nomSalle);
        nouvelleSalle.setCapacite(capacite);

        
        salleDAO.add(nouvelleSalle);

   
        chargerSalles();

    
        nomSalleField.clear();
        capaciteField.clear();
    }


    private void ouvrirFenetreModification(salle salleSelectionnee) {
        if (salleSelectionnee == null) {
            System.out.println("Aucune salle sélectionnée.");
            return;
        }
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/ModifierSalle.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
    
          
            ModifierSalleController controller = loader.getController();
            controller.setSalle(salleSelectionnee);
    
            stage.setTitle("Modifier Salle");
            stage.showAndWait();
    
            salleDAO.update(salleSelectionnee, salleSelectionnee.getId_salle());
    
           
            tableSalles.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
