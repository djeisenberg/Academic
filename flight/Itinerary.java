import java.util.LinkedList;

// Daniel Eisenberg
public class Itinerary {

    private LinkedList<Flight> itin;
    private int time;

    // constructor
    public Itinerary(LinkedList<Flight> flights, int time) {
        itin = new LinkedList<Flight>();
        if (flights != null)
            itin.addAll(flights);
        this.time = time;
    }

    public boolean isFound() {
        return !itin.isEmpty();
    }

    public void print() {
        if (isFound()) {
            for (Flight f : itin)
                f.print();
            System.out.println();
        } else {
            System.out.println("No Flight Schedule Found.");
        }
    }

}
