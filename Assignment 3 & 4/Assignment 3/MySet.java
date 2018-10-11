import java.util.Iterator;

public class MySet<E> {
    private MyLinkedList<E> set = new MyLinkedList<E>();

    // LINKED LIST RELATED OPERATIONS

    public boolean IsEmpty() {
        return set.isEmpty();
    }

    public void addElement(E e) {
        if(!set.isMember(e))
            set.Insert(e);
    }

    public boolean IsMember(E e) {
        return set.isMember(e);
    }

    public void Delete(E e) {
        try {
            set.deleteMember(e);
        }
        catch (IllegalArgumentException exception) {
            System.out.println(e.toString()+" not in set...");
        }
    }

    public MyLinkedList<E> getSetList() {
        return set;
    }

    public int Size() {
        return set.getSize();
    }

    // SET RELATED OPERATIONS

    public MySet<E> Union(MySet<E> otherSet) {
        MySet<E> unionSet = new MySet<E>();
        unite(unionSet, this);
        unite(unionSet, otherSet);
        return unionSet;
    }

    // NOT USING THIS METHOD

    // public MySet<E> Union(MySet<E>... sets) {
    //     MySet<E> unionSet = new MySet<E>();
    //     unite(unionSet, this);
    //     for(MySet<E> i : sets)
    //         unite(unionSet, i);
    //     return unionSet;
    // }

    public void unite(MySet<E> union, MySet<E> part) {
        Iterator<E> it = part.getSetList().iterator();
        while(it.hasNext())
            union.addElement(it.next());
    }

    public MySet<E> Intersection(MySet<E> set2) {
        MySet<E> intersection_set = new MySet<E>();
        if(Size()>set2.Size())
            intersect(this, set2, intersection_set);
        else
            intersect(this, set2, intersection_set);
        return intersection_set;
    }

    public void intersect(MySet<E> a, MySet<E> b, MySet<E> mix) {
        Iterator<E> it = b.getSetList().iterator();
        while(it.hasNext()) {
            E temp = it.next();
            if(a.IsMember(temp))
                mix.addElement(temp);
        }
    }

    // SET ITEM RELATED OPERATIONS

    public E getItem(E e) {
        if(IsMember(e)) {
            Iterator<E> it = set.iterator();
            while(it.hasNext()) {
                E e_current = it.next();
                if(e_current.equals(e))
                    return e_current;
            }
        }
        throw new IllegalArgumentException(e.toString()+" not found.");
    }

    /*  MOSTLY REDUNDANT... :(

    public void changeItemDetails(E o_old, E o_new) {
        Node temp = set.getHead();
        if(isMember(o_new))
            throw new IllegalArgumentException("E "+o_new+" already present in the set. Can't Duplicate...");
        while(temp!=null) {
            if(temp.getData().equals(o_old)) {
                temp.setData(o_new);
                return;
            }
            temp = temp.next;
        }
        throw new IllegalArgumentException("E "+o_old+" not present in the set...");
    }

    */

    // SET PRINTING OPERATION

    public void printSet() {
        Iterator<E> it = set.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }

    public void clearSet() {
        set.clearSet();
    }
}
