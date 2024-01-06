import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Problem08 
{
    public static void main(String[] args) {
        // Create a ConcurrentHashMap
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();

        // Create and start multiple threads to modify the map concurrently
        Thread writerThread1 = new Thread(() -> addToMap(concurrentMap, "key1", 1));
        Thread writerThread2 = new Thread(() -> addToMap(concurrentMap, "key2", 2));
        Thread writerThread3 = new Thread(() -> addToMap(concurrentMap, "key3", 3));

        writerThread1.start();
        writerThread2.start();
        writerThread3.start();

        // Wait for all threads to finish
        try {
            writerThread1.join();
            writerThread2.join();
            writerThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final contents of the map
        System.out.println("Final Map: " + concurrentMap);
    }

    private static void addToMap(Map<String, Integer> map, String key, int value) {
        // Access and modify the map using the put method
        map.put(key, value);

        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the current thread and the updated map
        System.out.println(Thread.currentThread().getName() + " added to map: " + map);
    }
}