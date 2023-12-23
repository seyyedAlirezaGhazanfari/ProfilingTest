import java.util.ArrayList;
import java.util.List;

public class Node {

    private final int value;
    private Node next;
    private Node prev;

    public Node(int value, Node next, Node prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public Node(int value) {
        this(value, null, null);
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public static Node naiveInsert(int value, Node next, Node prev) {
        Node newNode = new Node(value);
        newNode.setNext(next);
        newNode.setPrev(prev);
        return newNode;
    }

    public void setNext(Node next) {
        if (next != null) {
            this.next = next;
            next.prev = this;
        }
    }

    public void setPrev(Node prev) {
        if (prev != null) {
            this.prev = prev;
            prev.next = this;
        }
    }

    public List<Integer> getNodesList() {
        Node currentNode = this;
        List<Integer> nodesList = new ArrayList<>();
        while (currentNode != null) {
            nodesList.add(currentNode.value);
            currentNode = currentNode.getNext();
        }
        return nodesList;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public static Node sortedInsert(Node head, int n) {
        if (head == null) {
            return new Node(n);
        }

        Node currentNode = head;
        Node prevNode = null;
        while (currentNode != null) {
            if (currentNode.getValue() >= n) {
                Node newNode = naiveInsert(n, currentNode, prevNode);
                if (newNode.getPrev() == null) {
                    head = newNode;
                }
                return head;
            }
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        Node newNode = naiveInsert(n, currentNode, prevNode);
        return head;
    }

    public static boolean hasDuplicates(Node head) {
        Node nodeIter1 = head;
        while (nodeIter1 != null) {
            if (nodeIter1.getNext() != null && nodeIter1.getValue() == nodeIter1.getNext().getValue()) {
                return true;
            }
            nodeIter1 = nodeIter1.getNext();
        }
        return false;
    }
}
