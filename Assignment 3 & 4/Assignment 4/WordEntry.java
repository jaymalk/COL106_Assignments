import java.util.Iterator;

public class WordEntry {
    private MyLinkedList<Position> indices;
    private String word;

    public WordEntry(String word) {
        this.indices = new MyLinkedList<>();
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public void addPositions(Position position) {
        indices.Insert(position);
    }

    public void addPositions(MyLinkedList<Position> positions) {
        // indices.mergeLists(positions);
        Iterator<Position> it = positions.iterator();
        while(it.hasNext())
            indices.Insert(it.next());
    }

    MyLinkedList<Position> getAllPositionsForThisWord() {
        return indices;
    }

    public float getTermFrequency(String word, PageIndex p) {
        int totalWords = p.getTotalWords();
        int wordOccurence = indices.getSize();
        return (float)wordOccurence/(float)totalWords;
    }

    @Override
    public String toString() {
        Iterator<Position> it = indices.iterator();
        String s = "";
        while(it.hasNext())
            s = it.next().toString()+", "+s;
        return word+" : { "+s.substring(0, s.length()-2)+" }";
    }

    @Override
    // public boolean equals(Object other) {
    //     WordEntry w = (WordEntry)other;
    //     return word.equals(w.getWord());
    // }
    public boolean equals(Object other) {
        return other.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
