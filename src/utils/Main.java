package utils;

import tasks.CoCycleBasis;
import graph.Graph;
import io.readGraph;
import io.printGraph;

/**
 * Created by Seoman on 05.05.14.
 */


public class Main {
    public static void main(String[] args) {
        if (args[0].equals("2") && args[1].equals("-file") && args[3].equals("-o")) {
            Graph g = readGraph.readAdjacenceGraph(args[2]);
            Graph sf = tasks.SpanningTree.getSpanningTree(g);
            String s = printGraph.returnGraphViz(sf);
            utils.FileUtils.writeString(args[4] + "/SpanningForest.dot", s);
        }

	else if (args[0].equals("3") && args[1].equals("-file")) {
            CoCycleBasis.main(args);
	}
    }
}
