// Daniel Eisenberg

public class Airport {

    private String name;
    private String connectionTime;
    private int time;
    
    public Airport(String port, String connectTime) {
        name = port;
        connectionTime = connectTime;
        time = Integer.parseInt(connectTime);
        time = time % 100 + time / 100 * 60;
    } // constructor

    public void print() {
        System.out.println(name + " " + connectionTime);
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }
}
