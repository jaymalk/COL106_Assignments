public class Position {
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
        return (page.equals(p.getPageEntry())) && (wordPosition == p.getWordIndex());
    }

    @Override
    public String toString() {
        return String.format("("+page.getPageName()+", "+wordPosition+")");
    }
}
