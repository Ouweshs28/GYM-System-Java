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
                Scanner in = new Scanner(ins);
                ObjectOutputStream outobj = new ObjectOutputStream(outs);)

        {
            String userinput;
            while (in.hasNextLine()) {
                userinput = in.nextLine();
                String result[] = Query.splitInput(userinput);
                if (userinput.contains("ADD")) {

                    System.out.println("ADD Operation: " + userinput + " from client: " + socket.toString());
                } else if (userinput.equals("LISTALL") && result.length == 1) {

                    sendAllResult(outobj);
                    System.out.println("LISTALL Operation: " + userinput + " from client: " + socket.toString());
                } else if (userinput.contains("LISTPT") && result.length == 2) {

                    sendQueryResult(outobj, result);
                    System.out.println(
                            "LIST PERSONAL TRANIER Operation: " + userinput + " from client: " + socket.toString());

                } else if (userinput.contains("LISTCLIENT") && result.length == 2) {
                    System.out.println("LIST Client Operation: " + userinput + " from client: " + socket.toString());
                    sendQueryResult(outobj, result);
                } else if (userinput.contains("LISTDAY") && result.length == 2) {
                    System.out.println(
                            "LIST BOOKING DAYS Operation: " + userinput + " from client: " + socket.toString());
                    sendQueryResult(outobj, result);
                } else if (userinput.contains("UPDATE")) {

                    System.out.println("UPDATE BOOKING Operation: " + userinput + " from client: " + socket.toString());
                } else if (userinput.contains("DELETE") && result.length == 2) {

                    System.out.println("DELETE BOOKING Operation: " + userinput + " from client: " + socket.toString());
                    sendQueryResult(outobj, result);
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

    public void sendQueryResult(ObjectOutputStream outobj, String[] split) {
        ArrayList<Booking> bookings = Query.listQuries(split);
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
