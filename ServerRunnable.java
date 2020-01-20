import java.net.Socket;
import java.io.*;
import java.util.*;

public class ServerRunnable implements Runnable {

    private Socket socket;

    public ServerRunnable(Socket sk) {
        this.socket = sk;
    }

    public void run() {
        try (OutputStream outs = socket.getOutputStream();
                InputStream ins = socket.getInputStream();
                PrintWriter out = new PrintWriter(outs, true);
                Scanner in = new Scanner(ins);
                ObjectOutputStream outobj = new ObjectOutputStream(outs);)

        {
            String userinput;
            while (in.hasNextLine()) {
                System.out.println();
                userinput = in.nextLine();
                if (userinput.contains("ADD")) {
                    System.out.println("ADD Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                if (userinput.contains("LISTALL")) {
                    ArrayList<Booking> bookings = Query.listAll();
                    try {
                        outobj.writeObject(bookings);
                        System.out.println("Written");
                    } catch (IOException e) {
                        System.out.println("IO Exception");
                        e.printStackTrace();
                    }
                    System.out.println("LIST ALL Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                if (userinput.contains("LISTPT")) {
                    System.out.println(
                            "LIST PERSONAL TRANIER Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                if (userinput.contains("LISTCLIENT")) {
                    System.out.println("LIST Client Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                if (userinput.contains("LISTDAY")) {
                    System.out.println(
                            "LIST BOOKING DAYS Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                if (userinput.contains("UPDATE")) {
                    System.out.println("UPDATE BOOKING Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                if (userinput.contains("DELETE")) {
                    System.out.println("DELETE BOOKING Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                } else {
                    System.out.println("Invalid Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }

            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port");
            System.out.println(e.getMessage());
        }
    }
}