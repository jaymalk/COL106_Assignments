import java.util.*;
import java.io.FileInputStream;

public class PageEntry {

    private PageIndex pageIndex;
    private String pageName;
    private int totalNumberWords;

    public PageEntry(String pageName) {
        try {
            this.pageName = pageName;
            List<String> connectorWords =   Arrays.asList("a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or", "and", "does", "will", "whose");
            String[] punctuations = {"{", "}", "[", "]", "<", ">", "=", "(", ")", ".", ",", ";", "’", "'", "”", "?", "#", "!", "-", ":", "\""};
            pageIndex = new PageIndex();
            FileInputStream file = new FileInputStream(pageName);
            Scanner s = new Scanner(file);
            try {
                int pos = 0;
                while(s.hasNextLine()) {
                    String line = s.nextLine();
                    for(String a: punctuations)
                        line = line.replace(a, " ");
                    Scanner lineScan = new Scanner(line);
                    while(lineScan.hasNext()) {
                        String word = lineScan.next().toLowerCase();
                        pos++;
                        if(word.equals("structures") || word.equals("stacks") || word.equals("applications"))
                            word = word.substring(0, word.length()-1);
                        if(connectorWords.contains(word))
                            continue;
                        else
                            pageIndex.addPositionForWord(word, new Position(this, pos));
                    }
                    this.totalNumberWords = pos;
                    lineScan.close();
                }
            }
            finally {
                s.close();
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("No webpage with name "+pageName);
        }
    }

    public String positionsOfWord(String word) throws Exception {
        String s = "";
        MyLinkedList<WordEntry> list = getPageIndex().getWordEntries();
        if(!list.isMember(new WordEntry(word)))
            throw new NullPointerException("Webpage "+pageName+" does not contain word "+word);
        Iterator<WordEntry> it = list.iterator();
        while(it.hasNext()) {
            WordEntry temp  = it.next();
            if(temp.equals(new WordEntry(word))) {
                MyLinkedList<Position> pos = temp.getAllPositionsForThisWord();
                Iterator<Position> it2 = pos.iterator();
                while(it2.hasNext()) {
                    Position temp2 = it2.next();
                    if(temp2.getPageEntry().getPageName().equals(pageName))
                        s = temp2.getWordIndex()+", "+s;
                }
            }
        }
        if(s.equals(""))
            throw new Exception("Webpage "+pageName+" does not contain word "+word);
        return s.substring(0, s.length()-2);
    }

    public PageIndex getPageIndex() {
        return this.pageIndex;
    }

    public void changePageName(String newName) {
        this.pageName = newName;
    }

    public String getPageName() {
        return this.pageName;
    }

    public int getTotalNumberWords() {
    	return totalNumberWords;
    }

    @Override
    public String toString() {
        String s = "Page Name : "+pageName+"\n"+pageIndex.toString();
        return s;
    }

    @Override
    public boolean equals(Object o) {
        PageEntry p = (PageEntry)o;
        return pageName.equals(p.getPageName());
    }
}
