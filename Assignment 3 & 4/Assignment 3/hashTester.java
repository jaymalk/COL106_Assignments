import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class hashTester {
    public static void main(String[] args) {
        try {
            FileInputStream fread = new FileInputStream("words.txt");
            Scanner reader = new Scanner(fread);
            int k = Integer.parseInt(args[0]);
            int[] distribution = new int[k];
            while(reader.hasNext())
                // distribution[hashVal(reader.next().toLowerCase().toCharArray(), k)]++;
                // distribution[myHash(reader.next().toLowerCase().toCharArray(), k)]++;
                distribution[Math.abs(reader.next().toLowerCase().hashCode()%k)]++;
            for(int i=0; i<distribution.length; i++)
                System.out.println(i+" "+distribution[i]);
            reader.close();
        }
        catch(FileNotFoundException e) {
        }
    }

    static int hashVal(char[] charArray, int k) {
        int h=0;
        for (char e: charArray) {
            h = (h << 10) | (h >>> 22); 
            h += (int) e;
        }
        return Math.abs(h%k);
    } 

    static int myHash(char[] charArray, int k) {
        int sum = 65535;
        for(char e: charArray)
            sum = (sum << 7) ^ (e << 9) | (sum >>> 25);
        return Math.abs((int)(sum)%k);
    }
}