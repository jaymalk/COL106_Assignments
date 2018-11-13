import java.util.Vector;

public class Network {
    private User user;
    private Vector<User> list;

    public Network(User associatedUser) {
        this.user = associatedUser;
        this.list = new Vector<>();
    }

    public User getUser() {
        return this.user;
    }

    public void add(User u) {
        list.add(u);
    }

    public void remove(User u) {
        list.remove(u);
    }

    public void sendToFeed(Text t) {
        for(User u : list) {
            u.addToFeed(t);
        }
    }

    public boolean contains(User u) {
        return list.contains(u);
    }
}
