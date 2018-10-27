import java.util.*;

public class MySort {
    public static ArrayList<SearchResult> sortThisList(MySet<SearchResult> listOfSortableEntries) {
        ArrayList<SearchResult> results = new ArrayList<>();
        Iterator<SearchResult> it = listOfSortableEntries.getSetList().iterator();
        while(it.hasNext())
            results.add(it.next());
        results.sort((s1, s2) -> s2.compareTo(s1));
        return results;
    }
}
