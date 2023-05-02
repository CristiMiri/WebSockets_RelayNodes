import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class Sender {
    Socket socket = null;

    public Sender(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(List<String> payLoads) {
        int i = 1;
        int ifinal = i + 100;
        Random rand = new Random();

        try {
            // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream outobj = new ObjectOutputStream(socket.getOutputStream());
            while (i < ifinal) {
                String randomAddress = payLoads.get(rand.nextInt(payLoads.size()));
                Payload p = new Payload(randomAddress, i);
                // out.println(randomAddress + ":" + i);
                outobj.writeObject(p);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}