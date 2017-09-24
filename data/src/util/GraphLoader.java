/**
 * @author UCSD MOOC development team
 * 
 * Utility class to add vertices and edges to a graph
 *
 */
package util;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.graphstream.graph.Graph;

import graph.CapGraph;

public class GraphLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
    public static void loadGraph(CapGraph g, String filename) {
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            Integer v1 = sc.nextInt();
            Integer v2 = sc.nextInt();
            Integer v3;
            if (!seen.contains(v1)) {
                g.addNode(v1.toString());
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                g.addNode(v2.toString());
                seen.add(v2);
            }
            if (v1>v2) {v3 = v1; v1 = v2; v2 = v3;}
            if (g.getEdge(v1.toString()+'~' + v2.toString()) == null)
            g.addEdge(v1.toString(), v2.toString());
        }
        
        sc.close();
    }
}
