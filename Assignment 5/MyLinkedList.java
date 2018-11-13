import java.util.*;

public class MyLinkedList<E> implements Iterable<E> {
    protected Node<E> head = null;
    private int size = 0;

    private class Node<T> {
        Node<T> next;
        private T data;

        public T getData() {
            return this.data;
        }

        public Node(T nodeData, Node<T> nextNode) {
            this.data = nodeData;
            this.next = nextNode;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node<E> getHead() {
        return this.head;
    }

    public void Insert(E e) {
        head = new Node<E>(e, head);
        size++;
    }

    public boolean isMember(E e) {
        Iterator<E> it = this.iterator();
        while(it.hasNext()) {
            if(it.next().equals(e))
                return true;
        }
        return false;
    }

    public void deleteMember(E e) {
        if(!isMember(e))
            throw new IllegalArgumentException("E not present in the linked list...");
        if(head.getData().equals(e)) {
            head = head.next;
        }
        else {
            Node<E> temp2 = head;
            while(temp2.next!=null) {
                if(temp2.next.getData().equals(e)) {
                    temp2.next = temp2.next.next;
                    return;
                }
                temp2 = temp2.next;
            }
        }
        size--;
    }

    public int getSize() {
        return size;
    }

    public Iterator<E> iterator() {
        return new ListIterator();
    }

    public void clearSet() {
        this.head = null;
        this.size = 0;
    }

    public void mergeLists(MyLinkedList<E> otherList) {
        Node<E> temp = otherList.getHead(), temp2 = head;
        if(temp2 == null) {
            head = temp;
            return;
        }
        while(temp2.next!=null)
            temp2 = temp2.next;
        temp2.next = temp;
        return;
    }

    private class ListIterator implements Iterator<E> {
        private Node<E> current = head;

        public boolean hasNext() {
            return current!=null;
        }

        public E next() {
            E data = current.getData();
            current = current.next;
            return data;
        }

        public void remove() {
            deleteMember(current.getData());
        }
    }
}
