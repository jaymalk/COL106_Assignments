import java.util.Iterator;

public class Myset {
    private MyLinkedList set = new MyLinkedList();

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public void insertItem(Object o) {
        if(!set.isMember(o))
            set.Insert(o);
    }

    public boolean isMember(Object o) {
        return set.isMember(o);
    }

    public void deleteItem(Object o) {
        try {
            set.deleteMember(o);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Object not in set...");
        }
    }

    public void printSet() {
        Iterator it = set.iterator();
        while(it.hasNext())
            System.out.println(it.next());
    }

    public MyLinkedList getSetList() {
        return set;
    }

    public int Size() {
        return set.getSize();
    }

    public Myset Union(Myset... sets) {
        Myset union_set = new Myset();
        unite(union_set, this);
        for(Myset i : sets)
            unite(union_set, i);
        return union_set;
    }

    public void unite(Myset union, Myset part) {
        Iterator it = part.getSetList().iterator();
        while(it.hasNext())
            union.insertItem(it.next());
    }

    public Myset Intersection(Myset set2) {
        Myset intersection_set = new Myset();
        if(Size()>set2.Size())
            intersect(this, set2, intersection_set);
        else
            intersect(this, set2, intersection_set);
        return intersection_set;
    }

    public void intersect(Myset a, Myset b, Myset mix) {
        Iterator it = b.getSetList().iterator();
        while(it.hasNext()) {
            Object temp = it.next();
            if(a.isMember(temp))
                mix.insertItem(temp);
        }
    }

    public Object getItem(Object o) {
        if(isMember(o)) {
            Iterator it = set.iterator();
            while(it.hasNext()) {
                Object o_current = it.next();
                if(o_current.equals(o))
                    return o_current;
            }
        }
        throw new IllegalArgumentException("Object not present...");
    }

    public void changeItemDetails(Object o_old, Object o_new) {
        Node temp = set.getHead();
        if(isMember(o_new))
            throw new IllegalArgumentException("Object "+o_new+" already present in the set. Can't Duplicate...");
        while(temp!=null) {
            if(temp.getData().equals(o_old)) {
                temp.setData(o_new);
                return;
            }
            temp = temp.next;
        }
        throw new IllegalArgumentException("Object "+o_old+" not present in the set...");
    }
}
