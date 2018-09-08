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
            if(Exchange.Root.residentSet().getMobilePhone(mobileNumber).status())
                throw new IllegalArgumentException("Mobile already regisetered and switched on");
            else {
                Exchange.Root.residentSet().getMobilePhone(mobileNumber).switchOn();
                Exchange temp = Exchange.Root.residentSet().getMobilePhone(mobileNumber).location();
                while(!temp.isRoot()) {
                    temp.residentSet().removeMobile(mobileNumber);
                    temp = temp.getParent();
                }
                Exchange.Root.residentSet().removeMobile(mobileNumber);
            }
        getExchange(involvedExchange).addMobilePhone(mobileNumber);
    }

    public void switchOff(MobilePhone a) {
        int mobileNumber = a.number();
        if(contains(mobileNumber))
            if(Exchange.Root.residentSet().getMobilePhone(mobileNumber).status())
                topLevel.residentSet().getMobilePhone(mobileNumber).switchOff();
            else
                throw new IllegalArgumentException("Phone is already off. ");
        else
            throw new IllegalArgumentException("Phone doesn't exist.");
    }

    public void performAction(String actionMessage) {

        String[] tokens = actionMessage.split(" ");

        if (actionMessage.contains("addExchange")) {
            if(tokens.length!=3) {
                System.out.println(actionMessage+": "+"Error - "+"Input Mismatch");
                return;
            }
            int parentExchange = Integer.parseInt(tokens[1]);
            int newExchange = Integer.parseInt(tokens[2]);
            try {
                if(Exchange.Root.associatedTree().containsNode(newExchange))
                    throw new IllegalArgumentException("Exchange already in tree");
                getExchange(parentExchange).addChild(newExchange);
            }
            catch(IllegalArgumentException e) {
                System.out.println(actionMessage+": "+"Error: "+e.getMessage());
            }
        }

        else if (actionMessage.contains("switchOnMobile")) {
            if(tokens.length!=3) {
                System.out.println(actionMessage+": "+"Error - "+"Input Mismatch");
                return;
            }
            int involvedExchange = Integer.parseInt(tokens[2]);
            int mobileNumber = Integer.parseInt(tokens[1]);
            try {
                switchOn(new MobilePhone(mobileNumber), new Exchange(involvedExchange));
            }
            catch(IllegalArgumentException e) {
                System.out.println(actionMessage+": "+"Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("switchOffMobile")) {
            if(tokens.length!=2) {
                System.out.println(actionMessage+": "+"Error - "+"Input Mismatch");
                return;
            }
            int mobileNumber = Integer.parseInt(tokens[1]);
            try {
                switchOff(new MobilePhone(mobileNumber));
            }
            catch(IllegalArgumentException e) {
                System.out.println(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("queryNthChild")) {
            if(tokens.length!=3) {
                System.out.println(actionMessage+": "+"Error - "+"Input Mismatch");
                return;
            }
            int parentExchange = Integer.parseInt(tokens[1]);
            int childNumber = Integer.parseInt(tokens[2]);
            try {
                System.out.println(actionMessage+": "+getExchange(parentExchange).child(childNumber).getNumber());
            }
            catch(Exception e) {
                System.out.println(actionMessage+": "+"Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("queryMobilePhoneSet")) {
            if(tokens.length!=2) {
                System.out.println(actionMessage+": "+"Error - "+"Input Mismatch");
                return;
            }
            int exchangeNumber = Integer.parseInt(tokens[1]);
            try {
                System.out.println(actionMessage+": "+getExchange(exchangeNumber).residentSet().printOnPhones());
            }
            catch(Exception e) {
                System.out.println(actionMessage+": "+"Error - "+e.getMessage());
            }
        }
        else {
            System.out.println(actionMessage+": Error - Illegal Action.");
        }

    }
}
