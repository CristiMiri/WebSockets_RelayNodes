
public class App {
    public static void main(String[] args) throws Exception {
        // Create the nodes in the network.
        RelayNode D3 = new RelayNode("127.0.0.3", 5003);
        RelayNode D2 = new RelayNode("127.0.0.2", 5002);
        RelayNode D1 = new RelayNode("127.0.0.1", 5001);
        // Setup the connections between the nodes.
        D2.setHopPoint("127.0.0.3", 5003);
        D1.setHopPoint("127.0.0.2", 5002);
        // Start the nodes.
        D3.start();
        D2.start();
        D1.start();
        // Create the sender and send the payloads.
        Sender sender = new Sender("127.0.0.1", 5001);
        sender.send(0);
        // For wireShark to filter out the packets sent by the sender
        // use tcp.port ==5003 || tcp.port ==5002 || tcp.port ==5001 for
        // filtering.
    }
}
