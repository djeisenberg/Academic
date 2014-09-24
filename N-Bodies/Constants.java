/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012
 * Parallel Project: N-Bodies Problem with Collisions
 */

public class Constants {

    public static final boolean DEBUG            = true;
    
    public static final double  DT                = 1.0;        // max time
                                                                   // granularity
    public static final double  EPSILON           = 1e-6;       // collision
                                                                   // radius
    public static final double  G                 = -6.67e-11;  // universal
                                                                   // gravitational
                                                                   // constant
    public static final double  DEFAULT_MASS      = 1e6;
    public static final double  DEFAULT_RADIUS    = 1.0;
    public static final double  A_LARGE_VALUE     = 1e100;      // used in place of
                                                                   // infinity
    public static final double  MAX               = 1e2;        // bound for randomly 
                                                                   // generated initial 
                                                                   // positions and velocities
    
    public static final double  PARAMETERS[]      = 
                                                      // 2d collision test (2 seconds)
//                                                    { 1.0, 3.0, -1.0, -3.0, 1e-100, 1.0,
//                                                      -1.0, -3.0, 1.0, 1.0, 1e-100, 1.0 };
                                                      // gravity test
//                                                    { 5.0, 6.0, 0.0, 0.0, 1e100, 1.0,
//                                                      5.0, 4.0, 0.0, 0.0, 1e100, 1.0, 
//                                                      6.0, 5.0, 0.0, 0.0, 1e100, 1.0,
//                                                      4.0, 5.0, 0.0, 0.0, 1e100, 1.0 };
                                                      // 1d momentum exchange test
//                                                    { -3.0, 0.0, 5.0, 0.0, 1e-100, 1.0,
//                                                       0.0, 0.0, 0.0, 0.0, 1e-100, 1.0,
//                                                       3.0, 0.0, 0.0, 0.0, 1e-100, 1.0 };
                                                      // 1d momentum exchange test 2
                                                    { -15.0, 0.0, 5.0, 0.0, 1e-200, 1.0,
                                                      -5.0, 0.0, 0.0, 0.0, 1e-200, 1.0,
                                                      5.0, 0.0, 0.0, 0.0, 1e-200, 1.0,
                                                      15.0, 0.0, -5.0, 0.0, 1e-200, 1.0 };
                                                      // falling body (2 seconds)
//                                                    { 0.0, -6e6, 0.0, 0.0, 1e24, 6e6,
//                                                      0.0, 10.0, 0.0, 8.0, .2, 1.0};
    

    public static final int     MAX_WORKERS       = 16; // = 2**MAX_STAGES
    public static final int     MAX_STAGES        = 4; // = lg(MAX_WORKERS)
    
    public static final String  FILENAME          = "output.txt";
}
