package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import dao.UtilisateurDAO;
import model.Utilisateurs;
import service.TransactionManager;
public class AppTest {

    @Test
    public void testGetNom() {
        Utilisateurs utilisateur = new Utilisateurs("hello", "Doe", "kha@.com", "etudaint","hhhh");
        assertEquals("hello", utilisateur.getNom());
    }
    @Test
    void testAddUtilisateur() {
        Utilisateurs utilisateur = new Utilisateurs("Khadija", "Doe", "khadija@example.com", "etudiant","hhhhh");
        UtilisateurDAO user =new UtilisateurDAO();
        user.add(utilisateur);
    }
    TransactionManager transaction = new TransactionManager();

 
    
}
