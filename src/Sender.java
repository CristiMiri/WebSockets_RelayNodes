import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class Sender {
    Socket socket = null;
    ServerSocket server = null;

    public Sender(String ip, int port) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            server = new ServerSocket(port, 200, address);
            socket = new Socket(address, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(List<String> Addresses, int start) {

        int end = start + 100;
        Random rand = new Random();
        try {
            // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream outobj = new ObjectOutputStream(socket.getOutputStream());
            for (int i = start; i < end; i++) {

                String randomAddress = Addresses.get(rand.nextInt(Addresses.size()));
                Payload p = new Payload(randomAddress, i);
                // out.println(randomAddress + ":" + i);
                outobj.writeObject(p);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}