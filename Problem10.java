import java.util.concurrent.Phaser;

public class Problem10 {

    public static void main(String[] args) {
        // Create a Phaser with one registered party (main thread)
        Phaser phaser = new Phaser(1);

        // Start worker threads
        for (int i = 0; i < 3; i++) {
            new Thread(new Worker(phaser, "Worker-" + i)).start();
        }

        phaser.arriveAndDeregister();

        // Main thread waiting for all threads to complete the first phase
        int phase = phaser.getPhase();
        System.out.println("Main thread waiting for phase " + phase + " to complete.");
        phaser.awaitAdvance(phase);

        // Main thread continues after all threads have completed the first phase
        System.out.println("Main thread continues after phase " + phase + " is completed.");
    }

    static class Worker implements Runnable {
        private final Phaser phaser;
        private final String name;

        Worker(Phaser phaser, String name) {
            this.phaser = phaser;
            this.name = name;
            // Register this thread with the phaser
            this.phaser.register();
        }

        @Override
        public void run() {
            System.out.println(name + " starts working.");
            // Work phase 1
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + " continues to work after completing phase 1.");

            // Work phase 2
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + " finishes working.");
            // Deregister this thread from the phaser
            phaser.arriveAndDeregister();
        }
    }
}