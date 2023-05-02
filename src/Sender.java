import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sender {
    Socket socket = null;
    private List<InetAddress> Network = null;

    // Create a client socket and connect,
    // to server at specified port number and IP address.
    // After establish the nodes in the network.
    public Sender(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            System.out.println("Info Sender " + socket);
            initNetwork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a list of nodes in the network,using the IP address,
    // to kwon where to send the payloads.
    public void initNetwork() {
        this.Network = new ArrayList<InetAddress>();
        try {
            Network.add(InetAddress.getByName("127.0.0.3"));
            Network.add(InetAddress.getByName("127.0.0.2"));
            Network.add(InetAddress.getByName("127.0.0.1"));
            // Network.add(InetAddress.getByName("127.0.0.4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send 100 payloads to random nodes in the network.
    // The payload contains the IP address of the node target,
    // and the value that increment from +0 to +99 in each payload.
    // At the end of the loop send a payload with the value -1 and the IP address
    // "end", after close the socket.
    public void send(int start) {
        int end = start + 100;
        Random rand = new Random();
        try {
            ObjectOutputStream outobj = new ObjectOutputStream(socket.getOutputStream());
            for (int i = start; i <= end; i++) {
                String randomAddress = Network.get(rand.nextInt(Network.size())).getHostName();
                Payload p = new Payload(randomAddress, i);
                outobj.writeObject(p);
            }
            Payload endPayload = new Payload("end", -1);
            outobj.writeObject(endPayload);

            Thread.sleep(1000);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}