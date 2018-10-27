import java.util.*;
import java.io.FileInputStream;

public class PageEntry {

    private PageIndex pageIndex;
    private String pageName;
    // private Vector<String> wordOrdered;

    public PageEntry(String pageName) {
        try {
            this.pageName = pageName;
            List<String> connectorWords =   Arrays.asList("a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or", "and", "does", "will", "whose");
            String[] punctuations = {"{", "}", "[", "]", "<", ">", "=", "(", ")", ".", ",", ";", "’", "'", "”", "?", "#", "!", "-", ":", "\""};
            pageIndex = new PageIndex();
            // wordOrdered = new Vector<>();
            FileInputStream file = new FileInputStream(pageName);
            Scanner s = new Scanner(file);
            try {
                int pos = 0, ncpos = 0;
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
                        else {
                            ncpos++;
                            // wordOrdered.add(word);
                            pageIndex.addPositionForWord(word, new Position(this, pos));
                            pageIndex.addDummyPosition(word, new Position(this, ncpos));
                        }
                    }
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
        MyLinkedList<WordEntryUsingAVL> list = getPageIndex().getWordEntries();
        if(!list.isMember(new WordEntryUsingAVL(word)))
            throw new NullPointerException("Webpage "+pageName+" does not contain word "+word);
        Iterator<WordEntryUsingAVL> it = list.iterator();
        while(it.hasNext()) {
            WordEntryUsingAVL temp  = it.next();
            if(temp.equals(new WordEntryUsingAVL(word))) {
                AVLTree pos = temp.getAllPositionsForThisWord();
                Iterator<Position> it2 = pos.iterator();
                while(it2.hasNext()) {
                    Position temp2 = it2.next();
                    if(temp2.getPageEntry().getPageName().equals(pageName))
                        s = s+", "+temp2.getWordIndex();
                }
            }
        }
        if(s.equals(""))
            throw new Exception("Webpage "+pageName+" does not contain word "+word);
        return s.substring(2, s.length());
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
    	return pageIndex.getTotalNonConnectorWords();
    }


    public float wordFrequency(String word) {
        Iterator<WordEntryUsingAVL> it = pageIndex.getWordEntries().iterator();
        while(it.hasNext()) {
            WordEntryUsingAVL temp = it.next();
            if(temp.getWord().equals(word)) {
                float wordOccurence = temp.getAllPositionsForThisWord().totalNodes();
                return wordOccurence/(float)getTotalNumberWords();
            }
        }
        return 0;
    }

    public float phraseFrequency(String[] phrase) {
        float count = getPhraseCount(phrase);
        return (count)/(getTotalNumberWords()-count*(phrase.length-1));
    }

    // public boolean containsPhrase(String[] phrase) {
    //     for(int i = 0; i<phrase.length; i++) {
    //         phrase[i] = phrase[i].toLowerCase();
    //         if(phrase[i].equals("structures") || phrase[i].equals("stacks") || phrase[i].equals("applications"))
    //             phrase[i] = phrase[i].substring(0, phrase[i].length()-1);
    //     }
    //     int k = wordOrdered.indexOf(phrase[0]);
    //     if(k == -1)
    //         return false;
    //     do {
    //         int p = 0;
    //         while(k<wordOrdered.size() && k>=0) {
    //             if(p>=phrase.length)
    //                 return true;
    //             if(!phrase[p].equals(wordOrdered.get(k++))) {
    //                 k = wordOrdered.indexOf(phrase[0], k);
    //                 break;
    //             }
    //             p++;
    //         }
    //     } while(wordOrdered.size() > k && k >= 0);
    //     return false;
    // }

    public boolean containsPhrase(String[] phrase) {
        return pageIndex.containsPhrase(phrase);
    }

    // public int getPhraseCount(String[] phrase) {
    //     for(int i = 0; i<phrase.length; i++) {
    //         phrase[i] = phrase[i].toLowerCase();
    //         if(phrase[i].equals("structures") || phrase[i].equals("stacks") || phrase[i].equals("applications"))
    //             phrase[i] = phrase[i].substring(0, phrase[i].length()-1);
    //     }
    //     int k = wordOrdered.indexOf(phrase[0]), count = 0;
    //     do {
    //         int p = 0;
    //         while(k<wordOrdered.size() && k>=0) {
    //             if(p>=phrase.length) {
    //                 count++;
    //                 k = wordOrdered.indexOf(phrase[0], k);
    //                 break;
    //             }
    //             if(!phrase[p].equals(wordOrdered.get(k++))) {
    //                 k = wordOrdered.indexOf(phrase[0], k);
    //                 break;
    //             }
    //             p++;
    //         }
    //     } while(wordOrdered.lastIndexOf(phrase[0]) >= k && k>=0);
    //     return count;
    // }

    public int getPhraseCount(String[] phrase) {
        return pageIndex.phraseCount(phrase);
    }

    // public Vector<String> getOrderedPage() {
    //     return wordOrdered;
    // }

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

    @Override
    public int hashCode() {
        return getPageName().hashCode();
    }
}
