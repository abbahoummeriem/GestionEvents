package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Réservations;
import service.TransactionManager;

public class RéservationsDAO implements GenericDAO<Réservations> {

    private final TransactionManager transactionManager = new TransactionManager();

    public RéservationsDAO() {
    }

    @Override
    public void add(Réservations entity) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "INSERT INTO reservation (id_user, id_event, id_salle, id_terrain, date_reservation) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, entity.getId_user());
                    stmt.setInt(2, entity.getId_event());
                    stmt.setInt(3, entity.getId_salle());
                    stmt.setInt(4, entity.getId_terrain());
                    stmt.setTimestamp(5, new Timestamp(entity.getReservatiion().getTime())); // Conversion de Date à Timestamp
                    stmt.executeUpdate();
                    System.out.println("Réservation ajoutée : " + entity.getId_user());
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de l'insertion de la réservation : " + e.getMessage());
        }
    }

    @Override
    public Réservations get(int id) {
        Réservations reservation = new Réservations();
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "SELECT id_user, id_event, id_salle, id_terrain, date_reservation FROM reservation WHERE id_reservation = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            reservation.setId_user(rs.getInt("id_user"));
                            reservation.setId_event(rs.getInt("id_event"));
                            reservation.setId_salle(rs.getInt("id_salle"));
                            reservation.setId_terrain(rs.getInt("id_terrain"));
                            reservation.setReservatiion(rs.getTimestamp("date_reservation"));
                        } else {
                            System.out.println("Aucune réservation trouvée avec cet ID : " + id);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de la connexion à la base de données : " + e.getMessage());
        }
        return reservation;
    }

    @Override
    public List<Réservations> getAll() {
        List<Réservations> reservationsList = new ArrayList<>();
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "SELECT id_user, id_event, id_salle, id_terrain, date_reservation FROM reservation";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Réservations reservation = new Réservations();
                        reservation.setId_user(rs.getInt("id_user"));
                        reservation.setId_event(rs.getInt("id_event"));
                        reservation.setId_salle(rs.getInt("id_salle"));
                        reservation.setId_terrain(rs.getInt("id_terrain"));
                        reservation.setReservatiion(rs.getTimestamp("date_reservation"));
                        reservationsList.add(reservation);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
        return reservationsList;
    }

    @Override
    public void update(Réservations entity, int id) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "UPDATE reservation SET id_user = ?, id_event = ?, id_salle = ?, id_terrain = ?, date_reservation = ? WHERE id_reservation = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, entity.getId_user());
                    stmt.setInt(2, entity.getId_event());
                    stmt.setInt(3, entity.getId_salle());
                    stmt.setInt(4, entity.getId_terrain());
                    stmt.setTimestamp(5, new Timestamp(entity.getReservatiion().getTime())); // Conversion de Date à Timestamp
                    stmt.setInt(6, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Réservation mise à jour avec succès.");
                    } else {
                        System.out.println("Aucune réservation trouvée avec cet ID : " + id);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec lors de la mise à jour de la réservation : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            transactionManager.executeInTransaction(conn -> {
                String sql = "DELETE FROM reservation WHERE id_reservation = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Réservation supprimée.");
                    } else {
                        System.out.println("Aucune réservation trouvée avec cet ID.");
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de la suppression de la réservation : " + e.getMessage());
        }
    }
}
