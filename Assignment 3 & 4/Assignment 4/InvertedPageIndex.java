import java.util.Iterator;
import java.util.HashMap;
import java.util.Arrays;

public class InvertedPageIndex {
    private SearchEngine searchEngine;
    private MyHashTable indexTable;
    private int totalPages;

    public InvertedPageIndex(SearchEngine s) {
        this.searchEngine = s;
        this.indexTable = new MyHashTable();
        this.totalPages = 0;
    }

    public void addPage(PageEntry p) throws Exception {
        this.indexTable.addAllPositionsInPage(p);
        totalPages++;
    }

    public WordEntry getWordInvertedIndex(String word) throws Exception {
        return indexTable.wordPositions(word);
    }

    public MySet<PageEntry> getPagesWhichContainWord(String str) throws Exception {
        WordEntry w = indexTable.wordPositions(str);
        MySet<PageEntry> pageSet = new MySet<>();
        Iterator<Position> it = w.getAllPositionsForThisWord().iterator();
        while(it.hasNext())
            pageSet.addElement(it.next().getPageEntry());
        return pageSet;
        // WordEntry w = indexTable.wordPositions(str);
        // MySet<PageEntry> pageSet = new MySet<>();
        // Iterator<Position> it = w.getAllPositionsForThisWord().iterator();
        // HashMap<Float, PageEntry> pageDict = new HashMap();
        // while(it.hasNext()) {
        //     PageEntry temp = it.next().getPageEntry();
        //     if(!pageDict.containsValue(temp)) {
        //         pageDict.put(temp.getPageIndex().wordFrequency(str), temp);
        //     }
        // }
        // Object[] keys = pageDict.keySet().toArray();
        // Arrays.sort(keys);
        // for(Object k : keys)
        //     if((float)k!=0.0)
        //         pageSet.addElement(pageDict.get(k));
        // return pageSet;
    }

    public SearchEngine getSearchEngine() {
        return this.searchEngine;
    }

    public MySet<PageEntry> getPagesWhichContainPhrase(String str[]) {
        Iterator<PageEntry> it = this.searchEngine.getAddedPages().getSetList().iterator();
        MySet<PageEntry> pages = new MySet<>();
        while(it.hasNext()) {
            PageEntry temp = it.next();
            if(temp.containsPhrase(str))
                pages.addElement(temp);
        }
        return pages;
    }

    public int getTotalPages() {
    	return totalPages;
    }

    public float inverseDocumentFrequency(String word) {
        try {
            return (float)Math.log((float)getTotalPages()/(float)getPagesWhichContainWord(word).Size());
        }
        catch(Exception e) {
            return 0;
        }
    }

    public float inverseDocumentFrequency(String[] str) {
        try {
            return (float)Math.log((float)getTotalPages()/(float)getPagesWhichContainPhrase(str).Size());
        }
        catch(Exception e) {
            return 0;
        }
    }

    public void print() {
        indexTable.printHashTable();
    }
}
