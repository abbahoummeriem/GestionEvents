package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.evenements;
import service.TransactionManager;



public class EvenementDAO  implements GenericDAO<evenements>{
    private final TransactionManager Tronsaction = new TransactionManager();
    public EvenementDAO() {
    }
    @Override
    public void add(evenements entity) {
        try {
            Tronsaction.executeInTransaction(conn -> {
                String sql = "INSERT INTO evenement (nom_event, date_event, description, id_user) VALUES (?, ?, ?, ?)";
                // Inclure RETURN_GENERATED_KEYS pour récupérer l'ID généré
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
                // Paramétrer les valeurs
                stmt.setString(1, entity.getNom_event());
                stmt.setObject(2, entity.getDate_evnt());
                stmt.setString(3, entity.getDescription());
                stmt.setInt(4, entity.getId_user());
    
                // Exécuter l'insertion
                int rowsAffected = stmt.executeUpdate();
                System.out.println("Événement ajouté : " + entity.getNom_event());
    
                // Vérifier si l'insertion a réussi et récupérer l'ID généré
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            entity.setId_event(generatedId); // Assigner l'ID généré à l'entité
                            System.out.println("ID généré : " + generatedId);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("Échec de l'insertion");
            e.printStackTrace();
        }
    }
    
    @Override
    public evenements get(int id){
        evenements l = new evenements();
        try {
            Tronsaction.executeInTransaction(conn ->{ String sql = "SELECT nom_event ,date_event,description,id_user FROM evenement WHERE id_event = ?";
            PreparedStatement stmt = conn.prepareStatement(sql) ;
                stmt.setInt(1, id); 
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        l.setNom_event(rs.getString("nom_event"));
                        l.setDate_evnt(rs.getDate("date_event"));
                        l.setDescription(rs.getString("description"));
                        l.setId_user(rs.getInt("id_user"));
                    }else {System.out.println("cette id n'exit pas " );}
            }});
        } catch (SQLException e) {
            System.out.println("ECHE de conn " );
        }
        return  l;
    }
    @Override
    public List<evenements> getAll() {
        List<evenements> eventList = new ArrayList<>();

    try {
        Tronsaction.executeInTransaction(conn -> {
            String sql = "SELECT id_event nom_event, date_event, description, id_user FROM evenement";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    evenements e = new evenements();
                    e.setId_event(rs.getInt("id_event"));
                    e.setNom_event(rs.getString("nom_event"));
                    e.setDate_evnt(rs.getDate("date_event"));
                    e.setDescription(rs.getString("description"));
                    e.setId_user(rs.getInt("id_user"));
                    eventList.add(e);
                }
            }
        });}
        catch (SQLException e) {
            System.err.println("Erreur  " + e.getMessage());
        }
    
        return eventList;
    }

    @Override
    public void update(evenements entity,int id) {
        try {
            Tronsaction.executeInTransaction(conn -> {
                String sql = "UPDATE evenement SET nom_event = ?, date_event = ?, description = ?, id_user = ? WHERE id_event = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, entity.getNom_event());
                    stmt.setDate(2, new java.sql.Date(entity.getDate_evnt().getTime()));  
                    stmt.setString(3, entity.getDescription());
                    stmt.setInt(4, entity.getId_user());
                    stmt.setInt(5, id);  
                    int rowsAffected = stmt.executeUpdate();  
                    if (rowsAffected > 0) {
                        System.out.println("evénement mis à jour avec succès.");
                    } else {
                        System.out.println("Aucun événement avec cette id : " + id);
                    }
                }
            });
        } catch (SQLException e) {
            System.out.println("ECHEC lors de la mise à jour de l'événement : " + e.getMessage());
            
        }
    }


    @Override
    public void delete(int id) {
        try {
            Tronsaction.executeInTransaction(conn ->  {
                String sql = "Delete from evenement where id_event=? ";
              PreparedStatement stmt = conn.prepareStatement(sql);
              stmt.setInt(1, id); 
                      try{int  r=stmt.executeUpdate();
                        if (r > 0) {
                            System.out.println("Événement est supprimé ");
                        } else {
                            System.out.println("echec de la suppression.");
                        }
                    }catch(SQLException e){
                        System.out.println(" echec ");
                    }
               });
        } catch (SQLException e) {
            System.out.println("ECHEC " );
        }
    }
}
