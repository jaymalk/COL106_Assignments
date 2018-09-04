import java.util.Iterator;

public class Exchange {
    private int number;
    private Exchange parent;
    private ExchangeList children;
    private MobilePhoneSet residentSet;

    public Exchange(int number) {
        this.number = number;
        children = new ExchangeList();
    }

    public int numChildren() {
        return children.list.getSize();
    }

    public boolean isExternal() {
        return numChildren() == 0;
    }

    public MobilePhoneSet residentSet() {
        return this.residentSet();
    }

	public int getNumber() {
		return this.number;
	}

	public Exchange getParent() {
		return this.parent;
	}

    public Exchange child(int i) {
        if(children.list.getSize()<i)
            throw new IllegalArgumentException("Not enough children");
        Iterator it = children.list.iterator();
        while(--i!=0)
            it.next();
        return (Exchange)it.next();
    }

	public void setParent(Exchange parent) {
        parent.addChild(this);
		this.parent = parent;
	}

    public ExchangeList getChildrenList() {
        return this.children;
    }

    public void addChild(Exchange e) {
        children.addExchange(e);
        residentSet.uniteMobileSet(e.residentSet());
    }

    public void deleteChild(Exchange e) {
        children.removeChild(e);
    }
}
