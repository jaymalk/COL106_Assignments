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
                String s = "";
                Iterator<PageEntry> it = index.getPagesWhichContainWord(word).getSetList().iterator();
                while(it.hasNext())
                    s = it.next().getPageName()+", "+s;
                in.close();
                return String.format("%s", s.substring(0, s.length()-2));
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
