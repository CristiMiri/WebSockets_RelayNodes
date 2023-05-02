import java.io.*;
import java.net.*;

public class RelayNode extends Thread {
    ServerSocket receiver = null;
    Socket sender = null;
    private String IpAddres = null;
    private int port = 0;
    private String hopAddress = null;
    private int hopPort = 0;
    private ObjectOutputStream outobj = null;
    private ObjectInputStream inobj = null;
    private Payload endPayload = new Payload("end", -1);

    // Create a server socket and bind it to a specified port number and IP address.
    // Memorize the IP address and the port number for later use.
    public RelayNode(String receiveAddr, int receivePort) {
        try {
            InetAddress addr = InetAddress.getByName(receiveAddr);
            receiver = new ServerSocket(receivePort, 200, addr);
            IpAddres = receiveAddr;
            port = receivePort;
            // InetSocketAddress socketAddress = new InetSocketAddress(addr, receivePort);
            // receiver = new ServerSocket();
            // receiver.bind(socketAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set the IP address and the port number of the next node in the network.
    // And a object output stream to send the payloads.
    public void setHopPoint(String hopAddress, int hopPort) {
        this.hopAddress = hopAddress;
        this.hopPort = hopPort;
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(hopAddress);
            sender = new Socket(addr, hopPort);
            outobj = new ObjectOutputStream(sender.getOutputStream());
            System.out.println("Info Relay Sender " + sender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Process the payload, if the IP address of the payload is the same as the
    // current node, print the value of the payload, otherwise send the payload to
    // the next node in the network.
    public void processPayload(Payload p) throws IOException {

        if (p.getIpAddress().equals(IpAddres)) {
            System.out.println(IpAddres + ": Received " + p.getValue() + " with " + p.getIpAddress());
        } else {
            if (outobj == null)
                System.out.println("invalid address");
            else
                outobj.writeObject(p);
        }
    }

    // Get the client socket and create a object input stream to receive the
    // payloads.
    // Read the payloads until the payload with the value -1 and the IP address
    // "end"
    // is received, after close the socket.If the sender socket is not null, send
    // the
    // payload with the value -1 and the IP address "end" to the next node in the
    // network.
    // to close the other nodes.
    public void run() {
        try {
            Socket reader = receiver.accept();
            System.out.println("Info Relay Receiver " + reader);
            inobj = new ObjectInputStream(reader.getInputStream());
            Payload p = null;
            while (true) {
                p = (Payload) inobj.readObject();
                if (p.getIpAddress().equals("end")) {
                    if (outobj != null)
                        outobj.writeObject(endPayload);
                    break;
                }
                processPayload(p);
            }
            System.out.println("ending connection " + reader);
            sleep(1000);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
