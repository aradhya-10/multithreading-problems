class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class IncrementThread extends Thread {
    private Counter counter;
    private int increments;

    public IncrementThread(Counter counter, int increments) {
        this.counter = counter;
        this.increments = increments;
    }

    @Override
    public void run() {
        for (int i = 0; i < increments; i++) {
            counter.increment();
        }
    }
}

public class Problem01 {
    public static void main(String[] args) {
        Counter counter = new Counter();
        int numThreads = 5;
        int incrementsPerThread = 1000;

        Thread[] threads = new Thread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new IncrementThread(counter, incrementsPerThread);
            threads[i].start();
        }

        // Wait for all threads
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final count
        System.out.println("Final count: " + counter.getCount());
    }
}