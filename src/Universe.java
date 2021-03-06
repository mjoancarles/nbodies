import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.math.*;

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

    public Universe(int numBodies){
        this.numBodies= numBodies;
        this.radius = 2e11;
        this.bodies = new Body[numBodies];
        for (int i=0; i < numBodies; i++) {
            //random formula: a + Math.random()*(b-a)
            double rx = -1e11 + Math.random()*(1e11-(-1e11));
            double ry = -1e11 + Math.random()*(1e11-(-1e11));
            double vx = -1.2e04 + Math.random()*(1.2e04-(-1.2e04));
            double vy = -1.2e04 + Math.random()*(1.2e04-(-1.2e04));
            double mass = 2e27 + Math.random()*(2e28-2e27);
            double[] position = {rx,ry};
            double[] velocity = {vx,vy};
            Vector r = new Vector(position);
            Vector v = new Vector(velocity);
            bodies[i] = new Body(r, v, mass);
        }
    }

    public void update(double dt) {
        Vector[] f = new Vector[numBodies];
        // initialize the forces to zero
        long start = System.currentTimeMillis();
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
        long finish = System.currentTimeMillis();
        long elapsedTime = finish - start;
        System.out.println("Elapsed time " + elapsedTime + " milliseconds");
    }

    public Body[] getBodies() {return this.bodies;}
    public double getRadius() {return this.radius;}
    public int getNumBodies() {return this.numBodies;}
}
