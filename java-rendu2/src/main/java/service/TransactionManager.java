package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionManager {
    // private final String url = "jdbc:mysql://localhost:3306/java";
    // private final String user = "root";
    // private final String password = "";

    public static final String URL = "jdbc:postgresql://localhost:5432/gestionevents";
    public static final String USER = "postgres";
    public static final String PASSWORD = "*****";


    public void executeInTransaction(SQLConsumer<Connection> operation) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false); 
            try {
                operation.accept(conn); 
                conn.commit(); 
            } catch (SQLException e) {
                conn.rollback(); 
                throw e; 
            }
        }
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
