import java.util.Iterator;
import java.util.NoSuchElementException;

public class PageIndex {
    MyLinkedList<WordEntry> wordEntries;

    public PageIndex() {
        this.wordEntries = new MyLinkedList<>();
    }

    public void addPositionForWord(String str, Position p) {
        if(wordEntries.isMember(new WordEntry(str))) {
            Iterator<WordEntry> it = wordEntries.iterator();
            while(it.hasNext()) {
                WordEntry temp = it.next();
                if(temp.getWord().equals(str)) {
                    temp.addPositions(p);
                    break;
                }
            }
        }
        else {
            WordEntry temp = new WordEntry(str);
            temp.addPositions(p);
            wordEntries.Insert(temp);
        }
    }

    public MyLinkedList<WordEntry> getWordEntries() {
        return wordEntries;
    }

    public int getTotalConnectorWords() {
        int totalWords = 0;
        Iterator<WordEntry> it = wordEntries.iterator();
        while(it.hasNext())
            totalWords+=(it.next().getAllPositionsForThisWord().getSize());
        return totalWords;
    }

    @Override
    public String toString() {
        Iterator<WordEntry> it = wordEntries.iterator();
        String s = "";
        while (it.hasNext())
            s = (it.next().toString()+"\n")+s;
        return s;
    }
}
