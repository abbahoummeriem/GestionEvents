import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Deleted {
    public static void supprimerLigne(Connection conn, Scanner scanner, String tableName, String primaryKey) throws Exception {
    System.out.print("Entrer id (" + primaryKey + ") a supprimer: ");
    int primaryKeyValue = scanner.nextInt();
    scanner.nextLine(); 
    String query = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = ?";
    PreparedStatement pstmt = conn.prepareStatement(query);
    pstmt.setInt(1, primaryKeyValue);
    int rows = pstmt.executeUpdate();
    if (rows > 0) {
        System.out.println("la ligne est supprimer avec succès " + tableName + "!");
    } else {
        System.out.println("Id introuvable");
    }
}

}
