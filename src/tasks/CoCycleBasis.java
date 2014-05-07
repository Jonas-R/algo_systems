package tasks;


import graph.CoCycle;
import graph.Graph;
import io.readGraph;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CoCycleBasis {
    public static void main(String[] args) {
        Graph G = readGraph.readAdjacencyListGraph(args[2]);
        
        CoCycle cc = new CoCycle(G);
        
        int[][] base = cc.calculateCoCycleBase(G);
        
        for (int i = 0; i < base.length - 1; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < base[i].length; j++) {
                tmp.add(base[i][j]);
            }
            System.out.println("Basisvektor " + (i+1) + ":");
            System.out.println(asAdjacencyList(tmp, cc));
            System.out.println("");
        }
        
        ArrayList<ArrayList<Integer>> permutations = permutations(new ArrayList<>(Arrays.asList(-1,0,1)), base.length - 1);
        
        Set<ArrayList<Integer>> cocycles = new HashSet<>();
        for (ArrayList<Integer> permutation : permutations) {
            ArrayList<Integer> cycle = linearCombination(base, permutation);
            boolean iscycle = true;
            for (int i = 0; i < cycle.size(); i++) {
                int val = cycle.get(i);
                if (val != 0 && val != 1 && val != -1) {
                    iscycle = false;
                }
            }
            if (iscycle) {
                    //System.out.println();
                    System.out.println(asAdjacencyList(cycle, cc));
            }
        }
    }
    
    public static ArrayList<Integer> linearCombination(int[][] vectors, ArrayList<Integer> coefficients) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 0; i < Math.min(coefficients.size(), vectors.length); i++) {
            ArrayList<Integer> mult_vector = new ArrayList<>();
            for (int n : vectors[i]) {
                mult_vector.add(n * coefficients.get(i));
            }
            result.add(mult_vector);
        }
        return vectorSum(result);
    }
    
    public static ArrayList<Integer> vectorSum(ArrayList<ArrayList<Integer>> vectors) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i : vectors.get(0)) {
            result.add(0);
        }
        
        for (ArrayList<Integer> vector : vectors) {
            for (int i = 0; i < vector.size(); i++) {
                result.set(i, result.get(i) + vector.get(i));
            }
        }
        return result;
    }
    
    public static ArrayList<ArrayList<Integer>> permutations(ArrayList<Integer> input, int k) {
        ArrayList<ArrayList<Integer>> init = new ArrayList<>();
        for (int i : input) {
            init.add(new ArrayList<>(Arrays.asList(i)));
        }
        return permutations_helper(init, input, k);
    }
    
    public static ArrayList<ArrayList<Integer>> permutations_helper(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> input, int k) {
        if (k == 1) {
            return result;
        } else {
            ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
            for (ArrayList<Integer> r : result) {
                for (Integer i : input) {
                    ArrayList<Integer> tmp2 = new ArrayList<>(r);
                    tmp2.add(i);
                    tmp.add(tmp2);
                }
            }
            return permutations_helper(tmp, input, k - 1);
        }
    }
    
    public static String asAdjacencyList(ArrayList<Integer> vector, CoCycle cc) {
        SortedMap<String, ArrayList<String>> adjacency = new TreeMap<>();
        
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) == 1 || vector.get(i) == -1) {
                SimpleEntry<String, String> edge = cc.edgeNumbers.get(i);
                if (!adjacency.containsKey(edge.getKey())) {
                    adjacency.put(edge.getKey(), new ArrayList<String>());
                }
                adjacency.get(edge.getKey()).add(edge.getValue());
            }
        }
 
        StringBuilder sb = new StringBuilder();
        for(String key : adjacency.keySet()) {
            sb.append(key).append(" -> ");
            String delim = "";
            for (String edge : adjacency.get(key)) {
                sb.append(delim).append(edge);
                delim = ", ";
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }

}
