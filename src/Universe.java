import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Universe {
    private double radius;
    private int numBodies;
    private Body[] bodies;
    public Universe(String fname) {
        try {
            Scanner in = new Scanner((new FileReader(fname)));
            this.numBodies = Integer.parseInt(in.next());
            this.radius = Double.parseDouble(in.next());
            this.bodies = new Body[numBodies];
            for (int i=0; i < numBodies; i++) {
                double rx = Double.parseDouble(in.next());
                double ry = Double.parseDouble(in.next());
                double vx = Double.parseDouble(in.next());
                double vy = Double.parseDouble(in.next());
                double mass = Double.parseDouble(in.next());
                double[] position = {rx,ry};
                double[] velocity = {vx,vy};
                Vector r = new Vector(position);
                Vector v = new Vector(velocity);
                bodies[i] = new Body(r, v, mass);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(double dt) {
        Vector[] f = new Vector[numBodies];
        // initialize the forces to zero
        for (int i = 0; i < numBodies; i++) {
            f[i] = new Vector(new double[2]);
        }
        //compute the forces
        for (int i = 0; i < numBodies; i++) {
            for (int j = 0; j < numBodies; j++) {
                if (i != j) {
                    f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
                }
            }
        }
        // move the bodies
        for (int i = 0; i < numBodies; i++) {
            bodies[i].move(f[i], dt);
        }
    }

    public Body[] getBodies() {return this.bodies;}
    public double getRadius() {return this.radius;}
    public int getNumBodies() {return this.numBodies;}
}
