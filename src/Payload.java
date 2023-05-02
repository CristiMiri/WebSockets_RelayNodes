import java.io.Serializable;

public class Payload implements Serializable {
    private String ipAddress;
    private int value;

    public Payload(String ipAddress, int value) {
        this.ipAddress = ipAddress;
        this.value = value;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + ipAddress + ":" + value + "";
    }

}
