public class MobilePhone {
    private int number;
    private boolean phoneIsOn;
    private Exchange baseStation;

    public MobilePhone(int number) {
        this.number = number;
        this.phoneIsOn = true;
    }

    public int number() {
        return this.number;
    }

    public boolean status() {
        return this.phoneIsOn;
    }

    public void switchOn() {
        this.phoneIsOn = true;
    }

    public void swithOff() {
        this.phoneIsOn = false;
    }

    public Exchange location() {
        if(status())
            return this.baseStation;
        throw new IllegalArgumentException("Phone is switched off...");
    }

    public void setBaseStation(Exchange e) {
        this.baseStation = e;
    }

    @Override
    public String toString() {
        return String.format("%d", number);
    }
}
