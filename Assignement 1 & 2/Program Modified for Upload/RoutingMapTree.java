import java.util.*;

public class RoutingMapTree {
    public Exchange topLevel;

    public RoutingMapTree() {
        Exchange.exchangeCount=0;
        topLevel = new Exchange(Exchange.exchangeCount++);
        topLevel.setParent(null);
        Exchange.Root = topLevel;
    }

    public RoutingMapTree(Exchange e) {
        topLevel = e;
    }

    public int getID() {
        return topLevel.getNumber();
    }

    public boolean contains(int mobileNumber) {
        return topLevel.containsMobile(mobileNumber);
    }

    public boolean containsNode(int identifier) {
        if(topLevel.hashCode() == identifier)
            return true;
        ExchangeList list = topLevel.getChildrenList();
        Iterator it = list.list.iterator();
        boolean contains = false;
        while(it.hasNext()) {
            Exchange temp = (Exchange)it.next();
            contains = (contains || temp.associatedTree().containsNode(identifier));
            if(contains)
                return contains;
        }
        return false;
    }

    public Exchange getExchange(int identifier) {
        if(topLevel.hashCode() == identifier)
            return topLevel;
        if(containsNode(identifier)) {
            for(int i=0; i<topLevel.numChildren(); i++) {
                if(topLevel.subtree(i).containsNode(identifier))
                    return topLevel.subtree(i).getExchange(identifier);
            }
        }
        throw new IllegalArgumentException("No such exchange in tree [RoutingMapTree:getExchange]");
    }

    public void switchOn(MobilePhone a, Exchange b) {
        int mobileNumber = a.number();
        int involvedExchange = b.getNumber();
        if(Exchange.Root.containsMobile(mobileNumber))
            throw new IllegalArgumentException("Mobile already regisetered");
        getExchange(involvedExchange).addMobilePhone(mobileNumber);
    }

    public void switchOff(MobilePhone a) {
        int mobileNumber = a.number();
        if(contains(mobileNumber))
            topLevel.residentSet().getMobilePhone(mobileNumber).switchOff();
        else
            throw new IllegalArgumentException("Phone doesn't exist.");
    }

    public String performAction(String actionMessage) {

        String[] tokens = actionMessage.split(" ");

        if (actionMessage.contains("addExchange")) {
            int parentExchange = Integer.parseInt(tokens[1]);
            int newExchange = Integer.parseInt(tokens[2]);
            try {
                if(Exchange.Root.associatedTree().containsNode(newExchange))
                    throw new IllegalArgumentException("Exchange already in tree");
                getExchange(parentExchange).addChild(newExchange);
            }
            catch(IllegalArgumentException e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("switchOnMobile")) {
            int involvedExchange = Integer.parseInt(tokens[2]);
            int mobileNumber = Integer.parseInt(tokens[1]);
            try {
                switchOn(new MobilePhone(mobileNumber), new Exchange(involvedExchange));
            }
            catch(IllegalArgumentException e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("switchOffMobile")) {
            int mobileNumber = Integer.parseInt(tokens[1]);
            try {
                switchOff(new MobilePhone(mobileNumber));
            }
            catch(IllegalArgumentException e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("queryNthChild")) {
            int parentExchange = Integer.parseInt(tokens[1]);
            int childNumber = Integer.parseInt(tokens[2]);
            try {
                return String.format(actionMessage+": "+getExchange(parentExchange).child(childNumber).getNumber());
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("queryMobilePhoneSet")) {
            int exchangeNumber = Integer.parseInt(tokens[1]);
            try {
                return String.format(actionMessage+": "+getExchange(exchangeNumber).residentSet().printOnPhones());
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }
        return "";
    }
}
