import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sender {
    Socket socket = null;
    ServerSocket server = null;
    private List<String> Network = null;

    public Sender(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            initNetwork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initNetwork() {
        this.Network = new ArrayList<String>();
        Network.add("127.0.0.3");
        Network.add("127.0.0.2");
        Network.add("127.0.0.1");
        // Network.add("127.0.0.4");
    }

    public void send(int start) {
        int end = start + 100;
        Random rand = new Random();
        try {
            ObjectOutputStream outobj = new ObjectOutputStream(socket.getOutputStream());
            for (int i = start; i < end; i++) {
                String randomAddress = Network.get(rand.nextInt(Network.size()));
                Payload p = new Payload(randomAddress, i);
                outobj.writeObject(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInet(List<InetAddress> Addresses, int start) {

        int end = start + 100;
        Random rand = new Random();
        try {
            // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream outobj = new ObjectOutputStream(socket.getOutputStream());
            for (int i = start; i < end; i++) {

                String randomAddress = Addresses.get(rand.nextInt(Addresses.size())).getHostName();
                Payload p = new Payload(randomAddress, i);
                // out.println(randomAddress + ":" + i);
                outobj.writeObject(p);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}