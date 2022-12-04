import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class CosineSimilarity {
    // add the unique words in container
    public static HashMap<String, Double> SortedValue = new HashMap<>();

    static HashSet<String> buildHashSet(String[] Files) {
        HashSet<String> Uniqueterms = new HashSet<String>();
        for (String file : Files) {
            BufferedReader inReader = null;
            try {
                inReader = new BufferedReader(new FileReader(file));
                String read = null;
                while ((read = inReader.readLine()) != null) {
                    String[] spliter = read.split("\\W+");
                    for (String part : spliter) {
                        Uniqueterms.add(part);
                    }
                }
            } catch (IOException e) {
                System.out.println("There was a problem: " + e);
                e.printStackTrace();
            } finally {
                try {
                    inReader.close();
                } catch (Exception e) {
                }
            }
        }
        return Uniqueterms;
    }

    static ArrayList<String> buildArrayList(String file) {
        ArrayList<String> termList = new ArrayList<String>();
        BufferedReader inReader = null;
        try {
            inReader = new BufferedReader(new FileReader(file));
            String read = null;
            while ((read = inReader.readLine()) != null) {
                String[] spliter = read.split("\\W+");
                for (String part : spliter) {
                    termList.add(part);
                }
            }
        } catch (IOException e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        } finally {
            try {
                inReader.close();
            } catch (Exception e) {
            }
        }
        return termList;
    }

    static ArrayList<Integer> buildCosineVector(HashSet<String> UniqTerms, ArrayList<String> listTerms) {
        ArrayList<Integer> NewList = new ArrayList<Integer>();
        Iterator<String> it = UniqTerms.iterator();
        while (it.hasNext()) {
            String term = (String) it.next();
            NewList.add(Collections.frequency(listTerms, term));
        }
        return NewList;
    }

    // calculation
    static double cosineSimilarity(ArrayList<Integer> doc1, ArrayList<Integer> doc2, String Doc1, String Doc2) {
        double similarity = 0;
        int D1D2 = 0;
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < doc1.size(); i++) {
            D1D2 += (doc1.get(i) * doc2.get(i));
            sum1 += doc1.get(i) * doc1.get(i);
            sum2 += doc2.get(i) * doc2.get(i);
        }
        similarity = D1D2 / (Math.sqrt(sum1) * Math.sqrt(sum2));
        String temp = doc1 + " " + doc2 + " = ";
        SortedValue.put(temp, similarity);
        return similarity;
    }

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(
                hm.entrySet());

        // Sort the list using lambda expression
        Collections.sort(
                list, (i2, i1) -> i1.getValue().compareTo(i2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void main(String[] args) throws Exception {
        String[] Files = new String[] { "100.txt",
                "101.txt", "103.txt", "102.txt" };
        HashSet<String> s = buildHashSet(Files);
        ArrayList<String> List100 = buildArrayList("100.txt");
        ArrayList<String> List101 = buildArrayList("101.txt");
        ArrayList<String> List102 = buildArrayList("102.txt");
        ArrayList<String> List103 = buildArrayList("103.txt");

        ArrayList<Integer> d100 = buildCosineVector(s, List100);
        ArrayList<Integer> d101 = buildCosineVector(s, List101);
        ArrayList<Integer> d102 = buildCosineVector(s, List102);
        ArrayList<Integer> d103 = buildCosineVector(s, List103);

        cosineSimilarity(d100, d101, "100.txt", "101.txt");
        cosineSimilarity(d100, d102, "100.txt", "102.txt");
        cosineSimilarity(d100, d103, "100.txt", "103.txt");
        cosineSimilarity(d102, d103, "102.txt", "103.txt");
        System.out.println(sortByValue(SortedValue));

    }
}