import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        List<String> payLoads = new ArrayList<String>();
        payLoads.add("127.0.0.3");
        payLoads.add("127.0.0.2");
        payLoads.add("127.0.0.1");

        RelayNode D3 = new RelayNode("127.0.0.3", 5003);
        RelayNode D2 = new RelayNode("127.0.0.2", 5002);
        RelayNode D1 = new RelayNode("127.0.0.1", 5001);
        D2.setHopPoint("127.0.0.3", 5003);
        D1.setHopPoint("127.0.0.2", 5002);
        D1.start();
        D2.start();
        D3.start();
        Sender sender = new Sender("127.0.0.15", 5001);
        sender.send(payLoads, 0);

        // InetAddress address2 = InetAddress.getByName("127.0.0.2");
        // System.out.println(address2.getHostName());
        // ServerSocket server = new ServerSocket(2, 200, address2);
        // // ServerSocket server = new ServerSocket(2);
        // System.out.println("created server");
        // Socket client = server.accept();
        // System.out.println("Connected");
        // String ipaddr = client.getRemoteSocketAddress().toString();
        // String ipaddr2 = server.getInetAddress().getHostName();
        // String ipaddr3 = client.getLocalAddress().getHostName();
        // System.out.println(ipaddr3);
        // System.out.println(ipaddr2);
        // System.out.println(ipaddr);
        // server.close();
    }
}
