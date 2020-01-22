import java.sql.*;
import java.util.ArrayList;

public class Query {

    private static Connection DBconnect = DatabaseConnection.getConnection();

    public static ArrayList<Booking> listAll() {
        ArrayList<Booking> bookings = new ArrayList<Booking>();

        try {
            Statement statement = DBconnect.createStatement();
            String query = "SELECT * FROM Booking";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                bookings.add(new Booking(rs.getString("bookingID"), rs.getString("staffID"), rs.getString("trainerID"),
                        rs.getString("clientname"), rs.getString("gender"), rs.getString("focus"),
                        rs.getString("bookingdate"), rs.getString("bookingtime"), rs.getDouble("duration")));
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return bookings;

    }

    public static ArrayList<Booking> listPTID(String result) {

        ArrayList<Booking> bookings = new ArrayList<Booking>();

        try {
            String query = " SELECT * FROM Booking " + " WHERE trainerID = ?";
            PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
            preparedStatement.setString(1, result);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(rs.getString("bookingID"), rs.getString("staffID"), rs.getString("trainerID"),
                        rs.getString("clientname"), rs.getString("gender"), rs.getString("focus"),
                        rs.getString("bookingdate"), rs.getString("bookingtime"), rs.getDouble("duration")));
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }

        return bookings;
    }

    public static String[] splitInput(String str) {
        String split[] = str.split(" ", 0);
        return split;
    }

}
