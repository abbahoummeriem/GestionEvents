import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Reservation {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        try {
            conn = Conn.getConnection();  
            System.out.println("Connexion réussie!");

            System.out.println("Ajouter une réservation:");
            AjouterReservation(conn, scanner);

            System.out.println("Liste des réservations:");
            listReservation(conn);

            scanner.close();
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void AjouterReservation(Connection conn, Scanner scanner) throws Exception {
        System.out.print("ID Utilisateur: ");
        int idUser = scanner.nextInt();
        System.out.print("ID Evenement: ");
        int idEvent = scanner.nextInt();
        System.out.print("ID Salle: ");
        int idSalle = scanner.nextInt();
        System.out.print("ID Terrain: ");
        int idTerrain = scanner.nextInt();
        scanner.nextLine();  
        System.out.print("Date de reservation (AAAA-MM-JJ): ");
        String dateReservation = scanner.nextLine();
      if(!verifierUser(conn, idUser) || !verifierEvent(conn, idEvent) || !verifierSalle(conn, idSalle) || !verifierTerrain(conn, idTerrain)){
        System.out.println("Erreur: Utilisateur, evenement, salle ou terrain inexistant");
      }else{
        String insertReservation = "INSERT INTO reservation(id_user, id_event, id_salle, id_terrain, date_reservation)VALUES(" + idUser + ", " + idEvent + ", " + idSalle + ", " + idTerrain + ", '" + dateReservation + "')";
        Statement stmt = conn.createStatement();

        int verification = stmt.executeUpdate(insertReservation);

        if (verification > 0) {
            System.out.println("Reservation ajoutée avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout de la reservation");
        }
    
        }
    }

    public static void listReservation(Connection conn) throws Exception {
        Statement stmtaffiche = conn.createStatement();
        String queryAffiche = "SELECT * FROM reservation";  
        ResultSet rsAffiche = stmtaffiche.executeQuery(queryAffiche);
        System.out.println("La liste des réservations :");
        int i = 1;

        while (rsAffiche.next()) {
            System.out.println("Réservation " + i + ":");
        System.out.println("ID: " + rsAffiche.getInt("id_reservation"));
        System.out.println("ID Utilisateur: " + rsAffiche.getInt("id_user"));
         System.out.println("ID Événement: " + rsAffiche.getInt("id_event"));
           System.out.println("ID Salle: " + rsAffiche.getInt("id_salle"));
         System.out.println("ID Terrain: " + rsAffiche.getInt("id_terrain"));
           System.out.println("Date de réservation: " + rsAffiche.getString("date_reservation"));
      System.out.println("-------------------");
            i++;
        }
    }


    public static boolean verifierUser(Connection conn, int idUser) throws Exception {
        String query = "SELECT COUNT(*) FROM users WHERE id_user = "+idUser;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            return res.getInt(1) > 0;  
        }
        return false;  
    }

    public static boolean verifierEvent(Connection conn,int idEvent)throws Exception {
        String query = "SELECT COUNT(*) FROM evenement WHERE id_event = "+idEvent;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            return res.getInt(1) > 0;
        }
        return false;
    }
    public static boolean verifierSalle(Connection conn,int idSalle) throws Exception{
        String query = "SELECT COUNT(*) FROM salle WHERE id_salle = "+idSalle;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            return res.getInt(1) > 0;
        }
        return false;
    }
    public static boolean verifierTerrain(Connection conn, int idTerrain) throws Exception{
        String query = "SELECT COUNT(*) FROM terrain WHERE id_terrain = "+idTerrain;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            return res.getInt(1) > 0;
            }
            return false;   
    }
}
