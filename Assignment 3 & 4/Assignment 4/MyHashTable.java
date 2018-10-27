import java.util.Iterator;

public class MyHashTable {
    private MyLinkedList<WordEntry>[] hashArray;

    public MyHashTable() {
        // hashArray = new MyLinkedList[26];
        hashArray = new MyLinkedList[5000];
    }

    private int getHashIndex(String str) {
        str = str.toLowerCase();
        // return (((int)str.toLowerCase().charAt(0))%97)%26;
        // return (Math.abs(str.hashCode()))%5000;
        return (calculateHash(str.toCharArray()));
    }

    private int calculateHash(char[] charArray) {
        // int sum = 65535;
        // for(char e: charArray)
        //     sum = (sum << 7) ^ (e << 9) | (sum >>> 25);
        // return Math.abs((int)(sum)%5000);
        int h=0;
        for (char e: charArray) {
            h = (h << 5) | (h >>> 27);
            h += (int) e;
        }
        return Math.abs(h%5000);
    }

    public void addPositionsForWord(WordEntryUsingAVL w) {
        String word = w.getWord();
        WordEntry w_check = new WordEntry(word);
        int index = getHashIndex(word);
        MyLinkedList<WordEntry> posList = hashArray[index];
        if(hashArray[index] == null) {
            hashArray[index] = new MyLinkedList<WordEntry>();
            WordEntry newEntry = new WordEntry(word);
            newEntry.addPositions(w.getAllPositionsForThisWord());
            hashArray[index].Insert(newEntry);
        }
        else if(posList.isMember(w_check)) {
            Iterator<WordEntry> it = posList.iterator();
            while(it.hasNext()) {
                WordEntry temp = it.next();
                if(temp.equals(w_check)) {
                    temp.addPositions(w.getAllPositionsForThisWord());
                    break;
                }
            }
        }
        else {
            WordEntry newEntry = new WordEntry(word);
            newEntry.addPositions(w.getAllPositionsForThisWord());
            posList.Insert(newEntry);
        }
    }

    public void addAllPositionsInPage(PageEntry p) {
        PageIndex pI = p.getPageIndex();
        Iterator<WordEntryUsingAVL> it = pI.getWordEntries().iterator();
        while(it.hasNext())
            addPositionsForWord(it.next());
    }

    public WordEntry wordPositions(String word) throws IllegalArgumentException {
        int index = getHashIndex(word);
        MyLinkedList<WordEntry> posList = hashArray[index];
        if(posList == null)
            throw new IllegalArgumentException("No webpage contains word "+word);
        Iterator<WordEntry> it = posList.iterator();
        while(it.hasNext()) {
            WordEntry temp = it.next();
            if(temp.equals(new WordEntry(word))) {
                return temp;
            }
        }
        throw new IllegalArgumentException("No webpage contains word "+word);
    }

    public void printHashTable() {
        for(int i=0; i<hashArray.length; i++) {
            MyLinkedList<WordEntry> list = hashArray[i];
            if(list == null)
                continue;
            System.out.println("-------"+i+"-------");
            Iterator<WordEntry> it = list.iterator();
            while(it.hasNext())
                System.out.println(it.next());
            System.out.println("--------------");
        }
    }
}
