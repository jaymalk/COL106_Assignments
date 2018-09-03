public class Exchange {
    private int number;
    private Exchange parent;
    private ExchangeList children;
    private MobilePhoneSet residentSet;

    public Exchange(int number) {
        this.number = number;
        children = new ExchangeList();
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
}
