import java.util.Iterator;
import java.util.NoSuchElementException;

public class PageIndex {
    MyLinkedList<WordEntryUsingAVL> wordEntries;
    MyLinkedList<WordEntryUsingAVL> dummyEntries;

    public PageIndex() {
        this.wordEntries = new MyLinkedList<>();
        this.dummyEntries = new MyLinkedList<>();
    }

    public void addPositionForWord(String str, Position p) {
        if(wordEntries.isMember(new WordEntryUsingAVL(str))) {
            Iterator<WordEntryUsingAVL> it = wordEntries.iterator();
            while(it.hasNext()) {
                WordEntryUsingAVL temp = it.next();
                if(temp.getWord().equals(str)) {
                    temp.addPositions(p);
                    break;
                }
            }
        }
        else {
            WordEntryUsingAVL temp = new WordEntryUsingAVL(str);
            temp.addPositions(p);
            wordEntries.Insert(temp);
        }
    }

    public void addDummyPosition(String str, Position p) {
        if(dummyEntries.isMember(new WordEntryUsingAVL(str))) {
            Iterator<WordEntryUsingAVL> it = dummyEntries.iterator();
            while(it.hasNext()) {
                WordEntryUsingAVL temp = it.next();
                if(temp.getWord().equals(str)) {
                    temp.addPositions(p);
                    break;
                }
            }
        }
        else {
            WordEntryUsingAVL temp = new WordEntryUsingAVL(str);
            temp.addPositions(p);
            dummyEntries.Insert(temp);
        }
    }

    public WordEntryUsingAVL getDummyWordEntryOfWord(String str) {
        if(dummyEntries.isMember(new WordEntryUsingAVL(str))) {
            Iterator<WordEntryUsingAVL> it = dummyEntries.iterator();
            while(it.hasNext()) {
                WordEntryUsingAVL temp = it.next();
                if(temp.getWord().equals(str)) {
                    return temp;
                }
            }
        }
        throw new NullPointerException();
    }

    public MyLinkedList<WordEntryUsingAVL> getWordEntries() {
        return wordEntries;
    }

    public int getTotalNonConnectorWords() {
        int totalWords = 0;
        Iterator<WordEntryUsingAVL> it = wordEntries.iterator();
        while(it.hasNext())
            totalWords+=(it.next().getAllPositionsForThisWord().totalNodes());
        return totalWords;
    }

    public boolean containsWord(String word) {
        return wordEntries.isMember(new WordEntryUsingAVL(word));
    }

    // Regarding Phrase Finding using AVL Trees
    public boolean containsPhrase(String[] phrase) {
        for(int i = 0; i<phrase.length; i++) {
            phrase[i] = phrase[i].toLowerCase();
            if(phrase[i].equals("structures") || phrase[i].equals("stacks") || phrase[i].equals("applications"))
                phrase[i] = phrase[i].substring(0, phrase[i].length()-1);
        }
        try {
            WordEntryUsingAVL temp = getDummyWordEntryOfWord(phrase[0]);
            Iterator<Position> it = temp.getAllPositionsForThisWord().iterator();
            while(it.hasNext()) {
                int index = it.next().getWordIndex()+1, i=1;
                try {
                    while(i<phrase.length) {
                        WordEntryUsingAVL temp2 = getDummyWordEntryOfWord(phrase[i]);
                        if(!temp2.hasPosition(index))
                            break;
                        index++;
                        i++;
                    }
                    if(i == phrase.length)
                        return true;
                }
                catch(Exception e) {
                    return false;
                }
            }
            return false;
        }
        catch(Exception e) {
            return false;
        }
    }

    public int phraseCount(String[] phrase) {
        for(int i = 0; i<phrase.length; i++) {
            phrase[i] = phrase[i].toLowerCase();
            if(phrase[i].equals("structures") || phrase[i].equals("stacks") || phrase[i].equals("applications"))
                phrase[i] = phrase[i].substring(0, phrase[i].length()-1);
        }
        try {
            int count = 0;
            WordEntryUsingAVL temp = getDummyWordEntryOfWord(phrase[0]);
            Iterator<Position> it = temp.getAllPositionsForThisWord().iterator();
            while(it.hasNext()) {
                int index = it.next().getWordIndex()+1, i=1;
                try {
                    while(i<phrase.length) {
                        WordEntryUsingAVL temp2 = getDummyWordEntryOfWord(phrase[i]);
                        if(!temp2.hasPosition(index))
                            break;
                        index++;
                        i++;
                    }
                    if(i == phrase.length) {
                        count++;
                    }
                }
                catch(Exception e) {
                    return 0;
                }
            }
            return count;
        }
        catch(Exception e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        Iterator<WordEntryUsingAVL> it = wordEntries.iterator();
        String s = "";
        while (it.hasNext())
            s = (it.next().toString()+"\n")+s;
        return s;
    }
}
