package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.salle;
import service.TransactionManager;

public class SalleDAO implements GenericDAO<salle> {

    private final TransactionManager transactionManager = new TransactionManager();

    public SalleDAO() {
    }

    @Override
    public void add(salle entity) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "INSERT INTO salle (nom_salle, capacite) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, entity.getNom_salle());
                    stmt.setString(2, entity.getCapacite());
                    stmt.executeUpdate();
                    System.out.println("Salle ajoutée : " + entity.getNom_salle());
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de l'insertion de la salle : " + e.getMessage());
        }
    }

    @Override
    public salle get(int id) {
        salle salle = new salle();
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "SELECT nom_salle, capacite FROM salle WHERE id_salle = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            salle.setNom_salle(rs.getString("nom_salle"));
                            salle.setCapacite(rs.getString("capacite"));
                        } else {
                            System.out.println("Cette salle n'existe pas avec l'ID : " + id);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de la connexion à la base de données : " + e.getMessage());
        }
        return salle;
    }

    @Override
    public List<salle> getAll() {
        List<salle> salleList = new ArrayList<>();
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "SELECT id_salle,nom_salle, capacite FROM salle";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        salle salle = new salle();
                        salle.setId_salle(rs.getInt("id_salle"));
                        salle.setNom_salle(rs.getString("nom_salle"));
                        salle.setCapacite(rs.getString("capacite"));
                        salleList.add(salle);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des salles : " + e.getMessage());
        }
        return salleList;
    }

    @Override
    public void update(salle entity, int id) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "UPDATE salle SET nom_salle = ?, capacite = ? WHERE id_salle = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, entity.getNom_salle());
                    stmt.setString(2, entity.getCapacite());
                    stmt.setInt(3, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Salle mise à jour avec succès.");
                    } else {
                        System.out.println("Aucune salle trouvée avec cet ID : " + id);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec lors de la mise à jour de la salle : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "DELETE FROM salle WHERE id_salle = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Salle supprimée.");
                    } else {
                        System.out.println("Échec de la suppression. Aucune salle trouvée avec cet ID.");
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de la suppression de la salle : " + e.getMessage());
        }
    }
}
