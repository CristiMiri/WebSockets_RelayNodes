import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        List<String> payLoads = new ArrayList<String>();
        List<InetAddress> addresses = new ArrayList<InetAddress>();
        addresses.add(InetAddress.getByName("127.0.0.3"));
        addresses.add(InetAddress.getByName("127.0.0.2"));
        addresses.add(InetAddress.getByName("127.0.0.1"));
        addresses.add(InetAddress.getByName("127.0.0.4"));
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
        Sender sender = new Sender("127.0.0.1", 5001);
        sender.sendInet(addresses, 0);
    }
}
