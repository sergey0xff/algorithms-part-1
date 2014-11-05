public class Subset {
    public static void main(String[] args) {
        int k = 8;
        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}