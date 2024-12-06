import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class Salle {
    public static void main(String[] args) throws Exception {

        Scanner scanner=new Scanner(System.in);
        Connection conn =null;
        try {
            conn = Conn.getConnection();  
            System.out.println("Connexion réussie!");
    
            System.out.println("Ajouter une salle:");
            AjouterSalle(conn, scanner);
            System.out.println("Liste des salles:");
             listSalle(conn);
            
            scanner.close();
            conn.close();
        
            }catch(Exception e){
            e.printStackTrace();
            }
        }
    
    public static void AjouterSalle(Connection conn,Scanner scanner)throws Exception{
        System.out.print("Nom de la salle: ");
        String nomSalle = scanner.nextLine();
        System.out.print("Capacite : ");
        int capacite = scanner.nextInt();

        String insertsalle= "INSERT INTO salle(nom_salle,capacite) VALUES('"+nomSalle+"','"+capacite+"')";
        Statement stmt = conn.createStatement();
        int verification= stmt.executeUpdate(insertsalle);
        if(verification>0){
            System.out.println("Salle ajouté avec succès !");}
            else{  System.out.println("Erreur lors de l'ajout de la salle"); }

    }

    public static void listSalle(Connection conn) throws Exception{
        Statement stmtaffiche = conn.createStatement();
        String queryaffiche = "SELECT * From salle";  
        ResultSet rsaffiche = stmtaffiche.executeQuery(queryaffiche);
        System.out.println("la liste des salles :");
        int i=1 ;

            while (rsaffiche.next()) {
                System.out.println("Salle "+i+":");
                System.out.println("ID: " + rsaffiche.getInt("id_salle"));
                System.out.println("Nom de la salle: " + rsaffiche.getString("nom_salle"));
                System.out.println("capacite: " + rsaffiche.getInt("capacite"));
                System.out.println("-------------------");
                i++;

        }
    }


}


