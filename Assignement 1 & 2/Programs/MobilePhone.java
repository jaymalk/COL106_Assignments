public class MobilePhone {
    private int number;
    private boolean phoneIsOn;
    private Exchange baseStation;

    // CONNSTRUCTOR
    public MobilePhone(int number) {
        this.number = number;
        this.phoneIsOn = true;
    }

    // PHONE RELATED FUNNCTIONS
    public int number() {
        return this.number;
    }

    public boolean status() {
        return this.phoneIsOn;
    }

    public void switchOn() {
        this.phoneIsOn = true;
    }

    public void switchOff() {
        this.phoneIsOn = false;
    }

    // EXCHANGE RELATED FUNCTIONS
    public Exchange location() {
        if(status())
            return this.baseStation;
        throw new IllegalArgumentException("Phone is switched off...");
    }

    public void setBaseStation(Exchange e) {
        this.baseStation = e;
    }

    // FUNCTIONS TO FACILITATE COMPARINSON
    @Override
    public String toString() {
        return String.format("%d", number);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MobilePhone) {
            MobilePhone temp = (MobilePhone)o;
            if(temp.number() == this.number())
                return true;
        }
        return false;
    }

    /*  Am confused whether to use it or not.... (It's way too easy!)

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == this.hashCode();
    }

    */

    @Override
    public int hashCode() {
        return number;
    }
}
