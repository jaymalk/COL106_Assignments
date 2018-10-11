import java.util.Iterator;

public class InvertedPageIndex {
    private SearchEngine searchEngine;
    private MyHashTable indexTable;

    public InvertedPageIndex(SearchEngine s) {
        this.searchEngine = s;
        this.indexTable = new MyHashTable();
    }

    public void addPage(PageEntry p) throws Exception {
        this.indexTable.addAllPositionsInPage(p);
    }

    public MySet<PageEntry> getPagesWhichContainWord(String str) throws Exception {
        WordEntry w = indexTable.wordPositions(str);
        MySet<PageEntry> pageSet = new MySet<>();
        Iterator<Position> it = w.getAllPositionsForThisWord().iterator();
        while(it.hasNext())
            pageSet.addElement(it.next().getPageEntry());
        return pageSet;
    }

    public SearchEngine getSearchEngine() {
        return this.searchEngine;
    }

    public void print() {
        indexTable.printHashTable();
    }
}
