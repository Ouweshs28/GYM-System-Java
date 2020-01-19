
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConsole {
  public static void main(String[] args){

    String hostName = "localhost";
    int portNumber = 9000;

    try (Socket clientSocket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
          System.out.println("Connection to server successful");
      String userInput;
      while ((userInput = stdIn.readLine()) != null) {
        out.println(userInput);
      }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);
    }
  }
}
