public class Position implements Comparable<Position> {
    private PageEntry page;
    private int wordPosition;

    public Position(PageEntry p, int wordIndex) {
        this.page = p;
        this.wordPosition = wordIndex;
    }

    public PageEntry getPageEntry() {
        return this.page;
    }

    public int getWordIndex() {
        return this.wordPosition;
    }

    public boolean equals(Position p) {
        return (wordPosition == p.getWordIndex());
    }

    @Override
    public int compareTo(Position p) {
        if(!p.getPageEntry().equals(getPageEntry()))
            throw new IllegalArgumentException("Entries are from different webpages.");
        Integer selfVal = Integer.valueOf(getWordIndex());
        Integer pVal = Integer.valueOf(p.getWordIndex());
        return selfVal.compareTo(pVal);
    }

    @Override
    public String toString() {
        return String.format("("+page.getPageName()+", "+wordPosition+")");
    }
}
