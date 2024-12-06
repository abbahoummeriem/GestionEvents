import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

public class Create {
    

        public static void main(String[] args) throws Exception {
        //     String url="jdbc:mysql://localhost:3306/gestionevents";
        //     String driver = "com.mysql.cj.jdbc.Driver";
        //     String user = "root";
        //    String password="";
        //     try{
        //         Class.forName(driver);
        // Connection conn=DriverManager.getConnection(url, user, password);

        // System.out.println("Connexion réussie!");

        Scanner scanner=new Scanner(System.in);
        
        Connection conn =null;
        try {
            conn = Conn.getConnection();  // Obtenir la connexion à la base de données
            System.out.println("Connexion réussie!");

            // Ajouter un utilisateur
            System.out.println("Ajouter un utilisateur:");
            ajouterUser(conn, scanner);
        scanner.close();
            conn.close();
        
    }catch(Exception e){
        e.printStackTrace();
        }
    }




    public static void ajouterUser(Connection conn,Scanner scanner) throws Exception{
        System.err.println("entrer le nom :");
        String nomuser=scanner.nextLine();
        System.err.println("entrer le prenom :");
        String prenomuser=scanner.nextLine();
        System.err.println("entrer le email :");
        String emailuser=scanner.nextLine();
        System.err.println("entrer le type soit 1 (etudiant)soit 2 (professeur) :");
        int typee=scanner.nextInt();

        // Statement stmt = conn.createStatement();
        // String query = "INSERT INTO users(nom,prenom,email,type) VALUES("+nomuser+","+prenomuser+","+emaill+","+typee+")"; 
        // stmt.executeUpdate(query);

        String insertuser = "INSERT INTO users(nom, prenom, email, type) VALUES ('" + nomuser + "', '" + prenomuser + "', '" + emailuser + "', " + typee + ")";
        Statement stmt = conn.createStatement();
       int verification= stmt.executeUpdate(insertuser);
        if(verification>0){
        System.out.println("Utilisateur ajouté avec succès !");}
        else{  System.out.println("Erreur lors de l'ajout de l'utilisateur");}
        
        }

        public static void listUser(Connection conn) throws Exception{
        Statement stmtaffiche = conn.createStatement();
        String queryaffiche = "SELECT * From users";  
        ResultSet rsaffiche = stmtaffiche.executeQuery(queryaffiche);
        System.out.println("la liste des utilisateurs :");
        int i=1 ;
        while (rsaffiche.next()) {
            System.out.println("Utilisateur "+i+":");
            System.out.println("ID: " + rsaffiche.getInt("id_user"));
            System.out.println("Nom: " + rsaffiche.getString("nom"));
            System.out.println("PRENOM: " + rsaffiche.getString("prenom"));
            System.out.println("EMAIL: " + rsaffiche.getString("email"));
            System.out.println("TYPE: " + rsaffiche.getString("type"));
            System.out.println("-------------------");
            i++;
            }
        }


    }