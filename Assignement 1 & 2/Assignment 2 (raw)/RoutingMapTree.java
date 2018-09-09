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

    public Exchange findPhone(MobilePhone m) {
        return Exchange.Root.residentSet().getMobilePhone(m.number()).location();
    }

    public Exchange lowestRouter(Exchange a, Exchange b) {
        int ida = a.getNumber(), idb = b.getNumber();
        Exchange temp = getExchange(ida);
        if(!temp.isExternal())
            throw new IllegalArgumentException(temp.toString()+" is not zero level.");
        if(!getExchange(idb).isExternal())
            throw new IllegalArgumentException("Exchange no. "+idb+" is not zero level.");
        while(temp!=Exchange.Root) {
            if(temp.associatedTree().containsNode(idb))
                break;
            temp = temp.getParent();
        }
        return temp;
    }

    public ExchangeList routeCall(MobilePhone a, MobilePhone b) {
        if(!a.status())
            throw new IllegalArgumentException("Mobile phone with identifier "+a.number()+" is currently switched off");
        if(!b.status())
            throw new IllegalArgumentException("Mobile phone with identifier "+b.number()+" is currently switched off");
        ExchangeList route = new ExchangeList();
        MyLinkedList routelist = route.list, tempList = new MyLinkedList();
        Exchange temp = a.location();
        while(!temp.associatedTree().containsNode(b.location().getNumber())) {
            tempList.Insert(temp);
            temp = temp.getParent();
        }
        temp = b.location();
        while(!temp.associatedTree().containsNode(a.location().getNumber())) {
            routelist.Insert(temp);
            temp = temp.getParent();
        }
        routelist.Insert(temp);
        Iterator it = tempList.iterator();
        while(it.hasNext())
            routelist.Insert(it.next());
        return route;
    }

    public void movePhone(MobilePhone a, Exchange b) {
        switchOff(a);
        switchOn(a, b);
    }

    public String performAction(String actionMessage) {

        String[] tokens = actionMessage.split(" ");

        if (actionMessage.contains("addExchange")) {
            try {
                int parentExchange = Integer.parseInt(tokens[1]);
                int newExchange = Integer.parseInt(tokens[2]);
                if(Exchange.Root.associatedTree().containsNode(newExchange))
                    throw new IllegalArgumentException("Exchange already in tree");
                getExchange(parentExchange).addChild(newExchange);
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("switchOnMobile")) {
            try {
                int involvedExchange = Integer.parseInt(tokens[2]);
                int mobileNumber = Integer.parseInt(tokens[1]);
                switchOn(new MobilePhone(mobileNumber), new Exchange(involvedExchange));
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("switchOffMobile")) {
            try {
                int mobileNumber = Integer.parseInt(tokens[1]);
                switchOff(new MobilePhone(mobileNumber));
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("queryNthChild")) {
            try {
                int parentExchange = Integer.parseInt(tokens[1]);
                int childNumber = Integer.parseInt(tokens[2]);
                return String.format(actionMessage+": "+getExchange(parentExchange).child(childNumber).getNumber());
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }

        else if (actionMessage.contains("queryMobilePhoneSet")) {
            try {
                int exchangeNumber = Integer.parseInt(tokens[1]);
                return String.format(actionMessage+": "+getExchange(exchangeNumber).residentSet().printOnPhones());
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }
        else if (actionMessage.contains("findPhone")) {
            try {
                int mobileNumber = Integer.parseInt(tokens[1]);
                return String.format(actionMessage.replaceFirst("f", "queryF")+": "+findPhone(new MobilePhone(mobileNumber)).getNumber());
            }
            catch(Exception e) {
                return String.format(actionMessage.replaceFirst("f", "queryF")+": Error - "+e.getMessage());
            }
        }
        else if (actionMessage.contains("lowestRouter")) {
            try {
                int ex1 = Integer.parseInt(tokens[1]);
                int ex2 = Integer.parseInt(tokens[2]);
                return String.format(actionMessage.replaceFirst("l", "queryL")+": "+lowestRouter(new Exchange(ex1), new Exchange (ex2)).getNumber());
            }
            catch(Exception e) {
                return String.format(actionMessage.replaceFirst("l", "queryL")+": Error - "+e.getMessage());
            }
        }
        else if (actionMessage.contains("findCallPath")) {
            try {
                int mobile1 = Integer.parseInt(tokens[1]);
                int mobile2 = Integer.parseInt(tokens[2]);
                ExchangeList path = routeCall(Exchange.Root.residentSet().getMobilePhone(mobile1), Exchange.Root.residentSet().getMobilePhone(mobile2));
                Iterator it = path.list.iterator();
                String i = "";
                while(it.hasNext())
                    i = i+it.next().hashCode()+", ";
                return String.format(actionMessage.replaceFirst("f", "queryF")+": "+i.substring(0, i.length()-2));
            }
            catch(Exception e) {
                return String.format(actionMessage.replaceFirst("f", "queryF")+": Error - "+e.getMessage());
            }
        }
        else if (actionMessage.contains("movePhone")) {
            try {
                int involvedExchange = Integer.parseInt(tokens[2]);
                int mobileNumber = Integer.parseInt(tokens[1]);
                movePhone(new MobilePhone(mobileNumber), new Exchange(involvedExchange));
            }
            catch(Exception e) {
                return String.format(actionMessage+": Error - "+e.getMessage());
            }
        }
        else {
            return String.format(actionMessage+": Error - Illegal Action.");
        }
        return "";
    }
}
