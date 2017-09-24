


package runtest;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;

import graph.CapGraph;

import org.graphstream.*;

import util.GraphLoader;


public class Runtest {
	public static void main(String args[]) {
		//System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		CapGraph graph = new CapGraph("The Graph");
		String stylesheet = "node { size: 5px; fill-color: rgb(50,50,150); text-size:20;}" +
							"edge { fill-color: black; size: 1px; }" +						
							"graph{fill-color:rgb(241,241,241); padding:100px; }";
		graph.addAttribute("ui.stylesheet", stylesheet);
		
		GraphLoader.loadGraph(graph, "data/test_data.txt");
		
		
		graph.display();
		for (int i =0 ; i<40; i++) {
			graph.removeEdge(graph.computeBet());
			
		}
		
	}
	
}
