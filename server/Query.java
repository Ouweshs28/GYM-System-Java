package server;

import java.sql.*;
import java.util.ArrayList;
import common.*;

public class Query {

    private static Connection DBconnect = DatabaseConnection.getConnection();

    public ArrayList<Booking> listAll() {
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

    public ArrayList<Booking> listQuries(String[] result) {
        String query = null;

        switch (result[0]) {
        case "LISTPT":
            query = " SELECT * FROM Booking " + " WHERE trainerID = ?";
            break;
        case "LISTCLIENT":
            query = " SELECT * FROM Booking " + " WHERE clientID = ?";
            break;
        case "LISTDAY":
            query = " SELECT * FROM Booking " + " WHERE bookingDate = ?";
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
        if (result[0].equals("DELETE")) {
            deleteFromDB(bookings, result[1]);
        }

        return bookings;
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

    public static Boolean verifyBookingid(String bid) {
        Boolean dublicate = false;
        String query = " SELECT * FROM Booking " + " WHERE bookingID = ?";
        try {

            PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
            preparedStatement.setString(1, bid);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dublicate = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
        return dublicate;
    }

    public static Boolean verifyTrainer(String tid, String focus) {
        Boolean valid = false;
        String query = " SELECT * FROM Specialism " + " WHERE trainerID = ? AND focus= ?";
        try {

            PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
            preparedStatement.setString(1, tid);
            preparedStatement.setString(2, focus);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                valid = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
        return valid;
    }

    public static Boolean checkClient(String cid) {
        Boolean exist = false;
        String query = " SELECT * FROM Booking " + " WHERE clientID = ?";
        try {

            PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
            preparedStatement.setString(1, cid);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
        return exist;
    }

    public static void addClient(String[] userInput) {
        String add = "INSERT INTO Client VALUES(?, ?, ?)";
        String cid = userInput[3];
        String fname = userInput[4];
        String lame = userInput[5];
        String cname = fname + " " + lame;
        String gender = userInput[6];
        try {
            PreparedStatement preparedStatement = DBconnect.prepareStatement(add);
            preparedStatement.setString(1, cid);
            preparedStatement.setString(2, cname);
            preparedStatement.setString(3, gender);

            preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }

    }

    public static void addBooking(String[] add) {
        /*
         * 1-BookingID - Position 1 2-TrainerID - Position 2 3-ClientID - Position 3
         * 4-CLientDetails- Postion 4-FName, 5 LastName, 6 Gender
         * 
         * 
         */
        String addQury = "INSERT INTO Booking VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = DBconnect.prepareStatement(addQury);
            preparedStatement.setString(1, add[1]);
            preparedStatement.setString(2, add[2]);
            preparedStatement.setString(3, add[3]);
            preparedStatement.setString(4, add[7]);
            preparedStatement.setString(5, add[8]);
            preparedStatement.setString(6, add[9]);
            preparedStatement.setString(7, add[10]);
            preparedStatement.setString(8, add[11]);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
    }

    public static void updateBooking(String[] add) {
        /*
         * 1-BookingID - Position 1 2-TrainerID - Position 2 3-ClientID - Position 3
         * 4-CLientDetails- Postion 4-FName, 5 LastName, 6 Gender
         * 
         * 
         */
        String update = "UPDATE Booking SET bookingID=?, trainerID=?, clientID=?, focus=?, bookingDate=?, bookingTime=?, duration=?, endtime=? WHERE bookingID=?";
        try {
            PreparedStatement preparedStatement = DBconnect.prepareStatement(update);
            preparedStatement.setString(1, add[1]);
            preparedStatement.setString(2, add[2]);
            preparedStatement.setString(3, add[3]);
            preparedStatement.setString(4, add[7]);
            preparedStatement.setString(5, add[8]);
            preparedStatement.setString(6, add[9]);
            preparedStatement.setString(7, add[10]);
            preparedStatement.setString(8, add[11]);
            preparedStatement.setString(9, add[1]);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
    }

    public Boolean checkTrainerAvaiable(String tid, String date, String time) {
        Boolean exist = false;
        System.out.println(tid + " " + date + " " + time);
        String query = "SELECT EXISTS (SELECT * FROM Booking WHERE trainerID = ? AND bookingDate= ? AND bookingTime=?);";
        try {

            PreparedStatement preparedStatement = DBconnect.prepareStatement(query);
            preparedStatement.setString(1, tid);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);

            ResultSet rs = preparedStatement.executeQuery();
            /*
             * while (rs.next()) { exist = true; }
             */
            int result = 0;
            if (rs.next()) {
                result = rs.getInt(1);
            }
            if (result == 1) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
        return exist;

    }

    public Integer performAdd(String[] userInput) {
        boolean confrim = false, valid = false, dublicate = false;
        Integer status = 0;// Dublicate BookingID
        System.out.println("Vertify Booking");
        dublicate = verifyBookingid(userInput[1]);
        if (!dublicate) {
            System.out.println("Booking Passed");
            valid = verifyTrainer(userInput[2], userInput[7]);
            status = 1; // Trainer Details does not match
            if (valid) {
                System.out.println("Trainer Passed");
                boolean clienExist = checkClient(userInput[3]);
                System.out.println("Checking Client");
                if (!clienExist) {
                    System.out.println("Creating Client");
                    addClient(userInput);
                }
            }

            valid = checkTrainerAvaiable(userInput[2], userInput[8], userInput[9]);
            if (valid) {
                status = 2;
            } else {
                confrim = true;
            }
        }
        if (confrim) {
            addBooking(userInput);
            status = 3;

        }
        return status;
    }

    public Integer performUpdate(String[] userInput) {
        boolean confrim = false, valid = false, dublicate = false;
        Integer status = 0;// Dublicate BookingID
        System.out.println("Vertify Booking");
        dublicate = verifyBookingid(userInput[1]);
        if (!dublicate) {
            return status;
        } else {
            valid = verifyTrainer(userInput[2], userInput[7]);
            status = 1; // Trainer Details does not match
            if (valid) {
                System.out.println("Trainer Passed");
                boolean clienExist = checkClient(userInput[3]);
                System.out.println("Checking Client");
                if (!clienExist) {
                    System.out.println("Creating Client");
                    addClient(userInput);
                }

            }
            valid = checkTrainerAvaiable(userInput[2], userInput[8], userInput[9]);
            if (valid) {
                status = 2;
            } else {
                confrim = true;
            }
        }
        if (confrim) {
            addBooking(userInput);
            status = 3;

        }

        return status;

    }

}
