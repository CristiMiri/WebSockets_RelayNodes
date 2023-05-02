import java.io.*;
import java.net.*;

public class RelayNode extends Thread {
    ServerSocket receiver = null;
    Socket sender = null;
    private String IpAddres = null;
    private int port = 0;
    private String hopAddress = null;
    private int hopPort = 0;
    private PrintWriter out = null;
    private ObjectOutputStream outobj = null;
    private ObjectInputStream inobj = null;
    private BufferedReader in = null;

    public RelayNode(String receiveAddr, int receivePort) {
        try {
            InetAddress addr = InetAddress.getByName(receiveAddr);
            receiver = new ServerSocket(receivePort, 200, addr);
            IpAddres = receiveAddr;
            port = receivePort;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHopPoint(String hopAddress, int hopPort) {
        this.hopAddress = hopAddress;
        this.hopPort = hopPort;
        try {
            sender = new Socket(hopAddress, hopPort);
            // out = new PrintWriter(sender.getOutputStream(), true);
            outobj = new ObjectOutputStream(sender.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processPayload(Payload p) {
        if (p.getIpAddress().equals(IpAddres)) {
            System.out.println("Message " + p.getValue() + " received from " + p.getIpAddress());
        } else {
            outobj.writeObject(p);
        }
    }

    public void fowardMessage(String message) {
        if (out != null)
            out.println(message);
        else {
            System.out.println("No hop point set/Address not found");
        }
    }

    public void run() {
        try {
            Socket reader = receiver.accept();
            // in = new BufferedReader(new InputStreamReader(reader.getInputStream()));
            inobj = new ObjectInputStream(reader.getInputStream());
            Payload p = null;
            while (true) {
                p = (Payload) inobj.readObject();
                processPayload(p);
                // //String message = in.readLine();
                // System.out.println("Message received: " + message);
                // String[] parts = message.split(":");
                // String ip = parts[0];
                // int value = Integer.parseInt(parts[1]);
                // Payload p = new Payload(ip, value);
                // System.out.println(ip + " " + IpAddres);
                // if (ip.equals(IpAddres)) {
                // System.out.println("Message " + value + " received from " + ip);
                // continue;
                // } else {
                // // fowardMessage(message);
                // outobj.writeObject(p);
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
