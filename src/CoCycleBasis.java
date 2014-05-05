
import graph.CoCycle;
import graph.Graph;
import io.readGraph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class CoCycleBasis {
    public static void main(String[] args) {
        Graph G = readGraph.readAdjacencyListGraph("testgraph_adjacency.txt");
        
        int[][] base = CoCycle.calculateCoCycleBase(G);
        
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[0].length; j++) {
                System.out.print(base[i][j] + "\t");
            }
            System.out.println("");
        }
        
        ArrayList<ArrayList<Integer>> permutations = permutations(new ArrayList<>(Arrays.asList(-1,0,1)), base.length);

        //for (ArrayList<Integer> permutation : permutations) {
        for (int i = 0; i < 2; i++) {
            System.out.println(new ArrayList<>(Arrays.asList(base[i])));
            System.out.println(permutations.get(i));
            System.out.println(linearCombination(base, permutations.get(i)));
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

}
