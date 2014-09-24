// Daniel Eisenberg
public class Flight {
    
    private String departure;
    private String destination;
    private String leaves;
    private String arrives;
    private int time; // flight time in minutes
    private int leaveTimeMinutes;
    private int arriveTimeMinutes;

    // constructor
    public Flight(String src, String dest, String stime, String dtime) {
        departure = src;
        destination = dest;
        leaves = stime;
        arrives = dtime;

        int leaveTime = Integer.parseInt(stime);
        int arriveTime = Integer.parseInt(dtime);
        leaveTimeMinutes = leaveTime % 100 + leaveTime / 100 * 60;
        arriveTimeMinutes = arriveTime % 100 + arriveTime / 100 * 60;
        time = arriveTimeMinutes - leaveTimeMinutes;
        if (time < 0)
            time += 1440;
    }

    public void print() {
        System.out.print("[" + departure + "->" + destination + ":" + leaves + "->" + arrives + "]");
    }

    public String getDeparture() {
        return departure;
    }

    public int getTime() {
        return time;
    }

    public String getDestination() {
        return destination;
    }
    
    public int getLeaveTime() {
        return leaveTimeMinutes;
    }
    
    public int getArriveTime() {
        return arriveTimeMinutes;
    }
}
