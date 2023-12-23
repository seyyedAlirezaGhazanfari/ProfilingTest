import java.util.List;
import java.util.Random;


public class NodeRunner {
    private final static int RAND_BOUND = 1_000_000_000;
    private final static int NODES_COUNT = 50_000;

    private static void mainSleep() {
        for (int i = 0; i < 100; i++) {
            Utils.sleepUninterrruptable(0.01);
        }
    }

    private static void printLinkedList(Node head) {
        // Print the values of the linked list
        List<Integer> nodesList = head.getNodesList();

        for (Integer value : nodesList) {
            System.out.println(value + " ");
        }
    }
    private static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(RAND_BOUND) + 1;
    }

    public static void main(String[] args) {
        Node head = new Node(0, null, null);

        for (int i = 0; i < NODES_COUNT; i++) {
            int randomNumber = getRandomNumber();
            head = Node.sortedInsert(head, randomNumber);
        }
        printLinkedList(head);

        System.out.println("HAS DUPLICATES? " + Node.hasDuplicates(head));

        mainSleep();
    }
}
