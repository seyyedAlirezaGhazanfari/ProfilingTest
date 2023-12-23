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
        Node prevNode = null;
        Node currentNode = head;

        if (currentNode == null) {
            return new Node(n);
        }

        while (currentNode != null) {
            if (currentNode.getValue() >= n) {
                Node newNode = new Node(n);
                newNode.setNext(currentNode);
                newNode.setPrev(prevNode);
                break;
            }
            prevNode = currentNode;
            currentNode = currentNode.getNext();

            if (currentNode == null) {
                Node newNode = new Node(n);
                newNode.setNext(null);
                newNode.setPrev(prevNode);
            }
        }
        return head;
    }

    public static boolean hasDuplicates(Node head) {
        Node nodeIter1 = head;
        Node nodeIter2 = head;
        while (nodeIter1 != null) {
            while (nodeIter2 != null) {
                if (nodeIter1 != nodeIter2 && nodeIter2.getValue() == nodeIter1.getValue()) {
                    return true;
                }
                nodeIter2 = nodeIter2.getNext();
            }
            nodeIter2 = head;
            nodeIter1 = nodeIter1.getNext();
        }
        return false;
    }
}
