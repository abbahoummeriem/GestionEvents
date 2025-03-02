package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Utilisateurs;
import service.TransactionManager;

public class UtilisateurDAO implements GenericDAO<Utilisateurs> {

    private final TransactionManager transaction = new TransactionManager();

    @Override
    public void add(Utilisateurs entity) {
        try {
            transaction.executeInTransaction(conn -> {
                String sql = "INSERT INTO Utilisateurs (nom, prenom, email, type, password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, entity.getNom());
                stmt.setString(2, entity.getPrenom());
                stmt.setString(3, entity.getEmail());
                stmt.setString(4, entity.getType());
                stmt.setString(5, entity.getPassword());
                stmt.executeUpdate();
                System.out.println("Utilisateur ajouté : " + entity.getNom());
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
        }
    }

    @Override
    public Utilisateurs get(int id) {
        Utilisateurs utilisateur = new Utilisateurs();
        try {
            transaction.executeInTransaction(conn -> {
                String sql = "SELECT nom, prenom, email, type, password FROM Utilisateurs WHERE id_user = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        utilisateur.setNom(rs.getString("nom"));
                        utilisateur.setPrenom(rs.getString("prenom"));
                        utilisateur.setEmail(rs.getString("email"));
                        utilisateur.setType(rs.getString("type"));
                        utilisateur.setPassword(rs.getString("password"));
                    } else {
                        System.out.println("Aucun utilisateur avec cet ID.");
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return utilisateur;
    }

    @Override
    public List<Utilisateurs> getAll() {
        List<Utilisateurs> utilisateursList = new ArrayList<>();
        try {
            transaction.executeInTransaction(conn -> {
                String sql = "SELECT nom, prenom, email, type, password FROM Utilisateurs";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Utilisateurs utilisateur = new Utilisateurs();
                        utilisateur.setNom(rs.getString("nom"));
                        utilisateur.setPrenom(rs.getString("prenom"));
                        utilisateur.setEmail(rs.getString("email"));
                        utilisateur.setType(rs.getString("type"));
                        utilisateur.setPassword(rs.getString("password"));
                        utilisateursList.add(utilisateur);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return utilisateursList;
    }

    @Override
    public void update(Utilisateurs entity, int id) {
        try {
            transaction.executeInTransaction(conn -> {
                String sql = "UPDATE Utilisateurs SET nom = ?, prenom = ?, email = ?, type = ?, password = ? WHERE id_user = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, entity.getNom());
                stmt.setString(2, entity.getPrenom());
                stmt.setString(3, entity.getEmail());
                stmt.setString(4, entity.getType());
                stmt.setString(5, entity.getPassword());
                stmt.setInt(6, id);
                stmt.executeUpdate();
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            transaction.executeInTransaction(conn -> {
                String sql = "DELETE FROM Utilisateurs WHERE id_user = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    
    public boolean existsByEmail(String email) {
        final boolean[] exists = {false}; // Utilisation d'un tableau pour capturer la valeur
    
        try {
            transaction.executeInTransaction(conn -> {
                String query = "SELECT COUNT(*) FROM users WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        exists[0] = rs.getInt(1) > 0; // Capture la valeur dans le tableau
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return exists[0]; // Retourne la valeur après la transaction
    }
    

}
