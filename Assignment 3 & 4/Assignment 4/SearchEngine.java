import java.util.Scanner;
import java.util.Iterator;
import java.util.NoSuchElementException;;

public class SearchEngine {
    private InvertedPageIndex index;
    private MySet<PageEntry> pagesAdded;

    public SearchEngine() {
        index = new InvertedPageIndex(this);
        pagesAdded = new MySet<>();
    }

    public void addPage(String p) throws Exception{
        PageEntry page = new PageEntry("./webpages/"+p);
        page.changePageName(p);
        if(!pagesAdded.IsMember(page)) {
            pagesAdded.addElement(page);
            index.addPage(page);
        }
    }

    public PageEntry getPage(String pageName) {
        Iterator<PageEntry> it = pagesAdded.getSetList().iterator();
        while(it.hasNext()) {
            PageEntry temp = it.next();
            if(temp.getPageName().equals(pageName))
                return temp;
        }
        throw new NullPointerException("No page with name "+pageName+" in the inverted index.");
    }

    public MySet<PageEntry> getAddedPages() {
        return pagesAdded;
    }

    public boolean containsAllWords(PageEntry p, String[] words) {
        for(int i = 0; i<words.length; i++) {
            words[i] = words[i].toLowerCase();
            if(words[i].equals("structures") || words[i].equals("stacks") || words[i].equals("applications"))
                words[i] = words[i].substring(0, words[i].length()-1);
        }
        boolean flag = true;
        for(String word: words)
            flag = flag && p.getPageIndex().containsWord(word);
        return flag;
    }

    public boolean containsAnyWord(PageEntry p, String[] words) {
        for(int i = 0; i<words.length; i++) {
            words[i] = words[i].toLowerCase();
            if(words[i].equals("structures") || words[i].equals("stacks") || words[i].equals("applications"))
                words[i] = words[i].substring(0, words[i].length()-1);
        }
        for(String word: words)
            if(p.getPageIndex().containsWord(word))
                return true;
        return false;
    }

    public float getRelevance_Word(PageEntry p, String word) {
        float termFrequency = p.wordFrequency(word);
        float inverseDocumentFrequency = index.inverseDocumentFrequency(word);
        return termFrequency*inverseDocumentFrequency;
    }

    public float getRelevance_Phrase(PageEntry p, String[] phrase) {
        float termFrequency = p.phraseFrequency(phrase);
        float inverseDocumentFrequency = index.inverseDocumentFrequency(phrase);
        return termFrequency*inverseDocumentFrequency;
    }

    public float getRelevanceOfPage(String[] str, boolean doTheseWordsRepresentAPhrase, PageEntry p) {
        if(doTheseWordsRepresentAPhrase) {
            return getRelevance_Phrase(p, str);
        }
        else {
            float relevance = 0;
            for(String word : str)
                relevance += (getRelevance_Word(p, word));
            return relevance;
        }
    }

