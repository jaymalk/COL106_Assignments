public class Text implements Comparable<Text>{
    private int time;
    private String uid, type, text, tid;

    // Constructor
    public Text(int time, String uid, String type, String text, String tid) {
        this.time = time;
        this.uid = uid ;
        this.type = type;
        this.text = text;
        this.tid = tid ;
    }

    public Text(String tid) {
        this.time = -1;
        this.uid = null ;
        this.type = null;
        this.text = null;
        this.tid = tid ;
    }

    // Getters of fields.
    public String text() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public int time() {
        return this.time;
    }

    public String id() {
        return this.tid;
    }

    public String type() {
        return this.type;
    }

    public String userId() {
        return this.uid;
    }

    // If a REPLY or REPOST, then about the conected text.
    public String pastTextId() {
        if(type.equals("NEW"))
            throw new IllegalArgumentException("The text is new. [Error: Text.java]");
        else if(type.startsWith("REPLY"))
            return type.substring(6, type.length()-1);
        else
            return type.substring(7, type.length()-1);
    }

    // Equality checker.
    public boolean equals(Text other) {
        return tid.equals(other.id());
    }

    // Comparable w.r.t. time.
    public int compareTo(Text t) {
        if(t.time() > time)
            return -1;
        else if (t.time() == time || t.id().equals(tid))
            return 0;
        else
            return 1;
    }

    //OVERRIDE
    @Override
    public boolean equals(Object other) {
        Text temp = (Text)other;
        return tid.equals(temp.id());
    }
    @Override
    public int hashCode() {
        return tid.hashCode();
    }
}
