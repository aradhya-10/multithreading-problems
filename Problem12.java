import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Problem12 {

    public static void main(String[] args) {
        // Create a single-threaded ExecutorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Create a Callable task
        Callable<String> callableTask = () -> {
            Thread.sleep(2000); // Simulate a time-consuming task
            return "Callable task completed";
        };

        // Submit the Callable task to the executor and obtain a Future
        Future<String> future = executorService.submit(callableTask);

        // Perform other tasks while waiting for the Callable task to complete

        try {
            // Retrieve the result of the Callable task (blocks until the task is complete)
            String result = future.get();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown the ExecutorService
            executorService.shutdown();
        }
    }
}
