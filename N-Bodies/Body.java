public class Body {

    double                      posx;
    double                      posy;
    double                      vx;
    double                      vy;
    double                      fx;
    double                      fy;
    double                      m;
    double                      r;

    public Body(double iPosx, double iPosy, double iVx, double iVy,
                double mass, double radius) {
        posx = iPosx;
        posy = iPosy;
        vx = iVx;
        vy = iVy;
        m = mass;
        r = radius;
        fx = 0.0;
        fy = 0.0;
    }
}
