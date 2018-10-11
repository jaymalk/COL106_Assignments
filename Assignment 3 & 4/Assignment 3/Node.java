public class Node<E> {
    Node<E> next;
    private E data;

    public E getData() {
        return this.data;
    }

    public void setData(E e) {
        this.data = e;
    }

    public Node(E nodeData, Node<E> nextNode) {
        this.data = nodeData;
        this.next = nextNode;
    }
}
