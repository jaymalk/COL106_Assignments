public class Node {
    Node next;
    private Object data;

    public Object getData() {
        return this.data;
    }

    public void setData(Object o) {
        this.data = o;
    }

    public Node(Object nodeData, Node nextNode) {
        this.data = nodeData;
        this.next = nextNode;
    }
}
