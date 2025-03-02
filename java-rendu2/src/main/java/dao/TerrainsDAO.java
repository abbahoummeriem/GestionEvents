package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Terrains;
import service.TransactionManager;

public class TerrainsDAO implements GenericDAO<Terrains> {

    private final TransactionManager transactionManager = new TransactionManager();

    public TerrainsDAO() {
    }

    @Override
    public void add(Terrains entity) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "INSERT INTO terrain (nom_terrain, type) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, entity.getNom());
                    stmt.setString(2, entity.getType());
                    stmt.executeUpdate();
                    System.out.println("Terrain ajouté : " + entity.getNom());
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de l'insertion du terrain : " + e.getMessage());
        }
    }

    @Override
    public Terrains get(int id) {
        Terrains terrain = new Terrains();
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "SELECT id_terrain, nom_terrain, type FROM terrain WHERE id_terrain = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            terrain.setId_terrain(rs.getInt("id_terrain"));
                            terrain.setNom(rs.getString("nom_terrain"));
                            terrain.setType(rs.getString("type"));
                        } else {
                            System.out.println("Ce terrain n'existe pas avec l'ID : " + id);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de la connexion à la base de données : " + e.getMessage());
        }
        return terrain;
    }

    @Override
    public List<Terrains> getAll() {
        List<Terrains> terrainsList = new ArrayList<>();
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "SELECT id_terrain, nom_terrain, type FROM terrain";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Terrains terrain = new Terrains();
                        terrain.setId_terrain(rs.getInt("id_terrain"));
                        terrain.setNom(rs.getString("nom_terrain"));
                        terrain.setType(rs.getString("type"));
                        terrainsList.add(terrain);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des terrains : " + e.getMessage());
        }
        return terrainsList;
    }

    @Override
    public void update(Terrains entity, int id) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "UPDATE terrain SET nom_terrain = ?, type = ? WHERE id_terrain = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, entity.getNom());
                    stmt.setString(2, entity.getType());
                    stmt.setInt(3, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Terrain mis à jour avec succès.");
                    } else {
                        System.out.println("Aucun terrain trouvé avec cet ID : " + id);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec lors de la mise à jour du terrain : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "DELETE FROM terrain WHERE id_terrain = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Terrain supprimé.");
                    } else {
                        System.out.println("Échec de la suppression. Aucun terrain trouvé avec cet ID.");
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de la suppression du terrain : " + e.getMessage());
        }
    }
}
