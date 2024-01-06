import java.util.concurrent.Exchanger;

public class Problem11 {

    public static void main(String[] args) {
        // Create an Exchanger for exchanging data of type String
        Exchanger<String> exchanger = new Exchanger<>();

        // Start two threads for exchanging data
        Thread thread1 = new Thread(new DataProducer(exchanger, "Thread-1", "Data from Thread-1"));
        Thread thread2 = new Thread(new DataProducer(exchanger, "Thread-2", "Data from Thread-2"));

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class DataProducer implements Runnable {
        private final Exchanger<String> exchanger;
        private final String threadName;
        private final String data;

        DataProducer(Exchanger<String> exchanger, String threadName, String data) {
            this.exchanger = exchanger;
            this.threadName = threadName;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                System.out.println(threadName + " is preparing to exchange data: " + data);
                Thread.sleep(1000); // Simulate some processing time

                // Exchange data with the other thread
                String exchangedData = exchanger.exchange(data);

                System.out.println(threadName + " received exchanged data: " + exchangedData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
