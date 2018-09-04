public class MobilePhoneSet {

    private Myset mpset;

    public MobilePhoneSet() {
        mpset = new Myset();
    }

    public MobilePhone getMobilePhone(int number) {
        return null;
    }

    public void addMobile(MobilePhone m) {
        mpset.insertItem(m);
    }

    public void removeMobile(MobilePhone m) {
        mpset.deleteItem(m);
    }

    public Myset getSet() {
        return mpset;
    }

    public void uniteMobileSet(MobilePhoneSet... phoneSets) {
        for(MobilePhoneSet m: phoneSets)
            mpset.unite(mpset, m.getSet());
    }
}
