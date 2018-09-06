import java.util.*;

public class MyLinkedList implements Iterable {
    private Node head = null;
    private int size = 0;

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return this.head;
    }

    public void Insert(Object o) {
        head = new Node(o, head);
        size++;
    }

    public boolean isMember(Object o) {
        Iterator it = this.iterator();
        while(it.hasNext()) {
            if(it.next().equals(o))
                return true;
        }
        return false;
    }

    public void deleteMember(Object o) {
        if(!isMember(o))
            throw new IllegalArgumentException("Object not present in the linked list...");
        if(head.getData().equals(o)) {
            head = head.next;
        }
        else {
            Node temp2 = head;
            while(temp2.next!=null) {
                if(temp2.next.getData().equals(o)) {
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

    public Iterator iterator() {
        return new ListIterator();
    }

    public void clearSet() {
        this.head = null;
        this.size = 0;
    }

    private class ListIterator implements Iterator {
        private Node current = head;

        public boolean hasNext() {
            return current!=null;
        }

        public Object next() {
            Object data = current.getData();
            current = current.next;
            return data;
        }

        public void remove() {
            deleteMember(current.getData());
        }
    }
}
