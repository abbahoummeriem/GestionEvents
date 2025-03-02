package com.example.controllers;
import dao.TerrainsDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Terrains;
public class ModifierTerrainController {
    
    @FXML
    private TextField nomTerrainField;

    @FXML
    private TextField typeField;

    private Terrains terrain;
    private TerrainsDAO terrainDao; 

    public ModifierTerrainController() {
        terrainDao= new TerrainsDAO();
    }

    /**
     * Associe une salle à ce contrôleur et pré-remplit les champs avec ses données.
     * @param terrain L'objet salle à modifier.
     */
    public void setTerrain(Terrains terrain) {
        this.terrain = terrain;
        nomTerrainField.setText(terrain.getNom());
        typeField.setText(terrain.getType());
     
    }

   
    @FXML
    private void sauvegarder() {
        if (terrain == null) {
            System.out.println("Erreur : Aucun objet terrainn'est défini.");
            return;
        }

       
        terrain.setNom(nomTerrainField.getText());
        terrain.setType(typeField.getText());

      
        try {
            terrainDao.update(terrain, terrain.getId_terrain());
            System.out.println("terrain mise à jour avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour de terrain : " + e.getMessage());
        }

       
        Stage currentStage = (Stage) nomTerrainField.getScene().getWindow();
        currentStage.close();
    }

    
    @FXML
    private void annuler() {
        Stage currentStage = (Stage) nomTerrainField.getScene().getWindow();
        currentStage.close();
    }
}

