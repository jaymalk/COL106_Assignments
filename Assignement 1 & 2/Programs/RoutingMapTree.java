public class RoutingMapTree {
    public Exchange topLevel;

    public RoutingMapTree() {
        topLevel = new Exchange(Exchange.exchangeCount, null);
        Exchange.exchangeCount++;
        Exchange.Root = topLevel;
    }

    public RoutingMapTree(Exchange e) {
        topLevel = e;
    }

    // public RoutingMapTree(Exchange e) {
    //     NOTHING
    // }

    public int getID() {
        return topLevel.getNumber();
    }

    public boolean contains(int mobileNumber) {
        return topLevel.residentSet().containsMobile(mobileNumber);
    }

    public void addMobile(Exchange e, int mobileNumber) {
        //  ???
    }
}
