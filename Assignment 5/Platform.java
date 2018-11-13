import java.util.Vector;
import java.util.Iterator;

/*
IMPORTANT ASSUMPTION
1. AT TIME 0 ONLY SUSCRIBE IS POSSIBLE. AND t(-1) IS SET TO 0.
2. SUBSCRIBE AND UNSUBSCRIBE CAN'T BE AT THE SAME TIME (INVOLVING AN ORDERED PAIR).
3. AT ANY TIME THE ORDER OF OPERATIONS IS:
    I.      PUBLISH - READ
    II.     SUBSCRIBE - UNSUBSCRIBE
*/

public class Platform {
    private Vector<User> allUsers;
    private Vector<Text> allMessages;

    public Platform() {
        this.allUsers = new Vector<>();
        this.allMessages = new Vector<>();
    }

    public User getUser(String uid) {
        return allUsers.get(allUsers.indexOf(new User(uid)));
    }

    public void addMessagesToNewUser(int time, String pid, String uid) {
        Iterator<Text> it = allMessages.iterator();
        while(it.hasNext()) {
            Text temp = it.next();
            if(temp.userId().equals(pid) && temp.time() == time)
                getUser(uid).addToFeed(temp);
        }
    }

    public String performAction(String action) {
        String[] task = action.split(",");
        if(action.startsWith("READ")) {
            try {
                int time = Integer.parseInt(task[1]);
                String uid = task[2];
                return action+"["+getUser(uid).readMessages(time)+"]";
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else if(action.startsWith("PUBLISH")) {
            try {
                if(task.length == 6) {
                    int time = Integer.parseInt(task[1]);
                    User user = getUser(task[2]);
                    Text post = new Text(time, task[2], task[3], task[4], task[5]);
                    if(allMessages.contains(new Text(post.id())))
                        throw new IllegalArgumentException("Id "+post.id()+" pre-exists");
                    if(post.type().startsWith("REPLY")) {
                        if(!allMessages.contains(new Text(post.pastTextId())))
                            throw new NullPointerException("No text with id "+post.pastTextId());
                        Text temp = allMessages.get(allMessages.indexOf(new Text(post.pastTextId())));
                        User u = getUser(temp.userId());
                        if(!user.hasSubscriber(u))
                            u.addToFeed(post);
                    }
                    allMessages.add(post);
                    user.postMessage(post);
                    return action;
                }
                else {
                    int time = Integer.parseInt(task[1]);
                    User user = getUser(task[2]);
                    Text post = new Text(time, task[2], task[3], null, task[4]);
                    if(!allMessages.contains(new Text(post.pastTextId())))
                        throw new NullPointerException("No text with id "+post.pastTextId());
                    if(allMessages.contains(new Text(post.id())))
                        throw new IllegalArgumentException("Id "+post.id()+" pre-exists");
                    Text temp = allMessages.get(allMessages.indexOf(new Text(post.pastTextId())));
                    allMessages.add(post);
                    user.postMessage(post);
                    post.setText(temp.text());
                    return action;
                }
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else if(action.startsWith("SUBSCRIBE")) {
            try {
                int time = Integer.parseInt(task[1]);
                String uid = task[2], pid = task[3];
                if(!allUsers.contains(new User(uid)))
                    allUsers.add(new User(uid));
                if(!allUsers.contains(new User(pid)))
                    allUsers.add(new User(pid));
                if(getUser(uid).isSubscriberOf(getUser(pid)))
                    throw new Exception(uid+" is already subscribed to "+pid);
                getUser(uid).Subscribe(getUser(pid));
                return action;
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else if(action.startsWith("UNSUBSCRIBE")) {
            try {
                int time = Integer.parseInt(task[1]);
                String uid = task[2], pid = task[3];
                if(!allUsers.contains(new User(uid)))
                    allUsers.add(new User(uid));
                if(!allUsers.contains(new User(pid)))
                    allUsers.add(new User(pid));
                if(!getUser(uid).isSubscriberOf(getUser(pid)))
                    throw new Exception(uid+" is already not subscribed to "+pid);
                getUser(uid).Unsubscribe(getUser(pid));
                // removeMessagesFromOldUser(time, pid, uid);
                return action;
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else {
            return String.format("Illegal Action");
        }
    }
}
