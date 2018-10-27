import java.util.Comparator;

public class SearchResult implements Comparable<SearchResult> {
    private PageEntry page;
    private float relevance;

    public SearchResult(PageEntry page, float relevance) {
        this.page = page;
        this.relevance = relevance;
    }

    public PageEntry getPageEntry() {
        return this.page;
    }

    public float getRelevance() {
        return this.relevance;
    }

    public int compareTo(SearchResult otherObject) {
        if(page.equals(otherObject.getPageEntry()))
            return 0;
        return Double.compare(relevance, otherObject.getRelevance());
    }

    @Override
    public String toString() {
        return String.format("("+page.getPageName()+", "+relevance+")");
    }
}
