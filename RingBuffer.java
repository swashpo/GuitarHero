// Description: Includes constructor for ring buffer and methods
// to manipulate and extract information from ring buffer

public class RingBuffer {

    // instance variables
    private double[] ringBuffer;
    private int buffCap; // capacity of ring buffer
    private int buffSize; // number of items in buffer
    private int first; // first index of ring buffer that is not empty
    private int last; // first index that is empty after most recently queued
    private double firstVal; // value at index first


    // creates an empty ring buffer with the specified capacity
    public RingBuffer(int capacity) {
        ringBuffer = new double[capacity];
        buffCap = capacity;
        buffSize = 0;
        first = 0;
        last = 0;
    }

    // return the capacity of this ring buffer
    public int capacity() {
        return buffCap;
    }

    // return number of items currently in this ring buffer
    public int size() {
        return buffSize;
    }

    // is this ring buffer empty (size equals zero)?
    public boolean isEmpty() {
        if (size() == 0)
            return true;
        return false;
    }

    // is this ring buffer full (size equals capacity)?
    public boolean isFull() {
        if (size() == buffCap)
            return true;
        return false;
    }

    // adds item x to the end of this ring buffer
    public void enqueue(double x) {
        if (isFull())
            throw new RuntimeException("The queue is full");

        ringBuffer[last] = x;
        last++;
        buffSize++;

        // if last exceeds queue return to beginning
        if (last == buffCap)
            last = 0;
    }

    // deletes and returns the item at the front of this ring buffer
    public double dequeue() {
        if (isEmpty())
            throw new RuntimeException("The queue is empty");

        firstVal = ringBuffer[first]; // finds value to be dequeued
        ringBuffer[first] = 0; // dequeues this index
        first++; // increments to next full slot
        buffSize--;

        // if first exceeds queue return to beginning
        if (first == buffCap)
            first = 0;

        return firstVal; // returns value that was dequeued
    }

    // returns the item at the front of this ring buffer
    public double peek() {
        if (isEmpty())
            throw new RuntimeException("The queue is empty");
        firstVal = ringBuffer[first];
        return firstVal;
    }

    // tests and calls every instance method in this class
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(n);

        for (int i = 1; i <= n; i++) {
            buffer.enqueue(i);
        }

        double t = buffer.dequeue();
        buffer.enqueue(t);
        StdOut.println("Size after wrap-around is " + buffer.size());

        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        StdOut.println(buffer.peek());
    }
}
