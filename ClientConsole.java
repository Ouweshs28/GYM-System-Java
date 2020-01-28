
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientConsole {
  public static void main(String[] args) {

    String hostName = "localhost";
    int portNumber = 9000;

    try (Socket clientSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ObjectInputStream inobj = new ObjectInputStream(clientSocket.getInputStream());
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
      System.out.println("Connection to server successful");
      String userInput;
      while ((userInput = stdIn.readLine()) != null) {
        out.println(userInput);
        boolean valid;
        if (valid = validateInput(userInput)) {
          if (userInput.contains("LIST")) {
            if (valid = validateID(userInput, 1)) {
              getFromServer(inobj, userInput);
            }
          } else if (userInput.contains("ADD")) {
            if (valid = validateID(userInput, 1)) {
              getFromServerAdd(inobj);
            }
          }
        }

      }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);
    }
  }

  public static void getFromServerAdd(ObjectInputStream inobj) {
    Integer status;
    try {
      status = (Integer) inobj.readObject();
      switch (status) {
      case 0:
        System.err.println("Dublicate BookingID, perform UPDATE!");
        break;
      case 1:
        System.err.println("Please check trainer inputs, trainer specialism doesnt match!");
        break;
      case 2:
        System.out.println("Add Successful");
        break;
      }

    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Exception: ");
      e.printStackTrace();
    }
  }

  public static void getFromServer(ObjectInputStream inobj, String userInput) {
    ArrayList<Booking> bookings = new ArrayList<Booking>();
    try {
      Object object = inobj.readObject();
      bookings = (ArrayList<Booking>) object;

      if (bookings.size() == 0 || bookings == null) {
        System.out.println("No result found");
      } else if (!userInput.contains("DELETE")) {

        for (Booking i : bookings) {
          i.printBooking();
        }
      } else {
        System.out.println("Successfully deleted: ");
        bookings.get(0).printBooking();
      }

    } catch (ClassNotFoundException e) {
      System.err.println("The title list has not come from the server");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("IO Exception: ");
      e.printStackTrace();
    }
  }

  public static boolean validateInput(String userInput) {
    boolean valid = true;
    String[] userInputArray = Query.splitInput(userInput);
    if (!userInput.contains("LISTALL") && !userInput.contains("LISTPT") && !userInput.contains("LISTCLIENT")
        && !userInput.contains("LISTDAY") && !userInput.contains("DELETE") && !userInput.contains("ADD")
        && !userInput.contains("UPDATE")) {

      System.err.println("Inavalid Commad, try again!");
      valid = false;
    }

    if (userInput.contains("LISTALL") && userInputArray.length > 1) {

      System.err.println("Invalid command provided: Usage LISTALL");
      valid = false;
    }

    else if (userInput.contains("LISTPT") && (userInputArray.length > 2 || userInputArray.length == 1)) {

      System.err.println("Invalid command provided: Usage LISTPT <PT ID>");
      valid = false;
    }

    else if (userInput.contains("LISTCLIENT") && (userInputArray.length > 2 || userInputArray.length == 1)) {

      System.err.println("Invalid command provided: Usage LISTCLIENT <CLIENT ID>");
      valid = false;
    }

    else if (userInput.contains("LISTDAY") && (userInputArray.length > 2 || userInputArray.length == 1)) {

      System.err.println("Invalid command provided: Usage LISTDAY <DATE> FORMAT 'YYYY-MM-DD'");
      valid = false;
    }

    else if (userInput.contains("DELETE") && (userInputArray.length > 2 || userInputArray.length == 1)) {

      System.err.println("Invalid command provided: Usage DELETE <BookingID>");
      valid = false;
    } else if (userInput.contains("ADD") && (userInputArray.length > 12 || userInputArray.length < 1)) {
      System.err.println(
          "Invalid command provided: Usage ADD <BookingID> <ClientID> <TrainerID> <ClientID> <Client Name> <Client Gender> <Focus> <Date> <Start Time> <Duration> <End Time>");
      valid = false;
    }

    return valid;

  }

  public static Boolean validateID(String userInput, Integer postion) {
    String[] userInputArray = Query.splitInput(userInput);
    Boolean valid = false;
    if (userInputArray[postion].startsWith("B") && userInputArray[postion].startsWith("C")
        && userInputArray[postion].startsWith("T") || userInputArray[postion].length() == 4) {
      valid = true;
    } else {
      System.err.println("Invalid ID entered!");
    }
    return valid;
  }

}
