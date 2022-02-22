public class Main {
    public static void main(String[] args) {
// test : creates a Universe from file and prints bodies
        double dt = Double.parseDouble(args[0]);
        String fname = args[1];
        int pauseTime = Integer.parseInt(args[2]);
        boolean do_trace = args[3].toLowerCase().equals("trace");
        Universe universe = new Universe(fname);
        for (Body body : universe.getBodies()) {
            System.out.println(body);
        }
    }
}