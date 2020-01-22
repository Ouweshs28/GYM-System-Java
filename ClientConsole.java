
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
        
        switch (userInput) {
        case "LISTALL":
          getFromServer(inobj);
          break;
          case "LISTPT":
          getFromServer(inobj);
        break;
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

  public static void getFromServer(ObjectInputStream inobj){
    ArrayList<Booking> bookings = new ArrayList<Booking>();
    try {
      Object object = inobj.readObject();
      bookings =  (ArrayList<Booking>) object;
     for(Booking i:bookings){
       i.printBooking();
     }
  } catch (ClassNotFoundException e) {
    System.err.println("The title list has not come from the server");
      e.printStackTrace();
  } catch (IOException e) {
    System.err.println("The title list has not come from the server");
      e.printStackTrace();
    }
  }
}
