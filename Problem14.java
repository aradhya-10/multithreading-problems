import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class RecursiveSumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 5; // Threshold for splitting the task
    private int[] array;
    private int start;
    private int end;

    public RecursiveSumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            // If the range is small enough, compute the sum directly
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Split the task into two subtasks
            int mid = (start + end) / 2;
            RecursiveSumTask leftTask = new RecursiveSumTask(array, start, mid);
            RecursiveSumTask rightTask = new RecursiveSumTask(array, mid, end);

            // Fork the left and right subtasks
            leftTask.fork();
            rightTask.fork();

            // Join the results of the subtasks
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // Combine the results
            return leftResult + rightResult;
        }
    }
}

public class Problem14 {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
			// Create a RecursiveTask for summing the elements of the array
			RecursiveSumTask sumTask = new RecursiveSumTask(array, 0, array.length);

			// Invoke the task in the ForkJoinPool
			int result = forkJoinPool.invoke(sumTask);

			System.out.println("Sum of array elements: " + result);

			// Shutdown the ForkJoinPool
			forkJoinPool.shutdown();
		}
    }
}

