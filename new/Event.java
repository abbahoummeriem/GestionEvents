import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.SQLException;

public class Event {
    public static void main(String[] args) throws Exception {

    Scanner scanner=new Scanner(System.in);
    Connection conn =null;
    try {
        conn = Conn.getConnection();  
        System.out.println("Connexion réussie!");

        // System.out.println("Ajouter un evenement:");
        // AjouterEvent(conn, scanner);
        System.out.println("Supprimer un evenement:");
        Deleted.supprimerLigne(conn,scanner,"evenement","id_event");
        System.out.println("mise a jour d'un evenement :");
       Update.updateTable(conn, scanner, "evenement", "id_event", 2);

        System.out.println("Liste des evenements:");
         listEvent(conn);
        //  Update.updateTable(conn, scanner, "evenement", "id_event", 1);
        
        scanner.close();
        conn.close();
    
        }catch(Exception e){
        e.printStackTrace();
        }
    }



    public static void AjouterEvent(Connection conn,Scanner scanner)throws Exception{
        System.out.print("Nom de l'événement : ");
        String nomEvent = scanner.nextLine();
        System.out.print("Date (AAAA-MM-JJ) : ");
        String dateEvent = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("ID Utilisateur : ");
        int idUser = scanner.nextInt();

    if(!verifierUser(conn,idUser)){
    System.out.println("L'utilisateur n'existe pas");
    }else{
        String insertevent = "INSERT INTO evenement(nom_event, date_event, description, id_user) VALUES('" + nomEvent + "', '" + dateEvent + "', '" + description + "', " + idUser + ")";
        Statement stmt = conn.createStatement();
        int verification= stmt.executeUpdate(insertevent);
        if(verification>0){
            System.out.println("Evenement ajouté avec succès !");}
            else{  System.out.println("Erreur lors de l'ajout de l'evenement"); }

    }
}

    public static void listEvent(Connection conn) throws Exception{
        Statement stmtaffiche = conn.createStatement();
        String queryaffiche = "SELECT * From evenement";  
        ResultSet rsaffiche = stmtaffiche.executeQuery(queryaffiche);
        System.out.println("la liste des evenements :");
        int i=1 ;

            while (rsaffiche.next()) {
                System.out.println("Evenement "+i+":");
                System.out.println("ID: " + rsaffiche.getInt("id_event"));
                System.out.println("Nom de l'événement: " + rsaffiche.getString("nom_event"));
                System.out.println("Date: " + rsaffiche.getDate("date_event"));
                System.out.println("Description: " + rsaffiche.getString("description"));
                System.out.println("ID Utilisateur: " + rsaffiche.getInt("id_user"));
                System.out.println("-------------------");
                i++;

        }

    }

    public static boolean verifierUser(Connection conn, int idUser) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE id_user = "+idUser;
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet res = pstmt.executeQuery();
        if (res.next()) {
            return res.getInt(1) > 0;  
        }
        return false;  
    }

}