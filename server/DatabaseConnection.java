package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
 
        private static String url = "jdbc:mysql://localhost/gym";    
        private static String username = "root";   
        private static String password = "";
        private static Connection con;
    
        public static Connection getConnection() {
          
                try {
                    con = DriverManager.getConnection(url, username, password);
                System.out.println("Database Connection Establised");
                } catch (SQLException ex) {
                    // log an exception. fro example:
                    System.out.println("Failed to create the database connection."); 
                }
           
            return con;
        }
       
        
    }

