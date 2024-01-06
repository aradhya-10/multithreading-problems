import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Problem13 {

    public static void main(String[] args) {
        // Create a ScheduledExecutorService with a single thread
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // Schedule a task to run after a delay of 2 seconds
        scheduledExecutorService.schedule(() -> {
            System.out.println("Task executed after 2 seconds");
        }, 2, TimeUnit.SECONDS);

        // Schedule a task to run repeatedly with a fixed delay of 3 seconds between executions
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("Repeated task with fixed rate every 3 seconds");
        }, 0, 3, TimeUnit.SECONDS);

        // Schedule a task to run with a fixed delay of 5 seconds between the end of one execution and the start of the next
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("Repeated task with fixed delay of 5 seconds");
        }, 0, 5, TimeUnit.SECONDS);

        // Shutdown the executor after 15 seconds (for demonstration purposes)
        scheduledExecutorService.schedule(() -> {
            scheduledExecutorService.shutdown();
            System.out.println("Executor shutdown");
        }, 15, TimeUnit.SECONDS);
    }
}