    public String performAction(String action) {
        Scanner in = new Scanner(action);
        String actionTask = in.next();
        if(actionTask.equals("addPage")) {
            try {
                String page = in.next();
                addPage(page);
            }
            catch(Exception e) {
                in.close();
                return "No such webpage!";
            }
        }
        else if(actionTask.equals("queryFindPagesWhichContainWord")) {
            try {
                String word = in.next().toLowerCase();
                if(word.equals("structures") || word.equals("stacks") || word.equals("applications"))
                    word = word.substring(0, word.length()-1);
                    in.close();
                    MySet<SearchResult> pages = new MySet<>();
                    Iterator<PageEntry> it = pagesAdded.getSetList().iterator();
                    while(it.hasNext()) {
                        PageEntry temp = it.next();
                        if(temp.getPageIndex().containsWord(word)) {
                            pages.addElement(new SearchResult(temp, getRelevance_Word(temp, word)));
                        }
                    }
                    if(pages.IsEmpty())
                        throw new NullPointerException("No webpage contains the word "+word);
                    Iterator<SearchResult> it2 = MySort.sortThisList(pages).iterator();
                    String s = "";
                    while(it2.hasNext())
                    // s = s+", "+it2.next().toString();
                   s = s+", "+it2.next().getPageEntry().getPageName();
                   return String.format("%s", s.substring(2, s.length()));
            }
            catch(NoSuchElementException e) {
                in.close();
                return String.format("Incomplete Arguments");
            }
            catch(Exception e) {
                in.close();
                return ""+e.getMessage();
            }
        }
        else if(actionTask.equals("queryFindPositionsOfWordInAPage")) {
            try {
                String word = in.next().toLowerCase();
                if(word.equals("structures") || word.equals("stacks") || word.equals("applications"))
                    word = word.substring(0, word.length()-1);
                String page = in.next();
                Iterator<PageEntry> it = pagesAdded.getSetList().iterator();
                while(it.hasNext()) {
                    PageEntry temp = it.next();
                    if(temp.getPageName().equals(page)) {
                        in.close();
                        return temp.positionsOfWord(word);
                    }
                }
                in.close();
                throw new IllegalArgumentException("No webpage "+page+" found");
            }
            catch(NoSuchElementException e) {
                in.close();
                return String.format("Incomplete Arguments");
            }
            catch(Exception e) {
                return String.format("%s", e.getMessage());
            }
        }
        else if(actionTask.equals("queryFindPagesWhichContainAllWords")) {
            try {
                String[] words = in.nextLine().trim().split(" ");
                in.close();
                MySet<SearchResult> pages = new MySet<>();
                Iterator<PageEntry> it = pagesAdded.getSetList().iterator();
                while(it.hasNext()) {
                    PageEntry temp = it.next();
                    if(containsAllWords(temp, words))
                        pages.addElement(new SearchResult(temp, getRelevanceOfPage(words, false, temp)));
                }
                if(pages.IsEmpty())
                    throw new NullPointerException("No webpage contains all of these words.");
                Iterator<SearchResult> it2 = MySort.sortThisList(pages).iterator();
                String s = "";
                while(it2.hasNext())
                    s = s+", "+it2.next().getPageEntry().getPageName();
                return String.format("%s", s.substring(2, s.length()));
            }
            catch(NoSuchElementException e) {
                in.close();
                return String.format("Incomplete Arguments");
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else if(actionTask.equals("queryFindPagesWhichContainAnyOfTheseWords")) {
            try {
                String[] words = in.nextLine().trim().split(" ");
                in.close();
                MySet<SearchResult> pages = new MySet<>();
                Iterator<PageEntry> it = pagesAdded.getSetList().iterator();
                while(it.hasNext()) {
                    PageEntry temp = it.next();
                    if(containsAnyWord(temp, words))
                        pages.addElement(new SearchResult(temp, getRelevanceOfPage(words, false, temp)));
                }
                if(pages.IsEmpty())
                    throw new NullPointerException("No webpage contains any of these words.");
                Iterator<SearchResult> it2 = MySort.sortThisList(pages).iterator();
                String s = "";
                while(it2.hasNext())
                    //  s = s+", "+it2.next().toString();
                    s = s+", "+it2.next().getPageEntry().getPageName();
                return String.format("%s", s.substring(2, s.length()));
            }
            catch(NoSuchElementException e) {
                in.close();
                return String.format("Incomplete Arguments");
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else if(actionTask.equals("queryFindPagesWhichContainPhrase")) {
            try {
                String[] phrase = in.nextLine().trim().split(" ");
                in.close();
                MySet<SearchResult> pages = new MySet<>();
                Iterator<PageEntry> it = pagesAdded.getSetList().iterator();
                while(it.hasNext()) {
                    PageEntry temp = it.next();
                    if(temp.containsPhrase(phrase)) {
                        pages.addElement(new SearchResult(temp, getRelevanceOfPage(phrase, true, temp)));
                    }
                }
                if(pages.IsEmpty())
                    throw new NullPointerException("No webpage contains this phrase");
                Iterator<SearchResult> it2 = MySort.sortThisList(pages).iterator();
                String s = "";
                while(it2.hasNext())
                // s = s+", "+it2.next().toString();
               s = s+", "+it2.next().getPageEntry().getPageName();
               return String.format("%s", s.substring(2, s.length()));
            }
            catch(NoSuchElementException e) {
                in.close();
                return String.format("Incomplete Arguments");
            }
            catch(Exception e) {
                return e.getMessage();
            }
        }
        else {
            in.close();
            return String.format("No Such Action");
        }
        in.close();
        return "";
    }

    public void printInvertedIndex() {
        index.print();
    }

}
