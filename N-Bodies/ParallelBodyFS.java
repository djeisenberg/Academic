
public class ParallelBodyFS extends Body {

    double fx[];
    double fy[];
    
    public ParallelBodyFS(double iPosx, double iPosy, double iVx, double iVy,
                        double mass, double radius, int workers) {
        super(iPosx, iPosy, iVx, iVy, mass, radius);
        fx = new double[workers];
        fy = new double[workers];
    }

}
