import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

// Daniel Eisenberg
public class Planner {

    private HashMap<String, Integer> airports; // Constant lookup time for
                                               // indices
    private ArrayList<LinkedList<Flight>> graph; // Weighted, directed,
                                                 // potentially dense airport
                                                 // graph with flight edges
    private int[] connectionTimes; // Constant lookup time
                                   // for airport connection
                                   // times
    private int numEdges; // the total number of edges

    // constructor
    public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {
        graph = new ArrayList<LinkedList<Flight>>(portList.size());
        airports = new HashMap<String, Integer>(portList.size());
        connectionTimes = new int[portList.size()];
        numEdges = 0;
        if (portList == null)
            return;
        int i = 0;
        for (Airport a : portList) {
            airports.put(a.getName(), i);
            connectionTimes[i] = a.getTime();
            graph.add(i, new LinkedList<Flight>());
            i++;
        }
        if (fltList == null)
            return;
        for (Flight f : fltList) {
            graph.get(airports.get(f.getDeparture())).add(f);
            numEdges++;
        }

    }

    public Itinerary Schedule(String departure, String destination, String leave) {
        int size = graph.size();
        ArrayList<LinkedList<Flight>> paths = new ArrayList<LinkedList<Flight>>(
                size);
        if (departure == null || destination == null || leave == null
                || airports.isEmpty() || graph.isEmpty()
                || airports.get(departure) == null
                || graph.get(airports.get(departure)) == null
                || graph.get(airports.get(departure)).isEmpty()
                || airports.get(destination) == null) {
            // no outgoing flights from departure
            return new Itinerary(null, 0);
        }

        boolean visited[] = new boolean[size];
        MinHeap heap = new MinHeap();
        int travelTime[] = new int[size];
        for (int i = 0; i < travelTime.length; i++) {
            travelTime[i] = Integer.MAX_VALUE;
            paths.add(new LinkedList<Flight>());
        }
        travelTime[airports.get(departure)] = 0;

        int startTime = Integer.parseInt(leave);
        startTime = startTime % 100 + startTime / 100 * 60;
        visited[airports.get(departure)] = true;
        for (Flight f : graph.get(airports.get(departure))) {
            int next = airports.get(f.getDestination());
            int waitTime = f.getLeaveTime() - startTime;
            if (waitTime < 0) // leaves next day
                waitTime += 1440; // minutes per day
            int time = f.getTime() + waitTime;
            if (time < travelTime[next]) {
                travelTime[next] = time;
                heap.add(new Node(next, time));
                LinkedList<Flight> fl = new LinkedList<Flight>();
                fl.add(f);
                paths.set(next, fl);
            }
        }

        Node current = heap.getMin();
        int i = 1;
        int dest = airports.get(destination);
        while (current != null && !visited[dest] && i < size) {

            visited[current.v] = true;

            for (Flight f : graph.get(current.v)) {
                int next = airports.get(f.getDestination());
                if (!visited[next]) {
                    Flight fl = paths.get(current.v).getLast();
                    int waitTime = f.getLeaveTime() - fl.getArriveTime();
                    if (waitTime < 0) // leaves next day
                        waitTime += 1440; // minutes per day
                    if (waitTime < connectionTimes[current.v])
                        waitTime += 1440; // add a day to make connection
                    int time = waitTime + f.getTime() + travelTime[current.v];
                    if (time < travelTime[next]) {
                        // update shortest trip time
                        travelTime[next] = time;
                        heap.add(new Node(next, time));
                        // update path
                        LinkedList<Flight> path = new LinkedList<Flight>();
                        path.addAll(paths.get(current.v));
                        path.add(f);
                        paths.set(next, path);
                    }
                }
            }
            while (current != null && visited[current.v])
                current = heap.getMin();
            i++;
        }
        LinkedList<Flight> result = paths.get(dest);
        if (!result.isEmpty()
                && !result.getLast().getDestination().equals(destination))
            result = new LinkedList<Flight>();
        return new Itinerary(result, travelTime[dest]);
    }

    private class MinHeap {

        private ArrayList<Node> heap;

        private MinHeap() {
            heap = new ArrayList<Node>();
        }

        private Node getMin() {
            if (heap.isEmpty())
                return null;
            Node result = heap.get(0);
            deleteMin();
            return result;
        }

        private void deleteMin() {
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            heapify(0);
        }

        private void heapify(int index) {
            int lchild = (index << 1) + 1;
            int rchild = lchild + 1;

            while (lchild < heap.size()) {
                int min = (heap.get(lchild).w < heap.get(index).w) ? lchild
                        : index;
                if (rchild < heap.size())
                    min = (heap.get(rchild).w < heap.get(min).w) ? rchild : min;
                if (min == index)
                    return;
                swap(min, index);
                index = min;
                lchild = (index << 1) + 1;
                rchild = lchild + 1;
            }
        }

        private void swap(int a, int b) {
            Node tmp = heap.get(a);
            heap.set(a, heap.get(b));
            heap.set(b, tmp);
        }

        private void add(Node n) {
            heap.add(n);
            for (int i = heap.size() - 1; i > 0; i--, i >>= 1) {
                int parent = (i - 1) >> 1;
                if (heap.get(parent).w <= heap.get(i).w) {
                    return;
                } else {
                    swap(parent, i);
                }
            }
        }
    }

    private class Node {

        private int v;
        private int w;

        private Node(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
}
