
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
        
        if (userInput.contains("LISTALL") ||
         userInput.contains("LISTPT") || 
         userInput.contains("LISTCLIENT") ||
         userInput.contains("LISTDAY") ||
         userInput.contains("DELETE")){
          getFromServer(inobj,userInput);
        }
        else{
          System.out.println("Invalid Input try again");
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

  public static void getFromServer(ObjectInputStream inobj,String userInput) {
    ArrayList<Booking> bookings = new ArrayList<Booking>();
    try {
      Object object = inobj.readObject();
      bookings = (ArrayList<Booking>) object;

      if(bookings.size()==0 || bookings==null){
        System.out.println("No result found");
      }else if(!userInput.contains("DELETE")){

      for (Booking i : bookings) {
        i.printBooking();
      }
    }else{
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
}
