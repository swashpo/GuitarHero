 // Description:  Constructor for GuitarString and methods to interact with
 // GuitarString
 

public class GuitarString {

    private int n; // capacity
    private int sampleRate = 44100; // sampling rate
    RingBuffer guitString;
    private double decay = 0.996;

    // creates a guitar string of the specified frequency,
    // using sampling rate of 44,100
    public GuitarString(double frequency) {
        n = (int) Math.ceil((sampleRate / frequency)); // need to round UP

        guitString = new RingBuffer(n);
        for (int i = 0; i < n; i++)
            guitString.enqueue(0);
    }

    // creates a guitar string whose size and initial values are given by
    // the specified array
    public GuitarString(double[] init) {
        n = init.length;
        guitString = new RingBuffer(init.length);

        for (int i = 0; i < init.length; i++) {
            guitString.enqueue(init[i]);
        }
    }

    // returns the number of samples in the ring buffer
    public int length() {
        return guitString.size();
    }

    // plucks the guitar string (by replacing the buffer with white noise)
    public void pluck() {
        double random;
        for (int i = 0; i < n; i++) {
            random = StdRandom.uniform(-0.5, 0.5);
            guitString.dequeue();
            guitString.enqueue(random);
        }
    }

    // advances the Karplus-Strong simulation one time step
    public void tic() {
        double first = guitString.dequeue();
        double newFirst = guitString.peek();
        double sim = (first + newFirst) * decay / 2;
        guitString.enqueue(sim);
    }

    // returns the current sample
    public double sample() {
        return guitString.peek();
    }


    // tests and calls every constructor and instance method in this class
    public static void main(String[] args) {
        double[] samples = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
        GuitarString testString = new GuitarString(samples);
        int m = 25; // 25 tics
        for (int i = 0; i < m; i++) {
            double sample = testString.sample();
            StdOut.printf("%6d %8.4f\n", i, sample);
            testString.tic();
        }

    }
}
