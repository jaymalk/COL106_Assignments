import java.util.Iterator;

public class WordEntryUsingAVL {
    private AVLTree indices;
    private String word;

    public WordEntryUsingAVL(String word) {
        this.indices = new AVLTree();
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public void addPositions(Position position) {
        indices.addNewElement(position);
    }

    public void addPositions(MyLinkedList<Position> positions) {
        Iterator<Position> it = positions.iterator();
        while(it.hasNext())
            indices.addNewElement(it.next());
    }

    public void addPositions(AVLTree positions) {
        Iterator<Position> it = positions.iterator();
        while(it.hasNext())
            indices.addNewElement(it.next());
    }

    public boolean hasPosition(int index) {
        return indices.contains(new Position(null, index));
    }

    public AVLTree getAllPositionsForThisWord() {
        return indices;
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
    public boolean equals(Object other) {
        return other.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
