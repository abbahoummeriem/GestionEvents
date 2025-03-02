package com.example.controllers;

import dao.SalleDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.salle;

public class ModifierSalleController {
    @FXML
    private TextField nomSalleField;

    @FXML
    private TextField capaciteField;

    private salle salle; 
    private SalleDAO salleDAO; 

    public ModifierSalleController() {
       
        salleDAO = new SalleDAO();
    }

    /**
     * Associe une salle à ce contrôleur et pré-remplit les champs avec ses données.
     * @param salle L'objet salle à modifier.
     */
    public void setSalle(salle salle) {
        this.salle = salle;
        nomSalleField.setText(salle.getNom_salle());
        capaciteField.setText(salle.getCapacite());
     
    }

 
    @FXML
    private void sauvegarder() {
        if (salle == null) {
            System.out.println("Erreur : Aucun objet salle n'est défini.");
            return;
        }

        salle.setNom_salle(nomSalleField.getText());
        salle.setCapacite(capaciteField.getText());

       
        try {
            salleDAO.update(salle, salle.getId_salle());
            System.out.println("Salle mise à jour avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour de la salle : " + e.getMessage());
        }

       
        Stage currentStage = (Stage) nomSalleField.getScene().getWindow();
        currentStage.close();
    }

    
    @FXML
    private void annuler() {
        Stage currentStage = (Stage) nomSalleField.getScene().getWindow();
        currentStage.close();
    }
}
