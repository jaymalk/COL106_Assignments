import java.util.Iterator;

public class Exchange {
    protected static int exchangeCount = 0;
    private int exchangeId;
    private Exchange parent;
    protected static Exchange Root;
    private ExchangeList children;
    private MobilePhoneSet residentSet;

    public Exchange(int exchangeId, Exchange parent) {
        this.exchangeId = exchangeId;
        this.parent = parent;
        children = new ExchangeList();
        residentSet =  new MobilePhoneSet();
    }

    public int getNumber() {
    	return this.exchangeId;
    }

    // NODE LIKE OPERATIONS
    public Exchange getParent() {
		return this.parent;
	}


    public boolean isExternal() {
        return numChildren() == 0;
    }

    public boolean isRoot() {
        return equals(Root);
    }

    // MOBILE PHONE RELATED OPERATIONS
    public MobilePhoneSet residentSet() {
        return this.residentSet();
    }

    public boolean containsMobile(int mobileNumber) {
        return residentSet.containsMobile(mobileNumber);
    }

    public int getLevelZero(int mobileNumber) {
        if(containsMobile(mobileNumber)) {
            if(isExternal())
                return this.exchangeId;
            else {
                //++++++
            }
        }
        return -1;
    }

    // CHILDREN OPERATIONS
    public void addChild(int childNumber) {
        children.createNewChildExchange(this, childNumber);
    }

    public Exchange child(int i) {
        return children.getChildAt(i);
    }

    public ExchangeList getChildrenList() {
        return this.children;
    }

    public int numChildren() {
        return children.list.getSize();
    }

    public RoutingMapTree subtree(int i) {
        return new RoutingMapTree(child(i));
    }

    public RoutingMapTree associatedTree() {
        return new RoutingMapTree(this);
    }

    // OVERRIDING SOME OBJECT FUNCTIONS
    @Override
    public int hashCode() {
        return exchangeId;
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }
}
