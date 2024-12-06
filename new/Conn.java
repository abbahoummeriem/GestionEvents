import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
   public static String url="jdbc:mysql://localhost:3306/gestionevents";
   public static String user = "root";
   public static String password="";

  static{
     try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e){
         System.out.println("Error");
        e.printStackTrace();
        }
    }   

     public static Connection getConnection() throws SQLException{
       return DriverManager.getConnection(url, user, password);
         
     }

}