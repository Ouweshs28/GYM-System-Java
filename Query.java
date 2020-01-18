import java.sql.*;


public class Query{

    private static Connection DBconnect=DatabaseConnection.getConnection();

    public static void listAll(){
        try{
            Statement statement= DBconnect.createStatement();
            String query="SELECT * FROM Booking";
            ResultSet rs=statement.executeQuery(query);

            while(rs.next()){
                System.out.println(rs.getString("bookingID") + '\t'+
                                   rs.getString("staffID")+ '\t'+
                                   rs.getString("trainerID")+ '\t'+
                                   rs.getString("clientname")+ '\t'+
                                   rs.getString("gender")+ '\t'+
                                   rs.getString("focus")+ '\t'+
                                   rs.getString("bookingdate")+ '\t'+
                                   rs.getString("bookingtime")+ '\t'+
                                   rs.getString("duration"));
            }
        }catch(SQLException ex){
                System.out.println("Error"+ ex.getMessage());
            }

        }
        

    }
