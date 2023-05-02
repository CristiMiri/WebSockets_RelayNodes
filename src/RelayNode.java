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

    public RelayNode(String receiveAddr, int receivePort) {
        try {
            InetAddress addr = InetAddress.getByName(receiveAddr);
            // receiver = new ServerSocket(receivePort, 200, addr);
            InetSocketAddress socketAddress = new InetSocketAddress(addr, receivePort);
            receiver = new ServerSocket();
            receiver.bind(socketAddress);
            IpAddres = receiveAddr;
            port = receivePort;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHopPoint(String hopAddress, int hopPort) {
        this.hopAddress = hopAddress;
        this.hopPort = hopPort;
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(hopAddress);
            sender = new Socket(addr, hopPort);
            outobj = new ObjectOutputStream(sender.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processPayload(Payload p) throws IOException {

        if (p.getIpAddress().equals(IpAddres)) {
            System.out.println("Message " + p.getValue() + " received from " + p.getIpAddress());
        } else {
            if (outobj == null)
                System.out.println("invalid address");
            else
                outobj.writeObject(p);
        }
    }

    public void run() {
        try {
            Socket reader = receiver.accept();
            System.out.println(reader);
            inobj = new ObjectInputStream(reader.getInputStream());
            Payload p = null;
            while (true) {
                p = (Payload) inobj.readObject();
                processPayload(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
