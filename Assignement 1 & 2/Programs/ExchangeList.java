import java.util.*;

public class ExchangeList {
    public MyLinkedList list = new MyLinkedList();

    public void createNewChildExchange(Exchange parent, int identifier) {
        Exchange e = new Exchange(identifier, parent);
        list.Insert(e);
    }

    public boolean containsChild(int id) {
        Iterator it = list.iterator();
        while(it.hasNext())
            if(it.next().hashCode() == id)
                return true;
        return false;
    }

    public Exchange getChild(int id) {
        if(containsChild(id)) {
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Object temp = it.next();
                if(temp.hashCode() == id)
                    return (Exchange)temp;
            }
        }
        throw new IllegalArgumentException("No child with iden");
    }

    public Exchange getChildAt(int i) {
        if(list.getSize()<i)
            throw new IllegalArgumentException("Not enough children");
        Iterator it = list.iterator();
        while(--i!=0)
            it.next();
        return (Exchange)it.next();
    }
}
