package graph;

import java.util.HashSet;
import java.util.Set;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.MultiNode;

public class CapNode extends MultiNode{
	private int index;
	private Set<CapNode> edges = new HashSet<CapNode>();
	
	protected CapNode(AbstractGraph graph, String id) {
		super(graph, id);
		
	}
	
	public int getIndex() {
		return index;
	}
	
	public void addEdges(CapNode end) {
		if (!edges.contains(end)) edges.add(end);
	}
	
	public HashSet<String> getEndVertices(){
		HashSet<String> res = new HashSet<String>();
		for (CapNode n : edges) res.add(n.getId());
		return res;
	}
	
}
