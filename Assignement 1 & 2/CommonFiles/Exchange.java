import java.util.Iterator;

public class Exchange {
    protected static int exchangeCount = 0;
    private int exchangeId;
    private Exchange parent;
    protected static Exchange Root;
    private ExchangeList children;
    private MobilePhoneSet residentSet;

    public Exchange(int exchangeId) {
        this.exchangeId = exchangeId;
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

    public void setParent(Exchange e) {
        this.parent = e;
    }

    public boolean isExternal() {
        return numChildren() == 0;
    }

    public boolean isRoot() {
        return equals(Root);
    }

    // MOBILE PHONE RELATED OPERATIONS
    public MobilePhoneSet residentSet() {
        return this.residentSet;
    }

    public void addMobilePhone(int number) {
        if(!isExternal())
            throw new IllegalArgumentException("Exchange is not a level zero exchange");
        MobilePhone temp = new MobilePhone(number);
        temp.setBaseStation(this);
        residentSet.addMobile(temp);
        updateParentSets();
    }

    public void updateParentSets() {
        if(isRoot())
            return;
        Exchange temp = this;
        do {
            temp = temp.getParent();
            temp.residentSet().uniteMobileSet(residentSet());
        } while(!temp.isRoot());
    }

    public void switchOffMobile(int number) {
        if (containsMobile(number))
            if(residentSet().getMobilePhone(number).status())
                residentSet().getMobilePhone(number).switchOff();
            else
                throw new IllegalArgumentException("Phone is already switched Off");
        else
            throw new IllegalArgumentException("Phone not in the resident set.");
    }

    public boolean containsMobile(int mobileNumber) {
        return residentSet.containsMobile(mobileNumber);
    }

    public static int getLevelZero(int mobileNumber) {
        try {
            return Root.residentSet().getMobilePhone(mobileNumber).location().getNumber();
        }
        catch(Exception e) {
            throw new IllegalArgumentException("Phone is not regisetered anywhere.");
        }
    }

    // CHILDREN OPERATIONS
    public void addChild(int childNumber) {
        Exchange.exchangeCount++;
        children.createNewChildExchange(this, childNumber);
    }

    public Exchange child(int i) {
        return children.getChildAt(i+1);
    }

    public ExchangeList getChildrenList() {
        return this.children;
    }

    public int numChildren() {
        return children.list.getSize();
    }

    // TREE ASSOCIATED OPERATIONS
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

    @Override
    public String toString() {
        return String.format("Exchange no. "+getNumber());
    }
}
