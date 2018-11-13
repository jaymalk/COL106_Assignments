import java.util.Vector;

public class User {
    private String uid;
    private Network subscribers, following;
    private Queue<Text> feed;
    private MyLinkedList<Text> posts;

    // Constructor
    public User(String uid) {
        this.uid = uid;
        this.subscribers = new Network(this);
        this.following = new Network(this);
        this.feed = new Queue<>();
        this.posts = new MyLinkedList<>();
    }

    // User ID.
    public String id() {
        return this.uid;
    }

    // Subscriber/Subscribed related operations
    public void addSubscriber(User u) {
        subscribers.add(u);
    }

    public boolean hasSubscriber(User u) {
        return subscribers.contains(u);
    }

    public void subscribeTo(User u) {
        following.add(u);
    }

    public boolean isSubscriberOf(User u) {
        return following.contains(u);
    }


    // Subscribing and Unsubscribing
    public void Subscribe(User u) {
        u.addSubscriber(this);
        subscribeTo(u);
    }

    public void Unsubscribe(User u) {
        u.subscribers.remove(this);
        following.remove(u);
    }

    // Reading Messages
    public String readMessages(int t) {
        String s = "";
        if(!feed.isEmpty())
            while(feed.front().time() < t) {
                Text temp = feed.dequeue();
                s = s+","+temp.text();
                if(feed.isEmpty())
                    break;
            }
        if(s == "")
            throw new IllegalArgumentException("No data avaialable for uid "+uid);
        else
            return s.substring(1);
    }

    // Personal Posts.
    public void postMessage(Text t) {
        this.posts.Insert(t);
        subscribers.sendToFeed(t);
    }

    public void addToFeed(Text t) {
        feed.enqueue(t);
    }

    public MyLinkedList<Text> getPosts() {
        return this.posts;
    }

    // OVERRIDE
    @Override
    public boolean equals(Object other) {
        try {
            assert other instanceof User: "Error";
            User temp = (User)other;
            return temp.id().equals(uid);
        }
        catch(Exception e) {
            return false;
        }
    }
    @Override
    public int hashCode() {
        return uid.hashCode();
    }
    @Override
    public String toString() {
        return String.format("User: "+uid);
    }
}
