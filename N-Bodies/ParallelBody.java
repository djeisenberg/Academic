
public class ParallelBody extends Body {

    double fx[];
    double fy[];
    
    public ParallelBody(double iPosx, double iPosy, double iVx, double iVy,
                        double mass, double radius, int workers) {
        super(iPosx, iPosy, iVx, iVy, mass, radius);
        fx = new double[workers * 8];
        fy = new double[workers * 8];
    }

}
