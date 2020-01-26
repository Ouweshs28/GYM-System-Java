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
                bookings.add(new Booking(rs.getString("bookingID"), rs.getString("trainerID"), rs.getString("clientID"),
                        rs.getString("focus"), rs.getString("bookingDate"), rs.getString("bookingTime"),
                        rs.getString("duration"), rs.getString("endtime")));
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
        return bookings;

    }

    public static ArrayList<Booking> listQuries(String[] result) {
        String query = null;

        switch (result[0]) {
        case "LISTPT":
            query = " SELECT * FROM Booking " + " WHERE trainerID = ?";
            break;
        case "LISTCLIENT":
            query = " SELECT * FROM Booking " + " WHERE clientID = ?";
            break;
        case "LISTDAY":
            query = " SELECT * FROM Booking " + " WHERE bookingdate = ?";
            break;
        case "DELETE":
            query = " SELECT * FROM Booking " + " WHERE bookingID = ?";
            break;
        }

        ArrayList<Booking> bookings = new ArrayList<Booking>();

        try {

            PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
            preparedStatement.setString(1, result[1]);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(rs.getString("bookingID"), rs.getString("trainerID"), rs.getString("clientID"),
                        rs.getString("focus"), rs.getString("bookingDate"), rs.getString("bookingTime"),
                        rs.getString("duration"), rs.getString("endtime")));
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
        if(result[0].equals("DELETE")){
            deleteFromDB(bookings, result[1]);
        }

        return bookings;
    }

    public static String[] splitInput(String str) {
        String split[] = str.split(" ", 0);
        return split;
    }

    public static void deleteFromDB(ArrayList<Booking> bookings, String delete) {
        String query = "DELETE FROM Booking WHERE bookingID = ? ";
        if (bookings.size() != 0) {
            try {
                PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
                preparedStatement.setString(1, delete);

                preparedStatement.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("SQL error: " + ex.getMessage());
            }
        }

    }

}
