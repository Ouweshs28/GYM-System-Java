
package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import common.*;

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
        boolean valid;
        if (valid = validateInput(userInput)) {
          out.println(userInput);
          if (userInput.contains("LIST") || userInput.contains("DELETE")) {
            getFromServer(inobj, userInput);
          } else if (userInput.contains("ADD")) {
            getFromServerAdd(inobj);
          } else if (userInput.contains("UPDATE")) {
            getFromServerUpdate(inobj);
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
        System.err.println("Trainer is already booked, please enter a different date or time");
        break;
      case 3:
        System.out.println("Add Successful");
        break;
      }

    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Exception: ");
      e.printStackTrace();
    }
  }

  public static void getFromServerUpdate(ObjectInputStream inobj) {
    Integer status;
    try {
      status = (Integer) inobj.readObject();
      switch (status) {
      case 0:
        System.err.println("No BookingID found to update, use ADD command!");
        break;
      case 1:
        System.err.println("Please check trainer inputs, trainer specialism doesnt match!");
        break;
      case 2:
        System.err.println("Trainer is already booked, please enter a different date or time");
        break;
      case 3:
        System.out.println("Update Successful!");
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
    Functions split=new Functions();
    String[] userInputArray = split.splitInput(userInput);
    if (!userInput.contains("LISTALL") && !userInput.contains("LISTPT") && !userInput.contains("LISTCLIENT")
        && !userInput.contains("LISTDAY") && !userInput.contains("DELETE") && !userInput.contains("ADD")
        && !userInput.contains("UPDATE")) {

      System.err.println("Inavalid Commad, try again!");
      valid = false;
      return valid;
    }

    if (userInput.contains("LISTALL") && userInputArray.length > 1) {

      System.err.println("Invalid command provided: Usage LISTALL");
      valid = false;
      return valid;
    }

    if (userInput.contains("LISTPT") && (userInputArray.length > 2 || userInputArray.length == 1)) {

      System.err.println("Invalid command provided: Usage LISTPT <PT ID>");
      valid = false;
      return valid;
    }

    if (userInputArray.length > 1) {
      if ((userInput.contains("LISTPT")) && (!userInputArray[1].startsWith("T"))) {
        System.err.println("Invalid TrainerID! Format- TXXX");
        valid = false;
        return valid;

      }
    }

    if (userInput.contains("LISTCLIENT") && (userInputArray.length != 2)) {

      System.err.println("Invalid command provided: Usage LISTCLIENT <CLIENT ID>");
      valid = false;
      return valid;
    }

    if (userInputArray.length == 2) {
      if ((userInput.contains("LISTCLIENT")) && (!userInputArray[1].startsWith("C"))) {
        System.err.println("Invalid ClientID! Format- CXXX");
        valid = false;
        return valid;

      }
    }
    if (userInput.contains("LISTDAY") && (userInputArray.length != 2)) {
      System.err.println("Invalid command provided: Usage LISTDAY <DATE>");
      valid = false;
      return valid;
    }
    if (userInputArray.length > 1) {
      if (userInput.contains("LISTDAY")
          && (!userInputArray[1].matches("[0-9]{4}[-]{1}[0-1]{1}[0-2]{1}[-]{1}[0-3]{1}[0-9]{1}"))) {
        System.err.println("Invalid DATE! Format- YYYY-MM-DD");
        valid = false;
        return valid;
      }

    }
    if (userInput.contains("DELETE") && (userInputArray.length > 2 || userInputArray.length == 1)) {

      System.err.println("Invalid command provided: Usage DELETE <BookingID>");
      valid = false;
      return valid;
    }

    if ((userInput.contains("DELETE")) && (!userInputArray[1].startsWith("B"))) {
      System.err.println("Invalid BookingID! Format- BXXX");
      valid = false;
      return valid;

    }
    if (userInput.contains("ADD") && (userInputArray.length != 12)) {
      System.err.println(
          "Invalid command provided: Usage ADD <BookingID> <ClientID> <TrainerID> <ClientID> <Client Name> <Client Gender> <Focus> <Date> <Start Time> <Duration> <End Time>");
      valid = false;
      return valid;
    }

    if (userInput.contains("UPDATE") && (userInputArray.length != 12)) {
      System.err.println(
          "Invalid command provided: Usage UPDATE <BookingID> <ClientID> <TrainerID> <ClientID> <Client Name> <Client Gender> <Focus> <Date> <Start Time> <Duration> <End Time>");
      valid = false;
      return valid;
    }

    if (userInputArray.length > 1) {
      if (((userInput.contains("ADD") || userInput.contains("UPDATE")) && (!userInputArray[1].startsWith("B")))) {

        System.err.println("Invalid BookingID! Format- BXXX");
        valid = false;
        return valid;
      }
    }

    if (userInputArray.length > 2) {
      if (((userInput.contains("ADD") || userInput.contains("UPDATE")) && (!userInputArray[2].startsWith("T")))) {

        System.err.println("Invalid TrainerID! Format- TXXX");
        valid = false;
        return valid;
      }
    }

    if (userInputArray.length > 3) {
      if (((userInput.contains("ADD") || userInput.contains("UPDATE")) && (!userInputArray[3].startsWith("C")))) {

        System.err.println("Invalid ClientID! Format- CXXX");
        valid = false;
        return valid;
      }

    }
    if (userInputArray.length > 4) {
      if (((userInput.contains("ADD") || userInput.contains("UPDATE"))
          && (!userInputArray[4].matches("^[\\p{L} .'-]+$") && (!userInputArray[5].matches("^[\\p{L} .'-]+$"))))) {

        System.err.println("Invalid Name! Do not input numbers");
        valid = false;
        return valid;
      }

    }
    if (userInputArray.length > 6) {
      if (((userInput.contains("ADD")
          || userInput.contains("UPDATE")) && !(userInputArray[6].equals("M") || !userInputArray[6].equals("F")))) {
        System.err.println("Invalid Client Gender!: M for Male F for female");
        valid = false;
        return valid;
      }
    }
    if (userInputArray.length > 8) {
      if ((userInput.contains("ADD") || userInput.contains("UPDATE"))
          && (!userInputArray[8].matches("[0-9]{4}[-]{1}[0-1]{1}[0-2]{1}[-]{1}[0-3]{1}[0-9]{1}"))) {

        System.err.println("Invalid DATE! Format- YYYY-MM-DD");
        valid = false;
        return valid;
      }
    }

    if (userInputArray.length > 9) {
      if ((userInput.contains("ADD") || userInput.contains("UPDATE")) && (!userInputArray[9].matches("\\d{6}"))) {
        System.err.println("Invalid Time! Enter 100000 for 10 00am for example");
        valid = false;
        return valid;

      }
    }

    if (userInputArray.length > 10) {
      if ((userInput.contains("ADD") || userInput.contains("UPDATE")) && (!userInputArray[10].matches("\\d{6}"))) {
        System.err.println("Invalid Time! Enter 100000 for 10 00am for example");
        valid = false;
        return valid;

      }
    }
    if (userInputArray.length > 11) {
      if ((userInput.contains("ADD") || userInput.contains("UPDATE")) && (!userInputArray[11].matches("\\d{6}"))) {
        System.err.println("Invalid Time! Enter 100000 for 10 00am for example");
        valid = false;
        return valid;

      }
    }
    return valid;

  }
}
