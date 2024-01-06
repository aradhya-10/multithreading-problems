import java.util.concurrent.locks.StampedLock;

class SharedResource {
    private int data = 0;
    private StampedLock lock = new StampedLock();

    public int readData() {
        long stamp = lock.tryOptimisticRead(); // Optimistic read
        int value = data;

        if (!lock.validate(stamp)) {
            // Another thread acquired the write lock, need to re-read
            stamp = lock.readLock(); // Acquire read lock
            try {
                value = data;
            } finally {
                lock.unlockRead(stamp); // Release read lock
            }
        }

        return value;
    }

    public void writeData(int newValue) {
        long stamp = lock.writeLock(); // Acquire write lock
        try {
            data = newValue;
        } finally {
            lock.unlockWrite(stamp); // Release write lock
        }
    }
}

public class Problem15{

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Multiple threads for reading
        Runnable reader = () -> {
            for (int i = 0; i < 5; i++) {
                int value = sharedResource.readData();
                System.out.println("Reader Thread " + Thread.currentThread().getId() + " read: " + value);
                try {
                    Thread.sleep(200); // Simulate some processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Thread for writing
        Runnable writer = () -> {
            for (int i = 1; i <= 5; i++) {
                sharedResource.writeData(i);
                System.out.println("Writer Thread " + Thread.currentThread().getId() + " wrote: " + i);
                try {
                    Thread.sleep(500); // Simulate some processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create multiple threads for reading
        Thread readerThread1 = new Thread(reader);
        Thread readerThread2 = new Thread(reader);

        // Create a thread for writing
        Thread writerThread = new Thread(writer);

        // Start the threads
        readerThread1.start();
        readerThread2.start();
        writerThread.start();
    }
}
