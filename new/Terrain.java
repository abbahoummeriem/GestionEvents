import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class Terrain {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        try {
            conn = Conn.getConnection();  
            System.out.println("Connexion réussie!");

            System.out.println("Ajouter un terrain:");
            AjouterTerrain(conn, scanner);

            System.out.println("Liste des terrains:");
            listTerrain(conn);

            scanner.close();
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void AjouterTerrain(Connection conn, Scanner scanner) throws Exception {
        System.out.print("Nom du terrain: ");
        String nomTerrain = scanner.nextLine();
        System.out.print("Type du terrain ");
        String typeTerrain = scanner.nextLine();

        String insertTerrain = "INSERT INTO terrain(nom_terrain, type) VALUES('" + nomTerrain + "', '" + typeTerrain + "')";
        Statement stmt = conn.createStatement();
        int verification = stmt.executeUpdate(insertTerrain);
        if(verification > 0) {
            System.out.println("Terrain ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout du terrain");
        }
    }

    // Méthode pour afficher la liste des terrains
    public static void listTerrain(Connection conn) throws Exception {
        Statement stmtaffiche = conn.createStatement();
        String queryAffiche = "SELECT * FROM terrain";  
        ResultSet rsAffiche = stmtaffiche.executeQuery(queryAffiche);
        System.out.println("La liste des terrains :");
        int i = 1;

        while (rsAffiche.next()) {
            System.out.println("Terrain " + i + ":");
            System.out.println("ID: " + rsAffiche.getInt("id_terrain"));
            System.out.println("Nom du terrain: " + rsAffiche.getString("nom_terrain"));
            System.out.println("Type: " + rsAffiche.getString("type"));
            System.out.println("-------------------");
            i++;
        }
    }
}
