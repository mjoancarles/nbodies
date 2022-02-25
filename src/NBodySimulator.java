public class NBodySimulator {
    private double timeStep;
    private int pauseTime;
    private boolean trace;
    private Universe universe;
    //private int numBodies;

    public NBodySimulator(String fname, double dt, int pauseTime, boolean do_trace){
        this.universe = new Universe(fname);
        timeStep = dt;
        this.pauseTime = pauseTime;
        trace = do_trace;
    }

    public NBodySimulator(int numBodies,double dt, int pauseTime, boolean do_trace){
        this.universe = new Universe(numBodies);
        timeStep = dt;
        this.pauseTime = pauseTime;
        trace = do_trace;
    }

    public static void main(String[] args) {
        NBodySimulator nbody;
        int numargs = args.length;
        if ((numargs == 3) || (numargs == 4)) {
            double dt = Double.parseDouble(args[0]);
            int pauseTime = Integer.parseInt(args[2]);
            boolean do_trace = false;
            if (args.length == 4) {
                do_trace = args[3].toLowerCase().equals("trace");
            }
            try {
                int numBodies = Integer.parseInt(args[1]);
                nbody = new NBodySimulator(numBodies, dt, pauseTime, do_trace);
            } catch (NumberFormatException e){
                String fname = args[1];
                nbody = new NBodySimulator(fname, dt, pauseTime, do_trace);
            }
            nbody.simulate();
        } else {
            assert false : "invalid number of arguments" ;
        }
    }

    public void simulate() {
        createCanvas();
        if (this.trace) {
            while(true) {
                StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                drawUniverse();
                this.universe.update(this.timeStep);
                StdDraw.setPenColor(StdDraw.BLACK);
                drawUniverse();
                StdDraw.show();
                StdDraw.pause(this.pauseTime);
            }
        } else {
            while (true) {
                StdDraw.clear();
                this.universe.update(this.timeStep);
                drawUniverse();
                StdDraw.show();
                StdDraw.pause(this.pauseTime);
            }
        }
    }

    private void createCanvas() {
        //StdDraw.setCanvasSize(700, 700);
        // this makes a larger window than the default;
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.025);
        double radius = universe.getRadius();
        // read from txt file, second line
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
    }
    private void drawUniverse(){
        //int numBodies = this.universe.getNumBodies();
        Body[] bodies=universe.getBodies();
        for (Body i : bodies) {
            Vector v = i.getPosition();
            double x=v.cartesian(0);
            double y=v.cartesian(1);
            StdDraw.point(x,y);
        }
    }
}