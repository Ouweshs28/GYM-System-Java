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
                userinput = in.nextLine();
                if (userinput.contains("ADD")) {
                    System.out.println("ADD Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                else if (userinput.equals("LISTALL")) {
                    sendAllResult(outobj);
                }
                else if (userinput.contains("LISTPT")) {
                    String result[]=Query.splitInput(userinput);
                    sendPTIDResult(outobj, result);
                    System.out.println(
                            "LIST PERSONAL TRANIER Operation: " + userinput + " from client: " + socket.toString());
                            
                    //out.println(result[0]);
                }
                else if (userinput.contains("LISTCLIENT")) {
                    System.out.println("LIST Client Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                else if (userinput.contains("LISTDAY")) {
                    System.out.println(
                            "LIST BOOKING DAYS Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                else if (userinput.contains("UPDATE")) {
                    System.out.println("UPDATE BOOKING Operation: " + userinput + " from client: " + socket.toString());
                    out.println(userinput);
                }
                else if (userinput.contains("DELETE")) {
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
    public void sendAllResult(ObjectOutputStream outobj) {
        ArrayList<Booking> bookings = Query.listAll();
        sendResultToClient(outobj, bookings);

    }

    public void sendPTIDResult(ObjectOutputStream outobj, String[] split) {
        ArrayList<Booking> bookings = Query.listPTID(split[1]);
        sendResultToClient(outobj, bookings);
    }

    public void sendResultToClient(ObjectOutputStream outobj, ArrayList<Booking> bookings) {
        try {
            outobj.reset();
            outobj.flush();
            outobj.writeObject(bookings);
            System.out.println("Written");
        } catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }
}
