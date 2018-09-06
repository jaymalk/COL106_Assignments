import java.util.*;

public class ExchangeList {
    public MyLinkedList list = new MyLinkedList();

    public void createNewChildExchange(Exchange parent, int identifier) {
        Exchange e = new Exchange(identifier);
        e.setParent(parent);
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
        Object temp2 = null;
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Exchange temp = (Exchange)it.next();
            temp2 = temp.getChildrenList().getChild(id);
        }
        return (Exchange)temp2;
    }

    public Exchange getChildAt(int i) {
        if(list.getSize()<i)
            throw new IllegalArgumentException("Not enough children [ExchangeList:getChildAt]");
        Iterator it = list.iterator();
        while(list.getSize()-i!=0) {
            it.next();
            i++;
        }
        return (Exchange)it.next();
    }
}
