import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Update {
    public static void updateTable(Connection conn, Scanner scanner, String tableName, String primaryKey, int primaryKeyValue) throws Exception {
    System.out.println("Enter the columns to update (comma-separated): ");
    String[] columns = scanner.nextLine().split(",\\s*");
    String query = "UPDATE " + tableName + " SET " +
            String.join(" = ?, ", columns) + " = ? WHERE " + primaryKey + " = ?";
    PreparedStatement pstmt = conn.prepareStatement(query);
    for (int i = 0; i < columns.length; i++) {
        System.out.print("Enter new value for " + columns[i] + ": ");
        pstmt.setString(i + 1, scanner.nextLine());
    }
    pstmt.setInt(columns.length + 1, primaryKeyValue); 
    int res = pstmt.executeUpdate();
    if(res>0)
    System.out.println("mide a jour réussie");
 else
    System.out.println("ID introuvable.");
}

}
