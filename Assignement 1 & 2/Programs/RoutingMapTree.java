public class RoutingMapTree {
    public Exchange topLevel;
    public int identifier;

    public RoutingMapTree() {
        identifier = 0;
        topLevel = new Exchange(0);
    }

    public RoutingMapTree(Exchange e) {
        identifier = e.getNumber();
        topLevel = e;
    }
}
