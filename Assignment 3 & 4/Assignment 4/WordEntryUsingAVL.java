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

    AVLTree getAllPositionsForThisWord() {
        return indices;
    }

    @Override
    public String toString() {
        if(indices.getRoot() == null)
            return "";
        return indices.leftSubTree().toString()+", "+indices.getRootKey().toString()+", "+indices.rightSubTree().toString();
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
